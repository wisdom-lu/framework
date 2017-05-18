/*  
 * @(#)ByteBuffer.java V1.0 2013-11-18 下午04:57:10
 * @ org.framework.util
 *
 * Copyright (c) 2013, Wafersystems All rights reserved.
 * Wafer PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.framework.h4.utils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * Title: 字节缓冲类
 * </p>
 * 
 * <p>
 * Description: 用字节数组缓冲数据，提供数据的各种读写方法
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2012 Wafer (XA'AN) Systems Co., Ltd. All rights
 * reserved.
 * </p>
 * 
 * <p>
 * Company: Wafersystems
 * </p>
 * 
 * @author Mou Lu
 * @Date：2013-11-18 下午04:57:10
 * @version 1.0
 */

public final class ByteBuffer implements Serializable {
	// -------------------------------------------------------------- Constants

	// ----------------------------------------------------- Instance Variables
	/**
	 * 字节数组
	 */
	private byte[] buffer;

	// ------------------------------------------------------------ Constructor
	/**
	 * 构造函数
	 */
	public ByteBuffer() {
		buffer = null;
	}

	/**
	 * 构造函数
	 * 
	 * @param buffer
	 *            字节数组
	 */
	public ByteBuffer(byte[] buffer) {
		setBuffer(buffer);
	}

	// --------------------------------------------------------- Public Methods
	/**
	 * 添加byte数据（8位，取值范围：-128--127）
	 * 
	 * @param data
	 *            byte数据
	 */
	public void appendByte(byte data) {
		byte[] byteBuffer = new byte[1];
		byteBuffer[0] = data;
		appendBytes(byteBuffer, byteBuffer.length);
	}

	/**
	 * 添加short数据（16位，取值范围：-32768--32767）
	 * 
	 * @param data
	 *            short数据
	 */
	public void appendShort(short data) {
		byte[] shortBuffer = new byte[2];
		shortBuffer[1] = (byte) (data & 0xff);
		shortBuffer[0] = (byte) ((data >>> 8) & 0xff);
		appendBytes(shortBuffer, shortBuffer.length);
	}

	/**
	 * 添加int数据（32位，取值范围：-2147483648--2147483647）
	 * 
	 * @param data
	 *            int数据
	 */
	public void appendInt(int data) {
		byte[] intBuffer = new byte[4];
		intBuffer[3] = (byte) (data & 0xff);
		intBuffer[2] = (byte) ((data >>> 8) & 0xff);
		intBuffer[1] = (byte) ((data >>> 16) & 0xff);
		intBuffer[0] = (byte) ((data >>> 24) & 0xff);
		appendBytes(intBuffer, intBuffer.length);
	}

	/**
	 * 添加float数据（32位，取值范围：1.4013E-45--3.4028E+38 ）
	 * 
	 * @param data
	 *            float数据
	 */
	public void appendFloat(float data) {
		int intBits = Float.floatToIntBits(data);
		byte[] intBuffer = new byte[4];
		intBuffer[3] = (byte) (intBits & 0xff);
		intBuffer[2] = (byte) ((intBits >>> 8) & 0xff);
		intBuffer[1] = (byte) ((intBits >>> 16) & 0xff);
		intBuffer[0] = (byte) ((intBits >>> 24) & 0xff);
		appendBytes(intBuffer, intBuffer.length);
	}

	/**
	 * 添加固定长度的String数据
	 * 
	 * @param data
	 *            String数据
	 * @param size
	 *            固定长度
	 */
	public void appendFixedString(String data, int size) {
		if ((data == null) || (data.length() <= 0) || (size <= 0)) {
			appendZeroBytes(size);
			return;
		}

		try {
			int count = data.length() < size ? data.length() : size;
			appendString(data.substring(0, count));
			appendZeroBytes(size - data.length());
		} catch (UnsupportedEncodingException uee) {
		}
	}

	/**
	 * 添加byte[]数据
	 * 
	 * @param data
	 *            byte[]数据
	 */
	public void appendBytes(byte[] data) {
		if (data != null) {
			appendBytes(data, data.length);
		}
	}

