package com.sensei.easycalc.core;

import com.sensei.easycalc.MainActivity;
import com.sensei.easycalc.R;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Controller {

    private StringBuilder expression = null;
    private Lexer         lexer      = null;
    private BigDecimal    answer     = null;
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
            outputAnswerOnExpressionView();
        }
    }

    private void outputAnswerOnExpressionView() {
        expression = new StringBuilder( convertToString( answer ) );
        refreshOutput( true );
    }

    private void refreshOutput( boolean showSeparator ) {
        lexer.reset( expression.toString() );
        ArrayList<Token> tokens = lexer.getAllTokens() ;
        activity.refreshOutput( tokens, showSeparator );

        calculateAndShowAnswer();
    }

    private void calculateAndShowAnswer() {
        lexer.reset( expression.toString() );
        try {
            answer = evaluator.evaluate( lexer );
            // complex logic
            if( !( convertToString( answer ).equals( expression.toString().replace( " ", "" ) ) ) ) {
                showAnswer( answer );
            }
        }
        catch ( Exception e ) {
            // do nothing!
        }
    }

    private void showAnswer( BigDecimal answer ) {
        String s = convertToString( answer );
        activity.showAnswer( s );
    }

    public String convertToString( BigDecimal d ) {
        StringBuilder b = new StringBuilder( d.toPlainString() );

        for( int i=0; i<b.length(); i++ ) {
            if( b.charAt( i ) == '-' ) {
                b.replace( i, i+1, "–" );
            }
        }

        return b.toString();
    }

    public void updateInput( String inputEntered ) {
        if( isCommandInput( inputEntered ) ) {
            processCommand( inputEntered ) ;
        }
        else {
            expression.append( inputEntered ) ;
            refreshOutput( true );
        }
    }

    public BigDecimal getAnswer() {
        return answer;
    }
}
