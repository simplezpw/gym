package com.vooda.frame.umeng.ios;

import com.vooda.frame.umeng.IOSNotification;

public class IOSUnicast extends IOSNotification {
	public IOSUnicast() {
		try {
			this.setPredefinedKeyValue("type", "unicast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