	/**
	 * 添加byte[]数据
	 * 
	 * @param data
	 *            byte[]数据
	 * @param size
	 *            大小
	 */
	public void appendBytes(byte[] data, int size) {
		if ((data == null) || (data.length <= 0) || (size <= 0)) {
			appendZeroBytes(size);
			return;
		}

		int count = data.length < size ? data.length : size;
		int oldLength = length();
		byte[] newBuffer = new byte[oldLength + count];
		if (oldLength > 0) {
			System.arraycopy(buffer, 0, newBuffer, 0, oldLength);
		}

		System.arraycopy(data, 0, newBuffer, oldLength, count);
		this.buffer = newBuffer;
	}

	/**
	 * 取出byte数据
	 * 
	 * @exception Exception
	 *                抛出Exception异常
	 * @return byte数据
	 */
	public byte removeByte() throws Exception {
		byte result = 0;
		byte[] resBuffer = removeBytes(1);
		result = resBuffer[0];

		return result;
	}

	/**
	 * 取出short数据
	 * 
	 * @exception Exception
	 *                抛出Exception异常
	 * @return short数据
	 */
	public short removeShort() throws Exception {
		short result = 0;
		byte[] resBuffer = removeBytes(2);
		result |= resBuffer[0] & 0xff;
		result <<= 8;
		result |= resBuffer[1] & 0xff;

		return result;
	}

	/**
	 * 取出int数据
	 * 
	 * @exception Exception
	 *                抛出Exception异常
	 * @return int数据
	 */
	public int removeInt() throws Exception {
		int result = 0;
		byte[] resBuffer = removeBytes(4);
		result |= resBuffer[0] & 0xff;
		result <<= 8;
		result |= resBuffer[1] & 0xff;
		result <<= 8;
		result |= resBuffer[2] & 0xff;
		result <<= 8;
		result |= resBuffer[3] & 0xff;

		return result;
	}

	/**
	 * 取出float数据
	 * 
	 * @exception Exception
	 *                抛出Exception异常
	 * @return float数据
	 */
	public float removeFloat() throws Exception {
		int result = 0;
		byte[] resBuffer = removeBytes(4);
		result |= resBuffer[0] & 0xff;
		result <<= 8;
		result |= resBuffer[1] & 0xff;
		result <<= 8;
		result |= resBuffer[2] & 0xff;
		result <<= 8;
		result |= resBuffer[3] & 0xff;

		return Float.intBitsToFloat(result);
	}

	/**
	 * 取出固定长度的String数据
	 * 
	 * @param size
	 *            固定长度
	 * 
	 * @exception Exception
	 *                抛出Exception异常
	 * @return String数据
	 */
	public String removeFixedString(int size) throws Exception {
		String result = "";
		try {
			byte[] resBuffer = removeBytes(size);
			if (resBuffer != null) {
				result = new String(resBuffer, 0, size, "ASCII");
			}
		} catch (UnsupportedEncodingException uee) {
		}

		return result;
	}
	
	/**
	 * 取出固定长度的String数据
	 * 
	 * @param size
	 *            固定长度
	 * @param encode 
	 * 			  字符编码,ASCII,UTF-8
	 * 
	 * @exception Exception
	 *                抛出Exception异常
	 * @return String数据
	 */
	public String removeFixedString(int size,String encode) throws Exception {
		String result = "";
		try {
			byte[] resBuffer = removeBytes(size);
			if (resBuffer != null) {
				result = new String(resBuffer, 0, size, encode);
			}
		} catch (UnsupportedEncodingException uee) {
		}

		return result;
	}

	/**
	 * 取出byte[]数据
	 * 
	 * @param size
	 *            长度
	 * 
	 * @exception Exception
	 *                抛出Exception异常
	 * @return byte[]数据
	 */
	public byte[] removeBytes(int size) throws Exception {
		byte[] result = readBytes(size);
		deleteBytes(size);
		return result;
	}

