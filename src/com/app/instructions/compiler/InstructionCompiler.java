/**
 * 
 */
package com.app.instructions.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

import com.app.instructions.compiler.exception.CompilerException;

/**
 * Class is responsible to compile the instruction sheet
 * and return map of errors if any
 * 
 * @author prashant.joshi (198joshi@gmail.com)
 * @version 06-Aug-2017
 */
public class InstructionCompiler {

	/* Data Members */
	private String filePath;
	private File instructionFile;
	private LinkedHashMap<String, HashMap<String, List<String>>> mapOfErrors;
	private LinkedHashMap<String, HashMap<String, List<Action>>> instructionData;
	private ExcelReader reader;
	private InstructionsVerification instructionVerifier;
	private Map<String, String> properties;
	private InstructionLogger logger;
	
	/**
	 * Constructor (filePath is Mandatory)
	 */
	public InstructionCompiler(String filePath) throws CompilerException {
		this.filePath = filePath;
		logger = new InstructionLogger();
		mapOfErrors = new LinkedHashMap<String, HashMap<String, List<String>>>();
		instructionData = new LinkedHashMap<>();
		
		properties = InstructionsPropertyLoader.getAllPropertiesFromResource("compiler.properties");
		List<String> listOfValidTags = Arrays.asList(properties.get("tags").split(","));
		List<String> noClosingTags = Arrays.asList(properties.get("noClosingTags").split(","));
		List<String> commands = Arrays.asList(properties.get("commands").split(","));
		//logger.logInfo(commands);
		
		logger.setLoggingDebug(isTrueOrFalse(properties, "loggingDebug"));
		logger.setLoggingWarning(isTrueOrFalse(properties, "loggingWarning"));
		logger.setLoggingInfo(isTrueOrFalse(properties, "loggingInfo"));
		
		// Setting Up Instructions
		instructionVerifier = new InstructionsVerification(logger);
		instructionVerifier.setListOfValidTags(listOfValidTags);
		instructionVerifier.setNoClosingTags(noClosingTags);
		instructionVerifier.setCommands(commands);
		
		// Print list of valid tags
		logger.logDebug(listOfValidTags);
	}
	
	/**
	 * Method is responsible to compile the instructions and generate the errors if any
	 */
	public boolean compileInstructions() throws CompilerException {
		if(validateFile()) {
			reader = new ExcelReader(instructionFile);
			
			// Parse the sheet
			reader.parseExcelSheet();
			
			// Get and print the instruction sheet data
			LinkedHashMap<String, HashMap<String, String>> excelData = reader.getInstructionData();
			
			// Validate Data
			return validateInstructionActionData(excelData);
		}
		return Boolean.FALSE;
	}
	
	/**
	 * Method is responsible to validate the instruction set.
	 */
	private boolean validateInstructionActionData(LinkedHashMap<String, HashMap<String, String>> excelData) {
		Set<Entry<String, HashMap<String, String>>> entrySet = excelData.entrySet();
		for (Entry<String, HashMap<String, String>> entry : entrySet) {
			String rowNum = entry.getKey();
			HashMap<String, String> map = entry.getValue();
			Set<Entry<String, String>> sEntry =  map.entrySet();
			for (Entry<String, String> entry2 : sEntry) {
				String instruction = entry2.getKey();
				String action = entry2.getValue();
				
				// Create error map
				HashMap<String, List<String>> errorsMap = new HashMap<String, List<String>>();
				
				// Verify Instructions
				List<String> insErrors = instructionVerifier.validateInstruction(instruction);
				if(insErrors != null && !insErrors.isEmpty()) {
					errorsMap.put("instructionError", insErrors);
				} else {
					errorsMap.put("instructionError", new ArrayList<String>());
				}
				
				// Verify Actions
				List<String> actErrors = instructionVerifier.validateActions(action);
				if(actErrors != null && !actErrors.isEmpty()) {
					errorsMap.put("actionError", actErrors);
				} else {
					errorsMap.put("actionError", new ArrayList<String>());
				}
				
				if((insErrors != null && !insErrors.isEmpty()) || (actErrors != null && !actErrors.isEmpty())) {
					mapOfErrors.put(rowNum, errorsMap);
				}
				
				// Creating temp list
				HashMap<String, List<Action>> tMap = null;
				tMap = new HashMap<String, List<Action>>();
				tMap.put(instruction, instructionVerifier.getListOfActions());
				
				// Add data to instruction Map
				instructionData.put(rowNum, tMap);
				
			}
		}
		
		if(!mapOfErrors.isEmpty())
			return Boolean.FALSE;
		else 
			return Boolean.TRUE;
	}
	
	/**
	 * Validate Instruction File
	 */
	private boolean validateFile() throws CompilerException {
		// Verify path is correct or not
		if(filePath == null || filePath.isEmpty()) {
			throw (new CompilerException("Please provide a valid path!!!"));
		}
		
		// verify file is exist or not
		instructionFile = new File(filePath);
		if(!instructionFile.exists()) {
			throw (new CompilerException("Instruction file not exist. Please provide a valid Path!!!"));
		}
		
		// verify whether it's a file or not
		if(!instructionFile.isFile()) {
			throw (new CompilerException("Instruction file is not a file. Please provide a valid File!!!"));
		}
		
		// verify extension
		String ext = FilenameUtils.getExtension(filePath);
		if(!ext.equalsIgnoreCase("xls") && !ext.equalsIgnoreCase("xlsx")) {
			throw (new CompilerException("Instruction file extension should be either xls or xlsx!!!"));
		}
		
		return Boolean.TRUE;
	}
	

	/**
	 * @return the mapOfErrors
	 */
	public LinkedHashMap<String, HashMap<String, List<String>>> getMapOfErrors() {
		return mapOfErrors;
	}
	
	/**
	 * @return the instructionData
	 */
	public LinkedHashMap<String, HashMap<String, List<Action>>> getInstructionData() {
		return instructionData;
	}

	/**
	 * Method is responsible to get the boolean property value
	 * from properties file and convert it to Boolean
	 */
	private Boolean isTrueOrFalse(Map<String, String> attributes, String properyName) {
		String val = attributes.get(properyName) != null? attributes.get(properyName) : "false";
		return val.equalsIgnoreCase("true") ? Boolean.TRUE : Boolean.FALSE;
	}
	
}
