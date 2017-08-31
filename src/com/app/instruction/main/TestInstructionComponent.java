package com.app.instruction.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.app.instruction.panel.InstructionPanel;
import com.app.instructions.compiler.InstructionCompiler;
import com.app.instructions.compiler.exception.CompilerException;
import com.app.integrated.main.Performer;
import com.app.test.TextAreaRenderer;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JButton;



public class TestInstructionComponent extends JFrame {

	private JPanel contentPane;
	private InstructionPanel panel;
	/**
	 * @return the panel
	 */
	public InstructionPanel getPanel() {
		return panel;
	}

	/**
	 * @param panel the panel to set
	 */
	public void setPanel(InstructionPanel panel) {
		this.panel = panel;
	}
	
	private JTextField textField;
	private InstructionCompiler complier;
	private JPanel tablepanel;
	private JTable table;
	private String filename;
	private VoiceManager vm;
	private Voice voice;
	InstructionPerformer performer;

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Launch the application.
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		TestInstructionComponent ob = new TestInstructionComponent();
		
		SwingUtilities.invokeLater(new Runnable() {
		   public void run() {
			   try {
				ob.show_panel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		});
	}

	/**
	 * Create the frame.
	 * @throws Throwable 
	 */
	public TestInstructionComponent() throws Throwable {
		this.setBounds(1, 1, 870, 592);
		panel = new InstructionPanel();
		setPanel(panel);

		panel.setBounds(0, 0,864, 419);
		//this.getContentPane().add(panel);
		
		this.getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel controllpanel = new JPanel();
		controllpanel.setBounds(10, 452, 795, 105);
		getContentPane().add(controllpanel);
		controllpanel.setLayout(null);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Tutor Thinking");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				panel.setIstutorthinking(chckbxNewCheckBox.isSelected());
			}
		});
		chckbxNewCheckBox.setBounds(4, 71, 128, 23);
		controllpanel.add(chckbxNewCheckBox);
		
		textField = new JTextField();
		textField.setBounds(133, 70, 335, 26);
		controllpanel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Tutor");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("tutor");
				panel.performinstruction("T:"+textField.getText(), panel);
			}
		});
		btnNewButton.setBounds(4, 30, 117, 29);
		controllpanel.add(btnNewButton);
		JButton btnNewButton_1 = new JButton("Student");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.performinstruction("S:"+textField.getText(),panel);
			}
		});
		btnNewButton_1.setBounds(123, 30, 117, 29);
		controllpanel.add(btnNewButton_1);
		JCheckBox chckbxThinking = new JCheckBox("Student Thinking");
		chckbxThinking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("student");
				panel.setIsstudentthinking(chckbxThinking.isSelected());
			}
		});
		chckbxThinking.setBounds(470, 71, 154, 23);
		controllpanel.add(chckbxThinking);
		
		JButton btnNewButton_2 = new JButton("Upload Sheet");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser jFileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel FILES", "xlsx", "Excel");
				jFileChooser.setFileFilter(filter);
				int result = jFileChooser.showOpenDialog(new JFrame());
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jFileChooser.getSelectedFile();
					textField.setText(selectedFile.getAbsolutePath());
					setFilename(textField.getText().toString());
				}
				
			}
		});
		btnNewButton_2.setBounds(270, 30, 117, 29);
		controllpanel.add(btnNewButton_2);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					compile(textField.getText().toString(), panel);
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		});
		btnStart.setBounds(399, 29, 117, 29);
		controllpanel.add(btnStart);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performer.stopPlayback();
			}
		});
		btnStop.setBounds(541, 30, 117, 29);
		controllpanel.add(btnStop);
		
		tablepanel = new JPanel();
        tablepanel.setBackground(Color.RED);
        tablepanel.setBounds(10, 100, this.getWidth() - 20, this.getHeight() - 150);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Row");
        model.addColumn("Instructions");
        model.addColumn("Actions");
        
       // this.getContentPane().add(new WrappableTable(model));
        table = new JTable(model);
        table.setGridColor(Color.LIGHT_GRAY);
        
        table.getColumn("Instructions").setCellRenderer(new TextAreaRenderer());
        table.getColumn("Actions").setCellRenderer(new TextAreaRenderer());
        
        TableColumn column = table.getColumnModel().getColumn(0);
        column.setPreferredWidth(100);
        
        column = table.getColumnModel().getColumn(1);
        column.setPreferredWidth(300);
        
        column = table.getColumnModel().getColumn(2);
        column.setPreferredWidth(600);
        
        table.setPreferredScrollableViewportSize(new Dimension(tablepanel.getWidth() - 20, tablepanel.getHeight() - 40));
        tablepanel.add(new JScrollPane(table));
    	
	}
	
	
	public void compilesheet(String filename)
	{
		
	}
	
	
	private void compile(String filename, InstructionPanel instruct) throws Throwable 
	{
		try {
			complier = new InstructionCompiler(filename);
			boolean isAllSet = complier.compileInstructions();
			if(!isAllSet) {
				JOptionPane.showMessageDialog(null, "Found Errors!!!. Please resolve!!!", "InfoBox: Abacus Compiler", JOptionPane.INFORMATION_MESSAGE);
				displayErrors(complier.getMapOfErrors());
			} else {
				JOptionPane.showMessageDialog(null, "No Errors!!!.", "InfoBox: Abacus Compiler", JOptionPane.INFORMATION_MESSAGE);
				start_instructions(complier.getInstructionData(), instruct);
			}
		} catch (CompilerException e1) { e1.printStackTrace(); }
	}
	
	
	
	private void start_instructions(
			LinkedHashMap<String, HashMap<String, List<com.app.instructions.compiler.Action>>> instructionData, InstructionPanel instruct) throws Throwable {
		
		//performer.setAbacusPanel(instruct);
		performer = new InstructionPerformer();
		performer.setInstructionPanel(instruct);
		performer.setData(instructionData);
		performer.startReading();
		// TODO Auto-generated method stub
		
	}

	private void displayErrors(LinkedHashMap<String, HashMap<String, List<String>>> errors) {
		// TODO Auto-generated method stub
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.getDataVector().removeAllElements();
		
		Set<Entry<String, HashMap<String, List<String>>>> entrySet = errors.entrySet();
		for (Entry<String, HashMap<String, List<String>>> entry : entrySet) {
			Object[] tableRow = new Object[3];
			String row = entry.getKey();
			tableRow[0] = row;
			
			HashMap<String, List<String>> errorMsgs = entry.getValue();
			List<String> errorList = errorMsgs.get("instructionError");
			StringBuffer insBuf = new StringBuffer(); 
			for (String errorMsg : errorList) {
				System.out.println(errorMsg);
				insBuf.append(errorMsg).append("\n");
			}
			
			tableRow[1] = insBuf.toString();
			
			StringBuffer actBuf = new StringBuffer(); 
			List<String> errorAList = errorMsgs.get("actionError");
			for (String errorMsg : errorAList) {
				actBuf.append(errorMsg).append("\n");
			}
			tableRow[2] = actBuf.toString();
			
			model.addRow(tableRow);
		}
		    
		JOptionPane.showConfirmDialog(null, tablepanel, "Error Details",JOptionPane.CANCEL_OPTION);
	}
	
	

	public void show_panel() throws IOException
	{
		this.setVisible(true);
		
		
		panel.Initialize_Instruction_Panel(panel);
		//panel.repaint();
		//this.panel.setVisible(true);
		
	}
	}
