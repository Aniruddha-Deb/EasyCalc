package com.sensei.FXUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
		layout.setPadding( new Insets( 10, 10, 10, 10 ) );
		layout.setVgap( 10 );
		layout.add( outputPane, 0, 0 );
		layout.add( inputPane, 0, 1 );
		
		Scene scene = new Scene( layout, 270, 350 );
		scene.addEventHandler( KeyEvent.KEY_PRESSED, event -> handle(event) );
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
		else if( s.matches( "\\n" ) || s.matches( "=" ) ) {
			inputPane.doClick( "=" );
			System.out.println( "Clicked" );
		}
		else if( s.matches( "\b" ) ) {
			inputPane.doClick( "Del" );
			System.out.println( "Clicked" );
		}
		else if( s.equalsIgnoreCase( "c" ) ) {
			inputPane.doClick( "C" );
			System.out.println( "Clicked" );
		}
	}
}
