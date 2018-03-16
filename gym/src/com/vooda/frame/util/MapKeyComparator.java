package com.vooda.frame.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * map按key排序
 * @author Administrator
 *
 */
public class MapKeyComparator  implements Comparator<String>{

	@Override
	public int compare(String str1, String str2) {
		return str2.compareTo(str1);
	}
	
	/**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
 
        Map<String, Object> sortMap = new TreeMap<String, Object>(
                new MapKeyComparator());
 
        sortMap.putAll(map);
 
        return sortMap;
    }

}
