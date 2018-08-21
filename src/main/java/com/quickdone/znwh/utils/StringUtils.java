/* 
 *
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName： StringUtils.java
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0    wangt   2016年8月30日下午3:02:43         Create   
 */
package com.quickdone.znwh.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串公共操作类
 * 
 * @author wangt
 *
 * @version 1.0
 * 
 */
public class StringUtils {

	/**
	 * @description 判断字符串是否为空 包括是否为null，是否为空字符串，过滤完空格后是否为空字符串
	 * @author wangt
	 * @create 2016年8月30日下午3:18:14
	 * @version 1.0
	 * @param str
	 *            字符串参数
	 * @return 是否为空
	 */
	public static boolean isNullOrEmpry(final String str) {
		if (str == null || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @description
	 * @author wangt
	 * @create 2016年8月30日下午3:18:32
	 * @version 1.0
	 * @param str
	 *            字符串参数
	 * @return 包括是否为null，是否为空字符串，过滤完空格后是否为空字符串
	 */
	public static boolean isAbsEmpry(final String str) {
		if (str == null) {
			return true;
		} else {
			return str.trim().isEmpty();
		}
	}

	/**
	 * @description
	 * @author wangt
	 * @create 2016年8月30日下午3:18:42
	 * @version 1.0
	 * @param str
	 *            字符串参数
	 * @return 过滤特殊字符
	 */
	public static boolean checkStr(final String str) {
		String regEx = "[`~!@#$%^&*<>]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @description 去除字符串中的所有空格
	 * @author wangt
	 * @create 2016年8月30日下午3:18:52
	 * @version 1.0
	 * @param str
	 *            原始字符串
	 * @return String
	 */
	public static String removeBlank(String str) {
		String result = "";
		result = str.replace(" ", "");
		return result.trim();
	}

	/**
	 * @description 解析以seperator分割的idStr字符串成list
	 * @author HP
	 * @create 2016年10月25日上午10:55:42
	 * @version 1.0
	 * @param idStr
	 *            String
	 * @param seperator
	 *            String
	 * @return List
	 */
	public static List<Integer> parseIdStrToList(String idStr, String seperator) {
		List<Integer> ids = new ArrayList<Integer>();

		try {
			String[] idStrs = idStr.split(seperator);
			for (String id : idStrs) {
				ids.add(Integer.valueOf(id));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			ids.clear();
		}

		return ids;
	}

	/**
	 * 将字符串按指定分隔符截取成list
	 * 
	 * @param str
	 * @param seperator
	 * @return
	 */
	public static List<String> parseStrToList(String str, String seperator) throws NumberFormatException{
		List<String> list = new ArrayList<String>();

		try {
			String[] strs = str.split(seperator);
			list = Arrays.asList(strs);
		} catch (NumberFormatException e) {
			list.clear();
			throw e;

		}

		return list;
	}

	/**
	 * @description 去除特殊标点符号(包括空格)
	 * @author wangt
	 * @create 2017年4月2日下午5:00:27
	 * @version 1.0
	 * @param str
	 * @return
	 */
	public static String removeSpecialStr(String str) {
		String returnStr = "";

		if (isNullOrEmpry(str)) {
			return returnStr;
		}

		returnStr = str.replaceAll("[\\pP\\p{Punct}\\d+]", "");
		return removeBlank(returnStr);
	}

	/**
	 * 
	 * @description 剔除非中文或者非数字字符串
	 * @author lli
	 * @create 2017年4月5日上午11:18:36
	 * @version 1.0
	 * @param str
	 * @return
	 */
	public static String matchChineseAndNum(final String str) {
		String returnStr = "";
		returnStr = str.replaceAll("[\\pP\\p{Punct}+]", "");
		return removeBlank(returnStr);

	}

	/**
	 * 
	 * @description 剔除非中文或者非数字字符串
	 * @author lli
	 * @create 2017年4月5日上午11:18:36
	 * @version 1.0
	 * @param str
	 * @return
	 */
	public static String numToChinese(final String str) {
		String returnStr = "";
		returnStr = str.replaceAll("0", "零").replace("1", "一")
				.replace("2", "二").replace("3", "三").replace("4", "四")
				.replace("5", "五").replace("6", "六").replace("7", "七")
				.replace("8", "八").replace("9", "九");
		return removeBlank(returnStr);

	}

	/**
	 * 
	 * @description 获取最大子字符串长度
	 * @author lli
	 * @create 2017年4月5日下午4:00:23
	 * @version 1.0
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int getMaxSubStringLen(final String s1, final String s2) {
		String max = "", min = "";
		max = (s1.length() > s2.length()) ? s1 : s2;
		min = (s1.length() > s2.length()) ? s2 : s1;
		String target = null;
		// 最外层：min子串的长度，从最大长度开始
		for (int i = min.length(); i >= 1; i--) {
			// 遍历长度为i的min子串，从0开始
			for (int j = 0; j <= min.length() - i; j++) {
				target = min.substring(j, j + i);
				// 遍历长度为i的max子串，判断是否与target子串相同，从0开始
				for (int k = 0; k <= max.length() - i; k++) {
					if (max.substring(k, k + i).equals(target)) {
						return i;
					}
				}
			}
		}
		return 0;
	}

	/**
	 * 将文本按序号自动换行
	 * @param reserve
	 * @return
	 */
	public static String ReserveSort(String reserve) {
		String[] array1 = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
		for (int i = 0; i < array1.length; i++) {
			if (reserve.indexOf(array1[i] + "、") > 0) {
				reserve = reserve.replace(array1[i] + "、", "<br >" + array1[i] + "、");
			}
			if (reserve.indexOf(array1[i] + ".") > 0) {
				reserve = reserve.replace(array1[i] + ".", "<br >" + array1[i] + ".");
			}
			if (reserve.indexOf("（" + array1[i] + "）") > 0) {
				reserve = reserve.replace("（" + array1[i] + "）", "<br >" + "（" + array1[i] + "）");
			}
		}
//		String[] array2 = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "g"};
//		for (int i = 0; i < array2.length; i++) {
//			if (reserve.indexOf(array2[i] + "、") > 0) {
//				reserve = reserve.replace(array2[i] + "、", "<br >" + array2[i] + "、");
//			}
//			if (reserve.indexOf(array2[i] + ".") > 0) {
//				reserve = reserve.replace(array2[i] + ".", "<br >" + array2[i] + ".");
//			}
//			if (reserve.indexOf("（" + array2[i] + "）") > 0) {
//				reserve = reserve.replace("（" + array2[i] + "）", "<br >" + "（" + array2[i] + "）");
//			}
//		}
		String[] array3 = new String[]{"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"};
		for (int i = 0; i < array3.length; i++) {
			if (reserve.indexOf(array3[i] + "、") > 0) {
				reserve = reserve.replace(array3[i] + "、", "<br >" + array3[i] + "、");
			}
			if (reserve.indexOf(array3[i] + ".") > 0) {
				reserve = reserve.replace(array3[i] + ".", "<br >" + array3[i] + ".");
			}
			if (reserve.indexOf("（" + array3[i] + "）") > 0) {
				reserve = reserve.replace("（" + array3[i] + "）", "<br >" + "（" + array3[i] + "）");
			}
			if (reserve.indexOf(array3[i]) > 0) {
				reserve = reserve.replace(array3[i], "<br >" + array3[i]);
			}
		}
		//这个就是是否包含1.2这样的序号
		for (int i = 1; i < 16; i++) {
			if (reserve.indexOf(i + "、") > 0) {
				reserve = reserve.replace(i + "、", "<br >" + i + "、");
			}
			if (reserve.indexOf(i + ".") > 0) {
				reserve = reserve.replace(i + ".", "<br >" + i + ".");
			}
			if (reserve.indexOf("（" + i + "）") > 0) {
				reserve = reserve.replace("（" + i + "）", "<br >" + "（" + i + "）");
			}
		}
		return reserve;
	}



	public static String ReserveSortChange(String reserve) {
		String[] array1 = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十","十一","十二","十三"};
		for (int i = 0; i < array1.length; i++) {
			if (reserve.indexOf(array1[i] + "、") > 0) {
				reserve = reserve.replace(array1[i] + "、", "<br >" + array1[i] + "、");
			}
			if (reserve.indexOf(array1[i] + ".") > 0) {
				reserve = reserve.replace(array1[i] + ".", "<br >" + array1[i] + ".");
			}
			if (reserve.indexOf("（" + array1[i] + "）") > 0) {
				reserve = reserve.replace("（" + array1[i] + "）", "<br >" + "（" + array1[i] + "）");
			}
		}
//		String[] array2 = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "g"};
//		for (int i = 0; i < array2.length; i++) {
//			if (reserve.indexOf(array2[i] + "、") > 0) {
//
//				reserve = reserve.replace(array2[i] + "、", "<br >" + array2[i] + "、");
//			}
//			if (reserve.indexOf(array2[i] + ".") > 0) {
//				reserve = reserve.replace(array2[i] + ".", "<br >" + array2[i] + ".");
//			}
//			if (reserve.indexOf("（" + array2[i] + "）") > 0) {
//				reserve = reserve.replace("（" + array2[i] + "）", "<br >" + "（" + array2[i] + "）");
//			}
//		}
		String[] array3 = new String[]{"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"};
		for (int i = 0; i < array3.length; i++) {
			if (reserve.indexOf(array3[i] + "、") > 0) {

				int leng =reserve.indexOf(array3[i] + "、");
				int strLeng=reserve.length();
				if(strLeng==2){
					//就是a、这种情况
					reserve = reserve.replace(array3[i] + "、", "<br >" + array3[i] + "、");
				}else{
					//防止超过字符串最大长度
					String chCharacter=reserve.substring(leng+2,leng+3);
					//不为空格时
					if(" ".equals(chCharacter)){
						//再截取一位字符
						int lengT=reserve.indexOf(" ");
						if(strLeng==3){
							//这种情就是a、后面带一个空格
							reserve = reserve.replace(array3[i] + "、", "<br >" + array3[i] + "、");
						}else{
							String chCharacterOne=reserve.substring(lengT+1,lengT+2);
							Boolean bool=JudgingCharacterChinese(chCharacterOne);
							if(bool==true){
								//为中文时添加换行
								reserve = reserve.replace(array3[i] + "、", "<br >" + array3[i] + "、");
							}
						}
					}else{
						Boolean bool=JudgingCharacterChinese(chCharacter);
						if(bool==true){
							//为中文时添加换行
							reserve = reserve.replace(array3[i] + "、", "<br >" + array3[i] + "、");
						}
					}

				}
			}
			if (reserve.indexOf(array3[i] + ".") > 0) {
				int leng =reserve.indexOf(array3[i] + ".");
				int strLeng=reserve.length();
				if(strLeng==2){
					//就是a.这种情况
					reserve = reserve.replace(array3[i] + ".", "<br >" + array3[i] + ".");
				}else{
					//防止超过字符串最大长度
					String chCharacter=reserve.substring(leng+2,leng+3);
					//不为空格时
					if(" ".equals(chCharacter)){
						//再截取一位字符
						int lengT=reserve.indexOf(" ");
						if(strLeng==3){
							//这种情就是a. 后面带一个空格
							reserve = reserve.replace(array3[i] + ".", "<br >" + array3[i] + ".");
						}else{
							String chCharacterOne=reserve.substring(lengT+1,lengT+2);
							Boolean bool=JudgingCharacterChinese(chCharacterOne);
							if(bool==true){
								//为中文时添加换行
								reserve = reserve.replace(array3[i] + ".", "<br >" + array3[i] + ".");
							}
						}
					}else{
						Boolean bool=JudgingCharacterChinese(chCharacter);
						if(bool==true){
							//为中文时添加换行
							reserve = reserve.replace(array3[i] + ".", "<br >" + array3[i] + ".");
						}
					}

				}

			}
			if (reserve.indexOf("（" + array3[i] + "）") > 0) {
				int leng =reserve.indexOf("（" + array3[i] + "）");
				int strLeng=reserve.length();
				if(strLeng==3){
					//就是(1)这种情况
					reserve = reserve.replace("（" + array3[i] + "）", "<br >" + "（" + array3[i] + "）");
				}else{
					//防止超过字符串最大长度
					String chCharacter=reserve.substring(leng+3,leng+4);
					//不为空格时
					if(" ".equals(chCharacter)){
						//再截取一位字符
						int lengT=reserve.indexOf(" ");
						if(strLeng==4){
							//这种情就是(1)后面带一个空格
							reserve = reserve.replace("（" + array3[i] + "）", "<br >" + "（" + array3[i] + "）");
						}else{
							String chCharacterOne=reserve.substring(lengT+1,lengT+2);
							Boolean bool=JudgingCharacterChinese(chCharacterOne);
							if(bool==true){
								//为中文时添加换行
								reserve = reserve.replace("（" + array3[i] + "）", "<br >" + "（" + array3[i] + "）");
							}
						}
					}else{
						Boolean bool=JudgingCharacterChinese(chCharacter);
						if(bool==true){
							//为中文时添加换行
							reserve = reserve.replace("（" + array3[i] + "）", "<br >" + "（" + array3[i] + "）");
						}
					}

				}


			}
			if (reserve.indexOf(array3[i]) > 0) {

				int leng =reserve.indexOf(array3[i]);
				int strLeng=reserve.length();
				if(strLeng==1){
					//就是(1)这种情况
					reserve = reserve.replace(array3[i], "<br >" + array3[i]);
				}else{
					//防止超过字符串最大长度
					String chCharacter=reserve.substring(leng+1,leng+2);
					//不为空格时
					if(" ".equals(chCharacter)){
						//再截取一位字符
						int lengT=reserve.indexOf(" ");
						if(strLeng==4){
							//这种情就是(1)后面带一个空格
							reserve = reserve.replace(array3[i], "<br >" + array3[i]);
						}else{
							String chCharacterOne=reserve.substring(lengT+1,lengT+2);
							Boolean bool=JudgingCharacterChinese(chCharacterOne);
							if(bool==true){
								//为中文时添加换行
								reserve = reserve.replace(array3[i], "<br >" + array3[i]);
							}
						}
					}else{
						Boolean bool=JudgingCharacterChinese(chCharacter);
						if(bool==true){
							//为中文时添加换行
							reserve = reserve.replace(array3[i], "<br >" + array3[i]);
						}
					}

				}

			}
		}

		//这个就是是否包含1.2这样的序号(这个地方要对于超过12的字符做出)
		for (int i = 1; i <=9; i++) {
			if (reserve.indexOf(i + "、") > 0) {
				int leng =reserve.indexOf(i + "、");
				int strLeng=reserve.length();
				if(strLeng==2){
					//就是1.这种情况
					reserve = reserve.replace(i + "、", "<br >" + i + "、");
				}else{
					// 防止超过字符串最大长度
					String chCharacter=reserve.substring(leng+2,leng+3);
					//不为空格时
					if(" ".equals(chCharacter)){
						//再截取一位字符
						int lengT=reserve.indexOf(" ");
						if(strLeng==3){
							//这种情就是1、后面带一个空格
							reserve = reserve.replace(i + "、", "<br >" + i + "、");
						}else{
							String chCharacterOne=reserve.substring(lengT+1,lengT+2);
							Boolean bool=JudgingCharacterChinese(chCharacterOne);
							if(bool==true){
								//为中文时添加换行
								reserve = reserve.replace(i + "、", "<br >" + i + "、");
							}
						}
					}else{
						Boolean bool=JudgingCharacterChinese(chCharacter);
						if(bool==true){
							//为中文时添加换行
							reserve = reserve.replace(i + "、", "<br >" + i + "、");
						}
					}

				}

			}

			if (reserve.indexOf(i + ".") > 0) {
				int leng =reserve.indexOf(i + ".");
				int strLeng=reserve.length();
				if(strLeng==2){
					//就是1.这种情况
					reserve = reserve.replace(i + ".", "<br >" + i + ".");
				}else{
					//防止超过字符串最大长度
					String chCharacter=reserve.substring(leng+2,leng+3);
					//不为空格时
					if(" ".equals(chCharacter)){
						//再截取一位字符
						int lengT=reserve.indexOf(" ");
						if(strLeng==3){
							//这种情就是1.后面带一个空格
							reserve = reserve.replace(i + ".", "<br >" + i + ".");
						}else{
							String chCharacterOne=reserve.substring(lengT+1,lengT+2);
							Boolean bool=JudgingCharacterChinese(chCharacterOne);
							if(bool==true){
								//为中文时添加换行
								reserve = reserve.replace(i + ".", "<br >" + i + ".");
							}
						}
					}else{
						Boolean bool=JudgingCharacterChinese(chCharacter);
						if(bool==true){
							//为中文时添加换行
							reserve = reserve.replace(i + ".", "<br >" + i + ".");
						}
					}

				}
			}

			if (reserve.indexOf(i + "．") > 0) {

				int leng =reserve.indexOf(i + "．");
				int strLeng=reserve.length();
				if(strLeng==2){
					//就是1.这种情况
					reserve = reserve.replace(i + "．", "<br >" + i + "．");
				}else{
					//防止超过字符串最大长度
					String chCharacter=reserve.substring(leng+2,leng+3);
					//不为空格时
					if(" ".equals(chCharacter)){
						//再截取一位字符
						int lengT=reserve.indexOf(" ");
						if(strLeng==3){
							//这种情就是1.后面带一个空格
							reserve = reserve.replace(i + "．", "<br >" + i + "．");
						}else{
							String chCharacterOne=reserve.substring(lengT+1,lengT+2);
							Boolean bool=JudgingCharacterChinese(chCharacterOne);
							if(bool==true){
								//为中文时添加换行
								reserve = reserve.replace(i + "．", "<br >" + i + "．");
							}
						}
					}else{
						Boolean bool=JudgingCharacterChinese(chCharacter);
						if(bool==true){
							//为中文时添加换行
							reserve = reserve.replace(i + "．", "<br >" + i + "．");
						}
					}
				}

			}
		/*	Map<Integer,String> mapOne=new HashMap<>();
			if(reserve.indexOf("（" + i + "）") > 0){
				StringBuffer sbf =new StringBuffer();
				sbf.append("parentheses");
				sbf.append(i);
				String mapString=sbf.toString();
				mapOne.put(i,mapString);

			}*/
	/*		if (reserve.indexOf(i + "）") > 0) {
				int leng =reserve.indexOf(i + "）");
				int strLeng=reserve.length();
				if(strLeng==2){
					//就是1.这种情况
					reserve = reserve.replace(i + "）", "<br >" + i + "）");
				}else{
					//防止超过字符串最大长度
					String chCharacter=reserve.substring(leng + 2,leng+3);
					//不为空格时
					if(" ".equals(chCharacter)){
						//再截取一位字符
						int lengT=reserve.indexOf(" ");
						if(strLeng==3){
							//这种情就是1.后面带一个空格
							reserve = reserve.replace(i + "）", "<br >" + i + "）");
						}else{
							String chCharacterOne=reserve.substring(lengT+1,lengT+2);
							Boolean bool=JudgingCharacterChinese(chCharacterOne);
							if(bool==true){
								//为中文时添加换行
								reserve = reserve.replace(i + "）", "<br >" + i + "）");
							}
						}
					}else{
						Boolean bool=JudgingCharacterChinese(chCharacter);
						if(bool==true){
							//为中文时添加换行
							reserve = reserve.replace(i + "）", "<br >" + i + "）");
						}
					}


				}
			}
*/
			if (reserve.indexOf("（" + i + "）") > 0) {

				int leng =reserve.indexOf("（" + i + "）");
				int strLeng=reserve.length();
				if(strLeng==3){
					//就是(1)这种情况
					reserve = reserve.replace("（" + i + "）", "<br >" + "（" + i + "）");
				}else{
					//防止超过字符串最大长度
					String chCharacter=reserve.substring(leng+3,leng+4);
					//不为空格时
					if(" ".equals(chCharacter)){
						//再截取一位字符
						int lengT=reserve.indexOf(" ");
						if(strLeng==4){
							//这种情就是(1)后面带一个空格
							reserve = reserve.replace("（" + i + "）", "<br >" + "（" + i + "）");
						}else{
							String chCharacterOne=reserve.substring(lengT+1,lengT+2);
							Boolean bool=JudgingCharacterChinese(chCharacterOne);
							if(bool==true){
								//为中文时添加换行
								reserve = reserve.replace("（" + i + "）", "<br >" + "（" + i + "）");
							}
						}
					}else{
						Boolean bool=JudgingCharacterChinese(chCharacter);
						if(bool==true){
							//为中文时添加换行
							reserve = reserve.replace("（" + i + "）", "<br >" + "（" + i + "）");
						}
					}

				}

			}
		}



		return reserve;
	}
	/**
	 * 判断字符串是否是中文
	 * @param str
	 * @return
	 */
	public static Boolean JudgingCharacterChinese(String str){
		String reg = "[\\u4e00-\\u9fa5]+";
		boolean result1=false;
		if(str != null  && !"".equals(str)){
			result1 = str.matches(reg);
		}
		return  result1;
	}




	/**
	 * 除去所有html标签的方法
	 *
	 * @param input
	 * @return
	 */
	public static String removeHTMLTag(String input) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		return str;
	}
}