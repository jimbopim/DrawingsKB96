package com.jim.jimbo.drawingskb96;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawingContent {
    public final List<DrawingEntry> DRAWINGS_TO_SHOW = new ArrayList<DrawingEntry>();
    public static final Map<String, DrawingEntry> LISTENTRIES = new HashMap<String, DrawingEntry>();
    public final Map<String, Drawing> DRAWINGS_REG = new HashMap<String, Drawing>();

    public final String FILE_NAME = "Drawings.txt";

    DrawingContent() {
        //AddTestDrawing(13591, "Skjutdörr 2p", 1, 0, 0, 0, 0, 0);
        //AddTestDrawing(13592, "Skjutdörr 3p", 1, 0, 0, 0, 0, 0);
        //AddTestDrawing(13593, "Skjutdörr 3L", 1, 0, 0, 0, 0, 0);

    }

    private void addDrawingEntry(DrawingEntry drawing) {
        DRAWINGS_TO_SHOW.add(drawing);
        LISTENTRIES.put(drawing.id, drawing);
    }

    private DrawingEntry createDrawingEntry(int position) {
        String l = String.valueOf(DRAWINGS_REG.get(String.valueOf(position)).getLength());
        String w = String.valueOf(DRAWINGS_REG.get(String.valueOf(position)).getWidth());

        float l_temp = DRAWINGS_REG.get(String.valueOf(position)).getLength();
        float w_temp = DRAWINGS_REG.get(String.valueOf(position)).getWidth();

        if (l_temp - (int) l_temp == 0)
            l = String.valueOf((int) l_temp);
        if (w_temp - (int) w_temp == 0)
            w = String.valueOf((int) w_temp);


        return new DrawingEntry(String.valueOf(DRAWINGS_REG.get(String.valueOf(position)).getDrawingnumber()),
                DRAWINGS_REG.get(String.valueOf(position)).getName(), makeDetails(position), l + "x" + w);
    }

    private void editDrawingEntry(DrawingEntry drawing, int position) {
        int i = 0;
        for (DrawingEntry d : DRAWINGS_TO_SHOW) {
            if (d.getIDAsInt() == position) {
                DRAWINGS_TO_SHOW.set(i, drawing);
                break;
            }
            i++;
        }

        LISTENTRIES.put(String.valueOf(position), drawing);
    }


    private String makeDetails(int position) {

        Drawing d = DRAWINGS_REG.get(String.valueOf(position));

        String l = String.valueOf(d.getLength());
        String w = String.valueOf(d.getWidth());

        float l_temp = DRAWINGS_REG.get(String.valueOf(position)).getLength();
        float w_temp = DRAWINGS_REG.get(String.valueOf(position)).getWidth();

        if (l_temp - (int) l_temp == 0)
            l = String.valueOf((int) l_temp);
        if (w_temp - (int) w_temp == 0)
            w = String.valueOf((int) w_temp);

        StringBuilder builder = new StringBuilder();

        if (d.getDrawingnumber() != 0)
            builder.append(d.getDrawingnumber());
        builder.append("\n");
        if (d.getLength() != 0)
            builder.append(l);
        builder.append("\n");
        if (d.getWidth() != 0)
            builder.append(w);
        builder.append("\n");
        if (d.getHeight() != 0)
            builder.append(d.getHeight());
        builder.append("\n");
        if (d.getProgKf1() != 0)
            builder.append(d.getProgKf1());
        builder.append("\n");
        if (d.getProgKf2() != 0)
            builder.append(d.getProgKf2());
        builder.append("\n");
        if (d.getProgWeeke() != 0)
            builder.append(d.getProgWeeke());
        builder.append("\n");
        builder.append(d.getAdditionalInfo());

        return builder.toString();
    }

    public void AddTestDrawing(int dNumber, String s, int l, int w, int h, int pKf1, int pKf2, int pWeeke, String extraInfo) {
        DRAWINGS_REG.put(String.valueOf(dNumber), new Drawing(dNumber, s, l, w, h, pKf1, pKf2, pWeeke, extraInfo));
        addDrawingEntry(createDrawingEntry(dNumber));
    }

    public void AddDrawingFromMenu(int dNumber, String s, float l, float w, int h, int pKf1, int pKf2, int pWeeke, String extraInfo) {
        DRAWINGS_REG.put(String.valueOf(dNumber), new Drawing(dNumber, s, l, w, h, pKf1, pKf2, pWeeke, extraInfo));
        addDrawingEntry(createDrawingEntry(dNumber));
        MainActivity.SIRVA.notifyDataSetChanged();
    }

    public void AddDrawingToSearchMenu(int position, int dNumber, String s, float l, float w, int h, int pKf1, int pKf2, int pWeeke, String extraInfo) {

        DRAWINGS_REG.put(String.valueOf(position), new Drawing(dNumber, s, l, w, h, pKf1, pKf2, pWeeke, extraInfo));
        addDrawingEntry(createDrawingEntry(position));
        MainActivity.SIRVA.notifyDataSetChanged();
    }

    public void AddEntriesFromReg(int sorting) {
        List<Drawing> list = new ArrayList<>();
        for (String pos : DRAWINGS_REG.keySet()) {
            list.add(DRAWINGS_REG.get(pos));
        }
        switch (sorting) {
            case 0:
                list = SortByDrawingNumber(list);
                break;
            case 1:
                list = SortByDrawingName(list);
                break;
            case 2:
                list = SortByLength(list);
                break;
            case 3:
                list = SortByWidth(list);
                break;
            case 4:
                list = SortByHeight(list);
                break;
        }

        for (Drawing d : list) {
            addDrawingEntry(createDrawingEntry(d.getDrawingnumber()));
        }

    }

    public void EditDrawingFromMenu(int position, int dNumber, String s, float l, float w, int h, int pKf1, int pKf2, int pWeeke, String extraInfo) {
        DRAWINGS_REG.put(String.valueOf(position), new Drawing(dNumber, s, l, w, h, pKf1, pKf2, pWeeke, extraInfo));
        editDrawingEntry(createDrawingEntry(position), position);
        MainActivity.SIRVA.notifyDataSetChanged();
    }

    public void RemoveDrawingFromMenu(int position) {
        DRAWINGS_REG.remove(String.valueOf(position));
        LISTENTRIES.remove(String.valueOf(position));

        for (int i = 0; i < DRAWINGS_TO_SHOW.size(); i++) {
            if (DRAWINGS_TO_SHOW.get(i).getIDAsInt() == position) {
                DRAWINGS_TO_SHOW.remove(i);
                break;
            }
        }

        MainActivity.SIRVA.notifyDataSetChanged();
    }

    public List<Drawing> SortByDrawingNumber(List<Drawing> list) {
        Drawing d1, d2;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == j)
                    continue;
                if (list.get(i).getDrawingnumber() < list.get(j).getDrawingnumber()) {
                    d1 = list.get(i);
                    d2 = list.get(j);
                    list.set(i, d2);
                    list.set(j, d1);
                } else if (list.get(i).getDrawingnumber() == list.get(j).getDrawingnumber()) { //Annars sortera efter namn
                    if (list.get(i).getName().compareToIgnoreCase(list.get(j).getName()) < 0) {
                        d1 = list.get(i);
                        d2 = list.get(j);
                        list.set(i, d2);
                        list.set(j, d1);
                    }
                }
            }
        }
        return list;
    }

    public List<Drawing> SortByDrawingName(List<Drawing> list) {
        Drawing d1, d2;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == j)
                    continue;
                if (list.get(i).getName().compareToIgnoreCase(list.get(j).getName()) < 0) {
                    d1 = list.get(i);
                    d2 = list.get(j);
                    list.set(i, d2);
                    list.set(j, d1);
                } else if (list.get(i).getName().compareToIgnoreCase(list.get(j).getName()) == 0) { //Annars sortera efter nummer
                    if (list.get(i).getDrawingnumber() < list.get(j).getDrawingnumber()) {
                        d1 = list.get(i);
                        d2 = list.get(j);
                        list.set(i, d2);
                        list.set(j, d1);
                    }
                }
            }
        }
        return list;
    }


    public List<Drawing> SortByLength(List<Drawing> list) {
        Drawing d1, d2;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == j)
                    continue;
                if (list.get(i).getLength() < list.get(j).getLength()) {
                    d1 = list.get(i);
                    d2 = list.get(j);
                    list.set(i, d2);
                    list.set(j, d1);
                } else if (list.get(i).getLength() == list.get(j).getLength()) { //Annars sortera efter bredd
                    if (list.get(i).getWidth() < list.get(j).getWidth()) {
                        d1 = list.get(i);
                        d2 = list.get(j);
                        list.set(i, d2);
                        list.set(j, d1);
                    }
                }
            }
        }
        return list;
    }

    public List<Drawing> SortByWidth(List<Drawing> list) {
        Drawing d1, d2;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == j)
                    continue;
                if (list.get(i).getWidth() < list.get(j).getWidth()) {
                    d1 = list.get(i);
                    d2 = list.get(j);
                    list.set(i, d2);
                    list.set(j, d1);
                } else if (list.get(i).getWidth() == list.get(j).getWidth()) { //Annars sortera efter längd
                    if (list.get(i).getLength() < list.get(j).getLength()) {
                        d1 = list.get(i);
                        d2 = list.get(j);
                        list.set(i, d2);
                        list.set(j, d1);
                    }
                }
            }
        }
        return list;
    }

    public List<Drawing> SortByHeight(List<Drawing> list) {
        Drawing d1, d2;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == j)
                    continue;
                if (list.get(i).getHeight() < list.get(j).getHeight()) {
                    d1 = list.get(i);
                    d2 = list.get(j);
                    list.set(i, d2);
                    list.set(j, d1);
                } else if (list.get(i).getHeight() == list.get(j).getHeight()) { //Annars sortera efter namn
                    if (list.get(i).getName().compareToIgnoreCase(list.get(j).getName()) < 0) {
                        d1 = list.get(i);
                        d2 = list.get(j);
                        list.set(i, d2);
                        list.set(j, d1);
                    }
                }
            }
        }
        return list;
    }

    public void Clear_Drawings() {
        DRAWINGS_REG.clear();
        DRAWINGS_TO_SHOW.clear();
        LISTENTRIES.clear();
        MainActivity.SIRVA.notifyDataSetChanged();
    }

    public void Clear_Entries() {
        DRAWINGS_TO_SHOW.clear();
        LISTENTRIES.clear();
    }

    public void AddSearchResults(String s) {
        Map<String, Drawing> list = GetSearchResults(s);


        Log.i("main", "d.size(): " + list.size());
        Clear_Drawings();
        Drawing d;
        int i = 1;
        //for (int pos = 1; pos < list.size() + 10; pos++) {    //TODO: Måste fixas
        for (String pos : list.keySet()) {
            Log.i("main", "key; " + pos);
            d = list.get(String.valueOf(pos));

            if (d == null)
                continue;
            AddDrawingToSearchMenu(
                    Integer.valueOf(pos),
                    d.getDrawingnumber(),
                    d.getName(),
                    d.getLength(),
                    d.getWidth(),
                    d.getHeight(),
                    d.getProgKf1(),
                    d.getProgKf2(),
                    d.getProgWeeke(),
                    d.getAdditionalInfo());
            i++;
        }

        Log.i("main", "list.size(): " + list.size());

    }


    public Map<String, Drawing> GetSearchResults(String s) {
        Map<String, Drawing> list = new HashMap<String, Drawing>();
        for (Drawing d : MainActivity.DC.DRAWINGS_REG.values()) {
            if (String.valueOf(d.getDrawingnumber()).contains(s)) {
                list.put(String.valueOf(d.getDrawingnumber()), d);
            } else if (d.getName().toLowerCase().contains(s)) {
                list.put(String.valueOf(d.getDrawingnumber()), d);
            } else if (String.valueOf(d.getLength()).contentEquals(s)) {
                list.put(String.valueOf(d.getDrawingnumber()), d);
            } else if (String.valueOf(d.getWidth()).contentEquals(s)) {
                list.put(String.valueOf(d.getDrawingnumber()), d);
            } else if (String.valueOf(d.getHeight()).contentEquals(s)) {
                list.put(String.valueOf(d.getDrawingnumber()), d);
            } else if (String.valueOf(d.getProgKf1()).contentEquals(s)) {
                list.put(String.valueOf(d.getDrawingnumber()), d);
            } else if (String.valueOf(d.getProgKf2()).contentEquals(s)) {
                list.put(String.valueOf(d.getDrawingnumber()), d);
            } else if (String.valueOf(d.getProgWeeke()).contentEquals(s)) {
                list.put(String.valueOf(d.getDrawingnumber()), d);
            }
            Log.i("main", "Get result end");

        }
        return list;
    }

    public void Reload_Drawings() {
        Clear_Entries();
        AddEntriesFromReg(0); //EGENTLIGEN FEL
    }


    public static class DrawingEntry {

        public final String id;
        public final String content;
        public final String details;
        public final String dimensions;

        public DrawingEntry(String id, String content, String details, String dimensions) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.dimensions = dimensions;
        }

        @Override
        public String toString() {
            return content;
        }

        public Integer getIDAsInt() {
            return Integer.valueOf(id);
        }
    }
}