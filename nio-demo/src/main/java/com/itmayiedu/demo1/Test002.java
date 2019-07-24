
package com.itmayiedu.demo1;

import java.nio.ByteBuffer;

public class Test002 {

	public static void main(String[] args) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(10);
		String str = "abcd";
		byteBuffer.put(str.getBytes());
		// 开启读的模式
		byteBuffer.flip();
		byte[] bytes = new byte[byteBuffer.limit()];
		byteBuffer.get(bytes, 0, 2);
		byteBuffer.mark();  ///打印标记
		System.out.println(new String(bytes, 0, 2));
		System.out.println(byteBuffer.position());
		System.out.println("----------------------------");
		//byteBuffer.reset();//还原到mark位置
		byteBuffer.get(bytes, 2, 2);
		System.out.println(new String(bytes, 2, 2));
		byteBuffer.reset();
		System.out.println("重置还原到make标记..");
		System.out.println(byteBuffer.position());
	}

}
