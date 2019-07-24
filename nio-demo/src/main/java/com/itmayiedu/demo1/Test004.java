
package com.itmayiedu.demo1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test004 {
	public static void main(String[] args) throws IOException {
		//随机访问
		RandomAccessFile raf= new RandomAccessFile("test.txt", "rw");

		//获取通道
		FileChannel channel = raf.getChannel();
		//分配指定大小指定缓冲区
		ByteBuffer buf1=ByteBuffer.allocateDirect(100);
		ByteBuffer buf2=ByteBuffer.allocateDirect(1024);
		// 分散读取
		ByteBuffer[] bufs={buf1,buf2};
		channel.read(bufs);
		for (ByteBuffer byteBuffer : bufs) {
			// 切换成读模式
			byteBuffer.flip();
		}
		System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
		System.out.println("*************************************");
		System.out.println(new String(bufs[1].array(),1,bufs[1].limit()));
		System.out.println("------聚集读取---------");
		RandomAccessFile raf2= new RandomAccessFile("test2.txt", "rw");
		//获取通道
		FileChannel channel2 = raf2.getChannel();
		channel2.write(bufs);
		raf2.close();
		raf.close();
		
	}
}
