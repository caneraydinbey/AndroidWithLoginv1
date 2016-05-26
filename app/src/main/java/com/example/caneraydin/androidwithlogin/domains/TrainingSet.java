package com.example.caneraydin.androidwithlogin.domains;

/**
 * Created by caneraydin on 25.04.2016.
 */
public class TrainingSet {

    int id;
    int trainingID;
    int studentID;
    int trainingsetCreateTime;
    String trainingsetFinishTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrainingID() {
        return trainingID;
    }

    public void setTrainingID(int trainingID) {
        this.trainingID = trainingID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getTrainingsetCreateTime() {
        return trainingsetCreateTime;
    }

    public void setTrainingsetCreateTime(int trainingsetCreateTime) {
        this.trainingsetCreateTime = trainingsetCreateTime;
    }

    public String getTrainingsetFinishTime() {
        return trainingsetFinishTime;
    }

    public void setTrainingsetFinishTime(String trainingsetFinishTime) {
        this.trainingsetFinishTime = trainingsetFinishTime;
    }

    public TrainingSet() {
    }

    public TrainingSet( int id, int trainingID, int studentID,  int trainingsetCreateTime, String trainingsetFinishTime) {
        this.trainingsetCreateTime = trainingsetCreateTime;
        this.id = id;
        this.trainingID = trainingID;
        this.studentID = studentID;
        this.trainingsetFinishTime = trainingsetFinishTime;
    }

    @Override
    public String toString() {
        return "TrainingSet{" +
                "id=" + id +
                ", trainingID=" + trainingID +
                ", studentUserName=" + studentID +
                ", trainingsetCreateTime=" + trainingsetCreateTime +
                ", trainingsetFinishTime='" + trainingsetFinishTime + '\'' +
                '}';
    }
}
