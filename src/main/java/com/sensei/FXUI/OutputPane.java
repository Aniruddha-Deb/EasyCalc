package com.sensei.FXUI;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class OutputPane extends Pane {
	
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
		output.setPrefSize( 215 , 20 );
		layout.getChildren().add( output );
		super.getChildren().add( layout );
	}
}
