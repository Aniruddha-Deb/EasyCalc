package com.sensei.easycalc.core;

import android.content.Context;

import com.sensei.easycalc.MainActivity;
import com.sensei.easycalc.R;
import com.sensei.easycalc.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Stack;

public class Lexer {

    private String input      = null;
    private int    currentPos = 0;

    private Context ctx = null;

    private Stack<Token> pushedBackTokens = new Stack<>() ;

    private char add      = (char)0;
    private char subtract = (char)0;
    private char multiply = (char)0;
    private char divide   = (char)0;
    private char lbracket = (char)0;
    private char rbracket = (char)0;

    public Lexer( String input, MainActivity activity ) {
        this.ctx = activity;
        this.input = LocaleUtil.convertToString( input, activity );

        add      = activity.getString( R.string.add ).charAt(0);
        subtract = activity.getString( R.string.subtract ).charAt(0);
        multiply = activity.getString( R.string.multiply ).charAt(0);
        divide   = activity.getString( R.string.divide ).charAt(0);
        lbracket = activity.getString( R.string.lbracket ).charAt(0);
        rbracket = activity.getString( R.string.rbracket ).charAt(0);
    }

    public ArrayList<Token> getAllTokens() {
        ArrayList<Token> tokens = new ArrayList<>() ;
        Token token = getNextToken() ;
        while( token != null ) {
            tokens.add( token ) ;
            token = getNextToken() ;
        }
        return tokens ;
    }

    public void reset( String input ) {
        this.input = LocaleUtil.convertToString( input, ctx );
        currentPos = 0;
        pushedBackTokens = new Stack<>();
    }

    public Token getNextToken() {

        if( !pushedBackTokens.isEmpty() ) {
            return pushedBackTokens.pop() ;
        }

        for( ; currentPos<input.length(); currentPos++ ) {
            char ch = input.charAt( currentPos );
            if( isOperator( ch ) ) {
                currentPos++ ;
                if( ch == '-' ) {
                    return new Token( Token.OPERATOR, subtract );
                }
                return new Token( Token.OPERATOR, ch );
            }
            else if( isParenthesis( ch ) ) {
                currentPos++ ;
                return new Token( Token.PARENTHESES, ch );
            }
            else if( isPartOfNumToken( ch ) ) {
                Token token = null ;
                while( isPartOfNumToken( ch ) ) {
                    if( token == null ) {
                        token = new Token( Token.NUMERIC, ch );
                    }
                    else {
                        token.append( ch );
                    }

                    currentPos++ ;
                    if( currentPos >= input.length() ) {
                        break ;
                    }
                    else {
                        ch = input.charAt( currentPos ) ;
                    }
                }
                return token ;
            }
        }
        return null;
    }

    public void pushBackToken( Token t ) {
        pushedBackTokens.push( t ) ;
    }

    private boolean isParenthesis( char ch ) {
        return ch == lbracket || ch == rbracket;
    }

    private boolean isOperator( char ch ) {
        return ch == add || ch == subtract || ch == multiply || ch == divide || ch == '-';
    }

    private boolean isPartOfNumToken( char ch ) {
        return ch == '.' ||
               Character.isDigit( ch ) ;
    }
}
