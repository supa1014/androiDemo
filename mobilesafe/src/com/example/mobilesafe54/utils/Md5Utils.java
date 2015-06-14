package com.example.mobilesafe54.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    /**
     * 
     * @param text  需要加密的文本
     * @return
     */
	public static String encode(String text) {
		
		try {
			
			//初始化消息摘要签名
			MessageDigest messageDigest = MessageDigest.getInstance("md5");
			//把当前的密码转成byte字节。然后进行加密
			byte[] result = messageDigest.digest(text.getBytes());
			
			StringBuffer sb = new StringBuffer();
			
			for (byte b : result) {
				//获取低八位
				int number =  b&0xff;
				//把当前的字节转换成哈希值
				String hex = Integer.toHexString(number);
				
				if(hex.length() == 1){
					sb.append("0"+hex);
				}else{
					sb.append(hex);
				}
				
				System.out.println(sb.toString());
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "";
	}
    /**
     * 获取到文件的md5值
     * @param apkPath
     * @return
     */
	public static String getMd5(String apkPath) {
		
		
		
		
		try {
			
			//初始化消息摘要签名
			MessageDigest messageDigest = MessageDigest.getInstance("md5");
			
			File file = new File(apkPath);
			
			FileInputStream fis = new FileInputStream(file);
			
		    byte[] buffer = new byte[1024];
		    
		    int len = -1;
		    
		    while((len = fis.read(buffer))!= -1){
		    	messageDigest.update(buffer, 0, len);
		    	
		    }
		    
		  //把当前的密码转成byte字节。然后进行加密
			byte[] result = messageDigest.digest();
		
			
			StringBuffer sb = new StringBuffer();
			
			for (byte b : result) {
				//获取低八位
				int number =  b&0xff;
				//把当前的字节转换成哈希值
				String hex = Integer.toHexString(number);
				
				if(hex.length() == 1){
					sb.append("0"+hex);
				}else{
					sb.append(hex);
				}
				
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "";
	}

}
