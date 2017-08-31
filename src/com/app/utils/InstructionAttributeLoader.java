package com.app.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;


public class InstructionAttributeLoader {
	
private static Properties prop;
	
	static {
		prop = new Properties();
	}
	
	/**
	 * Loading resource file
	 */
	private static Properties getPropertyHandleFromResource(String propertiesFileName)  {
		try {
			InputStream inputStream = InstructionAttributeLoader.class.getClassLoader().getResourceAsStream(propertiesFileName);
			prop.load(inputStream);
		} catch (IOException e) {
			//throw (new AbacusException("Not able to read the attributes file!!!"));
		}
		return prop;
	}
	
	/**
	 * Loading resource file
	 */
	private static Properties getPropertyHandleFromFile(String propertiesFileName) 
	{
		try {
			File file = new File(propertiesFileName);
			if(file.exists() && file.isFile()) {
				FileReader fr = new FileReader(file);
				prop.load(fr);
			} else {
				
			}
		} catch (IOException e) {
			
		}
		return prop;
	}
	
	public static Map<String, String> getAllPropertiesFromFile(String propertiesFileName) 
	{
		Map<String, String> propertiesMap = new LinkedHashMap<String, String>();
		Properties prop = InstructionAttributeLoader.getPropertyHandleFromFile(propertiesFileName);
		Iterator<Object> it = prop.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			String value = prop.getProperty(key);
			propertiesMap.put(key, value);
		}
		return propertiesMap;
	}
	
	public static Map<String, String> getAllPropertiesFromResource(String propertiesFileName)  
	{
		Map<String, String> propertiesMap = new LinkedHashMap<String, String>();
		Properties prop = InstructionAttributeLoader.getPropertyHandleFromResource(propertiesFileName);
		Iterator<Object> it = prop.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			String value = prop.getProperty(key);
			propertiesMap.put(key, value);
		}
		return propertiesMap;
	}


}
