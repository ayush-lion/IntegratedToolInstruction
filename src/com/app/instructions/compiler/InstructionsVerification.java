/**
 * 
 */
package com.app.instructions.compiler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Class is responsible to validate instructions
 * 
 * @author prashant.joshi (198joshi@gmail.com)
 * @version 06-Aug-2017
 */
public class InstructionsVerification {

	private Stack<String> tagStack;
	private List<String> listOfValidTags;
	private List<String> noClosingTags; 
	private List<String> commands;
	private InstructionLogger logger;
	private List<String> errors;
	private List<Action> listOfActions;
	
	/**
	 * Parameterized constructor to setup initial values
	 */
	public InstructionsVerification(InstructionLogger logger) {
		this.logger = logger;
	}
	
	/**
	 * Method is responsible to verify tags
	 */
	public List<String> validateInstruction(String instruction) {
		// Prints Instruction
		logger.logDebug("Instruction ==> " + instruction);
		
		// Remove all the extra spaces
		instruction = instruction.replaceAll("\\s+", " ");
		
		// Initialize error list and stack
		errors = new ArrayList<String>();
		tagStack = new Stack<String>();
		
		// Start verifying tags
		LinkedList<InstructionTag> listOftags = InstructionTag.tokenize(instruction);
		//logger.logInfo(listOftags);
		for (InstructionTag instructionTag : listOftags) {
			String tag = instructionTag.toString();
			
			if(tag.charAt(0) == '<' && tag.charAt(1) == '/') {
				
				String closingTag = tag.substring(0, 1) + tag.substring(2);
				if(tagStack.isEmpty()) {
					errors.add(tag + " is not started!!!");
					continue;
				}
				String tagInStack = tagStack.pop();
				
				if(!tagInStack.equalsIgnoreCase(closingTag)) {
					errors.add(tagInStack + " is not properly closed!!!");
					continue;
				}
				
			} else if(tag.charAt(0) == '<') {
				String tagForCompare = tag.substring(1,tag.length() - 1);
				//logger.logInfo("tagForCompare ==> " + tagForCompare);
				if(!listOfValidTags.contains(tagForCompare) && !noClosingTags.contains(tagForCompare)) {
					errors.add(tag + " not a valid tag!!!");
					continue;
				}
				if(noClosingTags.contains(tagForCompare.toLowerCase()))
					continue;
				tagStack.push(tag);
			}
		}
		/*
		if(!tagStack.isEmpty()) {
			String tag = tagStack.pop();
			//logger.logInfo("stack is not empty!!! " + tag);
			errors.add(tag + " is not properly closed!!!");
		}*/
		
		// Print errors
		logger.logDebug(errors);
		
		// Return
		return errors;
	}
	
	/**
	 * Method is responsible to verify tags
	 */
	public List<String> validateActions(String actions) {
		// Creating a list of Actions
		listOfActions = new ArrayList<Action>();
		
		// Prints Actions
		logger.logDebug("Actions ==> " + actions);
		
		// Parse Actions
		errors = parseActions(actions);
		
		// Print errors
		logger.logDebug(errors);
		
		// Return
		return errors;
	}
	
