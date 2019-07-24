
package com.itmayiedu.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
//nio客户端
class NioCliet {
	public static void main(String[] args) throws IOException {
		System.out.println("客户端已经被启动.....");
		// 1.创建socket通道
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
		// 2.切换异步非阻塞
		sChannel.configureBlocking(false);// jdk1.7以上
		// 3.指定缓冲区大小
		ByteBuffer buff = ByteBuffer.allocate(1024);
		buff.put(new Date().toString().getBytes());
		// 4.切换到读取模式
		buff.flip();
		sChannel.write(buff);
		buff.clear();
		// 5.关闭通道
		sChannel.close();
	}
}
// nio服务器端
class NioServer {
	public static void main(String[] args) throws IOException {
		System.out.println("服务器端已经被启动...");
		// 1.创建服务通道
		ServerSocketChannel sChannel = ServerSocketChannel.open();
		// 2.切换异步非阻塞
		sChannel.configureBlocking(false);// jdk1.7以上
		// 3.绑定连接
		sChannel.bind(new InetSocketAddress(8080));
		// 4.获取选择器
		Selector selector = Selector.open();
		// 5.将通道注册到选择器中" 并且监听已经接受到事件"
		sChannel.register(selector, SelectionKey.OP_ACCEPT);
		// 6.轮训获取"已经准备就绪的"事件
		while (selector.select() > 0) {
			// 7.获取当前选择器 有注册已经监听到事件
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while (it.hasNext()) {
				// 8.获取准备就绪事件
				SelectionKey sk = it.next();
				// 9.判断事件准备就绪
				if (sk.isAcceptable()) {
					// 10. “若” 接受就绪，获取客户端连接
					SocketChannel socketChannel = sChannel.accept();
					// 11. 设置为阻塞费事
					socketChannel.configureBlocking(false);// 异步非阻塞IO
					// 12.将该通道注册到服务器上
					socketChannel.register(selector, SelectionKey.OP_READ);
				}else if(sk.isReadable()){
					//13.获取当前选择“就绪状态”的通道
					SocketChannel socketChannel=	(SocketChannel) sk.channel();
					//14.读取数据
					int len=0;
					ByteBuffer byteBuffer=	ByteBuffer.allocate(1024);
					while ((len=socketChannel.read(byteBuffer))>0) {
						byteBuffer.flip();
						System.out.println(new String(byteBuffer.array(),0,len));
						byteBuffer.clear();
						
					}
				}
				it.remove();

			}
		}

	}

}
