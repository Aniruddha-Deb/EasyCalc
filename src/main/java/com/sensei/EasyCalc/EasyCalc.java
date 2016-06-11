package com.sensei.EasyCalc;

import static com.sensei.EasyCalc.Logger.log;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.sensei.EasyCalc.core.*;

@SuppressWarnings("serial")
public class EasyCalc extends JFrame implements KeyListener{
	
	private OutputPanel   outputPanel   = null;
	private SettingsPanel settingsPanel = null;
	private InputPanel    inputPanel    = null;
	private ControlPanel  controlPanel  = null;
	private StringBuilder expression    = null;
	private Lexer         lexer         = null;
	private Evaluator     evaluator     = null;
	
	private static String   CMD_ENTER   = "=" ;
	private static String   CMD_CLEAR   = "C" ;
	private static String   CMD_DELETE  = "Del" ;
	private static String[] CMDS        = { CMD_ENTER, CMD_CLEAR, CMD_DELETE } ;
	
	public EasyCalc() {
		expression = new StringBuilder() ;
		createCoreComponents( expression );
		setUpUI();
	}
	
	private void setUpUI() {
		createPanels();
		addPanelsToFrame();
		setFrameAttributes();
	}
	
	private void createPanels() {
		log( "\tCreating Panels" );
		outputPanel   = new OutputPanel();
		settingsPanel = new SettingsPanel();
		inputPanel    = new InputPanel( this );
		controlPanel  = new ControlPanel( settingsPanel, inputPanel );
	}
	
	private void createCoreComponents( StringBuilder input ) {
		lexer     = new Lexer ( input.toString() );
		evaluator = new Evaluator();
	}
	
	private void addPanelsToFrame() {
		log( "\tAdding Panels to frame" );
		Container container = super.getContentPane(); 
		container.setLayout( new BorderLayout() );
		super.add( outputPanel, BorderLayout.NORTH );
		super.add( controlPanel, BorderLayout.CENTER );
	}
	
	private void setFrameAttributes() {
		log( "\tSetting frame attributes" );
		
		super.setTitle( "EasyCalc" );
		super.setLocationRelativeTo( null );
		super.setSize( 300, 350 );
		
		super.addKeyListener( this );
		super.setFocusable( true );
		super.requestFocusInWindow();
		
		setSystemLookAndFeel();
		
		super.setResizable( false );
		super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
	
	private void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} 
		catch (ClassNotFoundException e) {
			setDefaultLookAndFeel();
		} 
		catch (InstantiationException e) {
			setDefaultLookAndFeel();
		} 
		catch (IllegalAccessException e) {
			setDefaultLookAndFeel();
		} 
		catch (UnsupportedLookAndFeelException e) {
			setDefaultLookAndFeel();
		}
	}
	
	private void setDefaultLookAndFeel() {
		try {
			UIManager.setLookAndFeel( new MetalLookAndFeel() );
		} catch (UnsupportedLookAndFeelException e1) {
		}
	}
	
	private void refreshOutput( boolean showSeparator ) {
		lexer.reset( expression.toString() );
		ArrayList<Token> tokens = lexer.getAllTokens() ;
		
		try {
			if( !tokens.isEmpty() ) {
				Token lastToken = tokens.get( tokens.size()-1 );
				
				if( lastToken.getTokenType() == Token.NUMERIC ) {
					Double.parseDouble( lastToken.getTokenValue() ) ;
				}
			}
			outputPanel.refreshOutput( tokens, showSeparator ) ;
		}
		catch ( Exception e ) {
			processCommand( "Del" ) ;
		}
	}
	
	private void calculateAndShowAnswer() {
		Double answer;
		
		lexer.reset( expression.toString() );
		try {
			answer = evaluator.evaluate( lexer );
			expression.delete( 0, expression.length() );
			expression.append( answer.toString() );
			refreshOutput( false );
		} 
		catch ( Exception e ) {
			outputPanel.showError();
		}
	}
	
	public void inputEntered( String inputEntered ) {
		if( isCommandInput( inputEntered ) ) {
			processCommand( inputEntered ) ;
		}
		else {
			expression.append( inputEntered ) ;
			refreshOutput( true );
		}
		log( "Input entered = " + inputEntered );
	}
	
	private boolean isCommandInput( String input ) {
		for( int i=0; i<CMDS.length; i++ ) {
			if( input.equals( CMDS[i] ) ) {
				return true ;
			}
		}
		return false ;
	}
	
	private void processCommand( String cmd ) {
		if( cmd.equals( "C" ) ) {
			expression.delete( 0, expression.length() );
			refreshOutput( true );
		}
		else if( cmd.equals( "Del" ) ) {
			try {
				expression.delete( expression.length()-1, expression.length() );
				refreshOutput( true );
			}
			catch( StringIndexOutOfBoundsException e ) {
			}
		}
		else if( cmd.equals( "=" ) ) {
			calculateAndShowAnswer();
		}
	}
	
	public static void main(String[] args) {
		log( "\nCreating EasyCalc main window" );
		
		EasyCalc calculator = new EasyCalc();
		calculator.setVisible( true );
		
		log( "EasyCalc main window now active" );
	}

	public void keyTyped( KeyEvent e ) {
		String s = e.getKeyChar() + "";
		
		if( s.matches( "[0-9+\\-*/()\\.]" ) ) {
			inputPanel.doClick( s );
		}
		else if( e.getExtendedKeyCode() == KeyEvent.VK_ENTER || s.matches( "=" ) ) {
			inputPanel.doClick( "=" );
		}
		else if( e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE ) {
			inputPanel.doClick( "Del" );
		}
		else if( s.equalsIgnoreCase( "c" ) ) {
			inputPanel.doClick( "C" );
		}
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}
}
