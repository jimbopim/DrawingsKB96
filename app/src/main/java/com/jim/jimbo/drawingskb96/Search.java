package com.jim.jimbo.drawingskb96;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class Search extends AppCompatActivity {

    private SimpleItemRecyclerViewAdapter SIRVA;
    private DrawingContent DC_Search;
    private TextView countsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        DC_Search = new DrawingContent();

        SIRVA = new SimpleItemRecyclerViewAdapter(DC_Search.DRAWINGS_TO_SHOW);

        countsText = (TextView) findViewById(R.id.s_counts);

        Create_Toolbar();
        Create_ETSearch();
        Update_Counts();
        Setup_RecyclerView();
    }


    public void Update_Counts() {
        if (countsText != null) {
            countsText.setText("Antal träffar: " + String.valueOf(DC_Search.DRAWINGS_REG.size()));
            countsText.setBackgroundColor(Color.WHITE);
        }
    }

    private void Create_Toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setTitle(getTitle());
        }
    }

    private void Create_ETSearch() {

        final EditText edit_search = (EditText) findViewById(R.id.etSearch);

        if (edit_search != null) {
            edit_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    DC_Search.Clear_Drawings();
                    if (s.length() > 0)
                        DC_Search.AddSearchResults(s.toString());
                    SIRVA.notifyDataSetChanged();
                    Update_Counts();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    private void Setup_RecyclerView() {

        View recyclerView = findViewById(R.id.drawing_list);

        if (recyclerView != null) {
            setupRecyclerView((RecyclerView) recyclerView);
        }

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(SIRVA);
    }

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DrawingContent.DrawingEntry> mValues;

        public SimpleItemRecyclerViewAdapter(List<DrawingContent.DrawingEntry> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);               //Siffran i listan
            holder.mContentView.setText(mValues.get(position).content); //Namnet i listan
            holder.mDimensionView.setText(holder.mItem.dimensions);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditDrawing.position = holder.mItem.id;
                    Log.i("main", "Search: " + holder.mContentView.getText());
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DrawingDetailActivity.class);
                    intent.putExtra(DrawingDetailFragment.ARG_ITEM_ID, holder.mItem.id);

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
            public final TextView mDimensionView;
            public DrawingContent.DrawingEntry mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
                mDimensionView = (TextView) view.findViewById(R.id.dimensions);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}