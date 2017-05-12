package com.sensei.easycalc;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sensei.easycalc.core.Controller;
import com.sensei.easycalc.core.Token;
import com.sensei.easycalc.ui.adapter.BottomViewPagerAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;

import me.grantland.widget.AutofitHelper;

public class MainActivity extends AppCompatActivity{

    private TextView expressionView = null;
    private TextView answerView = null;
    private TextView memoryView = null;
    private Controller controller = null;

    private BigDecimal memory = null;

    private ViewPager pager = null;
    private PagerAdapter adapter = null;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initializeComponents();
        setUpViewPager();
    }

    private void setUpViewPager() {
        pager = (ViewPager)findViewById( R.id.viewPager );
        adapter = new BottomViewPagerAdapter( getSupportFragmentManager() );
        pager.setAdapter( adapter );
    }

    private void initializeComponents() {
        expressionView = (TextView)findViewById( R.id.exprView );
        AutofitHelper.create( expressionView );
        answerView = (TextView)findViewById( R.id.answerView );
        AutofitHelper.create( answerView );
        memoryView = (TextView)findViewById( R.id.memoryView );
        controller = new Controller( this );

        memory = new BigDecimal( "0" );
    }

    @Override
    public void onBackPressed() {
        if( pager.getCurrentItem() == 0 ) {
            super.onBackPressed();
        }
        else {
            pager.setCurrentItem( pager.getCurrentItem()-1 );
        }
        super.onBackPressed();
    }

    public void refreshOutput(ArrayList<Token> tokens, boolean showSeparator ) {
        expressionView.setText( "" );
        answerView.setText( "" );

        for( Token token : tokens ) {
            outputToken( token, showSeparator );
        }
    }

    public void showAnswer( String answer ) {
        answerView.setText( answer );
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
                t.setTokenValue( t.getTokenValue().replaceFirst( "\\.", getString( R.string.decimal ) ) );
                break;
        }
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

    public void onMemoryButtonClick( View view ) {
        PopupMenu memoryMenu = new PopupMenu( this, view );
        memoryMenu.inflate( R.menu.memory_menu );
        memoryMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick( MenuItem item ) {
                switch( item.getItemId() ) {
                    case R.id.mem_plus:
                        onMemPlusButtonClick();
                        return true;

                    case R.id.mem_minus:
                        onMemMinusButtonClick();
                        return true;

                    case R.id.mem_clear:
                        onMemClearButtonClick();
                        return true;

                    case R.id.mem_recall:
                        onMemRecallButtonClick();
                        return true;
                }
                return false;
            }
        } );
        memoryMenu.show();
    }

    private void onMemPlusButtonClick() {
        memory = memory.add( controller.getAnswer() );
        refreshMemoryView();
    }

    private void onMemMinusButtonClick() {
        memory = memory.subtract( controller.getAnswer() );
        refreshMemoryView();
    }

    private void onMemClearButtonClick() {
        memory = new BigDecimal( 0 );
        refreshMemoryView();
    }

    private void onMemRecallButtonClick() {
        controller.updateInput( "C" );
        controller.updateInput( controller.convertToString( memory ) );
        refreshMemoryView();
    }

    private void refreshMemoryView() {
        if( !(memory.toPlainString().equals( "0" )) ) {
            memoryView.setText( "MEM = " + controller.convertToString( memory ) );
        } else {
            memoryView.setText( "" );
        }
    }
}