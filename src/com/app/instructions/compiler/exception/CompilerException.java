/**
 * 
 */
package com.app.instructions.compiler.exception;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author prashant.joshi (198joshi@gmail.com)
 * @version 06-Aug-2017
 */
public class CompilerException extends Exception {

	private static final long serialVersionUID = 1L;
	private String customizedException;
	private LinkedHashMap<String, List<String>> errors;
	
	public CompilerException(String customizedException) {
		this.customizedException = customizedException;
	}
	
	public CompilerException(LinkedHashMap<String, List<String>> errors) {
		this.errors = errors;
	}

	/**
	 * @return the customizedException
	 */
	public String getCustomizedException() {
		return customizedException;
	}

	/**
	 * @param customizedException the customizedException to set
	 */
	public void setCustomizedException(String customizedException) {
		this.customizedException = customizedException;
	}

	/**
	 * @return the errors
	 */
	public LinkedHashMap<String, List<String>> getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(LinkedHashMap<String, List<String>> errors) {
		this.errors = errors;
	}
}
