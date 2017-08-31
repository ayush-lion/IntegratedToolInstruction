/**
 * 
 */
package com.app.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.app.instructions.compiler.Action;
import com.app.instructions.compiler.InstructionCompiler;
import com.app.instructions.compiler.exception.CompilerException;

/**
 * @author prashant.joshi
 *
 */
public class TestCompilerFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtField;
	private JButton button;
	private JButton loadSheet;
	private JTable table;
	private JPanel panel;
	private InstructionCompiler complier;
	
	public TestCompilerFrame() {
		
        this.setLocation(50, 50);
        this.setResizable(false);
        this.setLayout(null);
		this.setBounds(100, 100, 1050, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Abacus Instructions compiler");     
		
        txtField = new JTextField();
        txtField.setBounds(10, 10, 800 , 40);
        
        loadSheet = new JButton("Load Sheet");
        loadSheet.setBounds(810, 10, 100 , 40);
        
        button = new JButton("Compile");
        button.setBounds(920, 10, 100 , 40);
        //create table with data
        
        panel = new JPanel();
        panel.setBackground(Color.RED);
        panel.setBounds(10, 100, this.getWidth() - 20, this.getHeight() - 150);
        //add the table to the frame
        
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
        
        table.setPreferredScrollableViewportSize(new Dimension(panel.getWidth() - 20, panel.getHeight() - 40));
        panel.add(new JScrollPane(table));
        
        this.add(txtField);
        this.add(button);
        this.add(loadSheet);
        this.add(panel);
        
        button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				compile();
			}
		});
        
        loadSheet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel FILES", "xlsx", "Excel");
				jFileChooser.setFileFilter(filter);
				int result = jFileChooser.showOpenDialog(new JFrame());
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jFileChooser.getSelectedFile();
					txtField.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		
        
        this.setVisible(true);
	}
	
	private void compile() {
		try {
			complier = new InstructionCompiler(txtField.getText());
			boolean isAllSet = complier.compileInstructions();
			if(!isAllSet) {
				JOptionPane.showMessageDialog(null, "Found Errors!!!. Please resolve!!!", "InfoBox: Abacus Compiler", JOptionPane.INFORMATION_MESSAGE);
				displayErrors(complier.getMapOfErrors());
			} else {
				JOptionPane.showMessageDialog(null, "No Errors!!!.", "InfoBox: Abacus Compiler", JOptionPane.INFORMATION_MESSAGE);
				displayInstructions(complier.getInstructionData());
			}
		} catch (CompilerException e1) { e1.printStackTrace(); }
	}
	
	private void displayErrors(LinkedHashMap<String, HashMap<String, List<String>>> errors) {
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
	}
	
	/**
	 * Method is responsible to print Map's data
	 */
	private void displayInstructions(LinkedHashMap<String, HashMap<String, List<Action>>> instructionData) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.getDataVector().removeAllElements();
		
		Set<Entry<String, HashMap<String, List<Action>>>> entrySet = instructionData.entrySet();
		for (Entry<String, HashMap<String, List<Action>>> entry : entrySet) {
			Object[] tableRow = new Object[3];
			String key = entry.getKey();
			tableRow[0] = key;
			
			HashMap<String, List<Action>> map = entry.getValue();
			Set<Entry<String, List<Action>>> sEntry =  map.entrySet();
			for (Entry<String, List<Action>> entry2 : sEntry) {
				String instruction = entry2.getKey();
				tableRow[1] = instruction;
				
				List<Action> listOfActions = entry2.getValue();
				StringBuffer actBuf = new StringBuffer(); 
				for (Action action : listOfActions) {
					actBuf.append(action.toString());
				}
				tableRow[2] = actBuf.toString();
			}
			model.addRow(tableRow);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TestCompilerFrame();
	}

}
