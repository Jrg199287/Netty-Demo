
package com.itmayiedu.demo2;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.internal.logging.InternalLoggerFactory;
class ServerHandler extends ChannelHandlerAdapter {
	// 当通道被调用,执行该方法
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 接收数据
		String value = (String) msg;System.out.println("Server msg:" + value);
		// 回复给客户端 “您好!”
		String res = "好的..._mayi";ctx.writeAndFlush(Unpooled.copiedBuffer(res.getBytes()));
	}
}
public class NettyServer {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("服务器端已经启动....");
		// 1.创建2个线程,一个负责接收客户端连接， 一个负责进行 传输数据
		NioEventLoopGroup pGroup = new NioEventLoopGroup();
		NioEventLoopGroup cGroup = new NioEventLoopGroup();
		// 2. 创建服务器辅助类
		ServerBootstrap b = new ServerBootstrap();
		b.group(pGroup, cGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
				// 3.设置缓冲区与发送区大小
				.option(ChannelOption.SO_SNDBUF, 32 * 1024).option(ChannelOption.SO_RCVBUF, 32 * 1024)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						//--设置截取长度的方式防止占包--一般不建议采用
						//sc.pipeline().addLast(new FixedLengthFrameDecoder(10));//通过后缀分隔的方式防止占包
						ByteBuf buf = Unpooled.copiedBuffer("_mayi".getBytes());
						sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
						sc.pipeline().addLast(new StringDecoder());
						sc.pipeline().addLast(new ServerHandler());
					}
				});
		ChannelFuture cf = b.bind(8080).sync();
		cf.channel().closeFuture().sync();
		pGroup.shutdownGracefully();
		cGroup.shutdownGracefully();
	
	}

}
