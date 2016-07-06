package com.sensei.EasyCalc2.UI;

import com.sensei.EasyCalc2.Controller;

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
	private String     input      = null;
	private Controller controller = null;

	public MainStage( Controller controller ) {
		this.controller = controller;
	}
	
	@Override
	public void start( Stage primaryStage ) throws Exception {
		createComponents();
		addComponentsToStage( primaryStage );
		setStageAttributes( primaryStage );
	}

	private void createComponents() {
		input = new String();
		inputPane = new InputPane( this );
		outputPane = new OutputPane( this );
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
		stage.setTitle( "EasyCalc 2" );
		stage.setResizable( false );
		stage.show();
	}
	
	public void inputEntered( String inputEntered ) {
		this.input = inputEntered;
		System.out.println( "Input entered = " + inputEntered );
		controller.updateInput( input );
	}
	
	public OutputPane getOutputPane() {
		return outputPane;
	}

	@Override
	public void handle(KeyEvent event) {
		String s = event.getText() + "";
		
		if( s.matches( "[0-9+\\-*/()\\.]" ) ) {
			inputEntered( s );
		}
		else if( s.matches( "=" ) || event.getCode().equals( KeyCode.ENTER ) ) {
			inputEntered( "=" );
		}
		else if( event.getCode().equals( KeyCode.BACK_SPACE ) ) {
			inputEntered( "Del" );
		}
		else if( s.equalsIgnoreCase( "c" ) ) {
			inputEntered( "C" );
		}
	}
}
