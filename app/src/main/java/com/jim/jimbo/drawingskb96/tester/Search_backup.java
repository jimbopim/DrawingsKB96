/*package com.jim.jimbo.draftskb96;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Search extends AppCompatActivity {

    public static File PATH;
    public static SimpleItemRecyclerViewAdapter SIRVA;
    DraftContent DC_Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        DC_Search = new DraftContent();

        SIRVA = new SimpleItemRecyclerViewAdapter(DC_Search.DRAFTS_TO_SHOW);

        PATH = this.getFilesDir();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setTitle(getTitle());
        }

        final EditText edit_search = (EditText)findViewById(R.id.etSearch);
        final Button search_button = (Button) findViewById(R.id.search_button);

        if (search_button != null) {
            search_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (edit_search != null) {
                        if(edit_search.getText().toString().matches(""))
                            DC_Search.Clear_Drafts();
                        else
                            DC_Search.AddSearchResults(edit_search.getText().toString());
                    }

                    SIRVA.notifyDataSetChanged();
                }
            });
        }


        /*FloatingActionButton search = (FloatingActionButton) findViewById(R.id.fab_searching);
        if (search != null) {
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Snackbar.make(view, "Replace with your own action babedi", Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show();

                    //Intent myIntent = new Intent(Search.this, Search.class);
                    //Search.this.startActivity(myIntent);
                }
            });
        }

        View recyclerView = findViewById(R.id.draft_list);

        if ((RecyclerView) recyclerView != null) {
            setupRecyclerView((RecyclerView) recyclerView);
        }

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(SIRVA);
    }

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DraftContent.DraftEntry> mValues;

        public SimpleItemRecyclerViewAdapter(List<DraftContent.DraftEntry> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.draft_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);               //Siffran i listan
            holder.mContentView.setText(mValues.get(position).content); //Namnet i listan

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditDraft.position = holder.getAdapterPosition() + 1;
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DraftDetailActivity.class);
                    intent.putExtra(DraftDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DraftContent.DraftEntry mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}*/