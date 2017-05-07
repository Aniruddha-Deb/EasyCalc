package com.sensei.EasyCalc2.UI;

import java.util.ArrayList;

import com.sensei.EasyCalc.core.Token;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OutputPane extends GridPane {
	
	private TextField output = new TextField();
	private Color defaultBGColor = null;
	
	public OutputPane( MainStage stage ) {
		setUpUI();
	}
	
	private void setUpUI() {
		makeComponents();
	}
	
	private void makeComponents() {
		StackPane layout = new StackPane();
		defaultBGColor = new Color( 0.29,0.29,0.29,1 );
		
		output = new TextField();
		output.setEditable( false );
		output.setFont( Font.font( "Helvetica", 20 ) );
		output.setPrefSize( 500, USE_COMPUTED_SIZE + 50);
		output.setFocusTraversable( false );
		
		layout.getChildren().add( output );
		super.getChildren().add( layout );
	}
	
	private void appendToOutput( String text ) {
		output.setText( output.getText() + text ) ;
	}
	
	private boolean showsError() {
		return output.getBackground().getFills().equals( defaultBGColor );
	}
	
	public String getText() {
		return output.getText();
	}
	
	public void setText( String text ) {
		output.setText( text );
	}
	
	public void showError() {
		output.setStyle( "-fx-background-color: rgb(210,60,36);" );
	}
	
	public void refreshOutput( ArrayList<Token> tokens, boolean showSeparator ) {
		output.setText( "" );
		
		if( !showsError() ) {
			output.setStyle( "-fx-background-color: #" + defaultBGColor.toString().substring( 2 ) + ";" );
		}
		
		for( Token token : tokens ) {
			outputToken( token, showSeparator );
		}
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
