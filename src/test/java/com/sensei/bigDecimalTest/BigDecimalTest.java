package com.sensei.bigDecimalTest;

import java.math.BigDecimal;

public class BigDecimalTest {

	public static void main( String[] args ){

		BigDecimal b = new BigDecimal( "–3" );
		b = b.multiply( BigDecimal.valueOf( -1 ) );
		System.out.println( b.toPlainString() );
	}
}
