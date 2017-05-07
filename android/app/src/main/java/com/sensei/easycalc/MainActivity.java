package com.sensei.easycalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sensei.easycalc.core.Controller;
import com.sensei.easycalc.core.Token;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView expressionView = null;
    private Controller controller = null;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        expressionView = (TextView)findViewById( R.id.exprView );
        controller = new Controller( this );
    }

    public void refreshOutput( ArrayList<Token> tokens, boolean showSeparator ) {
        expressionView.setText( "" );

        if( showsError() ) {
            expressionView.setBackgroundColor( getResources().getColor( R.color.colorPrimaryDark ) );
        }

        for( Token token : tokens ) {
            outputToken( token, showSeparator );
        }
    }

    private void outputToken( Token token, boolean showSeparator ) {
        String separator = "" ;
        if( showSeparator ) {
            separator = " " ;
        }

        if( token.getTokenType() == Token.NUMERIC ) {
            for( char c : token.getTokenValue().toCharArray() ) {
                replaceWithLocaleDigits( c, token );
            }
        }
        expressionView.setText( expressionView.getText() + token.getTokenValue() + separator );
    }

    private void replaceWithLocaleDigits( char c, Token t ) {
        switch( c ) {
            case '1':
                t.setTokenValue( t.getTokenValue().replaceFirst( c+"", getString( R.string.one ) ) );
                break;

            case '2':
                t.setTokenValue( t.getTokenValue().replaceFirst( c+"", getString( R.string.two ) ) );
                break;

            case '3':
                t.setTokenValue( t.getTokenValue().replaceFirst( c+"", getString( R.string.three ) ) );
                break;

            case '4':
                t.setTokenValue( t.getTokenValue().replaceFirst( c+"", getString( R.string.four ) ) );
                break;

            case '5':
                t.setTokenValue( t.getTokenValue().replaceFirst( c+"", getString( R.string.five ) ) );
                break;

            case '6':
                t.setTokenValue( t.getTokenValue().replaceFirst( c+"", getString( R.string.six ) ) );
                break;

            case '7':
                t.setTokenValue( t.getTokenValue().replaceFirst( c+"", getString( R.string.seven ) ) );
                break;

            case '8':
                t.setTokenValue( t.getTokenValue().replaceFirst( c+"", getString( R.string.eight ) ) );
                break;

            case '9':
                t.setTokenValue( t.getTokenValue().replaceFirst( c+"", getString( R.string.nine ) ) );
                break;

            case '0':
                t.setTokenValue( t.getTokenValue().replaceFirst( c+"", getString( R.string.zero ) ) );
                break;

            case '.':
                t.setTokenValue( t.getTokenValue().replaceFirst( c+"", getString( R.string.decimal ) ) );
                break;
        }
    }

    public void showError() {
        expressionView.setBackground( getResources().getDrawable( R.color.colorError ) );
    }

    private boolean showsError() {
        return !( expressionView.getBackground().equals( getResources().getDrawable( R.color.colorPrimaryDark ) ) );
    }

    public void onNonNumpadButtonClick( View view ) {
        controller.updateInput( ((Button)view).getText().toString() );
    }

    public void onSevenButtonClick( View view ) {
        controller.updateInput( "7" );
    }

    public void onEightButtonClick( View view ) {
        controller.updateInput( "8" );
    }

    public void onNineButtonClick( View view ) {
        controller.updateInput( "9" );
    }

    public void onFourButtonClick( View view ) {
        controller.updateInput( "4" );
    }

    public void onFiveButtonClick( View view ) {
        controller.updateInput( "5" );
    }

    public void onSixButtonClick( View view ) {
        controller.updateInput( "6" );
    }

    public void onOneButtonClick( View view ) {
        controller.updateInput( "1" );
    }

    public void onTwoButtonClick( View view ) {
        controller.updateInput( "2" );
    }

    public void onThreeButtonClick( View view ) {
        controller.updateInput( "3" );
    }

    public void onDecimalButtonClick( View view ) {
        controller.updateInput( "." );
    }

    public void onZeroButtonClick( View view ) {
        controller.updateInput( "0" );
    }
}