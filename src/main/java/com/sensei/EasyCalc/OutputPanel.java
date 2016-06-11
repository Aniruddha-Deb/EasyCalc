package com.sensei.EasyCalc;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sensei.EasyCalc.core.Token;

@SuppressWarnings("serial")
public class OutputPanel extends JPanel {
	
	private JTextField outputTextField = null;
	private static final Color ERROR_COLOR = new Color( 242, 172, 172 );
	private Color defaultBGColor;
	
	public OutputPanel() {
		setUpUI();
		defaultBGColor = outputTextField.getBackground();
	}
	
	private void setUpUI() {
		createComponents();
		putComponents();
	}
	
	private void createComponents() {
		outputTextField = new JTextField( 10 );
		Font f = new Font( "helvetica", Font.PLAIN, 30 );
		outputTextField.setFont( f );
		outputTextField.setEditable( false );
		outputTextField.setFocusable( false );
	}
	
	private boolean showsError() {
		return outputTextField.getBackground().equals( ERROR_COLOR ) ;
	}
	
	private void putComponents() {
		super.add( outputTextField );
	}
	
	private void appendToOutput( String text ) {
		outputTextField.setText( outputTextField.getText() + text ) ;
	}
	
	public void refreshOutput( ArrayList<Token> tokens, boolean showSeparator ) {
		outputTextField.setText( "" );
		
		if( showsError() ) {
			outputTextField.setBackground( defaultBGColor );
		}
		
		for( Token token : tokens ) {
			outputToken( token, showSeparator );
		}
	}
	
	public void showError() {
		outputTextField.setBackground( ERROR_COLOR );
	}
	
	private void outputToken( Token token, boolean showSeparator ) {
		
		String separator = "" ;
		if( showSeparator ) {
			separator = " " ;
		}
		
		if( token.getTokenValue().equals( "*" ) ) {
			appendToOutput( "\u00d7" + separator ) ;
		}
		else if( token.getTokenValue().equals( "/" ) ) {
			appendToOutput( "\u00f7" + separator );
		}
		else if( token.getTokenValue().equals( "-" ) ) {
			appendToOutput( "\u2212" + separator );
		}
		else {
			appendToOutput( token.getTokenValue() + separator ) ;
		}
	}
}
