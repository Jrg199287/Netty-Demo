
package com.itmayiedu;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
//udp服务器端
class UdpServer{
	 public static void main(String[] args) throws IOException {
		//1.ip地址+端口号
		 System.out.println("udp服务器已经启动... 8080");
		 //创建服务器端端口号  默认使用本机Ip地址
		 DatagramSocket ds = new DatagramSocket(8080);
		 //服务器接受客户端1024个字节
		 byte[] bytes= new byte[1024];
		 //定义数据包
		 DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
		 //接受客户端请求，将数据封装给数据包  如果客户端不往服务器端发送请求，就一直阻塞。
		 ds.receive(dp);
		 System.out.println("来源IP地址:"+dp.getAddress()+",端口号"+dp.getPort());
		 String result=	 new String(dp.getData(),0,dp.getLength());
		 System.out.println("服务器端接受客户端内容:"+result);
		 ds.close();
	}
}
//先写服务器端 在写客户端
public class UdpClinet {
	public static void main(String[] args) throws IOException {
		 System.out.println("udp客户端启动连接...");
		 //不传入端口号 作用客户端  创建一个socket客户端
		 DatagramSocket ds = new DatagramSocket();
		 String str="蚂蚁课堂";
		 byte[] strBytes= str.getBytes();
		 DatagramPacket dp = new DatagramPacket(strBytes, strBytes.length,InetAddress.getByName("127.0.0.1"),8080);
		 ds.send(dp);
		 ds.close();


	}
	
	
}
