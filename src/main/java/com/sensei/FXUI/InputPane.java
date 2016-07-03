package com.sensei.FXUI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class InputPane extends GridPane {
	
	MainStage stage = null;
	GridPane layout = null;
	Button button = null;
	
	public InputPane( MainStage stage ) {
		this.stage = stage;
		setUpUI();
	}
	
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
	
	public GridPane getGridPane() {
		return layout;
	}
	
	private void makeComponents() {
		layout = new GridPane();
		layout.setHgap( -1 );
		layout.setVgap( -1 );
		
		for( int row=0; row<5; row++ ) {
			for( int col=0; col<4; col++ ) {
				Button button = new Button( buttonTexts[row][col] );
				button.setPrefSize( 60, 55 );
				layout.add( button, col, row );
				button.setOnAction( e -> stage.out( button.getText() ) );
			}
		}
		super.getChildren().add( layout );
	}
	
	public void doClick( String text ) {
		for( int i=0; i<20; i++ ) {
			Button button = (Button) layout.getChildren().get(i);
			
			if( text.equals( "*" ) && button.getText().equals( "\u00d7" ) ) {
				button.fire();
			}
			else if( text.equals( "-" ) && button.getText().equals( "\u2212" ) ) {
				button.fire();
			}
			else if( text.equals( "/" ) && button.getText().equals( "\u00f7" ) ) {
				button.fire();
			}
			else if( button.getText().equals( text ) ) {
				button.fire();
			}
		}
	}
}
