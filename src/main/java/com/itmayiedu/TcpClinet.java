
package com.itmayiedu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

class TcpServer {
	public static void main(String[] args) throws IOException {
		System.out.println("tcp协议服务器端启动..");
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		// 创建服务器端连接
		ServerSocket serverSocket = new ServerSocket(8080);
		try {
//		    while (true) {
				// 接受客户端请求 阻塞功能
				final Socket accept = serverSocket.accept();
				newCachedThreadPool.execute(new Runnable(){
					@Override
					public void run() {
					try {
						InputStream inputStream = accept.getInputStream();
						// 将字节流转换成String类型
						byte[] bytes = new byte[1024];
						int len = inputStream.read(bytes);
						String result=new String(bytes,0,len);
						System.out.println("服务器端接受客户端内容:"+result);
						OutputStream outputStream = accept.getOutputStream();
						outputStream.write("this is yes itmayiedu.com".getBytes());
					} catch (Exception e) {
						// TODO: handle exception
					}
					}
				});
//			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			serverSocket.close();
		}
	}

}

public class TcpClinet {
	  public static void main(String[] args) throws IOException {
		System.out.println("socket tcp客户端启动....");
		//创建socket客户端
		Socket socket=new Socket("127.0.0.1",8080);
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write("我是蚂蚁课堂忠实粉丝....".getBytes());
		socket.close();
	}
	
}
