package com.vooda.emchat.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertiesUtils
 * 
 * @author Lynch 2014-09-15
 *
 */
public class PropertiesUtils {

	public static Properties getProperties() {

		Properties p = new Properties();

		try {
			InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(
					"emchat.properties");

			p.load(inputStream);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return p;
	}

}
