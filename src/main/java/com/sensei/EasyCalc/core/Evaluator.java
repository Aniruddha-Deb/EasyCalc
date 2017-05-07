package com.sensei.EasyCalc.core;

import java.math.BigDecimal;

public class Evaluator {
	
	public BigDecimal evaluate( Lexer lexer ) throws Exception{
		BigDecimal value = new BigDecimal( "0" );
		value = calculateASOp( lexer );
		Token token = lexer.getNextToken();
		
		while ( token != null ) {
			
			if( token.getTokenValue().equals("+") ||
			    token.getTokenValue().equals("-") ) {
				
				BigDecimal rightHandValue = calculateASOp( lexer );
				if( token.getTokenValue().equals( "+" ) ) {
					value = value.add( rightHandValue );
				}
				else if( token.getTokenValue().equals( "-" ) ) {
					value = value.subtract( rightHandValue );
				}
				token = lexer.getNextToken() ;
			}
			else {
				lexer.pushBackToken( token );
				break ;
			}
		}
		return value;
	}
	
	private BigDecimal calculateASOp( Lexer lexer ) throws Exception{
		BigDecimal value = new BigDecimal( "0" );
		value = calculateMDOp( lexer );
		Token token = lexer.getNextToken();
		
		while ( token != null ) {
			
			if( token.getTokenValue().equals("*") ||
			    token.getTokenValue().equals("/") ) {
				
				BigDecimal rightHandValue = calculateMDOp( lexer );
				if( token.getTokenValue().equals( "*" ) ) {
					value = value.multiply( rightHandValue );
				}
				else if( token.getTokenValue().equals( "/" ) ) {
					value = value.divide( rightHandValue );
				}
				token = lexer.getNextToken() ;
			}
			else {
				lexer.pushBackToken( token );
				break ;
			}
		}
		return value;
	}
	
	private BigDecimal calculateMDOp( Lexer lexer ) throws Exception{
		BigDecimal value = new BigDecimal( "0" );
		Token token = lexer.getNextToken();
		int    sign = 1 ;
		
		if( token == null ) {
			throw new Exception( "Incomplete expression" ) ;
		}
		
		if( token.getTokenValue().equals( "-" ) ) {
			// We have an unary negation operator.
			sign = -1 ;
			token = lexer.getNextToken() ;
		}
		else if( token.getTokenValue().equals( "+" ) ) {
			// We have an unary positive operator.
			sign = 1 ;
			token = lexer.getNextToken() ;
		}
		
		if( token == null ) {
			throw new Exception( "Incomplete expression" ) ;
		}

		if( token.getTokenType() == Token.NUMERIC ) {
			value = new BigDecimal( token.getTokenValue() );
		}
		else if( token.getTokenType() == Token.PARENTHESES ) {
			
			if( token.getTokenValue().equals( "(" ) ) {
				value = evaluate( lexer );
				token = lexer.getNextToken();
				if( !token.getTokenValue().equals( ")" ) ) {
					throw new Exception( "Right bracket missing" );
				}
			}
			else if( token.getTokenValue().equals( ")" ) ) {
				throw new Exception( "Invalid Input" );
			}
		}
		else {
			throw new Exception( "Invalid expression." ) ;
		}
		
		return value.multiply( BigDecimal.valueOf( sign ) );
	}
}
