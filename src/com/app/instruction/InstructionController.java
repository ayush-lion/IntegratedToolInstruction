package com.app.instruction;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.app.instruction.panel.InstructionPanel;
import com.app.utils.InstructionAttributeLoader;

public class InstructionController {
	ActorStudent actor_Student;
	ActorTutor actor_Tutor;
	boolean istutor_thinking = false;
	boolean isStudent_thinking = false;

	InstructionStudent instruction_Student;
	InstructionTutor instruction_Tutor;

	InstructionPanel panel;
	boolean isProvided = false;
	Image tutor_image, student_image, tutor_ins, student_ins, student_ins_thinking, tutor_ins_thinking, tutor_thinking,
			student_thinking;


	public InstructionController(InstructionPanel Panel) throws IOException {
		this.panel = Panel;
		actor_Tutor = new ActorTutor();
		actor_Student = new ActorStudent();
		instruction_Student = new InstructionStudent();
		instruction_Tutor = new InstructionTutor();
		this.isProvided = false;
		
		// Initialize_Attributes(null);
	}

	public InstructionController(InstructionPanel Panel, String filename) throws Exception {
		this.panel = Panel;
		actor_Tutor = new ActorTutor();
		actor_Student = new ActorStudent();
		instruction_Student = new InstructionStudent();
		instruction_Tutor = new InstructionTutor();

		// isProvided = true;
		// this.Initialize_Attributes(filename);
	}

	public void Initialize_Attributes(String filename) throws IOException {
		HashMap<String, String> attributes;
		if (filename != null) {
			this.isProvided = true;
			attributes = (HashMap<String, String>) InstructionAttributeLoader.getAllPropertiesFromFile(filename);
		} else {
			attributes = (HashMap<String, String>) InstructionAttributeLoader
					.getAllPropertiesFromResource("properties/instruction.properties");
			System.out.println("" + attributes.toString());
		}

		setTutor_image(getImage(attributes.get("tutor_image")));
		setStudent_image(getImage(attributes.get("student_image")));
		setStudent_ins_thinking(getImage(attributes.get("student_thought_image")));
		setStudent_ins(getImage(attributes.get("student_speech_image")));
		setTutor_ins_thinking(getImage(attributes.get("tutor_thought_image")));
		setTutor_ins(getImage(attributes.get("tutor_speech_image")));
		setStudent_text(attributes.get("student_text"));
		setTutor_text(attributes.get("tutor_text"));
		setStudent_thinking(getImage(attributes.get("student_thinking")));
		setTutor_thinking(getImage(attributes.get("tutor_thinking")));
		Initialize_Actors(getTutor_image(), getStudent_image());
		Initialize_Instructions(getTutor_text(), getStudent_text(), getTutor_ins(), getStudent_ins());

	}

	public void DrawInstructionPanel(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);

