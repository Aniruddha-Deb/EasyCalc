package com.sensei.easycalc;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sensei.easycalc.core.ExpressionController;
import com.sensei.easycalc.dao.DatabaseHelper;
import com.sensei.easycalc.ui.adapter.BottomViewPagerAdapter;
import com.sensei.easycalc.util.LocaleUtil;

import java.math.BigDecimal;

import me.grantland.widget.AutofitHelper;

public class MainActivity extends AppCompatActivity{

    private TextView expressionView = null;
    private TextView answerView = null;
    private TextView memoryView = null;
    private ExpressionController controller = null;

    private BigDecimal memory = null;

    private ViewPager pager = null;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        DatabaseHelper.createInstance( this );
        initializeComponents();
        setUpViewPager();
    }

    private void setUpViewPager() {
        pager = (ViewPager)findViewById( R.id.viewPager );
        BottomViewPagerAdapter adapter = new BottomViewPagerAdapter( getSupportFragmentManager() );
        pager.setAdapter( adapter );
        pager.addOnPageChangeListener( adapter );
        pager.setCurrentItem( 1 );
    }

    private void initializeComponents() {
        expressionView = (TextView)findViewById( R.id.exprView );
        AutofitHelper.create( expressionView );
        answerView = (TextView)findViewById( R.id.answerView );
        AutofitHelper.create( answerView );
        memoryView = (TextView)findViewById( R.id.memoryView );
        controller = new ExpressionController( this );

        memory = new BigDecimal( 0 );
    }

    @Override
    public void onBackPressed() {
        if( pager.getCurrentItem() == 1 ) {
            super.onBackPressed();
        }
        else {
            pager.setCurrentItem( 1 );
        }
    }

    public void showExpression( String str ) {
        expressionView.setText( str );
    }

    public void showAnswer( String answer ) {
        answerView.setText( answer );
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
        BigDecimal answer = controller.getAnswer();
        if( answer == null ) {
            Toast.makeText( this, R.string.memoryAddError, Toast.LENGTH_LONG ).show();
        }
        else {
            memory = memory.add( answer );
        }
        refreshMemoryView();
    }

    private void onMemMinusButtonClick() {
        BigDecimal answer = controller.getAnswer();
        if( answer == null ) {
            Toast.makeText( this, R.string.memorySubtractError, Toast.LENGTH_LONG ).show();
        }
        else {
            memory = memory.subtract( answer );
        }
        refreshMemoryView();
    }

    private void onMemClearButtonClick() {
        memory = null;
        refreshMemoryView();
    }

    private void onMemRecallButtonClick() {
        if( memory != null ) {
            controller.updateInput( LocaleUtil.convertToString( memory.toPlainString(), this ) );
        }
    }

    private void refreshMemoryView() {
        if( memory == null || memory.equals( BigDecimal.ZERO ) ) {
            memoryView.setText( "" );
        } else {
            memoryView.setText( "MEM = " + LocaleUtil.convertToString( memory.toPlainString(), this ) );
        }
    }
}