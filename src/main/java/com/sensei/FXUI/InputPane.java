package com.sensei.FXUI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class InputPane extends Pane {
	
	private String[][] buttonTexts = { 
			{"C", "Del", "=", "+"}, 
			{"7", "8", "9", "\u2212"},
			{"4", "5", "6", "\u00d7"}, 
			{"1", "2", "3", "\u00f7"},
			{".", "0", "(", ")"}
	};

	public InputPane() {
		setUpUI();
	}
	
	private void setUpUI() {
		makeComponents();
	}
	
	private void makeComponents() {
		GridPane layout = new GridPane();
		layout.setHgap( 5 );
		layout.setVgap( 5 );

		for( int row=0; row<5; row++ ) {
			for( int col=0; col<4; col++ ) {
				Button button = new Button( buttonTexts[row][col] );
				button.setPadding( new Insets( 10, 10, 10, 10 ) );
				button.setPrefSize( 50, 50 );
				layout.add( button, col, row );
			}
		}
		
		super.getChildren().add( layout );
	}
}
