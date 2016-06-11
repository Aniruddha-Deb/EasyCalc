package com.sensei.EasyCalc;

import java.awt.BorderLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	
	public ControlPanel( JPanel settingsPanel, JPanel inputPanel ) {
		super.setLayout( new BorderLayout() );
		super.add( settingsPanel, BorderLayout.NORTH );
		super.add( inputPanel, BorderLayout.CENTER );
	}

}
