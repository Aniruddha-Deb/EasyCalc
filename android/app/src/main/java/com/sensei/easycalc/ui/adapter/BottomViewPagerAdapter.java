package com.sensei.easycalc.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sensei.easycalc.ui.fragment.HistoryFragment;
import com.sensei.easycalc.ui.fragment.NumpadFragment;

public class BottomViewPagerAdapter extends FragmentStatePagerAdapter {

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
}
