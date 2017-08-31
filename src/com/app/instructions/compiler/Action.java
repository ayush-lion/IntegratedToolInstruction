/**
 * 
 */
package com.app.instructions.compiler;

/**
 * @author prashant.joshi (198joshi@gmail.com)
 * @version 08-Aug-2017
 */
public class Action {

	// Data members
	private String actionName;
	private Integer rodNumber;
	private Integer beadNumber;
	private Integer number;
	private String finger;
	
	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}
	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	/**
	 * @return the rodNumber
	 */
	public Integer getRodNumber() {
		return rodNumber;
	}
	/**
	 * @param rodNumber the rodNumber to set
	 */
	public void setRodNumber(Integer rodNumber) {
		this.rodNumber = rodNumber;
	}
	/**
	 * @return the beadNumber
	 */
	public Integer getBeadNumber() {
		return beadNumber;
	}
	/**
	 * @param beadNumber the beadNumber to set
	 */
	public void setBeadNumber(Integer beadNumber) {
		this.beadNumber = beadNumber;
	}
	/**
	 * @return the number
	 */
	public Integer getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}
	/**
	 * @return the finger
	 */
	public String getFinger() {
		return finger;
	}
	/**
	 * @param finger the finger to set
	 */
	public void setFinger(String finger) {
		this.finger = finger;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("Action Name=> ").append(actionName).append(" :: ");
		buf.append("RodNumber=> ").append(rodNumber).append(" :: ");
		buf.append("BeadNumber=> ").append(beadNumber).append(" :: ");
		buf.append("Number=> ").append(number).append(" :: ");
		buf.append("Finger=> ").append(finger).append("\n");
		return buf.toString();
	}

}