		Draw_Actor_Student(g);
		Draw_Actor_Tutor(g);
		Draw_Instruction_Student(g);
		Draw_Instruction_Tutor(g);
	}

	private Image getImage(String fileName) throws IOException {
		Image image = null;
		if (!isProvided) {
			image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(fileName));
		} else {
			image = ImageIO.read(new File(fileName));
		}
		return image;
	}

	public void Initialize_Actors(Image tutor_image, Image student_image) {
		actor_Tutor.setImage(tutor_image);
		actor_Tutor.setPosX(panel.getX());
		actor_Tutor.setPosY(panel.getY()/(5*6));
		actor_Tutor.setHeight(panel.getHeight()*2/2);
		actor_Tutor.setWidth(panel.getWidth()/5);

		actor_Student.setActor_image(student_image);
		actor_Student.setPosX(panel.getWidth()-panel.getWidth()/5);
		actor_Student.setPosY(panel.getY()/5);
		actor_Student.setHeight(panel.getHeight()*2/3);
		actor_Student.setWidth(panel.getWidth()/6);

	}

	public void Initialize_Instructions(String tutor_text, String student_text, Image tutor, Image student) {
		instruction_Tutor.setIns_text(tutor_text);
		instruction_Tutor.setImage(tutor);
		instruction_Tutor.setPosX(panel.getX()+panel.getWidth()/5);
		instruction_Tutor.setPosY(panel.getY()/5+25);
		instruction_Tutor.setWidth(panel.getWidth()/4);
		instruction_Tutor.setHeight(panel.getHeight()/2+10);
		instruction_Tutor.setSwitchable(true);

		//System.out.println(""+tutor.toString()+""+student.toString());
		
		instruction_Student.setIns_text(student_text);
		instruction_Student.setImage(student);
		instruction_Student.setPosX(panel.getX()+panel.getWidth()/2);
		instruction_Student.setPosY(panel.getY()/5+25);
		instruction_Student.setWidth(panel.getWidth()/4);
		instruction_Student.setHeight(panel.getHeight()/2+10);
		instruction_Student.setSwitchable(true);
	}

	public void ReadyforInstructing() {
		instruction_Tutor.setSwitchable(false);
		instruction_Student.setSwitchable(false);
	}

	public void TutorInstructing(String text) {

		if (istutor_thinking) {

			ReadyforInstructing();
			MakeTutorThink();
			instruction_Tutor.setImage(getTutor_ins_thinking());
			instruction_Tutor.setSwitchable(true);
			instruction_Tutor.setIns_text(text.replaceAll("\\<.*?>", ""));

		} else {
			ReadyforInstructing();
			instruction_Tutor.setImage(getTutor_ins());
			instruction_Tutor.setSwitchable(true);
			instruction_Tutor.setIns_text(text.replaceAll("\\<.*?>", ""));
		}

	}

	public void TutorThinking(String text) {

	}

	public void StudentInstructing(String text) {
		if (isStudent_thinking) {
			ReadyforInstructing();
			MakeStudentThink();
			instruction_Student.setImage(getStudent_ins_thinking());
			instruction_Student.setSwitchable(true);
			instruction_Student.setIns_text(text.replaceAll("\\<.*?>", ""));
		} else {
			ReadyforInstructing();

			instruction_Student.setImage(getStudent_ins());
			instruction_Student.setSwitchable(true);
			instruction_Student.setIns_text(text.replaceAll("\\<.*?>", ""));
		}
	}

	/**
	 * @return the istutor_thinking
	 */
	
	public boolean isIstutor_thinking() {
		return istutor_thinking;
	}

	/**
	 * @param istutor_thinking
	 *            the istutor_thinking to set
	 */
	
	public void setIstutor_thinking(boolean istutor_thinking) {
		this.istutor_thinking = istutor_thinking;
	}

	/**
	 * @return the isStudent_thinking
	 */
	
	public boolean isStudent_thinking() {
		return isStudent_thinking;
	}

	/**
	 * @param isStudent_thinking
	 *            the isStudent_thinking to set
	 */
	
	public void setStudent_thinking(boolean isStudent_thinking) {
		this.isStudent_thinking = isStudent_thinking;
	}

	

	/**
	 * @return the tutor_thinking
	 */
	public Image getTutor_thinking() {
		return tutor_thinking;
	}

	/**
	 * @param tutor_thinking
	 *            the tutor_thinking to set
	 */
	public void setTutor_thinking(Image tutor_thinking) {
		this.tutor_thinking = tutor_thinking;
	}

	/**
	 * @return the student_thinking
	 */
	public Image getStudent_thinking() {
		return student_thinking;
	}

	/**
	 * @param student_thinking
	 *            the student_thinking to set
	 */
	public void setStudent_thinking(Image student_thinking) {
		this.student_thinking = student_thinking;
	}

	String tutor_text, student_text;

	/**
	 * @return the tutor_image
	 */
	public Image getTutor_image() {
		return tutor_image;
	}

	/**
	 * @param tutor_image
	 *            the tutor_image to set
	 */
	public void setTutor_image(Image tutor_image) {
		this.tutor_image = tutor_image;
	}

	/**
	 * @return the student_image
	 */
	public Image getStudent_image() {
		return student_image;
	}

	/**
	 * @param student_image
	 *            the student_image to set
	 */
	public void setStudent_image(Image student_image) {
		this.student_image = student_image;
	}

	/**
	 * @return the tutor_ins
	 */
	public Image getTutor_ins() {
		return tutor_ins;
	}

	/**
	 * @param tutor_ins
	 *            the tutor_ins to set
	 */
	public void setTutor_ins(Image tutor_ins) {
		this.tutor_ins = tutor_ins;
	}

	/**
	 * @return the student_ins
	 */
	public Image getStudent_ins() {
		return student_ins;
	}

	/**
	 * @param student_ins
	 *            the student_ins to set
	 */
	public void setStudent_ins(Image student_ins) {
		this.student_ins = student_ins;
	}

	/**
	 * @return the student_ins_thinking
	 */
	public Image getStudent_ins_thinking() {
		return student_ins_thinking;
	}

	/**
	 * @param student_ins_thinking
	 *            the student_ins_thinking to set
	 */
	public void setStudent_ins_thinking(Image student_ins_thinking) {
		this.student_ins_thinking = student_ins_thinking;
	}

	/**
	 * @return the tutor_ins_thinking
	 */
	public Image getTutor_ins_thinking() {
		return tutor_ins_thinking;
	}

	/**
	 * @param tutor_ins_thinking
	 *            the tutor_ins_thinking to set
	 */
	public void setTutor_ins_thinking(Image tutor_ins_thinking) {
		this.tutor_ins_thinking = tutor_ins_thinking;
	}

	/**
	 * @return the tutor_text
	 */
	public String getTutor_text() {
		return tutor_text;
	}

	/**
	 * @param tutor_text
	 *            the tutor_text to set
	 */
	public void setTutor_text(String tutor_text) {
		this.tutor_text = tutor_text;
	}

	/**
	 * @return the student_text
	 */
	public String getStudent_text() {
		return student_text;
	}

	/**
	 * @param student_text
	 *            the student_text to set
	 */
	public void setStudent_text(String student_text) {
		this.student_text = student_text;
	}
	

	public void StudentThinking(String text) {

	}

	public void MakeTutorThink() {
		actor_Tutor.setImage(tutor_thinking);

	}

	public void MakeStudentThink() {
		actor_Student.setActor_image(student_thinking);
	}

	public void Change_Actor_Student(Image image) {
		actor_Tutor.setImage(image);
	}

	public void Change_Actor_Tutor(Image image) {
		actor_Tutor.setImage(image);
	}

	public void Change_Instruction_Tutor(String text, Image image) {
		instruction_Tutor.setImage(image);
		instruction_Tutor.setIns_text(text);
	}

	public void Change_Instruction_Student(String text, Image image) {
		instruction_Student.setIns_text(text);
		instruction_Tutor.setImage(image);
	}

	public void Draw_Actor_Tutor(Graphics g) {
		actor_Student.drawActor(g);
	}

	public void Draw_Actor_Student(Graphics g) {
		actor_Tutor.drawActor(g);
	}

	public void Draw_Instruction_Tutor(Graphics g) {
		instruction_Tutor.draw_instruction(g);
	}

	public void Draw_Instruction_Student(Graphics g) {
		instruction_Student.draw_instruction(g);
	}

	public void Draw_Intruction_Panel(Graphics g) {
		Draw_Actor_Tutor(g);
		Draw_Actor_Student(g);
		Draw_Instruction_Tutor(g);
		Draw_Instruction_Student(g);
	}
}
