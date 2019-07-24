
package com.itmayiedu.demo1;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class Test005 {

	public static void main(String[] args) throws CharacterCodingException {
		// 获取编码器
		Charset charset = Charset.forName("GBK");
		//获取加密器
		CharsetEncoder ce = charset.newEncoder();
		//获取解码器
		CharsetDecoder cd=	charset.newDecoder();
		CharBuffer  charBuffer= CharBuffer.allocate(1024);
		charBuffer.put("蚂蚁课堂牛逼..");
		charBuffer.flip();
		//编码加密
		ByteBuffer buBuff = ce.encode(charBuffer);
		for (int i = 0; i <12; i++) {
			System.out.println(buBuff.get());
		}
 
		buBuff.flip();
		//编码解密
//		CharBuffer decode = cd.decode(buBuff);
		Charset c2 = Charset.forName("GBK");
		CharBuffer decode2 = c2.newDecoder().decode(buBuff);
		System.out.println(decode2.toString());
	}

}