	// /////////////////////////////////////////////////////////////////////////
	/**
	 * 获得缓冲区
	 * 
	 * @return 字节数组
	 */
	public byte[] getBuffer() {
		if (buffer == null) {
			return null;
		}

		byte[] newBuffer = new byte[buffer.length];
		System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);

		return newBuffer;
	}

	/**
	 * 设置缓冲区
	 * 
	 * @param buffer
	 *            字节数组
	 */
	public void setBuffer(byte[] buffer) {
		this.buffer = null;
		if (buffer == null) {
			return;
		}

		appendBytes(buffer);
	}

	/**
	 * 获得缓冲区长度
	 * 
	 * @return 缓冲区长度
	 */
	public int length() {
		if (buffer == null) {
			return 0;
		}

		return buffer.length;
	}

	// /////////////////////////////////////////////////////////////////////////
	/**
	 * 读取数据
	 * 
	 * @param size
	 *            大小
	 * 
	 * @exception Exception
	 *                抛出Exception异常
	 * @return byte[]数据
	 */
	public byte[] readBytes(int size) throws Exception {
		if (size <= 0) {
			return null;
		}

		if (length() < size) {
			throw new Exception();
		} else {
			byte[] resBuffer = new byte[size];
			System.arraycopy(buffer, 0, resBuffer, 0, size);
			return resBuffer;
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param size
	 *            大小
	 */
	public void deleteBytes(int size) {
		int leave = length() - size;
		if (leave <= 0) {
			this.buffer = null;
			return;
		}

		byte[] newBuffer = new byte[leave];
		System.arraycopy(buffer, size, newBuffer, 0, leave);
		this.buffer = newBuffer;
	}

	// /////////////////////////////////////////////////////////////////////////
	/**
	 * 获得类的信息
	 * 
	 * @return 类的信息
	 */
	public String toString() {
		StringBuffer hexString = new StringBuffer("hex string = ");
		try {
			for (int i = 0; i < length(); i++) {
				hexString.append(Character
						.forDigit((buffer[i] >> 4) & 0x0f, 16));
				hexString.append(Character.forDigit(buffer[i] & 0x0f, 16));
				hexString.append(" ");
			}
		} catch (Throwable t) {
			hexString.append("Throwable caught when dumping = " + t);
		}
		return hexString.toString();
	}

	// ------------------------------------------------------ Protected Methods

	// -------------------------------------------------------- Private Methods
	/**
	 * 添加空的byte[]数据
	 * 
	 * @param size
	 *            大小
	 */
	private void appendZeroBytes(int size) {
		if (size > 0) {
			byte[] zeroBuffer = new byte[size];
			appendBytes(zeroBuffer, zeroBuffer.length);
		}
	}

	/**
	 * 添加字符串
	 * 
	 * @param data
	 *            字符串
	 * 
	 * @exception UnsupportedEncodingException
	 *                抛出UnsupportedEncodingException异常
	 */
	private void appendString(String data) throws UnsupportedEncodingException {
		byte[] stringBuffer = data.getBytes("ASCII");
		if ((stringBuffer != null) && (stringBuffer.length > 0)) {
			appendBytes(stringBuffer, stringBuffer.length);
		}
	}

	public void appendLong(long lgData) {
		byte[] longBuffer = new byte[8];
		for (int i = 0; i < 8; i++) {
			longBuffer[i] = (byte) (lgData >> longBuffer.length
					* (longBuffer.length - i - 1));
		}
		appendBytes(longBuffer, longBuffer.length);
	}

	public long removeLong() throws Exception {
		byte[] resBuffer = removeBytes(8);
		long lgData = 0;

		lgData = (((long) resBuffer[0] & 0xff) << 56)
				| (((long) resBuffer[1] & 0xff) << 48)
				| (((long) resBuffer[2] & 0xff) << 40)
				| (((long) resBuffer[3] & 0xff) << 32)
				| (((long) resBuffer[4] & 0xff) << 24)
				| (((long) resBuffer[5] & 0xff) << 16)
				| (((long) resBuffer[6] & 0xff) << 8)
				| (((long) resBuffer[7] & 0xff) << 0);

		return lgData;
	}
	
}
