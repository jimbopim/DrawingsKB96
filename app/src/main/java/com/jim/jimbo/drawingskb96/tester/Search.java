/*package com.jim.jimbo.draftskb96;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Search extends Activity {
    ListView listView;
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.search);

        final EditText Search = (EditText) findViewById(R.id.etString);

        ArrayList<String> korv = new ArrayList<String>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, korv);

        ArrayAdapter<Draft> adapter1 = new ArrayAdapter<Draft>(this,
                android.R.layout.simple_list_item_1, DraftContent.GetSearchResults("test"));

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        listView = (ListView) findViewById(R.id.lvDrafts);

        final Button search_button = (Button) findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<Draft.DraftEntry> list = new ArrayList<Draft.DraftEntry>();
                DraftContent.GetSearchResults(Search.getText().toString());

                String[] hej = {"1", "2"};
            }
        });
    }
}
*/