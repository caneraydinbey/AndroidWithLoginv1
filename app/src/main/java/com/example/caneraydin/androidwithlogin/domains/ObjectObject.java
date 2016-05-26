package com.example.caneraydin.androidwithlogin.domains;

import java.util.Arrays;

/**
 * Created by caneraydin on 23.04.2016.
 */
public class ObjectObject {

    int objectID;
    String objectName;
    String objectImage;
    int objectNumber;
    int shapeID;
    int colorID;
    String createTime;
    byte[] objectImageBlob;

    public ObjectObject() {
    }

    public ObjectObject(int objectID, String objectName, String objectImage, int objectNumber, int shapeID, int colorID, String createTime) {
        this.colorID = colorID;
        this.objectID = objectID;
        this.objectName = objectName;
        this.objectImage = objectImage;
        this.objectNumber = objectNumber;
        this.shapeID = shapeID;
        this.createTime = createTime;
    }

    public ObjectObject(int objectID, String objectName, String objectImage, int objectNumber, int shapeID, int colorID, String createTime, byte[] objectImageBlob) {
        this.objectID = objectID;
        this.objectName = objectName;
        this.objectImage = objectImage;
        this.objectNumber = objectNumber;
        this.shapeID = shapeID;
        this.colorID = colorID;
        this.createTime = createTime;
        this.objectImageBlob = objectImageBlob;
    }

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectImage() {
        return objectImage;
    }

    public void setObjectImage(String objectImage) {
        this.objectImage = objectImage;
    }

    public int getObjectNumber() {
        return objectNumber;
    }

    public void setObjectNumber(int objectNumber) {
        this.objectNumber = objectNumber;
    }

    public int getShapeID() {
        return shapeID;
    }

    public void setShapeID(int shapeID) {
        this.shapeID = shapeID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public byte[] getObjectImageBlob() {
        return objectImageBlob;
    }

    public void setObjectImageBlob(byte[] objectImageBlob) {
        this.objectImageBlob = objectImageBlob;
    }

    @Override
    public String toString() {
        return "ObjectObject{" +
                "objectID=" + objectID +
                ", objectName='" + objectName + '\'' +
                ", objectImage='" + objectImage + '\'' +
                ", objectNumber=" + objectNumber +
                ", shapeID=" + shapeID +
                ", colorID=" + colorID +
                ", createTime='" + createTime + '\'' +
                ", objectImageBlob=" + Arrays.toString(objectImageBlob) +
                '}';
    }
}
