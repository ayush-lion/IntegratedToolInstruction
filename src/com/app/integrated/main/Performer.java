package com.app.integrated.main;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import com.app.abacus.panel.AbacusPanel;
import com.app.abacus.panel.exception.AbacusException;
import com.app.instruction.panel.InstructionPanel;
import com.app.instructions.compiler.Action;
import com.app.sound.SpeechController;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Performer implements Runnable {

	
	
	LinkedHashMap<String, HashMap<String, List<Action>>> data;

	private Thread readerThread;
	InstructionPanel instructionPanel;
	AbacusPanel abacusPanel;
	private SpeechController playSound;
	private static final String VOICE = "kevin16";
	private VoiceManager vm = null;
	private Voice voice = null;
	private boolean isPlayRobotics;
	/**
	 * @return the isPlayRobotics
	 */
	public boolean isPlayRobotics() {
		return isPlayRobotics;
	}




	/**
	 * @param isPlayRobotics the isPlayRobotics to set
	 */
	public void setPlayRobotics(boolean isPlayRobotics) {
		this.isPlayRobotics = isPlayRobotics;
	}




	/**
	 * @return the isPlayNatural
	 */
	public boolean isPlayNatural() {
		return isPlayNatural;
	}




	/**
	 * @param isPlayNatural the isPlayNatural to set
	 */
	public void setPlayNatural(boolean isPlayNatural) {
		this.isPlayNatural = isPlayNatural;
	}

	private boolean isPlayNatural;
	
	
	public Performer()
	{
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		isPlayRobotics = false;
		isPlayNatural = false;
		
	}
	
	
	
	
	/**
	 * @return the instructionPanel
	 */
	public InstructionPanel getInstructionPanel() {
		return instructionPanel;
	}

	/**
	 * @param instructionPanel the instructionPanel to set
	 */
	public void setInstructionPanel(InstructionPanel instructionPanel) {
		this.instructionPanel = instructionPanel;
	}

	/**
	 * @return the abacusPanel
	 */
	public AbacusPanel getAbacusPanel() {
		return abacusPanel;
	}

	/**
	 * @param abacusPanel the abacusPanel to set
	 */
	public void setAbacusPanel(AbacusPanel abacusPanel) {
		this.abacusPanel = abacusPanel;
	}

	/**
	 * @return the data
	 */
	public LinkedHashMap<String, HashMap<String, List<Action>>> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(LinkedHashMap<String, HashMap<String, List<Action>>> data) {
		this.data = data;
	}

	public void startReading() {
		readerThread = new Thread(this);
		readerThread.start();
	}
	
	
	private void playRoboticsVoice(String insTxt) {
		String[] txt = insTxt.split("\n");
		String txtInput = "";
		for (String data : txt) {
			if (!data.trim().equalsIgnoreCase("")) {
				txtInput = txtInput + data + " ";
			}
		}
		try {
			/** Setting up voice manager */
			vm = VoiceManager.getInstance();
			voice = vm.getVoice("kevin16");
			voice.allocate();
			voice.speak(txtInput);
			voice.deallocate();
	    }catch(Exception e){ e.printStackTrace(); }
		finally {
			//setDataReadyForRead(false);
		}
	}
	
	private void playText(String insTxt, int counter) {
		String[] txt = insTxt.split("\n");
		String txtInput = "";
		for (String data : txt) {
			if (!data.trim().equalsIgnoreCase("")) {
				txtInput = txtInput + data + " \\pau=1000\\ ";
			}
		}

		playSound = new SpeechController();
		try {
			//playSound.Speak("sharon22k", txtInput);
			playSound.playSound("/audio/" + counter + ".wav");
			//setDataReadyForRead(false);
		} catch (IllegalArgumentException e1) { /** Eating exceptions */
		} catch (IllegalStateException e1) { /** Eating exceptions */}
	}
	
	/**
	 * @return the playSound
	 */
	public SpeechController getPlaySound() {
		return playSound;
	}




	/**
	 * @param playSound the playSound to set
	 */
	public void setPlaySound(SpeechController playSound) {
		this.playSound = playSound;
	}




	public void stopPlayback() {
		if(readerThread.isAlive()) {
			readerThread.stop();
			if(getPlaySound()!= null && getPlaySound().getClip() != null) {
				getPlaySound().stopClip();
			}
			
		}
	}
	
	public void start_instructing() throws AbacusException
	{
		Set<Entry<String, HashMap<String, List<Action>>>> entrySet = data.entrySet();
		int i=0;
		for (Entry<String, HashMap<String, List<Action>>> entry : entrySet) {
			Object[] tableRow = new Object[3];
			String key = entry.getKey();
			tableRow[0] = key;
			
			HashMap<String, List<Action>> map = entry.getValue();
			Set<Entry<String, List<Action>>> sEntry =  map.entrySet();
			
			for (Entry<String, List<Action>> entry2 : sEntry) {
				i++;
				String instruction = entry2.getKey();
				instructionPanel.performinstruction(instruction, instructionPanel);

				

					playRoboticsVoice(instruction);
					System.out.println("voice "+i);
					//playText(instruction, i);
				try {
					Thread.sleep(500);
									} catch (InterruptedException e) { e.printStackTrace(); }
				
				List<Action> listOfActions = entry2.getValue();
				StringBuffer actBuf = new StringBuffer(); 
				for (Action action : listOfActions) {
					
					if(action.getActionName().contains("HighlightFrame"))
					{
						abacusPanel.highlightFrame();
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) { e.printStackTrace(); }
					
					}
					else
						if(action.getActionName().contains("HighlightRods"))
						{
							abacusPanel.highlightRods();
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) { e.printStackTrace(); }
							
						}
						else
							if(action.getActionName().contains("HighlightBeam"))
							{
								abacusPanel.highlightLowerBeads();
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) { e.printStackTrace(); }
							
							}
							else
								if(action.getActionName().contains("AddRod"))
								{
									abacusPanel.moveEarthBeadUp(action.getRodNumber(), action.getBeadNumber(), action.getFinger());
									try {
										Thread.sleep(500);
									} catch (InterruptedException e) { e.printStackTrace(); }
				
								}
								else
									if(action.getActionName().contains("MinusRod"))
									{
										abacusPanel.moveEarthBeadDown(action.getRodNumber(), action.getBeadNumber(), action.getFinger());
										try {
											Thread.sleep(500);
										} catch (InterruptedException e) { e.printStackTrace(); }
									
									}
									
								
				
				}
				
			}
			//odel.addRow(tableRow);
		}
	}
	
	@Override
	public void run() {
		try {
			start_instructing();
		} catch (AbacusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}

}