	/**
	 * Parse Actions
	 */
	private List<String> parseActions(String actions) {
		List<String> error = new ArrayList<>();
		if (actions != null && actions.trim().length() > 0) {
			String[] actionList = actions.split("\n");
			
			for (String action : actionList) {
				String act = action.replaceAll("\\s", "");
				act = act.toLowerCase();
				boolean isFound = Boolean.FALSE;
				
				for (String command : commands) {
					isFound = Boolean.FALSE;
					if(act.startsWith(command.toLowerCase()) || act.equalsIgnoreCase(command)) {
						isFound = Boolean.TRUE;
						Action nAction = new Action();
						nAction.setActionName(command);
						boolean isAddRodCommand = Boolean.FALSE;
						//System.out.println(act + " :: Command is ==> " + command);
						switch (command) {
							case "AddRod":
								isAddRodCommand = Boolean.TRUE;
								if(!validateAddRodCommand(act, error)) {
									break;
								}
							case "MinusRod":
								if(!isAddRodCommand && !validateMinusRodCommand(act, error)) {
									break;
								}
								
								String rodNumber = act.substring(command.length(), act.indexOf("bead"));
								Integer rNum = Integer.valueOf(rodNumber.trim());
								nAction.setRodNumber(rNum);
								
								if(act.length() == 15 || act.length() == 13) { // IF NO FINGER
									String beadNumber = act.substring(act.indexOf("bead") + 4, act.indexOf("bead") + 5);
									Integer bNum = Integer.valueOf(beadNumber.trim());
									nAction.setBeadNumber(bNum);
								} else {
									String beadNumber = act.substring(act.indexOf("bead") + 4, act.indexOf("use"));
									Integer bNum = Integer.valueOf(beadNumber.trim());
									nAction.setBeadNumber(bNum);
									
									String finger = act.substring(act.indexOf("use"));
									nAction.setFinger(finger);
								}
								
								break;
							case "Wait":
								if(!validateWaitCommand(act, error)) {
									break;
								}
								String number = act.substring(act.indexOf("wait") + 4);
								Integer num = Integer.valueOf(number.trim());
								nAction.setNumber(num);
								break;
							case "HighlightRod":
								if(!validateHighlightRodCommand(act, error)) {
									break;
								}
								// set action Name
								nAction.setActionName("HighlightRod");
								
								// get Rod Number
								nAction.setRodNumber(Integer.valueOf(act.substring(12, 14)));
								
								// get bead Number
								int lenHighlightRod = act.length();
								if(lenHighlightRod == 19 || lenHighlightRod == 33 || lenHighlightRod == 34) {
									nAction.setBeadNumber(Integer.valueOf(act.substring(18, 19)));
								}
								
								// get finger
								if(lenHighlightRod > 19) {
									if(lenHighlightRod == 28 || lenHighlightRod == 29) {
										nAction.setFinger(act.substring(14));
									} else if(lenHighlightRod == 33 || lenHighlightRod == 34) {
										nAction.setFinger(act.substring(19));
									} 
								}
								
								break;
								
							case "HighlightFrame":
							case "HighlightRods":
							case "HighlightBeam":
							case "HighlightLowerBeads":
							case "HighlightUpperBeads":
							case "HighlightDots":
							case "StopBlink":
							case "ShowAllLabels":
							case "HideAllLabels":
							case "ShowLBL_Rod":
							case "ShowLBL_Lbeads":
							case "ShowLBL_Ubeads":
							case "ShowLBL_Frame":
							case "HidePlaceValue":
							case "Reset":
								validateSingleCommands(act, error);
								break;
								
							case "Display":
								if(!validateDisplayCommand(act, error)) {
									break;
								}
								String display = act.substring(act.indexOf("display") + 7);
								Integer disp = Integer.valueOf(display.trim());
								nAction.setNumber(disp);
								break;
								
							case "BlinkRod":
								if(!validateBlinkRodCommand(act, error)) {
									break;
								}
								// set action Name
								nAction.setActionName("BlinkRod");
								
								// get Rod Number
								nAction.setRodNumber(Integer.valueOf(act.substring(8, 10)));
								
								// get bead Number
								int lenBlinkRod = act.length();
								if(lenBlinkRod == 15 || lenBlinkRod == 29 || lenBlinkRod == 30) {
									nAction.setBeadNumber(Integer.valueOf(act.substring(14, 15)));
								}
								
								// get finger
								if(lenBlinkRod > 15) {
									if(lenBlinkRod == 24 || lenBlinkRod == 25) {
										nAction.setFinger(act.substring(10));
									} else if(lenBlinkRod == 28 || lenBlinkRod == 29) {
										nAction.setFinger(act.substring(15));
									} 
								}
								break;
							case "AbacusRods":
								if(!validateAbacusRodsCommand(act, error)) {
									break;
								}
								
								// get Rod Number
								nAction.setRodNumber(Integer.valueOf(act.substring(10, 12)));
								break;
							case "SubP":
								validateSubjectDetailsCommand(act, error);
								break;
						}
						listOfActions.add(nAction);
					}
					if(isFound) {
						break;
					} 
				}
				if(!isFound) {
					error.add(act + " not a valid Action. Please validate (777) !!!");
				}
				
			}
		}
		return error;
	}
	
