package com.jim.jimbo.drawingskb96;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DrawingDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    private DrawingContent.DrawingEntry mItem;

    public DrawingDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the drawing content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DrawingContent.LISTENTRIES.get(getArguments().getString(ARG_ITEM_ID));
            Log.i("main", "arg item " + ARG_ITEM_ID);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.detail_text, container, false);
        View rootView = inflater.inflate(R.layout.detail_textview, container, false);
        TextView des;

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.description)).setText(MainActivity.DESCRIPTION);
            ((TextView) rootView.findViewById(R.id.details)).setText(mItem.details);
        }

        return rootView;
    }
}
