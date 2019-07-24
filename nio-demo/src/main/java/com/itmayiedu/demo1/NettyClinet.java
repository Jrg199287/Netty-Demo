
package com.itmayiedu.demo1;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.*;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
class ClientHanlder extends SimpleChannelHandler{
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
		System.out.println("服务器向客户端回复的内容:" + e.getMessage());
	}
}
//netty客户端 
public class NettyClinet {
	public static void main(String[] args) {
		// 1.创建服务对象--
		ClientBootstrap clientBootstrap = new ClientBootstrap();
		// 2.创建两个线程池 第一个 监听端口号 nio监听
		ExecutorService boos = Executors.newCachedThreadPool();
		//ExecutorService wook = Executors.newCachedThreadPool();
		ExecutorService wook = new ThreadPoolExecutor(5,100,0L, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(1024));
		// 3.将线程池放入工程
		clientBootstrap.setFactory(new NioClientSocketChannelFactory(boos, wook));
		// 4.设置管道工程
		clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			// 设置管道
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = org.jboss.netty.channel.Channels.pipeline();
				// 传输数据的时候直接为string类型
				pipeline.addLast("decoder", new StringDecoder());
				pipeline.addLast("encoder", new StringEncoder());
				// 设置事件监听类
				pipeline.addLast("clientHanlder", new ClientHanlder());
				return pipeline;
			}
		});
		// 绑定端口号
		ChannelFuture connect = clientBootstrap.connect(new InetSocketAddress("127.0.0.1", 8080));
		Channel channel = connect.getChannel();
		System.out.println("client start");
		Scanner scanner = new Scanner(System.in);
        while (true) {
        	System.out.println("请输入内容:");
        	channel.write(scanner.next());
			
		}
	}
}
