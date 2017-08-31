/**
 * 
 */
package com.app.instructions.compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.app.instructions.compiler.exception.CompilerException;

/**
 * Class is responsible to read instructions from Excel sheet
 * and generated map of textToSpeech and Actions
 * 
 * @author prashant.joshi (198josho@gmail.com)
 * @version 06-Aug-2017
 */
public class ExcelReader {

	/* Data Members */
	private LinkedHashMap<String, HashMap<String, String>> instructionData;
	private File instructionFile;
	
	/**
	 * Default Constructor
	 */
	public ExcelReader(File instructionFile) {
		instructionData = new LinkedHashMap<String, HashMap<String, String>>();
		this.instructionFile = instructionFile; 
	}
	
	/**
	 * Method is responsible to parse the sheet and generate the 
	 * map of TextToSpeech and Actions
	 */
	public void parseExcelSheet() throws CompilerException {
		FileInputStream excelFileToRead = null;
		XSSFWorkbook workbook = null;
		try {
			excelFileToRead = new FileInputStream(instructionFile);
			workbook = new XSSFWorkbook(excelFileToRead);
			XSSFSheet spreadsheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = spreadsheet.iterator();
			int counter = 2;
			
			// Ignore Header
			if(rowIterator.hasNext()) {
				XSSFRow row = (XSSFRow) rowIterator.next();
			}
			
			// Iterate All rows
			while (rowIterator.hasNext()) {
				String key = "Row" + counter++;
				String textToSpeech = "";
				String action = "";
				XSSFRow row = (XSSFRow) rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				if (cellIterator.hasNext()) {
					//Text to Speech
					Cell cell = cellIterator.next();
					if(cell != null) {
						switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								textToSpeech = cell.getStringCellValue();
								break;
						}
					}
				}
				if (cellIterator.hasNext()) {	
					//Action
					Cell cell = cellIterator.next();
					if(cell != null) {
						switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								action = cell.getStringCellValue();
								break;
						}
					}
				}
				HashMap<String, String> textAction = new HashMap<String, String>();
				textAction.put(textToSpeech, action);
				instructionData.put(key, textAction);
			}
		} catch (Exception e) { 
			throw (new CompilerException("Unable to load the Excel File!!!")); 
		} finally {
			// Close all the open connections
			try {
				if(workbook != null)
					workbook.close();
				
				if(excelFileToRead != null)
					excelFileToRead.close();
			} catch (IOException ioe) {}
		}
	}

	/**
	 * @return the instructionData
	 */
	public LinkedHashMap<String, HashMap<String, String>> getInstructionData() {
		return instructionData;
	}

	/**
	 * @param instructionData the instructionData to set
	 */
	public void setInstructionData(LinkedHashMap<String, HashMap<String, String>> instructionData) {
		this.instructionData = instructionData;
	}
}
