package com.sensei.FXUI.UI;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class InputPane extends GridPane {
	
	MainStage stage = null;
	GridPane layout = null;
	
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
	
	private Button makeButton( String text, String ID ) {
		Button button = new Button( text );
		button.setId( ID );
		button.setPrefSize( 70, 60 );
		button.setOnAction( e -> handleButtonPressEvent( button.getText() ) );
		
		// Animation
		button.setOnMouseEntered( e -> button.setStyle( 
				"-fx-background-color: rgb(190,190,190)" ) );
		button.setOnMouseExited( e -> button.setStyle( 
				"-fx-background-color: rgb(204,204,204)" ) );
		button.setOnMousePressed( e -> button.setStyle( 
				"-fx-background-color: rgb(150,150,150)" ) );
		button.setOnMouseReleased( e -> button.setStyle( 
				"-fx-background-color: rgb(190,190,190)" ) );

		return button;
	}
	
	private void makeComponents() {
		layout = new GridPane();
		layout.setHgap( -1 );
		layout.setVgap( -1 );
		
		for( int row=0; row<5; row++ ) {
			for( int col=0; col<4; col++ ) {
				if( row > 0 && row <= 3 && col < 3 || 
					row == 4 && col<2 ) {
					Button button = makeButton( buttonTexts[row][col], "numpad" );
					layout.add( button, col, row );
					
				}
				else if( row == 0 && col < 3 ) {
					Button button = makeButton( buttonTexts[row][col], "commands" );
					layout.add( button, col, row );
				}
				else {
					Button button = makeButton( buttonTexts[row][col], "other" );
					layout.add( button, col, row );
				}
			}
		}
		super.getChildren().add( layout );
	}
	
	private void handleButtonPressEvent( String keyPressed ) {
		if( keyPressed.equals( "\u00d7" ) ) {
			keyPressed = "*" ;
		}
		else if( keyPressed.equals( "\u2212" ) ) {
			keyPressed = "-" ;
		}
		else if( keyPressed.equals( "\u00f7" ) ) {
			keyPressed = "/" ;
		}
		stage.inputEntered( keyPressed );
	}

}
