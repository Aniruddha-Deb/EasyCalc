package com.sensei.EasyCalc2;

import java.util.ArrayList;

import com.sensei.EasyCalc.core.Evaluator;
import com.sensei.EasyCalc.core.Lexer;
import com.sensei.EasyCalc.core.Token;
import com.sensei.EasyCalc2.UI.MainStage;

public class Controller{

	private StringBuilder expression     = null;
	private MainStage     stage     = null;
	private Lexer         lexer     = null;
	private Evaluator     evaluator = null;
	
	private static String   CMD_ENTER   = "=" ;
	private static String   CMD_CLEAR   = "C" ;
	private static String   CMD_DELETE  = "Del" ;
	private static String[] CMDS        = { CMD_ENTER, CMD_CLEAR, CMD_DELETE } ;
	
	public Controller() {
		createComponents();
	}
	
	private void createComponents() {
		expression = new StringBuilder();
		stage = new MainStage( this );
		lexer = new Lexer( expression.toString() );
		evaluator = new Evaluator();
	}
	
	public MainStage getMainStage() {
		return stage;
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
			stage.getOutputPane().refreshOutput( tokens, showSeparator ) ;
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
			stage.getOutputPane().showError();
		}
	}

	public void updateInput( String inputEntered ) {
		if( isCommandInput( inputEntered ) ) {
			processCommand( inputEntered ) ;
		}
		else {
			expression.append( inputEntered ) ;
			refreshOutput( true );
		}
	}

}
