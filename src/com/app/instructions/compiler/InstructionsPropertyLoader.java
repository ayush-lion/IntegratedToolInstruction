/**
 * 
 */
package com.app.instructions.compiler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;


import com.app.instructions.compiler.exception.CompilerException;


/**
 * Class is responsible to load Abacus attributes to Map.
 * 
 * @author prashant.joshi (198joshi@gmail.com)
 * @version 03-Aug-2017
 */
public class InstructionsPropertyLoader {
	
	private static Properties prop; 
	
	static {
		prop = new Properties();
	}
	
	/**
	 * Loading resource file
	 */
	private static Properties getPropertyHandleFromResource(String propertiesFileName) throws CompilerException {
		try {
			if(prop.isEmpty()) {
				System.out.println("propertiesFileName : " + propertiesFileName);
				InputStream inputStream = InstructionsPropertyLoader.class.getClassLoader().getResourceAsStream(propertiesFileName);
				prop.load(inputStream);
			}
		} catch (IOException e) {
			throw (new CompilerException("Not able to read the attributes file!!!"));
		}
		return prop;
	}
	
	/**
	 * Loading resource file
	 */
	private static Properties getPropertyHandleFromFile(String propertiesFileName) throws CompilerException {
		try {
			if(prop.isEmpty()) {
				System.out.println("propertiesFileName : " + propertiesFileName);
				File file = new File(propertiesFileName);
				if(file.exists() && file.isFile()) {
					FileReader fr = new FileReader(file);
					prop.load(fr);
				} else {
					throw (new CompilerException("Abacus Attributes file not present!!!"));
				}
			}
		} catch (IOException e) {
			throw (new CompilerException("Not able to read the attributes file!!!"));
		}
		return prop;
	}
	
	public static Map<String, String> getAllPropertiesFromFile(String propertiesFileName) throws CompilerException {
		Map<String, String> propertiesMap = new LinkedHashMap<String, String>();
		Iterator<Object> it = InstructionsPropertyLoader.getPropertyHandleFromFile(propertiesFileName).keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			String value = InstructionsPropertyLoader.getPropertyHandleFromFile(propertiesFileName).getProperty(key);
			propertiesMap.put(key, value);
		}
		return propertiesMap;
	}
	
	public static Map<String, String> getAllPropertiesFromResource(String propertiesFileName) throws CompilerException {
		Map<String, String> propertiesMap = new LinkedHashMap<String, String>();
		Iterator<Object> it = InstructionsPropertyLoader.getPropertyHandleFromResource(propertiesFileName).keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			String value = InstructionsPropertyLoader.getPropertyHandleFromResource(propertiesFileName).getProperty(key);
			propertiesMap.put(key, value);
		}
		return propertiesMap;
	}

}
