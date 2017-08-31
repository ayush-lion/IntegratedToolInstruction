package com.app.instruction.main;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.app.abacus.panel.exception.AbacusException;
import com.app.instruction.panel.InstructionPanel;
import com.app.instructions.compiler.Action;
import com.app.sound.SpeechController;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class InstructionPerformer implements Runnable {
	
	private Thread readerThread;
	LinkedHashMap<String, HashMap<String, List<Action>>> data;
	private VoiceManager vm;
	private Voice voice;
	private InstructionPanel instructionPanel;
	

	/**
	 * @return the readerThread
	 */
	public Thread getReaderThread() {
		return readerThread;
	}


	/**
	 * @param readerThread the readerThread to set
	 */
	public void setReaderThread(Thread readerThread) {
		this.readerThread = readerThread;
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


	/**
	 * @return the vm
	 */
	public VoiceManager getVm() {
		return vm;
	}


	/**
	 * @param vm the vm to set
	 */
	public void setVm(VoiceManager vm) {
		this.vm = vm;
	}


	/**
	 * @return the voice
	 */
	public Voice getVoice() {
		return voice;
	}


	/**
	 * @param voice the voice to set
	 */
	public void setVoice(Voice voice) {
		this.voice = voice;
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


	public void startReading() {
		readerThread = new Thread(this);
		readerThread.start();
	}
	
	
	public InstructionPerformer()
	{
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		//isPlayRobotics = false;
		//isPlayNatural = false;
		
	}
	
	
	private void playRoboticsVoice(String insTxt) {
		System.out.println(""+insTxt.toString());
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
	
	/**
	 * @param playSound the playSound to set
	 */

	public void stopPlayback() {
		if(readerThread.isAlive()) {
			readerThread.stop();			
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
				
					playRoboticsVoice(instruction.replace("T:", "").replace("S:", ""));
					System.out.println("voice "+i);
					//playText(instruction, i);
				try {
					Thread.sleep(500);
									} catch (InterruptedException e) { e.printStackTrace(); }
				
				List<Action> listOfActions = entry2.getValue();
				StringBuffer actBuf = new StringBuffer(); 
				for (Action action : listOfActions) {
					
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
