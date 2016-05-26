package com.example.caneraydin.androidwithlogin.domains;

/**
 * Created by caneraydin on 23.04.2016.
 */
public class Training {

    int trainingID;
    int trainingEvaluation;
    String trainingAim;
    String trainingHood;
    String trainingExplanation;
    int behaviorID;
    int trainingTotalQuestion;
    int trainingOK;
    String trainingCreateTime;

    public Training(int trainingID, int trainingEvaluation, String trainingAim, String trainingHood, String trainingExplanation, int behaviorID, int trainingTotalQuestion, int trainingOK, String trainingCreateTime) {
        this.trainingID = trainingID;
        this.trainingEvaluation = trainingEvaluation;
        this.trainingAim = trainingAim;
        this.trainingExplanation = trainingExplanation;
        this.behaviorID = behaviorID;
        this.trainingTotalQuestion = trainingTotalQuestion;
        this.trainingOK = trainingOK;
        this.trainingCreateTime = trainingCreateTime;
        this.trainingHood = trainingHood;
    }

    public Training() {
    }

    public String getTrainingHood() {
        return trainingHood;
    }

    public void setTrainingHood(String trainingHood) {
        this.trainingHood = trainingHood;
    }

    public String getTrainingCreateTime() {
        return trainingCreateTime;
    }

    public void setTrainingCreateTime(String trainingCreateTime) {
        this.trainingCreateTime = trainingCreateTime;
    }

    public int getTrainingID() {
        return trainingID;
    }

    public void setTrainingID(int trainingID) {
        this.trainingID = trainingID;
    }

    public int getTrainingEvaluation() {
        return trainingEvaluation;
    }

    public void setTrainingEvaluation(int trainingEvaluation) {
        this.trainingEvaluation = trainingEvaluation;
    }

    public String getTrainingAim() {
        return trainingAim;
    }

    public void setTrainingAim(String trainingAim) {
        this.trainingAim = trainingAim;
    }

    public String getTrainingExplanation() {
        return trainingExplanation;
    }

    public void setTrainingExplanation(String trainingExplanation) {
        this.trainingExplanation = trainingExplanation;
    }

    public int getBehaviorID() {
        return behaviorID;
    }

    public void setBehaviorID(int behaviorID) {
        this.behaviorID = behaviorID;
    }

    public int getTrainingTotalQuestion() {
        return trainingTotalQuestion;
    }

    public void setTrainingTotalQuestion(int trainingTotalQuestion) {
        this.trainingTotalQuestion = trainingTotalQuestion;
    }

    public int getTrainingOK() {
        return trainingOK;
    }

    public void setTrainingOK(int trainingOK) {
        this.trainingOK = trainingOK;
    }

    @Override
    public String toString() {
        return "Training{" +
                "trainingID=" + trainingID +
                ", trainingEvaluation=" + trainingEvaluation +
                ", trainingAim='" + trainingAim + '\'' +
                ", trainingExplanation='" + trainingExplanation + '\'' +
                ", behaviorID=" + behaviorID +
                ", trainingTotalQuestion=" + trainingTotalQuestion +
                ", trainingOK=" + trainingOK +
                ", trainingCreateTime='" + trainingCreateTime + '\'' +
                '}';
    }
}
