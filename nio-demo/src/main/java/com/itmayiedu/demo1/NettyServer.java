
package com.itmayiedu.demo1;

import java.net.InetSocketAddress;
import java.util.concurrent.*;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

class ServerHanlder extends SimpleChannelHandler {
	// 通道被关闭的时候会触发
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelClosed(ctx, e);
		System.out.println("channelClosed");
	}
	// 必须要建立连接，关闭通道的时候才会触发
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelDisconnected(ctx, e);
		System.out.println("channelDisconnected");
	}
	// 接受出现异常
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		super.exceptionCaught(ctx, e);
		System.out.println("exceptionCaught");
	}
	// 接受客户端数据..
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		super.messageReceived(ctx, e);
		System.out.println("messageReceived");
		System.out.println("服务器获取客户端发来的参数:" + e.getMessage());
		ctx.getChannel().write("你好啊!");
	}
}

// netty 服务器端
public class NettyServer {
	public static void main(String[] args) {
		// 1.创建服务对象
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		// 2.创建两个线程池 第一个 监听端口号 nio监听
		ExecutorService boos = Executors.newCachedThreadPool();
		//ExecutorService wook = Executors.newCachedThreadPool();
		ExecutorService wook = new ThreadPoolExecutor(5,100,0L, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(1024));
		// 3.将线程池放入工程
		serverBootstrap.setFactory(new NioServerSocketChannelFactory(boos, wook));
		// 4.设置管道工程
		serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			// 设置管道
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = org.jboss.netty.channel.Channels.pipeline();
				// 传输数据的时候直接为string类型
				pipeline.addLast("decoder", new StringDecoder());
				pipeline.addLast("encoder", new StringEncoder());
				// 设置事件监听类
				pipeline.addLast("serverHanlder", new ServerHanlder());
				return pipeline;
			}
		});
		// 绑定端口号
		serverBootstrap.bind(new InetSocketAddress(8080));
		System.out.println("服务器端已经被启动.....");
//		while (true) {
//			try {
//				Thread.sleep(500);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			System.out.println("每隔0.五秒打印.....");
//
//		}
	}

}
