package com.sensei.FXUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainStage extends Application implements EventHandler<KeyEvent>{
	
	private InputPane  inputPane  = null;
	private OutputPane outputPane = null;

	public static void main(String[] args) {
		launch( args );
	}

	@Override
	public void start( Stage primaryStage ) throws Exception {
		createComponents();
		addComponentsToStage( primaryStage );
		setStageAttributes( primaryStage );
	}

	private void createComponents() {
		inputPane = new InputPane( this );
		outputPane = new OutputPane();
	}
	

	private void addComponentsToStage( Stage stage ) {
		GridPane layout = new GridPane();
		layout.add( outputPane, 0, 0 );
		layout.add( inputPane, 0, 1 );
		layout.setStyle( "-fx-background-color: transparent" );
		
		Scene scene = new Scene( layout, 277, 345 );
		scene.addEventHandler( KeyEvent.KEY_PRESSED, event -> handle(event) );
		scene.getStylesheets().add( MainStage.class.getResource( "LookAndFeel.css" ).toExternalForm() );
		stage.setScene( scene );
	}

	private void setStageAttributes( Stage stage ) {
		stage.setTitle( "EasyCalc" );
		stage.setResizable( false );
		stage.show();
	}
	
	public void out( String text ) {
		outputPane.setText( outputPane.getText() + text );
	}

	@Override
	public void handle(KeyEvent event) {
		System.out.println( "Entered" );
		String s = event.getText() + "";
		
		if( s.matches( "[0-9+\\-*/()\\.]" ) ) {
			inputPane.doClick( s );
			System.out.println( "Clicked" );
		}
		else if( event.getCode().equals( KeyCode.ENTER ) ) {
			inputPane.doClick( "=" );
			System.out.println( "Clicked" );
		}
		else if( event.getCode().equals( KeyCode.BACK_SPACE ) ) {
			inputPane.doClick( "Del" );
			System.out.println( "Clicked" );
		}
		else if( s.equalsIgnoreCase( "c" ) ) {
			inputPane.doClick( "C" );
			System.out.println( "Clicked" );
		}
	}
}
