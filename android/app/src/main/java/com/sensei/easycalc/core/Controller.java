package com.sensei.easycalc.core;

import android.util.Log;

import com.sensei.easycalc.MainActivity;
import com.sensei.easycalc.R;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Controller {

    private StringBuilder expression = null;
    private Lexer         lexer      = null;
    private Evaluator     evaluator  = null;
    private MainActivity  activity   = null;

    private static String   CMD_EQUALS = null;
    private static String   CMD_CLEAR  = null;
    private static String   CMD_DELETE = null;
    private static String[] CMDS       = null;

    public Controller( MainActivity activity ) {
        this.activity = activity;
        createComponents();
    }

    private void createComponents() {
        expression = new StringBuilder();
        lexer = new Lexer( expression.toString(), activity );
        evaluator = new Evaluator( activity );

        CMD_EQUALS = activity.getString( R.string.equals );
        CMD_CLEAR  = activity.getString( R.string.clear  );
        CMD_DELETE = activity.getString( R.string.delete );
        CMDS = new String[]{ CMD_EQUALS, CMD_CLEAR, CMD_DELETE };
    }

    private boolean isCommandInput( String input ) {
        for( String s : CMDS ) {
            if( input.equals( s ) ) {
                return true ;
            }
        }
        return false ;
    }

    private void processCommand( String cmd ) {
        if( cmd.equals( CMD_CLEAR ) ) {
            expression.delete( 0, expression.length() );
            refreshOutput( true );
        }
        else if( cmd.equals( CMD_DELETE ) ) {
            try {
                expression.delete( expression.length()-1, expression.length() );
                refreshOutput( true );
            }
            catch( StringIndexOutOfBoundsException e ) {
                // TODO stuff here!
            }
        }
        else if( cmd.equals( CMD_EQUALS ) ) {
            calculateAndShowAnswer();
        }
    }

    private void refreshOutput( boolean showSeparator ) {
        lexer.reset( expression.toString() );
        ArrayList<Token> tokens = lexer.getAllTokens() ;

        try {
            if( !tokens.isEmpty() ) {
                Token lastToken = tokens.get( tokens.size()-1 );

                if( lastToken.getTokenType() == Token.NUMERIC ) {
                    new BigDecimal( lastToken.getTokenValue() ) ;
                }
            }
            activity.refreshOutput( tokens, showSeparator );
        }
        catch ( Exception e ) {
            processCommand( "Del" ) ;
        }
    }

    private void calculateAndShowAnswer() {
        BigDecimal answer;

        lexer.reset( expression.toString() );
        try {
            answer = evaluator.evaluate( lexer );
            expression.delete( 0, expression.length() );
            expression.append( answer.toPlainString() );
            refreshOutput( false );
        }
        catch ( Exception e ) {
            activity.showError();
        }
    }

    public void updateInput( String inputEntered ) {
        if( isCommandInput( inputEntered ) ) {
            Log.d( "Controller", "Input is a Command" );
            processCommand( inputEntered ) ;
        }
        else {
            expression.append( inputEntered ) ;
            refreshOutput( true );
        }
    }

}
