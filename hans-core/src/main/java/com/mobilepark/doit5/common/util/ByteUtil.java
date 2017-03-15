package com.mobilepark.doit5.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ByteUtil
{
	public static byte[] getLittleEndian(int i)
	{
		byte[] byteArray = new byte[4];
		byteArray[3] = (byte)((i >>> 24) & 0xFF);
		byteArray[2] = (byte)((i >>> 16) & 0xFF);
		byteArray[1] = (byte)((i >>>  8) & 0xFF);
		byteArray[0] = (byte)((i >>>  0) & 0xFF);
		return byteArray;
	}

	public static int getLittleEndianInt(byte[] byteArray)
	{
		int i = (((int)byteArray[3]&0xff) << 24)
			   + (((int)byteArray[2]&0xff) << 16)
			   + (((int)byteArray[1]&0xff) <<  8)
			   + (((int)byteArray[0]&0xff) <<  0);
		return i;
	}
	
	public static byte[] getLittleEndian(short s)
	{
		byte[] byteArray = new byte[2];

		byteArray[1] = (byte) ((s >>>  8) & 0xFF);
		byteArray[0] = (byte) ((s >>>  0) & 0xFF);

		return byteArray;
	}

	public static short getLittleEndianShort(byte[] byteArray)
	{
		int i = (((int)byteArray[1] & 0xFF) << 8)
			   + (((int)byteArray[0] & 0xFF) << 0);

		return (short) i;
	}
	
	public static byte[] getBytes(long l)
	{
		byte[] byteArray = new byte[8];

		byteArray[0] = (byte) ((l >>> 56) & 0xFF);
		byteArray[1] = (byte) ((l >>> 48) & 0xFF);
		byteArray[2] = (byte) ((l >>> 40) & 0xFF);
		byteArray[3] = (byte) ((l >>> 32) & 0xFF);
		byteArray[4] = (byte) ((l >>> 24) & 0xFF);
		byteArray[5] = (byte) ((l >>> 16) & 0xFF);
		byteArray[6] = (byte) ((l >>>  8) & 0xFF);
		byteArray[7] = (byte) ((l >>>  0) & 0xFF);

		return byteArray;
	}

	public static long getLong(byte[] byteArray)
	{
		long l = (((long)byteArray[0] & 0xFF) << 56)
					+ (((long)byteArray[1] & 0xFF) << 48)
					+ (((long)byteArray[2] & 0xFF) << 40)
					+ (((long)byteArray[3] & 0xFF) << 32)
				   + (((long)byteArray[4] & 0xFF) << 24)
				   + (((long)byteArray[5] & 0xFF) << 16)
				   + (((long)byteArray[6] & 0xFF) <<  8)
				   + (((long)byteArray[7] & 0xFF) <<  0);

		return l;
	}

	public static byte[] getBytes(int i)
	{
		byte[] byteArray = new byte[4];

		byteArray[0] = (byte) ((i >>> 24) & 0xFF);
		byteArray[1] = (byte) ((i >>> 16) & 0xFF);
		byteArray[2] = (byte) ((i >>>  8) & 0xFF);
		byteArray[3] = (byte) ((i >>>  0) & 0xFF);

		return byteArray;
	}

	public static int getInt(byte[] byteArray)
	{
		int i = (((int)byteArray[0] & 0xFF) << 24)
			   + (((int)byteArray[1] & 0xFF) << 16)
			   + (((int)byteArray[2] & 0xFF) <<  8)
			   + (((int)byteArray[3] & 0xFF) <<  0);

		return i;
	}

	public static byte[] getBytes(short s)
	{
		byte[] byteArray = new byte[2];

		byteArray[0] = (byte) ((s >>>  8) & 0xFF);
		byteArray[1] = (byte) ((s >>>  0) & 0xFF);

		return byteArray;
	}

	public static short getShort(byte[] byteArray)
	{
		int i = (((int)byteArray[0] & 0xFF) << 8)
			   + (((int)byteArray[1] & 0xFF) << 0);

		return (short) i;
	}
	
	public static byte[] getBytes(String hex)
	{
		if (hex == null) return new byte[0];
		if (hex.length() == 0) return new byte[0];
		if (hex.length() % 2 != 0) return new byte[0];
		if (hex.startsWith("0x")) hex = hex.substring(2);
		
		int length = hex.length()/2;
		
		byte[] byteArray = new byte[length];
		for (int i=0 ; i<length ; i++) {
			String s = hex.substring(i*2, i*2+2);
			byteArray[i] = (byte)Integer.parseInt(s, 16);
		}
		
		return byteArray;
	}
	
	public static byte[] getBytes(String s, int len) {

		byte[] byteArray = new byte[len];
		byte[] buffer = s.getBytes();

		for (int i=0 ; i<len ; i++)
		{
			if (i < buffer.length) { byteArray[i] = buffer[i]; }
		}

		return byteArray;
	}
	
	public static byte[] getBytes(Object object)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	public static Object getObject(byte[] bytes)
	{
		Object object = null;
		try
		{
			object = new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return object;
	}

	public static String hexString(byte b)
	{
		String hex = Integer.toHexString(b & 0xFF).toUpperCase();
		if (hex.length() == 1) hex = "0" + hex;
		return hex;
	}
	
	public static String hexString(byte[] buffer)
	{
		int length = buffer.length;
		StringBuffer strBuf = new StringBuffer("");

		for (int i=0 ; i<length ; i++)
		{
			String hex = ByteUtil.hexString(buffer[i]);
			strBuf.append(hex);
		}
		return strBuf.toString();
	}
	
	public static String hexWithASCII(byte[] buffer)
	{
		int length = buffer.length;
		int column = 16;

		StringBuffer charBuf = new StringBuffer("");
		StringBuffer strBuf = new StringBuffer("");

		for (int i=0 ; i<length ; i++)
		{
			char c = (char)buffer[i];
			if (c < '!' ) charBuf = charBuf.append(' ');
			else charBuf = charBuf.append(c);

			String hex = ByteUtil.hexString(buffer[i]);
			int hexLength = hex.length();
			String space = "   ".substring(0, 4-hexLength);

			strBuf = strBuf.append(space + hex);
			if (i < (length-1)) strBuf = strBuf.append(" ");
			
			if ((i+1)%column == 0 || i+1 == length)
			{

				if (i+1 == length)
				{
					for (int j=0 ; j<(((i%column == 0 ? column : column-(i%column))-1) * 5) ; j++)
					strBuf = strBuf.append(" ");
				}

				strBuf = strBuf.append("   " + charBuf + "\n"); 
				charBuf = charBuf.delete(0, charBuf.length());
			}
		}
		return strBuf.toString();
	}

	public static void hexPrint(byte[] buffer)
	{
		System.out.println(hexString(buffer));
	}

	public static byte[] readBytes(byte[] src, int offset, int length) throws Exception
	{
		byte[] dest = new byte[length];
		System.arraycopy(src, offset, dest, 0, length);
		return dest;
	}
	
	public static String getBinary(int decimal, int bit)
	{
		String binary = Integer.toBinaryString(decimal);
		int length = binary.length();
		
		StringBuffer strBuf = new StringBuffer();
		for (int i=0 ; i<bit-length ; i++)
		{
			strBuf = strBuf.append("0");
		}
		strBuf = strBuf.append(binary);
		return strBuf.toString();
	}
	
	public static void binaryPrint(String binary)
	{
		if (binary == null) return;
		char[] arrays = binary.toCharArray();
		for (int i=0 ; i<arrays.length ; i++)
		{
			if ((i%4) == (arrays.length%4) && (i > 0)) System.out.print(" ");
			System.out.print(arrays[i]);
		}
		System.out.println();
	}
	
	public static void binaryPrint(byte[] buffer)
	{
		if (buffer == null) return;
		
		StringBuffer strBuf = new StringBuffer();
		for (int i=0 ; i<buffer.length ; i++)
		{
			strBuf = strBuf.append(getBinary(buffer[i], 8));
		}
		binaryPrint(strBuf.toString());
	}
}
