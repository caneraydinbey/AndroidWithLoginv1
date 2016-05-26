package com.example.caneraydin.androidwithlogin.domains;

/**
 * Created by caneraydin on 01.05.2016.
 */
public class Color {
    int colorID;
    String colorName;

    public Color() {
    }

    public Color(int colorID, String colorName) {
        this.colorID = colorID;
        this.colorName = colorName;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return "Color{" +
                "colorID=" + colorID +
                ", colorName='" + colorName + '\'' +
                '}';
    }
}
