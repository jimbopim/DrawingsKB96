package com.jim.jimbo.drawingskb96;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditDrawing extends Activity {

    int dnumber = 0;
    String name = "";
    float length = 0;
    float width = 0;
    int height = 0;
    int pkf1 = 0;
    int pkf2 = 0;
    int pweeke = 0;
    String extraInfo = "";

    public static String position; // Denna ändras från MainActivity resp Search

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.edit_drawing);

        Log.i("main", "Search: " + position);

        Update_Layout();

    }

    private void Update_Layout() {
        Drawing d = MainActivity.DC.DRAWINGS_REG.get(String.valueOf(position));

        final EditText eTdNumber = (EditText) findViewById(R.id.etDnumber);
        if (d.getDrawingnumber() > 0)
            eTdNumber.setText(String.valueOf(d.getDrawingnumber()));

        final EditText eTname = (EditText) findViewById(R.id.etName);
        eTname.setText(d.getName());


        final EditText eTlength = (EditText) findViewById(R.id.etLength);
        if (d.getLength() > 0) {

            if (d.getLength() - (int) d.getLength() == 0)
                eTlength.setText(String.valueOf((int) d.getLength()));
            else
                eTlength.setText(String.valueOf(d.getLength()));
        }


        final EditText eTwidth = (EditText) findViewById(R.id.etWidth);

        if (d.getWidth() > 0) {

            if (d.getWidth() - (int) d.getWidth() == 0)
                eTwidth.setText(String.valueOf((int) d.getWidth()));
            else
                eTwidth.setText(String.valueOf(d.getWidth()));
        }


        final EditText eTheight = (EditText) findViewById(R.id.etHeight);
        if (d.getHeight() > 0)
            eTheight.setText(String.valueOf(d.getHeight()));

        final EditText eTpkf1 = (EditText) findViewById(R.id.etPkf1);
        if (d.getProgKf1() > 0)
            eTpkf1.setText(String.valueOf(d.getProgKf1()));

        final EditText eTpkf2 = (EditText) findViewById(R.id.etPkf2);
        if (d.getProgKf2() > 0)
            eTpkf2.setText(String.valueOf(d.getProgKf2()));

        final EditText eTpweeke = (EditText) findViewById(R.id.etPweeke);
        if (d.getProgWeeke() > 0)
            eTpweeke.setText(String.valueOf(d.getProgWeeke()));

        final EditText eTadditionalInfo = (EditText) findViewById(R.id.etAdditionalInfo);
        eTadditionalInfo.setText(d.getAdditionalInfo());

        if (eTdNumber != null) {
            eTdNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (MainActivity.DC.DRAWINGS_REG.containsKey(s.toString()) && (!s.toString().equals(position))) {
                        eTdNumber.setTextColor(Color.parseColor("#ff0000"));
                        eTdNumber.setTypeface(null, Typeface.BOLD);
                        Toast.makeText(getBaseContext(), "Ritningsnumret finns redan!", Toast.LENGTH_SHORT).show();
                    } else {
                        eTdNumber.setTextColor(Color.parseColor("#000000"));
                        eTdNumber.setTypeface(null, Typeface.NORMAL);
                    }

                    //MainActivity.SIRVA.notifyDataSetChanged(); // Behövs denna?
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        final Button save_button = (Button) findViewById(R.id.save_button);
        final Button delete_button = (Button) findViewById(R.id.delete_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (eTdNumber != null && eTdNumber.getText().toString().length() > 10) {
                    Toast.makeText(getBaseContext(), "För långt ritningsnummer!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eTlength.getText().toString().length() > 10) {
                    Toast.makeText(getBaseContext(), "För långt längdnummer!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eTwidth.getText().toString().length() > 10) {
                    Toast.makeText(getBaseContext(), "För långt breddnummer!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eTheight.getText().toString().length() > 10) {
                    Toast.makeText(getBaseContext(), "För långt höjdnummer!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eTpkf1.getText().toString().length() > 10) {
                    Toast.makeText(getBaseContext(), "För långt Program KF1-nummer!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eTpkf2.getText().toString().length() > 10) {
                    Toast.makeText(getBaseContext(), "För långt Program KF2-nummer!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eTpweeke.getText().toString().length() > 10) {
                    Toast.makeText(getBaseContext(), "För långt Program Weeke-nummer!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (eTdNumber != null && !eTdNumber.getText().toString().matches(""))
                    dnumber = Integer.valueOf(eTdNumber.getText().toString());
                if (!eTname.getText().toString().matches(""))
                    EditDrawing.this.name = eTname.getText().toString();
                if (!eTlength.getText().toString().matches(""))
                    EditDrawing.this.length = Float.valueOf(eTlength.getText().toString());
                if (!eTwidth.getText().toString().matches(""))
                    EditDrawing.this.width = Float.valueOf(eTwidth.getText().toString());
                if (!eTheight.getText().toString().matches(""))
                    EditDrawing.this.height = Integer.valueOf(eTheight.getText().toString());
                if (!eTpkf1.getText().toString().matches(""))
                    EditDrawing.this.pkf1 = Integer.valueOf(eTpkf1.getText().toString());
                if (!eTpkf2.getText().toString().matches(""))
                    EditDrawing.this.pkf2 = Integer.valueOf(eTpkf2.getText().toString());
                if (!eTpweeke.getText().toString().matches(""))
                    EditDrawing.this.pweeke = Integer.valueOf(eTpweeke.getText().toString());
                if (!eTadditionalInfo.getText().toString().matches(""))
                    extraInfo = eTadditionalInfo.getText().toString();

                MainActivity.DC.EditDrawingFromMenu(
                        //Integer.valueOf(position),
                        dnumber,
                        dnumber,
                        EditDrawing.this.name,
                        EditDrawing.this.length,
                        EditDrawing.this.width,
                        EditDrawing.this.height,
                        EditDrawing.this.pkf1,
                        EditDrawing.this.pkf2,
                        EditDrawing.this.pweeke,
                        extraInfo);

                if (dnumber != Integer.valueOf(position))
                    MainActivity.DC.RemoveDrawingFromMenu(Integer.valueOf(position));

                MainActivity.SaveToFile(v.getContext());

                MainActivity.SIRVA.notifyDataSetChanged();

                Intent openMainActivity = new Intent(v.getContext(), MainActivity.class);
                openMainActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openMainActivity);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.DC.RemoveDrawingFromMenu(Integer.valueOf(position));
                MainActivity.SaveToFile(getBaseContext());
                MainActivity.SIRVA.notifyDataSetChanged();

                Intent openMainActivity = new Intent(v.getContext(), MainActivity.class);
                openMainActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openMainActivity);
            }
        });
    }
}
