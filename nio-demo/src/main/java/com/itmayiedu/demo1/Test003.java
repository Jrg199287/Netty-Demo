
package com.itmayiedu.demo1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

public class Test003 {

	//直接缓冲区
	@Test
	public void test002() throws IOException {
		long statTime=System.currentTimeMillis();
		//创建管道
		FileChannel   inChannel=	FileChannel.open(Paths.get("f://1.mp4"), StandardOpenOption.READ);
		FileChannel   outChannel=	FileChannel.open(Paths.get("f://2.mp4"), StandardOpenOption.READ,StandardOpenOption.WRITE, StandardOpenOption.CREATE);
	    //定义映射文件
		MappedByteBuffer inMappedByte = inChannel.map(MapMode.READ_ONLY,0, inChannel.size());
		MappedByteBuffer outMappedByte = outChannel.map(MapMode.READ_WRITE,0, inChannel.size());
		//直接对缓冲区操作
		byte[] dsf=new byte[inMappedByte.limit()];
		inMappedByte.get(dsf);
		outMappedByte.put(dsf);
		inChannel.close();
		outChannel.close();
		long endTime=System.currentTimeMillis();
		System.out.println("操作直接缓冲区耗时时间:"+(endTime-statTime));
	}

	// 非直接缓冲区 读写操作
	@Test
	public void test001() throws IOException {
		long statTime=System.currentTimeMillis();
		// 读入流
		FileInputStream fst = new FileInputStream("f://1.mp4");
		// 写入流
		FileOutputStream fos = new FileOutputStream("f://2.mp4");
		// 创建通道
		FileChannel inChannel = fst.getChannel();
		FileChannel outChannel = fos.getChannel();
		// 分配指定大小缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		while (inChannel.read(buf) != -1) {
			// 开启读取模式
			buf.flip();
			// 将数据写入到通道中
			outChannel.write(buf);
			buf.clear();
		}
		// 关闭通道 、关闭连接
		inChannel.close();
		outChannel.close();
		fos.close();
		fst.close();
		long endTime=System.currentTimeMillis();
		System.out.println("操作非直接缓冲区耗时时间:"+(endTime-statTime));
	}

}
