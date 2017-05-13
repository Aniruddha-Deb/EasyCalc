package com.sensei.easycalc.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sensei.easycalc.R;
import com.sensei.easycalc.dao.DatabaseHelper;
import com.sensei.easycalc.ui.adapter.HistoryCursorAdapter;

public class HistoryFragment extends Fragment {

    private HistoryCursorAdapter cursorAdapter = null;

    private static final String TAG = "HistoryFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate( R.layout.history_layout, container, false );
    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
        ListView historyView = (ListView)view.findViewById( R.id.historyList );
        cursorAdapter = new HistoryCursorAdapter(
                            getActivity(),
                            DatabaseHelper.getInstance().getHistoryCursor() );
        historyView.setAdapter( cursorAdapter );
        super.onViewCreated( view, savedInstanceState );
    }

    public void refreshData() {
        if( cursorAdapter != null ) {
            Log.d( TAG, "Notifying cursorAdapter that dataset changed" );
            cursorAdapter.swapCursor( DatabaseHelper.getInstance().getHistoryCursor() );
        }
        else {
            Log.d( TAG, "CursorAdapter is null" );
        }
    }
}
