
package com.itmayiedu.demo1;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * 
 * 缓冲区是NIO 提高给传输文件和通道一起配合使用,存储数据.<br>
 * Buffer<br>
 * ByteBuffer<br>
 * LongBuffer<br>
 * InteigBuffer<br>
 * FloatBuffer<br>
 * DubboBuffer<br>
 * 
 * @website:www.itmayiedu.com或者www.meiteedu.com
 * @weixin:官方微信号 每特学院
 * @QQ:644064779 QQ群:116295598
 */
public class BuffTest {
	/**
	 * position<br> 缓冲区正在操作的位置 默认从0开始。
	 * limit<br>  界面(缓冲区可用大小)
	 * capacity;<br> 缓冲区最大容量，一旦声明不能改变
	 * 
	 * 核心方法:
	 * put() 往buff存放数据
	 * get() 获取数据
	 * 
	 *  */
	@Test
	public void test001() {
		try {
			// 初始化byteBuffer大小
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		    System.out.println(byteBuffer.position());
		    System.out.println(byteBuffer.limit());
		    System.out.println(byteBuffer.capacity());
		    System.out.println("----------往bytebuff存放数据....----------");
		    System.out.println("");
		    byteBuffer.put("abcd1".getBytes());
		    System.out.println(byteBuffer.position());
		    System.out.println(byteBuffer.limit());
		    System.out.println(byteBuffer.capacity());
		    System.out.println("----------读取值...----------");
		    //开启读取模式
		    byteBuffer.flip();
		    System.out.println("position:"+byteBuffer.position());
		    System.out.println(byteBuffer.limit());
		    System.out.println(byteBuffer.capacity());
		    byte[] bytes=  new byte[byteBuffer.limit()];
		    byteBuffer.get(bytes);
		    System.out.println(new String(bytes,0,bytes.length));
		    
		    System.out.println("----------重复读取----------");
		    byteBuffer.rewind();//重复读取
		    System.out.println("position:"+byteBuffer.position());
		    System.out.println(byteBuffer.limit());
		    System.out.println(byteBuffer.capacity());
		    byte[] bytes2= new byte[byteBuffer.limit()];
		    byteBuffer.get(bytes2);
		    System.out.println(new String(bytes2,0,bytes2.length));
		    //清空缓冲数据被遗忘值...仅仅只是数据的指针被清空了但是数据还是有的
		    System.out.println("----------清空缓冲区--------------");
		    byteBuffer.clear();
		    System.out.println("position:"+byteBuffer.position());
		    System.out.println(byteBuffer.limit());
		    System.out.println(byteBuffer.capacity());
		    System.out.println((char)byteBuffer.get());
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test002() {

	}

}
