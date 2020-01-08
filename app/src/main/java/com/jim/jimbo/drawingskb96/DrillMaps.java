package com.jim.jimbo.drawingskb96;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TabHost;

import java.io.File;

public class DrillMaps extends Activity {

    ZoomableImageView drillmap1, drillmap2, drillmap3;
    Bitmap bm;

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.drill_maps);

        Setup_Tabs();
        Setup_Drill_Maps();
    }

    private void Setup_Drill_Maps() {
        drillmap1 = (ZoomableImageView) findViewById(R.id.imageView1);
        drillmap2 = (ZoomableImageView) findViewById(R.id.imageView2);
        drillmap3 = (ZoomableImageView) findViewById(R.id.imageView3);

        //String myJpgPath = Environment.getExternalStorageDirectory().getPath() + "/test.jpg"; //UPDATE WITH YOUR OWN JPG FILE

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6; //1 är orginalskala
        //Bitmap bm = BitmapFactory.decodeFile(baseFolder, options);
        //bm = BitmapFactory.decodeResource(getResources(),R.drawable.denna, options);
        //drillmap1.setImageBitmap(bm);

        /*bm = BitmapFactory.decodeResource(getResources(),R.drawable.denna, options);
        drillmap2.setImageBitmap(bm);

        bm = BitmapFactory.decodeResource(getResources(),R.drawable.denna, options);
        drillmap3.setImageBitmap(bm);*/
        File file = getAlbumStorageDir("test");

    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("main", "Directory not created");
        }
        return file;
    }

    private void Setup_Tabs() {

        TabHost th = (TabHost) findViewById(R.id.tabHost);
        th.setup();
        TabHost.TabSpec specs = th.newTabSpec("tag1");
        specs.setContent(R.id.tab1);
        specs.setIndicator("Weeke 1");
        th.addTab(specs);

        specs = th.newTabSpec("tag2");
        specs.setContent(R.id.tab2);
        specs.setIndicator("Weeke 2");
        th.addTab(specs);

        specs = th.newTabSpec("tag3");
        specs.setContent(R.id.tab3);
        specs.setIndicator("Weeke 3");
        th.addTab(specs);


    }
}