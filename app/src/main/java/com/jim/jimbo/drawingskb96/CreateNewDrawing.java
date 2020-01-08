package com.jim.jimbo.drawingskb96;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CreateNewDrawing extends Activity {
    int dnumber = 0;
    String name = "";
    float length = 0;
    float width = 0;
    int height = 0;
    int pkf1 = 0;
    int pkf2 = 0;
    int pweeke = 0;
    String extraInfo = "";


    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.create_new_drawing);

        AddLayout();
    }

    private void AddLayout() {

        final EditText dNumber = (EditText) findViewById(R.id.etDnumber);

        if (dNumber != null) {
            dNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (MainActivity.DC.DRAWINGS_REG.containsKey(s.toString())) {
                        dNumber.setTextColor(Color.parseColor("#ff0000"));
                        dNumber.setTypeface(null, Typeface.BOLD);
                        Toast.makeText(getBaseContext(), "Ritningsnumret finns redan!", Toast.LENGTH_SHORT).show();
                    } else {
                        dNumber.setTextColor(Color.parseColor("#000000"));
                        dNumber.setTypeface(null, Typeface.NORMAL);
                    }

                    //MainActivity.SIRVA.notifyDataSetChanged(); // Behövs denna?
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }


        final EditText eTname = (EditText) findViewById(R.id.etName);
        final EditText eTlength = (EditText) findViewById(R.id.etLength);
        final EditText eTwidth = (EditText) findViewById(R.id.etWidth);
        final EditText eTheight = (EditText) findViewById(R.id.etHeight);
        final EditText eTpkf1 = (EditText) findViewById(R.id.etPkf1);
        final EditText eTpkf2 = (EditText) findViewById(R.id.etPkf2);
        final EditText eTpweeke = (EditText) findViewById(R.id.etPweeke);
        final EditText eTextraInfo = (EditText) findViewById(R.id.etAdditionalInfo);

        final Button button = (Button) findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (dNumber != null && dNumber.getText().toString().length() > 10) {
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


                if (dNumber != null && !dNumber.getText().toString().matches(""))
                    dnumber = Integer.valueOf(dNumber.getText().toString());
                if (!eTname.getText().toString().matches(""))
                    name = eTname.getText().toString();
                if (!eTlength.getText().toString().matches(""))
                    length = Float.valueOf(eTlength.getText().toString());
                if (!eTwidth.getText().toString().matches(""))
                    width = Float.valueOf(eTwidth.getText().toString());
                if (!eTheight.getText().toString().matches(""))
                    height = Integer.valueOf(eTheight.getText().toString());
                if (!eTpkf1.getText().toString().matches(""))
                    pkf1 = Integer.valueOf(eTpkf1.getText().toString());
                if (!eTpkf2.getText().toString().matches(""))
                    pkf2 = Integer.valueOf(eTpkf2.getText().toString());
                if (!eTpweeke.getText().toString().matches(""))
                    pweeke = Integer.valueOf(eTpweeke.getText().toString());
                if (!eTextraInfo.getText().toString().matches(""))
                    extraInfo = eTextraInfo.getText().toString();

                MainActivity.DC.AddDrawingFromMenu(
                        dnumber,
                        name,
                        length,
                        width,
                        height,
                        pkf1,
                        pkf2,
                        pweeke,
                        extraInfo);
                MainActivity.SaveToFile(getBaseContext());
                MainActivity.SIRVA.notifyDataSetChanged();
                finish();
            }
        });
    }
}
