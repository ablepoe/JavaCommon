package com.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * serialize object
 * unserialize object
 * @author hanliang
 *
 */
public class SerializableUtil {

	/**
	 * serialize object
	 * @param object
	 * @return
	 */
	public static byte[] toByteArray(Object object){
		byte[] result = null;
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			result = baos.toByteArray();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * unserialize object
	 * @param byteArray
	 * @return
	 */
	public static Object unserializeByteArray(byte[] byteArray){
		Object obj = null;
		try{
			ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
			ObjectInputStream ois = new ObjectInputStream(bais);
			obj = ois.readObject();
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
}
