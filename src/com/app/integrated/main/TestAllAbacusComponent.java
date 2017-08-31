/**
 * 
 */
package com.app.integrated.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.app.abacus.Finger;
import com.app.abacus.panel.AbacusPanel;
import com.app.abacus.panel.exception.AbacusException;
import com.app.instruction.panel.InstructionPanel;
import com.app.instructions.compiler.Action;
import com.app.instructions.compiler.InstructionCompiler;
import com.app.instructions.compiler.exception.CompilerException;
import com.app.sound.DownloadSpeech;
import com.app.test.TextAreaRenderer;

/**
 * @author prashant.joshi
 *
 */
public class TestAllAbacusComponent extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	// Top panels
	private JPanel abacusTopPanel;

	private JMenuBar menuBar;
	private JMenu menu;
	private JCheckBoxMenuItem robotics;
	private JMenu natural;
	private JCheckBoxMenuItem Sharon;
	private JCheckBoxMenuItem Rachel;
	private JCheckBoxMenuItem Deepa;
	private ButtonGroup vGroup = null;
	private JRadioButton voice1 = new JRadioButton("Sharon");
    private JRadioButton voice2 = new JRadioButton("Rachel");
    private JRadioButton voice3 = new JRadioButton("Deepa");
    private boolean isPlayRobotics=false;
	private boolean isPlayNatural=true;
	private DownloadSpeech downloadSpeech;
	
	
	private AbacusPanel panel;
	private JPanel tablepanel;
	private JPanel playPanel;
	String filenameatt;
	//Abacus Top Panel
	private JCheckBox doWeNeedFrame;
	private JCheckBox doWeNeedFingers;
	private JTextField attrTxt;
	private JButton loadAbacus;
	private JButton showAbacus;
	private JButton stopAbacus;
	
	private JButton killButton;
	//Abacus Bead Up Panel


	private String abacuspath;
	private String instructionpath;
	private boolean isabacusprovided;
	private boolean isinstructionprovided;
	/**
	 * @return the instructionpath
	 */
	public String getInstructionpath() {
		return instructionpath;
	}

	/**
	 * @param instructionpath the instructionpath to set
	 */
	public void setInstructionpath(String instructionpath) {
		this.instructionpath = instructionpath;
	}

	/**
	 * @return the isabacusprovided
	 */
	public boolean isIsabacusprovided() {
		return isabacusprovided;
	}

	/**
	 * @param isabacusprovided the isabacusprovided to set
	 */
	public void setIsabacusprovided(boolean isabacusprovided) {
		this.isabacusprovided = isabacusprovided;
	}

	/**
	 * @return the isinstructionprovided
	 */
	public boolean isIsinstructionprovided() {
		return isinstructionprovided;
	}

	/**
	 * @param isinstructionprovided the isinstructionprovided to set
	 */
	public void setIsinstructionprovided(boolean isinstructionprovided) {
		this.isinstructionprovided = isinstructionprovided;
	}

	/**
	 * @return the excelfile
	 */
	public String getExcelfile() {
		return excelfile;
	}

	/**
	 * @param excelfile the excelfile to set
	 */
	public void setExcelfile(String excelfile) {
		this.excelfile = excelfile;
	}

	/**
	 * @return the abacusprop
	 */
	public JCheckBoxMenuItem getAbacusprop() {
		return abacusprop;
	}

	/**
	 * @param abacusprop the abacusprop to set
	 */
	public void setAbacusprop(JCheckBoxMenuItem abacusprop) {
		this.abacusprop = abacusprop;
	}

	private String excelfile;
	private InstructionPanel instructionpanel;
	private JMenu menu1;

	private JCheckBoxMenuItem abacusprop;

	private JCheckBoxMenuItem instprop;
	
	private Performer performer;

	private InstructionCompiler complier;

	private JMenu menu2;

	private JCheckBoxMenuItem loadinstructionfile;

	private JCheckBoxMenuItem startcheck;

	private JTable table;

	private JCheckBoxMenuItem refresh;

	private JCheckBoxMenuItem stop;
	
	public TestAllAbacusComponent() throws Throwable {
		try {
			this.getContentPane().setLayout(null);
			this.setResizable(false);
			this.setTitle("Abacus. Lets start learning mind math !!!");
			this.setBounds(100, 50, 1030, 665);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setBackground(Color.WHITE);
			
			/* Create Abacus Panel */
			panel = new AbacusPanel();
			panel.setBounds(5, 0, 1030, 330);
			setUpAbacusTopPanel();
			instructionpanel = new InstructionPanel();
			instructionpanel.setBounds(5, 331,1030,280);
			/* Create Abacus Play Controls */
			setupPlayPanel(); 
			
			/* Add Components to Frame */
			setupMenuBar();
			this.setJMenuBar(menuBar);
			this.getContentPane().add(abacusTopPanel);
			this.getContentPane().add(panel);
			this.getContentPane().add(instructionpanel);
			instructionpanel.setLayout(null);
		//	playPanel.repaint();
			
			this.setVisible(true);
			
			//panel.initializeAbacus();
		} catch ( Exception e ) { e.printStackTrace(); }
	
		tablepanel = new JPanel();
        tablepanel.setBackground(Color.RED);
        tablepanel.setBounds(10, 100, this.getWidth() - 20, this.getHeight() - 150);
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
        
        table.setPreferredScrollableViewportSize(new Dimension(tablepanel.getWidth() - 20, tablepanel.getHeight() - 40));
        tablepanel.add(new JScrollPane(table));
	
	}
	
	private void enableNaturalVoices(boolean enable) {
		Sharon.setEnabled(enable);
		Rachel.setEnabled(enable);
		Deepa.setEnabled(enable);
		
		if(enable) {
			Sharon.setSelected(true);
			Rachel.setSelected(false);
			Deepa.setSelected(false);
		} else {
			Sharon.setSelected(false);
			Rachel.setSelected(false);
			Deepa.setSelected(false);
		}
	}
	private void setupMenuBar() {
		menuBar = new JMenuBar();
		menu = new JMenu("Voices");
		menu.setMnemonic(KeyEvent.VK_V);
		menuBar.add(menu);
		
		natural = new JMenu("Natural");
		natural.setMnemonic(KeyEvent.VK_N);
		natural.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				robotics.setSelected(false);
				isPlayRobotics = false;
				isPlayNatural = true;
				
				/** Disabling the voice selection pane */
				enableNaturalVoices(true);
				
				killButton.setEnabled(true);
			}
		});
		menu.add(natural);
		
		robotics = new JCheckBoxMenuItem("Robotics");
		robotics.setMnemonic(KeyEvent.VK_R);
		robotics.setSelected(true);
		robotics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				natural.setSelected(false);
				isPlayRobotics = true;
				isPlayNatural = false;
				
				/** Disabling the voice selection pane */
				enableNaturalVoices(false);
				
				killButton.setEnabled(false);
			}
		});
		menu.add(robotics);
		
		Sharon = new JCheckBoxMenuItem("Sharon");
		Sharon.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				robotics.setSelected(false);
				isPlayRobotics = false;
				isPlayNatural = true;
				
				/** Disabling the voice selection pane */
				Rachel.setSelected(false);
				Deepa.setSelected(false);
				
				
				killButton.setEnabled(true);
			}
		});
		natural.add(Sharon);
		
		Rachel = new JCheckBoxMenuItem("Rachel");
		Rachel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotics.setSelected(false);
				isPlayRobotics = false;
				isPlayNatural = true;
				
				/** Disabling the voice selection pane */
				Sharon.setSelected(false);
				Deepa.setSelected(false);
				
				
				killButton.setEnabled(true);
			}
		});
		natural.add(Rachel);
		
		Deepa = new JCheckBoxMenuItem("Deepa");
		Deepa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotics.setSelected(false);
				isPlayRobotics = false;
				isPlayNatural = true;
				
				/** Disabling the voice selection pane */
				Sharon.setSelected(false);
				Rachel.setSelected(false);
				
				
				killButton.setEnabled(true);
			}
		});
		natural.add(Deepa);
		
		

		menu1 = new JMenu("Properties");
		menu1.setMnemonic(KeyEvent.VK_V);
		abacusprop = new JCheckBoxMenuItem("Load Abacus Properties");
		abacusprop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					abacusprop.setSelected(false);
				try {
					JFileChooser jFileChooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Properties FILES", "properties");
					jFileChooser.setFileFilter(filter);
					int result = jFileChooser.showOpenDialog(new JFrame());
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jFileChooser.getSelectedFile();
						attrTxt.setText(selectedFile.getAbsolutePath());
						panel.setAbacusAttributesFileName(attrTxt.getText());
						showAbacus.setEnabled(true);
						
					}
				} catch (Exception e1) { e1.printStackTrace(); }
				
				
				
			}
		});
		
		instprop = new JCheckBoxMenuItem("Load Instruction Properties");
		instprop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				instprop.setSelected(false);
				// TODO Auto-generated method stub
				try {
					JFileChooser jFileChooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Properties FILES", "properties");
					jFileChooser.setFileFilter(filter);
					int result = jFileChooser.showOpenDialog(new JFrame());
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jFileChooser.getSelectedFile();
						attrTxt.setText(selectedFile.getAbsolutePath());
						setInstructionpath(attrTxt.getText());
						System.out.println("instruction prop"+getInstructionpath());
						
						instructionpanel.setAttributespath(selectedFile.getAbsolutePath());
						//instructionpanel.Initialize_Instruction_Panel(instructionpanel);
					}
				} catch (Exception e1) { e1.printStackTrace(); }
				
				
				
				
			}
		});
		menu1.add(abacusprop);
		menu1.add(instprop);
		menuBar.add(menu1);
		
		menu2 = new JMenu("Start");
		menu2.setMnemonic(KeyEvent.VK_V);
		loadinstructionfile = new JCheckBoxMenuItem("Load File");
		loadinstructionfile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				loadinstructionfile.setSelected(false);
				// TODO Auto-generated method stub
				try {
					JFileChooser jFileChooser = new JFileChooser();
					int result = jFileChooser.showOpenDialog(new JFrame());
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jFileChooser.getSelectedFile();
						attrTxt.setText(selectedFile.getAbsolutePath());
					//	panel.setAbacusAttributesFileName(attrTxt.getText());
					//	showAbacus.setEnabled(true);
					}
				} catch (Exception e1) { e1.printStackTrace(); }
			}
		});
		
		startcheck = new JCheckBoxMenuItem("Start");
		startcheck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				compile(attrTxt.getText().toString());
				startcheck.setSelected(false);
			}
		});
		
		refresh = new JCheckBoxMenuItem("Refresh");
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh.setSelected(false);
				panel.showAbacus();
				panel.repaint();
				
				try {
					instructionpanel.Initialize_Instruction_Panel(instructionpanel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// TODO Auto-generated method stub
				//instructionpanel
				
			}
		});
		
		stop = new JCheckBoxMenuItem("Stop");
		stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//panel.showAbacus();
				performer.stopPlayback();
				try {
					instructionpanel.Initialize_Instruction_Panel(instructionpanel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		menu2.add(loadinstructionfile);
		menu2.add(startcheck);
		menu2.add(refresh);
		menu2.add(stop);
		menuBar.add(menu2);
		
		
		
	}
	
	public void showPanel() throws IOException {
		this.setVisible(true);
		try {
			//panel.hideFingers(Boolean.TRUE);
			panel.initializeAbacus();
			instructionpanel.Initialize_Instruction_Panel(instructionpanel);
			//compile("/Users/Panwar/Desktop/AppInstruction.xlsx");
		} catch (AbacusException e) { e.printStackTrace(); }
	}
	
	private void setupPlayPanel() {
		playPanel = new JPanel(new GridLayout(3, 6));
		
		playPanel.setBounds(0, this.getHeight() - 130, this.getWidth(), 90);
	}
	
	
	private void setUpAbacusTopPanel() {
		abacusTopPanel = new JPanel(new GridLayout(1, 5));
		abacusTopPanel.setBounds(0, 0, 1050, 0);
		
		// Buttons
		loadAbacus = new JButton("Load Abacus Instructions");
		showAbacus = new JButton("Start");
		stopAbacus = new JButton("Stop");
		
		// Frame
		
		doWeNeedFrame = new JCheckBox("Show Frame");
		
		doWeNeedFrame.setSelected(true);
		doWeNeedFrame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(doWeNeedFrame.isSelected()) {
					panel.hideFrame(Boolean.TRUE);
				} else {
					panel.hideFrame(Boolean.FALSE);
				}
			}
		});
		
		// Fingers
		doWeNeedFingers = new JCheckBox("Show Fingers");
		doWeNeedFingers.setSelected(true);
		doWeNeedFingers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(doWeNeedFingers.isSelected()) {
					panel.hideFingers(Boolean.TRUE);
				} else {
					panel.hideFingers(Boolean.FALSE);
				}
			}
		});
		
		attrTxt = new JTextField();
		
		// Show Abacus Button
		//showAbacus.setEnabled(Boolean.FALSE);
		showAbacus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startcompilation(attrTxt.getText());
			}
		});
		
		// Load Abacus
		loadAbacus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser jFileChooser = new JFileChooser();
					int result = jFileChooser.showOpenDialog(new JFrame());
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jFileChooser.getSelectedFile();
						attrTxt.setText(selectedFile.getAbsolutePath());
					//	panel.setAbacusAttributesFileName(attrTxt.getText());
					//	showAbacus.setEnabled(true);
					}
				} catch (Exception e1) { e1.printStackTrace(); }
			}
		});
		
		
		// Add components in Panel
		//abacusTopPanel.add(doWeNeedFrame);
		//abacusTopPanel.add(doWeNeedFingers);
		
		killButton = new JButton("Kill Demo");
		killButton.setBounds(945, 10, 90, 40);
		killButton.setEnabled(false);
		killButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					panel.initializeAbacus();
				} catch (AbacusException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//performer.stopPlayback();
				//fileButton.setEnabled(true);
				//startButton.setEnabled(true);
				
				if(isPlayNatural) {
					voice1.setEnabled(true);
					voice2.setEnabled(true);
					voice3.setEnabled(true);
				}
				
				//startButton.setEnabled(false);
			}
		});
	//	abacusTopPanel.add(attrTxt);
	//	abacusTopPanel.add(loadAbacus);
	//	abacusTopPanel.add(showAbacus);
	}
	
	public void startcompilation(String filename)
	{
		compile(filename);
		
	}
	
	private void compile(String filename) 
	{
		try {
			complier = new InstructionCompiler(filename);
			boolean isAllSet = complier.compileInstructions();
			if(!isAllSet) {
				JOptionPane.showMessageDialog(null, "Found Errors!!!. Please resolve!!!", "InfoBox: Abacus Compiler", JOptionPane.INFORMATION_MESSAGE);
				displayErrors(complier.getMapOfErrors());
			} else {
				JOptionPane.showMessageDialog(null, "No Errors!!!.", "InfoBox: Abacus Compiler", JOptionPane.INFORMATION_MESSAGE);
				start_instructions(complier.getInstructionData());
			}
		} catch (CompilerException e1) { e1.printStackTrace(); }
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

	public void start_instructions(LinkedHashMap<String, HashMap<String, List<Action>>> linkedHashMap)
	{
		
		performer = new Performer();
		performer.setAbacusPanel(panel);
		performer.setInstructionPanel(instructionpanel);
		performer.setData(linkedHashMap);
		performer.setPlayRobotics(isPlayRobotics);
		performer.setPlayNatural(false);
		performer.startReading();
			//odel.addRow(tableRow);
			}

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		TestAllAbacusComponent ob = new TestAllAbacusComponent();
		SwingUtilities.invokeLater(new Runnable() {
		   public void run() {
			   try {
				ob.showPanel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		});
		
	}
}
