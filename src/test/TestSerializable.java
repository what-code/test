package test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
import java.util.*;

/**
 * Title:TestSer.java
 * 
 * Description:TestSer.java
 * 
 * Copyright: Copyright (c) 2013-12-27
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TestSerializable {
	
	public static void readFromObj(String path){
		FileInputStream fis;
		try {
			fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			TaoGoods tg = (TaoGoods)ois.readObject();
			System.out.println("--read tg--->" + tg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void writeObjToFile(TaoGoods obj){
		try {
			FileOutputStream fos = new FileOutputStream("/home/gsj/doc/test");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			System.out.println("--->" + obj.getClass());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TaoGoods tg = new TaoGoods();
		tg.setId(123);
		tg.setName("test123");
		tg.setSourceUrl("http://123.com");
		//tg.setImg("test_img");
		//TestSerializable.writeObjToFile(tg);
		TestSerializable.readFromObj("/home/gsj/doc/test");
	}

}
