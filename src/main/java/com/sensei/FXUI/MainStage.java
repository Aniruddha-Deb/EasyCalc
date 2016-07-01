package com.sensei.FXUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainStage extends Application {
	
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
		inputPane = new InputPane();
		outputPane = new OutputPane();
	}
	

	private void addComponentsToStage( Stage stage ) {
		BorderPane layout = new BorderPane();
		layout.setPadding( new Insets( 10, 10, 10, 10 ) );
		layout.setTop( outputPane );
		layout.setCenter( inputPane );
		
		Scene scene = new Scene( layout, 400, 400 );
		stage.setScene( scene );
	}

	private void setStageAttributes( Stage stage ) {
		stage.setTitle( "EasyCalc" );
		stage.show();
	}
}
