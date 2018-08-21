package com.quickdone.znwh.utils;

import java.io.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by psf on 2018/7/5.
 */
public class Xml2JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(Xml2JsonUtil.class);

	/**
	 * 读取路径下的xml文件并转换为json
	 * 
	 * @param filePath
	 * @return
	 */
	public static String xml2json(String filePath) throws IOException {
		String result = "";
		File file = new File(filePath);
		//FileReader fileReader = new FileReader(file);
		  FileInputStream fis=new FileInputStream(file);
		  InputStreamReader reader=new InputStreamReader(fis,"UTF-8");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = "";
		String res = "";
		while ((line = bufferedReader.readLine()) != null) {
			res += line;
		}
		// System.out.println(res);
		bufferedReader.close();
		return new XMLSerializer().read(res).toString();

	}

	public static void main(String[] args) {
//		try {
//			String json = xml2json("D:\\aaa.xml");
//			JSONArray myJsonArray = JSONArray.fromObject(json);
//			// System.out.println(myJsonArray);
//			JSONObject obj = (JSONObject) myJsonArray.get(0);
//			System.out.println("本次流程的主题是:" + obj.get("@name"));
//			;
//			JSONArray nodeArray = JSONArray.fromObject(obj.get("node"));
//			int size = nodeArray.size();
//			if (size > 0) {
//				for (int i = 0; i < size; i++) {
//					JSONObject job = nodeArray.getJSONObject(i);
//					System.out.println("id为:" + job.get("@id") + "的节点内容是："
//							+ job.get("content"));
//					;
//				}
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
 	}
}
