package com.sensei.EasyCalc;

import javax.swing.*;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SettingsPanel extends JPanel {

	private JComboBox<String> numTypeComboBox = null;
	private static String[] numTypes = { "Arithmetic", "Time" };
	
	public SettingsPanel() {
		setUpUI(); 
	}
	
	private void setUpUI() {
		createComponents();
		putComponents();
	}
	
	private void createComponents() {
		numTypeComboBox = new JComboBox<String>( numTypes );
		numTypeComboBox.setVisible( false );
	}
	
	private void putComponents() {
		super.add( numTypeComboBox );
	}
}
