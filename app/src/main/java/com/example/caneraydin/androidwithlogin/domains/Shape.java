package com.example.caneraydin.androidwithlogin.domains;

/**
 * Created by caneraydin on 01.05.2016.
 */
public class Shape {
    int shapeID;
    String shapeName;

    public Shape() {
    }

    public Shape(int shapeID, String shapeName) {
        this.shapeID = shapeID;
        this.shapeName = shapeName;
    }

    public int getShapeID() {
        return shapeID;
    }

    public void setShapeID(int shapeID) {
        this.shapeID = shapeID;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "shapeID=" + shapeID +
                ", shapeName='" + shapeName + '\'' +
                '}';
    }
}
