package com.sensei.EasyCalc.core;

import java.util.ArrayList;
import java.util.Stack;

public class Lexer {
	
	private String input      = null;
	private int    currentPos = 0;
	
	private Stack<Token> pushedBackTokens = new Stack<Token>() ;
	
	public Lexer( String input ) {
		this.input = input;
	}
	
	public ArrayList<Token> getAllTokens() {
		ArrayList<Token> tokens = new ArrayList<Token>() ;
		Token token = getNextToken() ;
		while( token != null ) {
			tokens.add( token ) ;
			token = getNextToken() ;
		}
		return tokens ;
	}
	
	public void reset( String input ) {
		this.input = input;
		currentPos = 0;
	}
	
	public void setInput( String input ) {
		this.input = input;
	}
	
	public Token getNextToken() {
		
		if( !pushedBackTokens.isEmpty() ) {
			return pushedBackTokens.pop() ;
		}
		
		for( ; currentPos<input.length(); currentPos++ ) {
			char ch = input.charAt( currentPos );
			if( ch == ' ' ) {
				continue;
			}
			else if( isOperator( ch ) ) {
				currentPos++ ;
				return new Token( Token.OPERATOR, ch );
			}
			else if( ch == '(' || ch == ')' ) {
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
						token.append( ch ) ;
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
	
	private boolean isOperator( char ch ) {
		return ch == '+' || ch == '-' || ch == '*' || ch == '/' ;
	}
	
	private boolean isPartOfNumToken( char ch ) {
		return ch == '.' || Character.isDigit( ch ) ;
	}
	
	public static void main( String[] args ) {
		Lexer lexer = new Lexer( "5+(2*3.6)-(4.56/4.786)" );
		Token token = lexer.getNextToken();
		while( token != null ) {
			System.out.println( token.getTokenValue() );
			token = lexer.getNextToken() ;
		}
	}
}
