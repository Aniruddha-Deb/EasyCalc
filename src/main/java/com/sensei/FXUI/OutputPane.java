package com.sensei.FXUI;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class OutputPane extends GridPane {
	
	TextField output = new TextField();
	
	public OutputPane() {
		setUpUI();
	}
	
	private void setUpUI() {
		makeComponents();
	}
	
	private void makeComponents() {
		StackPane layout = new StackPane();
		output = new TextField();
		output.setEditable( false );
		output.setFont( Font.font( "Helvetica", 20 ) );
		output.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE + 100 );
		output.setFocusTraversable( false );
		
		layout.getChildren().add( output );
		super.getChildren().add( layout );
	}
	
	public String getText() {
		return output.getText();
	}
	
	public void setText( String text ) {
		output.setText( text );
	}
}
