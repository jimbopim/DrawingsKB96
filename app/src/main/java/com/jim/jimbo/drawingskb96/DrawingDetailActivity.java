package com.jim.jimbo.drawingskb96;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class DrawingDetailActivity extends AppCompatActivity {

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity", "OnRestart DrawingDetail");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main_frame);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);
        if (fabEdit != null) {
            fabEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    //Log.i("main", "View.id " + view.getId());
                    Intent myIntent = new Intent(view.getContext(), EditDrawing.class); //Lägga till ID här ev?
                    view.getContext().startActivity(myIntent);
                }
            });
        }

        FloatingActionButton fabDrillMaps = (FloatingActionButton) findViewById(R.id.fabDrillMaps);
        if (fabDrillMaps != null) {
            fabDrillMaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    //Log.i("main", "View.id " + view.getId());
                    Intent myIntent = new Intent(view.getContext(), DrillMaps.class); //Lägga till ID här ev?
                    view.getContext().startActivity(myIntent);
                }
            });
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(DrawingDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(DrawingDetailFragment.ARG_ITEM_ID));
            DrawingDetailFragment fragment = new DrawingDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.drawing_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
