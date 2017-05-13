package com.sensei.easycalc.core;

import com.sensei.easycalc.MainActivity;
import com.sensei.easycalc.R;
import com.sensei.easycalc.dao.DatabaseHelper;
import com.sensei.easycalc.util.LocaleUtil;

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
        answer = new BigDecimal( "0" );
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
            refreshOutput();
        }
        else if( cmd.equals( CMD_DELETE ) ) {
            try {
                expression.delete( expression.length()-1, expression.length() );
                refreshOutput();
            }
            catch( StringIndexOutOfBoundsException e ) {
                // TODO stuff here!
            }
        }
        else if( cmd.equals( CMD_EQUALS ) ) {
            saveAnswerToHistory();
            outputAnswerOnExpressionView();
        }
    }

    private void saveAnswerToHistory() {
        DatabaseHelper.getInstance().addTransactionToDatabase(
                LocaleUtil.convertToString( expression.toString(), activity ),
                LocaleUtil.convertToString( answer.toPlainString(), activity ) );
    }

    private void outputAnswerOnExpressionView() {
        expression = new StringBuilder( LocaleUtil.convertToString( answer.toPlainString(), activity ) );
        answer = new BigDecimal( "0" );
        refreshOutput();
    }

    private void refreshOutput() {
        lexer.reset( expression.toString() );
        String s = convertTokensToExpression( lexer.getAllTokens() );
        activity.refreshOutput( s );

        calculateAndShowAnswer();
    }

    private String convertTokensToExpression( ArrayList<Token> tokens ) {
        String s = "";

        for( Token t : tokens ) {
            if( t.getTokenType() == Token.NUMERIC ) {
                s += " " + LocaleUtil.convertToString( t.getTokenValue(), activity );
            }
            else {
                s += " " + t.getTokenValue();
            }
        }
        return s;
    }

    private void calculateAndShowAnswer() {
        lexer.reset( expression.toString() );
        try {
            answer = evaluator.evaluate( lexer );
            showAnswer( answer );
        }
        catch ( Exception e ) {
            // do nothing!
        }
    }

    private void showAnswer( BigDecimal answer ) {
        String s = LocaleUtil.convertToString( answer.toPlainString(), activity );
        activity.showAnswer( s );
    }

    public void updateInput( String inputEntered ) {
        if( isCommandInput( inputEntered ) ) {
            processCommand( inputEntered ) ;
        }
        else {
            expression.append( " " ) ;
            expression.append( inputEntered ) ;
            refreshOutput();
        }
    }

    public BigDecimal getAnswer() {
        return answer;
    }
}
