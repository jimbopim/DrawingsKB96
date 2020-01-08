package com.jim.jimbo.drawingskb96;

public class Drawing {
    private int drawingnumber, height, progKf1, progKf2, progWeeke;
    private float length, width;
    String name, additionalInfo;
    //String id, content, details;


    Drawing(int dNumber, String s, float l, float w, int h, int pKf1, int pKf2, int pWeeke, String extraInfo) {
        drawingnumber = dNumber;
        name = s;
        length = l;
        width = w;
        height = h;
        progKf1 = pKf1;
        progKf2 = pKf2;
        progWeeke = pWeeke;
        additionalInfo = extraInfo;
    }

    public int getDrawingnumber() {
        return drawingnumber;
    }

    public void setDdrawingrawingnumber(int drawingnumber) {
        this.drawingnumber = drawingnumber;
    }

    public float getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getProgKf1() {
        return progKf1;
    }

    public void setProgKf1(int progKf1) {
        this.progKf1 = progKf1;
    }

    public int getProgKf2() {
        return progKf2;
    }

    public void setProgKf2(int progKf2) {
        this.progKf2 = progKf2;
    }

    public int getProgWeeke() {
        return progWeeke;
    }

    public void setProgWeeke(int progWeeke) {
        this.progWeeke = progWeeke;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}

