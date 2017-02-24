package com.liee.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.liee.core.log.Logger;

public class SerializeUtil {
	
	private static Logger log = Logger.getLogger();
	
	public static byte[] serialize(Object value) {
		if (value == null) {  
			throw new NullPointerException("Can't serialize null");  
		}  
		byte[] result = null;  
		ByteArrayOutputStream bos = null;  
		ObjectOutputStream os = null;  
		try {  
			bos = new ByteArrayOutputStream();  
			os = new ObjectOutputStream(bos);
			Object m = (Object) value;
			os.writeObject(m);  
			os.close();  
			bos.close();  
			result = bos.toByteArray();  
		} catch (IOException e) {  
			throw new IllegalArgumentException("Non-serializable object", e);  
		} finally {  
			close(os);  
			close(bos);  
		}  
		return result;  
	}

	public static Object unserialize(byte[] bytes) {
		Object result = null;  
		ByteArrayInputStream bis = null;  
		ObjectInputStream is = null;  
		try {  
		  if (bytes != null) {  
			  bis = new ByteArrayInputStream(bytes);  
			  is = new ObjectInputStream(bis);  
			  result = (Object) is.readObject();  
			  is.close();  
			  bis.close();  
		  }  
		} catch (IOException e) {  
			e.printStackTrace();
			log.error(String.format("Caught IOException decoding %d bytes of data",  
					bytes == null ? 0 : bytes.length) + e,e);  
		} catch (ClassNotFoundException e) {  
			log.error( String.format("Caught CNFE decoding %d bytes of data",  
					bytes == null ? 0 : bytes.length) + e ,e);  
		} finally {  
		  close(is);  
		  close(bis);  
		}  
	  return result;  
	}
	
	
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				log.error("Unable to close " + closeable, e); 
			}
		}
	}
}
