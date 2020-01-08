package com.jim.jimbo.drawingskb96;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

public class BackupDrawings extends Activity {
    String savethis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backup_drawings);

        Setup_Tabs();

        Backup_View();
        Restore_View();

    }

    private void Restore_View() {
        final EditText Save = (EditText) findViewById(R.id.restore_text);

        final Button button = (Button) findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                savethis = Save.getText().toString();
                Restore_Drawings();
                MainActivity.SaveToFile(getBaseContext());
            }

        });
    }

    private void Backup_View() {

        final TextView tv = (TextView) findViewById(R.id.backup_text);
        StringBuilder sb = new StringBuilder();

        for (Drawing d : MainActivity.DC.DRAWINGS_REG.values()) {
            sb.append(d.getDraftnumber() + "\nEND\n").append(d.getName() + "\nEND\n").append(d.getLength() + "\nEND\n").append(d.getWidth() + "\nEND\n").append(d.getHeight() + "\nEND\n").append(d.getProgKf1() + "\nEND\n").append(d.getProgKf2() + "\nEND\n").append(d.getProgWeeke() + "\nEND\n").append(d.getAdditionalInfo() + "\nEND");
            sb.append("\n" + "END_OF_DRAWING");
            sb.append("\n");
        }
        tv.setText(sb);
    }

    private void Setup_Tabs() {

        TabHost th = (TabHost) findViewById(R.id.tabHost);
        th.setup();
        TabHost.TabSpec specs = th.newTabSpec("tag1");
        specs.setContent(R.id.tab1);
        specs.setIndicator("Backup");
        th.addTab(specs);

        specs = th.newTabSpec("tag2");
        specs.setContent(R.id.tab2);
        specs.setIndicator("Restore");
        th.addTab(specs);
    }

    public final void Restore_Drawings() {
        Scanner scan = null;
        try {
            int test = 0;
            MainActivity.DC.Clear_Drafts();
            scan = new Scanner(savethis);
            String t;
            ArrayList<String> s = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            boolean newline = false;
            while (scan.hasNext()) {
                t = scan.nextLine();
                if (t.matches("END_OF_DRAWING")) {
                    test++;
                    Log.i("main", "Restore: " + test);
                    MainActivity.DC.AddDrawingFromMenu(Integer.valueOf(s.get(0)), s.get(1), Float.valueOf(s.get(2)), Float.valueOf(s.get(3)), Integer.valueOf(s.get(4)),
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
        } finally {
            if (scan != null) {
                scan.close();
            }
        }
    }
}
