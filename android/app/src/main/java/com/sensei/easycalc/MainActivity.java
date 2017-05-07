package com.sensei.easycalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String expression = null;
    private TextView expressionView = null;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        expressionView = (TextView)findViewById( R.id.exprView );

    }

    public void onClearButtonClick( View view ) {
        expression = "";
        expressionView.setText( "" );
    }

    public void onDeleteButtonClick( View view ) {
        try {
            expression = expression.substring(0, expression.length() - 1);
            CharSequence exprViewText = expressionView.getText();
            expressionView.setText( exprViewText.subSequence( 0, exprViewText.length()-1 ) );
        } catch( Exception e ) {
            Toast.makeText( this, R.string.deleteError, Toast.LENGTH_SHORT ).show();
        }
    }

    public void onEqualsButtonClick( View view ) {
        Log.d( "MainActivity", "TODO implement equals functionality" );
    }

    private void onGeneralButtonClick( Button button ) {
        String exprViewText = expressionView.getText().toString();
        expressionView.setText( exprViewText + " " + button.getText() );
    }

    public void onSevenButtonClick( View view ) {
        expression += "7";
        onGeneralButtonClick( (Button)view );
    }

    public void onEightButtonClick( View view ) {
        expression += "8";
        onGeneralButtonClick( (Button)view );
    }

    public void onNineButtonClick( View view ) {
        expression += "9";
        onGeneralButtonClick( (Button)view );
    }

    public void onFourButtonClick( View view ) {
        expression += "4";
        onGeneralButtonClick( (Button)view );
    }

    public void onFiveButtonClick( View view ) {
        expression += "5";
        onGeneralButtonClick( (Button)view );
    }

    public void onSixButtonClick( View view ) {
        expression += "6";
        onGeneralButtonClick( (Button)view );
    }

    public void onOneButtonClick( View view ) {
        expression += "1";
        onGeneralButtonClick( (Button)view );
    }

    public void onTwoButtonClick( View view ) {
        expression += "2";
        onGeneralButtonClick( (Button)view );
    }

    public void onThreeButtonClick( View view ) {
        expression += "3";
        onGeneralButtonClick( (Button)view );
    }

    public void onDecimalButtonClick( View view ) {
        expression += ".";
        onGeneralButtonClick( (Button)view );
    }

    public void onZeroButtonClick( View view ) {
        expression += "0";
        onGeneralButtonClick( (Button)view );
    }

    public void onAddButtonClick( View view ) {
        expression += "+";
        onGeneralButtonClick( (Button)view );
    }

    public void onSubtractButtonClick( View view ) {
        expression += "-";
        onGeneralButtonClick( (Button)view );
    }

    public void onMultiplyButtonClick( View view ) {
        expression += "*";
        onGeneralButtonClick( (Button)view );
    }

    public void onDivideButtonClick( View view ) {
        expression += "/";
        onGeneralButtonClick( (Button)view );
    }

    public void onLeftBracketButtonClick( View view ) {
        expression += "(";
        onGeneralButtonClick( (Button)view );
    }

    public void onRightBracketButtonClick( View view ) {
        expression += ")";
        onGeneralButtonClick( (Button)view );
    }
}