	/**
	 * AddROD04Bead3 = 13, AddROD04Bead3UseLeftThumb = 25, AddROD04Bead3UseRightThumb = 26, AddROD04Bead3UseLeftThumbIndex = 30,
	 * AddROD04Bead3UseRightThumbIndex = 31, AddROD04Bead3UseLeftIndex = 25, AddROD04Bead3UseRightIndex = 26, AddROD04Bead3UseRightPointer = 28,
	 * AddROD04Bead3UseLeftPointer = 27
	 */
	private boolean validateAddRodCommand(String action, List<String> errors) {
		int len = action.length();
		if(len == 13 || len == 25 || len == 27 || len == 28 || len == 30 || len == 26 || len == 31) {
			
			// Verify rod and bead numbers
			//System.out.println("action ==> " + action);
			String rodNumber = action.substring(6, 8);
			String beadNumber = action.substring(12, 13);
			try {
				Integer.valueOf(rodNumber);
				Integer.valueOf(beadNumber);
			} catch (NumberFormatException nfe) {
				errors.add(action + " doesn't contains valid rod or beab number. Please validate!!!");
				return Boolean.FALSE;
			}
			
			String command = "";
			
			// Get the command
			if(len == 13) {
				command = action.substring(0, 6) + action.substring(8, 12);
			} else {
				command = action.substring(0, 6) + action.substring(8, 12) + action.substring(13);
			}
			logger.logDebug("Extracted Command : " + command);
			
			
			if(command.equalsIgnoreCase("AddRODBead") || command.equalsIgnoreCase("AddRODBeadUseLeftThumb")
					|| command.equalsIgnoreCase("AddRODBeadUseRightThumb") || command.equalsIgnoreCase("AddRODBeadUseLeftThumbIndex")
					|| command.equalsIgnoreCase("AddRODBeadUseRightThumbIndex") || command.equalsIgnoreCase("AddRODBeadUseLeftIndex")
					|| command.equalsIgnoreCase("AddRODBeadUseRightIndex") || command.equalsIgnoreCase("AddRODBeadUseRightPointer")
					|| command.equalsIgnoreCase("AddRODBeadUseLeftPointer")) {
				logger.logDebug(action + " is Good");
			} else {
				errors.add(action + " not a valid action. Please validate (001)!!!");
				return Boolean.FALSE;
			}
		} else {
			errors.add(action + " not a valid action. Please validate (002)!!!");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	/**
	 * MinusROD04Bead3 = 15, MinusROD04Bead3UseLeftThumb = 27, MinusROD04Bead3UseRightThumb = 28, MinusROD04Bead3UseLeftThumbIndex = 32,
	 * MinusROD04Bead3UseRightThumbIndex = 33, MinusROD04Bead3UseLeftIndex = 27, MinusROD04Bead3UseRightIndex = 28, MinusROD04Bead3UseRightPointer = 30,
	 * MinusROD04Bead3UseLeftPointer = 26
	 */
	private boolean validateMinusRodCommand(String action, List<String> errors) {
		int len = action.length();
		if(len == 15 || len == 29 || len == 27 || len == 28 || len == 30 || len == 32 || len == 33) {
			
			// Verify rod and bead numbers
			String rodNumber = action.substring(8, 10);
			String beadNumber = action.substring(14, 15);
			try {
				Integer.valueOf(rodNumber);
				Integer.valueOf(beadNumber);
			} catch (NumberFormatException nfe) {
				errors.add(action + " doesn't contains valid rod or beab number. Please validate!!!");
				return Boolean.FALSE;
			}
			
			String command = "";
			
			// Get the command
			if(len == 15) {
				command = action.substring(0, 8) + action.substring(10, 14);
			} else {
				command = action.substring(0, 8) + action.substring(10, 14) + action.substring(15);
			}
			logger.logDebug("Extracted Command : " + command);
			
			if(command.equalsIgnoreCase("MinusRODBead") || command.equalsIgnoreCase("MinusRODBeadUseLeftThumb")
					|| command.equalsIgnoreCase("MinusRODBeadUseRightThumb") || command.equalsIgnoreCase("MinusRODBeadUseLeftThumbIndex")
					|| command.equalsIgnoreCase("MinusRODBeadUseRightThumbIndex") || command.equalsIgnoreCase("MinusRODBeadUseLeftIndex")
					|| command.equalsIgnoreCase("MinusRODBeadUseRightIndex") || command.equalsIgnoreCase("MinusRODBeadUseRightPointer")
					|| command.equalsIgnoreCase("MinusRODBeadUseLeftPointer")) {
				logger.logDebug(action + " is Good");
			} else {
				errors.add(action + " not a valid action. Please validate (003)!!!");
				return Boolean.FALSE;
			}
		} else {
			errors.add(action + " not a valid action. Please validate (004)!!!");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	/**
	 * Validate Wait Command
	 */
	private boolean validateWaitCommand(String action, List<String> errors) {
		
		int len = action.length();
		if(len != 5) {
			errors.add(action + " is not valid. Please validate!!!.");
			return Boolean.FALSE;
		}
		
		String waitNum = action.substring(4, 5);
		try {
			Integer.valueOf(waitNum);
		} catch (NumberFormatException nfe) {
			errors.add(action + " doesn't contains valid wait time. Please validate (005)!!!");
			return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
	}
	
	/**
	 * Validate Single Commands
	 */
	private boolean validateSingleCommands(String action, List<String> errors) {
		if(action.equalsIgnoreCase("HighlightFrame") || action.equalsIgnoreCase("HighlightRods")
				|| action.equalsIgnoreCase("HighlightBeam") || action.equalsIgnoreCase("HighlightLowerBeads")
				|| action.equalsIgnoreCase("HighlightUpperBeads") || action.equalsIgnoreCase("HighlightDots")
				|| action.equalsIgnoreCase("Reset") || action.equalsIgnoreCase("StopBlink")
				|| action.equalsIgnoreCase("ShowAllLabels") || action.equalsIgnoreCase("HideAllLabels")
				|| action.equalsIgnoreCase("ShowLBL_Rod") || action.equalsIgnoreCase("ShowLBL_Lbeads")
				|| action.equalsIgnoreCase("ShowLBL_Ubeads") || action.equalsIgnoreCase("ShowLBL_Frame")
				|| action.equalsIgnoreCase("HidePlaceValue")) {
			logger.logDebug(action + " is valid command");
		} else {
			errors.add(action + " not a Valid command. Please validate (001)!!!");
			return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
	}
	
	/**
	 * Validate HightlightRod command
	 * HighlightRod04 = 14, HighlightRod04UseRightPointer = 29, HighlightRod04Bead5 = 19, HighlightRod04Bead3UseLeftPointer = 33
	 * HighlightRod04UseLeftPointer = 28, HighlightRod04Bead3UseRightPointer = 34
	 */
	private boolean validateHighlightRodCommand(String action, List<String> errors) {
		int len = action.length();
		if(len == 14 || len == 28 || len == 29 || len == 19 || len == 33 || len == 34) {
			// Validate Rod Number
			String number = action.substring(12, 14);
			try {
				Integer.valueOf(number);
			} catch (NumberFormatException nfe) {
				errors.add(action + " doesn't contains valid rod number. Please validate (006)!!!");
				return Boolean.FALSE;
			}
			
			// Validate Bead Number
			if(len == 19 || len == 33 || len == 34) {
				number = action.substring(18, 19);
				try {
					Integer.valueOf(number);
				} catch (NumberFormatException nfe) {
					errors.add(action + " doesn't contains valid bead number. Please validate (007)!!!");
					return Boolean.FALSE;
				}
			}
			
			String command = "";
			// Get the command
			if(len == 14) {
				command = action.substring(0, 12);
			} else if(len == 19){
				command = action.substring(0, 12) + action.substring(14, 18);
			} else if(len == 29 || len == 28){
				command = action.substring(0, 12) + action.substring(14);
			} else {
				command = action.substring(0, 12) + action.substring(14, 18) + action.substring(19);
			}
			logger.logDebug("Extracted Command : " + command);
			
			if(command.equalsIgnoreCase("HighlightRod") || command.equalsIgnoreCase("HighlightRodUseRightPointer")
					|| command.equalsIgnoreCase("HighlightRodBead") || command.equalsIgnoreCase("HighlightRodBeadUseLeftPointer")
					|| command.equalsIgnoreCase("HighlightRodUseLeftPointer") || command.equalsIgnoreCase("HighlightRodBeadUseRightPointer")) {
				logger.logDebug(command + " is valid command");
			} else {
				errors.add(action + " not a Valid command. Please validate (002)!!!");
				return Boolean.FALSE;
			}
		} else {
			errors.add(action + " not a valid action. Please validate (006)!!!");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	/**
	 * Validate Wait Command
	 * Display4
	 */
	private boolean validateDisplayCommand(String action, List<String> errors) {
		String displayNum = action.substring(7);
		try {
			Integer.valueOf(displayNum);
		} catch (NumberFormatException nfe) {
			errors.add(action + " doesn't contains valid display count. Please validate (007)!!!");
			return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
	}
	
	/**
	 * Validate BlinkRod command
	 * BlinkRod04 = 10, BlinkRod04UseRightPointer = 25, BlinkRod04Bead5 = 15, BlinkRod04Bead3UseLeftPointer = 29
	 * BlinkRod04UseLeftPointer = 24, BlinkRod04Bead3UseRightPointer = 30
	 */
	private boolean validateBlinkRodCommand(String action, List<String> errors) {
		int len = action.length();
		if(len == 10 || len == 25 || len == 15 || len == 29 || len == 24 || len == 30) {
			// Validate Rod Number
			String number = action.substring(8, 10);
			try {
				Integer.valueOf(number);
			} catch (NumberFormatException nfe) {
				errors.add(action + " doesn't contains valid rod number. Please validate (008)!!!");
				return Boolean.FALSE;
			}
			
			// Validate Bead Number
			if(len == 15 || len == 29 || len == 30) {
				number = action.substring(14, 15);
				try {
					Integer.valueOf(number);
				} catch (NumberFormatException nfe) {
					errors.add(action + " doesn't contains valid bead number. Please validate (009)!!!");
					return Boolean.FALSE;
				}
			}
			
			String command = "";
			// Get the command
			if(len == 10) {
				command = action.substring(0, 8);
			} else if(len == 15){
				command = action.substring(0, 8) + action.substring(10, 14);
			} else if(len == 24 || len == 25){
				command = action.substring(0, 8) + action.substring(10);
			} else {
				command = action.substring(0, 8) + action.substring(10, 14) + action.substring(15);
			}
			logger.logDebug("Extracted Command : " + command);
			
			if(command.equalsIgnoreCase("BlinkRod") || command.equalsIgnoreCase("BlinkRodBead")
					|| command.equalsIgnoreCase("BlinkRodBeadUseRightPointer") || command.equalsIgnoreCase("BlinkRodBeadUseLeftPointer")
					|| command.equalsIgnoreCase("BlinkRodUseRightPointer") || command.equalsIgnoreCase("BlinkRodUseLeftPointer")) {
				logger.logDebug(action + " is valid command");
			} else {
				errors.add(action + " not a Valid command. Please validate (002)!!!");
				return Boolean.FALSE;
			}
		} else {
			errors.add(action + " not a valid action. Please validate (006)!!!");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	/**
	 * AbacusRods10 = 12
	 */
	private boolean validateAbacusRodsCommand(String action, List<String> errors) {
		int len = action.length();
		if(len == 12) {
			// Validate Rod Number
			String number = action.substring(10, 12);
			try {
				Integer.valueOf(number);
			} catch (NumberFormatException nfe) {
				errors.add(action + " doesn't contains valid rod number. Please validate (011)!!!");
				return Boolean.FALSE;
			}
			
			// Get the command
			String command = action.substring(0, 10);
			logger.logDebug("Extracted Command : " + command);
			
			if(command.equalsIgnoreCase("AbacusRods")) {
				logger.logDebug(action + " is valid command");
			} else {
				errors.add(action + " not a Valid command. Please validate (012)!!!");
				return Boolean.FALSE;
			}
		} else {
			errors.add(action + " not a valid action. Please validate (013)!!!");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	/**
	 * Validate Subject Details
	 */
	private boolean validateSubjectDetailsCommand(String action, List<String> errors) {
		
		int subPPos = action.indexOf("subp");
		int gradePos = action.indexOf("grade");
		int topicPos = action.indexOf("topic");
		int chapter = action.indexOf("chapter");
		int section = action.indexOf("section");
		
		if(subPPos == -1 || gradePos == -1 || topicPos == -1 || chapter == -1 || section == -1) {
			errors.add(action + " Not a valid statement. Sequence should be => SubP -> Grade -> Topic -> Chapter -> Section. Please validate (013)!!!");
			return Boolean.FALSE;
		}
		
		if(!(section > chapter && chapter > topicPos && topicPos > gradePos && gradePos > subPPos)) {
			errors.add(action + " Not a valid statement. Sequence should be => SubP -> Grade -> Topic -> Chapter -> Section. Please validate (013)!!!");
			return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
	}

	/**
	 * @return the listOfValidTags
	 */
	public List<String> getListOfValidTags() {
		return listOfValidTags;
	}

	/**
	 * @param listOfValidTags the listOfValidTags to set
	 */
	public void setListOfValidTags(List<String> listOfValidTags) {
		this.listOfValidTags = listOfValidTags;
	}

	/**
	 * @param noClosingTags the noClosingTags to set
	 */
	public void setNoClosingTags(List<String> noClosingTags) {
		this.noClosingTags = noClosingTags;
	}

	/**
	 * @param commands the commands to set
	 */
	public void setCommands(List<String> commands) {
		this.commands = commands;
	}

	/**
	 * @return the listOfActions
	 */
	public List<Action> getListOfActions() {
		return listOfActions;
	}
	
}
