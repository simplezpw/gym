package com.vooda.frame.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class LatLonUtil {

	private static final double PI = 3.14159265; // 圆周率
	private static final double EARTH_RADIUS = 6378137; // 地球半径
	private static final double RAD = Math.PI / 180.0; // 一百八十度角

	/**
	 * @param raidus 单位米 return minLat 最小经度 minLng 最小纬度 maxLat 最大经度 maxLng 最大纬度 minLat
	 */
	public static double[] getAround(double lat, double lon, int raidus) {

		Double latitude = lat;// 传值给经度
		Double longitude = lon;// 传值给纬度

		Double degree = (24901 * 1609) / 360.0; // 获取每度
		double raidusMile = raidus;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		// 获取最小纬度
		Double minLat = latitude - radiusLat;
		// 获取最大纬度
		Double maxLat = latitude + radiusLat;

		Double mpdLng = degree * Math.cos(latitude * (PI / 180));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		//获取最小经度
		Double minLng = longitude - radiusLng;
		// 获取最大经度
		Double maxLng = longitude + radiusLng;

		System.out.println("jingdu" + minLat + "weidu" + minLng + "zuidajingdu" + maxLat + "zuidaweidu" + maxLng);

		return new double[] { minLat, minLng, maxLat, maxLng };
	}
	
	public static Map getCity(String x,String y) throws Exception{
		URL url = new URL("http://api.map.baidu.com/geocoder?ak" + "=Nw0tXaxOLicepHFGBYrK9E0b" + 
        		"&callback=renderReverse&location=" + x
                        + "," + y + "&output=json");
        URLConnection connection = url.openConnection();
        /**
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
         */
        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection
                .getOutputStream(), "utf-8");
//        remember to clean up
        out.flush();
        out.close();
//        一旦发送成功，用以下方法就可以得到服务器的回应：
        String res;
        InputStream l_urlStream;
        l_urlStream = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                l_urlStream,"UTF-8"));
        StringBuilder sb = new StringBuilder("");
        while ((res = in.readLine()) != null) {
            sb.append(res.trim());
        }
        String str = sb.toString();
        Map<String,String> map = null;
		if(!StringUtils.isEmpty(str)) {
			JSONObject object = JSONObject.fromObject(str);
            if("OK".equals(object.getString("status"))){
            	JSONObject result = object.getJSONObject("result");
            	JSONObject address = result.getJSONObject("addressComponent");
                System.out.println(object.get("result"));
				map = new HashMap<String,String>();
				if(address.get("city")!=null){
					map.put("city", address.getString("city"));
				}else{
					map.put("city", "深圳");
				}
				
				return map;
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
		}
		
		return null;
		
	}

	//测试方法
	public static void main(String[] src) {
		getAround(36.68027, 117.12744, 1000);
	}

}