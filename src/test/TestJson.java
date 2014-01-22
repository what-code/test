package test;

import java.util.Date;

import net.sf.json.JSONObject;


public class TestJson {
	public static void main(String[] args){
		Date d = new Date();
		JSONObject j = JSONObject.fromObject(d);
		System.out.println(j);
		System.out.println("---" + JSONObject.toBean(j));
	}
}
