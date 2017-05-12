package com.sensei.easycalc.ui.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.sensei.easycalc.ui.fragment.HistoryFragment;
import com.sensei.easycalc.ui.fragment.NumpadFragment;

public class BottomViewPagerAdapter extends FragmentStatePagerAdapter
                                    implements ViewPager.OnPageChangeListener{

    private static final String TAG = "BottomViewPagerAdapter";

    public static final int NUM_VIEWS = 2;

    public BottomViewPagerAdapter( FragmentManager fm ) {
        super( fm );
    }

    @Override
    public Fragment getItem( int position ) {
        switch( position ) {
            case 0:
                return new HistoryFragment();

            case 1:
                return new NumpadFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_VIEWS;
    }

    @Override
    public int getItemPosition( Object object ) {
        if( object instanceof HistoryFragment ) {
            Log.d( TAG, "View changed, refreshing" );
            ((HistoryFragment)object).refreshData();
        }

        return super.getItemPosition( object );
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageSelected(int position) {
        if( position == 0 ) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) { }
}
