package com.sensei.EasyCalc.core;

public class Token {

	public static final int NUMERIC     = 0;
	public static final int OPERATOR    = 1;
	public static final int PARENTHESES = 2;
	public static final int COMMAND     = 3;
	
	private int    tokenType  = 0;
	private String tokenValue = "";
	
	public Token( int type, char ch ) {
		this.tokenType  = type;
		this.tokenValue += ch ;
	}
	
	public int getTokenType() {
		return tokenType;
	}
	
	public void append( char ch ) {
		tokenValue += ch;  
	}
	
	public String getTokenValue() {
		return tokenValue;
	}
}
