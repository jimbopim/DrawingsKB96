package com.jim.jimbo.drawingskb96;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static File PATH;
    public static SimpleItemRecyclerViewAdapter SIRVA;
    public static DrawingContent DC;

    Spinner spinner;
    private TextView countsText;

    public static String DESCRIPTION = "Rit. nr:\nLängd:\nBredd:\nTjocklek:\nP. KF1:\nP. KF2:\nP. Weeke:\nExtra Info:";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //no result
            }
        }
    }//onActivityResult

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("main", "OnRestart");
        DC.Reload_Drawings();
        Update_Counts();

        DC.Clear_Entries();
        DC.AddEntriesFromReg(spinner.getSelectedItemPosition());
        SIRVA.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        DC = new DrawingContent();

        SIRVA = new SimpleItemRecyclerViewAdapter(DC.DRAWINGS_TO_SHOW);

        PATH = this.getFilesDir();

        countsText = (TextView) findViewById(R.id.counts);

        ReadFromFile();

        Create_Spinner();
        Create_Toolbar();
        Create_Buttons();
        Update_Counts();

        Setup_RecyclerView();


    }

    public void Update_Counts() {
        if (countsText != null) {
            countsText.setText("Antal ritningar: " + String.valueOf(DC.DRAWINGS_REG.size()));
            countsText.setBackgroundColor(Color.WHITE);
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
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        //RecyclerView.ItemDecoration vertical = new VerticalSpaceItemDecoration(20);
        //recyclerView.addItemDecoration(vertical);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void Create_Buttons() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Snackbar.make(view, "Replace with your own action babedi", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    Intent myIntent = new Intent(MainActivity.this, CreateNewDrawing.class);
                    MainActivity.this.startActivity(myIntent);
                }
            });
        }

        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.fab_search);
        if (search != null) {
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Snackbar.make(view, "Replace with your own action babedi", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    Intent myIntent = new Intent(MainActivity.this, Search.class);
                    MainActivity.this.startActivity(myIntent);
                }
            });
        }
        Button button = (Button) findViewById(R.id.ToBackupView);

        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent myIntent = new Intent(MainActivity.this, BackupDrawings.class);
                    MainActivity.this.startActivity(myIntent);
                }
            });
        }

    }

    private void Create_Toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = "";
        setTitle(title);
    }

    private void Create_Spinner() {
        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        // Spinner Drop down elements
        List<String> sortlist = new ArrayList<String>();
        sortlist.add("Ritn.nummer");
        sortlist.add("Namn");
        sortlist.add("Längd");
        sortlist.add("Bredd");
        sortlist.add("Höjd");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortlist);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        if (spinner != null) {
            spinner.setAdapter(dataAdapter);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Log.i("main", "Item Selected: " + position);
        DC.Clear_Entries();
        DC.AddEntriesFromReg(position);
        SIRVA.notifyDataSetChanged();

        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        //((TextView) parent.getChildAt(0)).setTextSize(5);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
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
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);
            holder.mDimensionView.setText(holder.mItem.dimensions);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditDrawing.position = holder.mItem.id;

                    Context context = v.getContext();
                    Intent intent = new Intent(context, DrawingDetailActivity.class);
                    intent.putExtra(DrawingDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                    context.startActivity(intent);
                }
            });
            holder.mView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    return false;
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

    class ShowReadingData extends Thread {
        public void run() {
            Toast.makeText(getBaseContext(), "Läser in ritningar...", Toast.LENGTH_SHORT).show(); // Funkar ej
        }

    }

    public static void SaveToFile(Context context) {

        OutputStreamWriter osw = null;
        Log.i("main", "Sparar...");
        StringBuilder sb = new StringBuilder();

        try {
            osw = new OutputStreamWriter(context.openFileOutput(MainActivity.DC.FILE_NAME, Context.MODE_PRIVATE));
            for (Drawing d : MainActivity.DC.DRAWINGS_REG.values()) {
                sb.append(d.getDrawingnumber() + "\nEND\n").append(d.getName() + "\nEND\n").append(d.getLength() + "\nEND\n").append(d.getWidth() + "\nEND\n").append(d.getHeight() + "\nEND\n").append(d.getProgKf1() + "\nEND\n").append(d.getProgKf2() + "\nEND\n").append(d.getProgWeeke() + "\nEND\n").append(d.getAdditionalInfo() + "\nEND");
                sb.append("\n" + "END_OF_DRAFT");
                sb.append("\n");
            }

            osw.write(sb.toString());
            Toast.makeText(context, "Sparat!", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("main", "FEL. KUNDE EJ SKAPA FILEN");
            Toast.makeText(context, "FEL. KUNDE EJ SKAPA FILEN", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("main", "FEL. KUNDE EJ SPARA");
            Toast.makeText(context, "FEL. KUNDE EJ SPARA", Toast.LENGTH_LONG).show();
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("main", "FEL. KUNDE EJ STÄNGA FILEN");
                Toast.makeText(context, "FEL. KUNDE EJ STÄNGA FILEN", Toast.LENGTH_LONG).show();
            }
        }
    }

    public final void ReadFromFile() {
        Log.i("main", "Laddar ritningar...");
        Scanner scan = null;
        InputStream is = null;
        try {

            is = openFileInput(MainActivity.DC.FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int c;
            StringBuilder temp = new StringBuilder();

            while ((c = bufferedReader.read()) != -1) {
                temp.append(Character.toString((char) c));
            }

            scan = new Scanner(temp.toString());
            String t;
            ArrayList<String> s = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            boolean newline = false;
            while (scan.hasNext()) {
                t = scan.nextLine();
                if (t.matches("END_OF_DRAFT")) {
                    DC.AddDrawingFromMenu(Integer.valueOf(s.get(0)), s.get(1), Float.valueOf(s.get(2)), Float.valueOf(s.get(3)), Integer.valueOf(s.get(4)),
                            Integer.valueOf(s.get(5)), Integer.valueOf(s.get(6)), Integer.valueOf(s.get(7)), s.get(8));
                    s.clear();

                } else if (t.matches("END")) {
                    s.add(sb.toString());
                    sb.setLength(0);
                    newline = false;
                } else {
                    if (newline)
                        sb.append("\n");
                    sb.append(t);
                    newline = true;
                }
            }

            Toast.makeText(getBaseContext(), "Ritningar inläst", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "IO Error", Toast.LENGTH_LONG).show();
            Log.e("main", e.toString());
        } finally {
            if (scan != null) {
                scan.close();
            }
            try {
                if (is != null) {
                    is.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "FEL. Kunde inte stänga fileinputstream!", Toast.LENGTH_LONG).show();
                Log.e("main", e.toString());
            }
        }
    }
}