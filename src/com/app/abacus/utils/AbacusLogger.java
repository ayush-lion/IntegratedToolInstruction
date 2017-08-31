package com.app.abacus.utils;
/**
 * 
 */


import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author prashant.joshi
 *
 */
public class AbacusLogger {

	private boolean loggingDebug;
	private boolean loggingWarning;
	private boolean loggingInfo;
	
	public AbacusLogger() {
		this.loggingDebug = Boolean.FALSE;
		this.loggingWarning = Boolean.FALSE;
		this.loggingInfo = Boolean.TRUE;
	}
	
	public AbacusLogger(Boolean loggingDebug, Boolean loggingWarning, Boolean loggingInfo) {
		this.loggingDebug = loggingDebug.booleanValue();
		this.loggingWarning = loggingWarning.booleanValue();
		this.loggingInfo = loggingInfo.booleanValue();
	}
	
	public void logDebug(String debugText) {
		if(isLoggingDebug())
			System.out.println(debugText);
	}
	
	public void logInfo(String infoText) {
		if(isLoggingInfo())
			System.out.println(infoText);
	}
	
	public void logWarning(String warText) {
		if(isLoggingWarning())
			System.out.println(warText);
	}
	
	/**
	 * Prints MAP DATA
	 */
	public void logDebug(Map map) {
		if(isLoggingDebug()) {
			if(map != null && !map.isEmpty()) {
				Iterator<String> mIt = map.keySet().iterator();
				System.out.println("Print Map ==> ");
				while(mIt.hasNext()) {
					String key = (String) mIt.next();
					Object value = map.get(key);
					System.out.println(key + " : " + value);
				}
				System.out.println("--------");
			}
		}
	}
	
	/**
	 * Prints List Data
	 */
	public void logDebug(List list) {
		if(isLoggingDebug()) {
			if(list != null && !list.isEmpty()) {
				System.out.println("Print List ==> ");
				list.forEach(System.out::println);
				System.out.println("--------");
			}
		}
	}
	
	/**
	 * @return the loggingDebug
	 */
	public boolean isLoggingDebug() {
		return loggingDebug;
	}
	/**
	 * @param loggingDebug the loggingDebug to set
	 */
	public void setLoggingDebug(boolean loggingDebug) {
		this.loggingDebug = loggingDebug;
	}
	/**
	 * @return the loggingWarning
	 */
	public boolean isLoggingWarning() {
		return loggingWarning;
	}
	/**
	 * @param loggingWarning the loggingWarning to set
	 */
	public void setLoggingWarning(boolean loggingWarning) {
		this.loggingWarning = loggingWarning;
	}
	/**
	 * @return the loggingInfo
	 */
	public boolean isLoggingInfo() {
		return loggingInfo;
	}
	/**
	 * @param loggingInfo the loggingInfo to set
	 */
	public void setLoggingInfo(boolean loggingInfo) {
		this.loggingInfo = loggingInfo;
	}
}
