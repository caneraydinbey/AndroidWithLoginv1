package com.example.caneraydin.androidwithlogin.domains;

/**
 * Created by caneraydin on 23.04.2016.
 */
public class TrainingObject {

    int trainingobjectID;
    int trainingID;
    int trainingobjectLevel;
    int trainingobjectAnswer;
    int trainingobjectOne;
    int trainingobjectTwo;
    int trainingobjectThree;
    int trainingobjectFour;
    int trainingobjectFive;
  //  int trainingobjectResponses;
//todo trainingobhect basharfbuyut

    public TrainingObject() {
    }

    public TrainingObject(int trainingobjectID, int trainingID, int trainingobjectLevel, int trainingobjectAnswer, int trainingobjectOne,int trainingobjectTwo, int trainingobjectThree, int trainingobjectFour, int trainingobjectFive ){//, /int trainingobjectResponses) {
        this.trainingobjectID = trainingobjectID;
        this.trainingobjectOne = trainingobjectOne;
        this.trainingID = trainingID;
        this.trainingobjectLevel = trainingobjectLevel;
        this.trainingobjectAnswer = trainingobjectAnswer;
        this.trainingobjectTwo = trainingobjectTwo;
        this.trainingobjectThree = trainingobjectThree;
        this.trainingobjectFour = trainingobjectFour;
        this.trainingobjectFive = trainingobjectFive;
        //this.trainingobjectResponses = trainingobjectResponses;
    }

    public int getTrainingID() {
        return trainingID;
    }

    public void setTrainingID(int trainingID) {
        this.trainingID = trainingID;
    }

    public int getTrainingobjectID() {
        return trainingobjectID;
    }

    public void setTrainingobjectID(int trainingobjectID) {
        this.trainingobjectID = trainingobjectID;
    }

    public int getTrainingobjectLevel() {
        return trainingobjectLevel;
    }

    public void setTrainingobjectLevel(int trainingobjectLevel) {
        this.trainingobjectLevel = trainingobjectLevel;
    }

    public int getTrainingobjectAnswer() {
        return trainingobjectAnswer;
    }

    public void setTrainingobjectAnswer(int trainingobjectAnswer) {
        this.trainingobjectAnswer = trainingobjectAnswer;
    }

    public int getTrainingobjectOne() {
        return trainingobjectOne;
    }

    public void setTrainingobjectOne(int trainingobjectOne) {
        this.trainingobjectOne = trainingobjectOne;
    }

    public int getTrainingobjectTwo() {
        return trainingobjectTwo;
    }

    public void setTrainingobjectTwo(int trainingobjectTwo) {
        this.trainingobjectTwo = trainingobjectTwo;
    }

    public int getTrainingobjectThree() {
        return trainingobjectThree;
    }

    public void setTrainingobjectThree(int trainingobjectThree) {
        this.trainingobjectThree = trainingobjectThree;
    }

    public int getTrainingobjectFour() {
        return trainingobjectFour;
    }

    public void setTrainingobjectFour(int trainingobjectFour) {
        this.trainingobjectFour = trainingobjectFour;
    }

    public int getTrainingobjectFive() {
        return trainingobjectFive;
    }

    public void setTrainingobjectFive(int trainingobjectFive) {
        this.trainingobjectFive = trainingobjectFive;
    }

   /* public int getTrainingobjectResponses() {
        return trainingobjectResponses;
    }

    public void setTrainingobjectResponses(int trainingobjectResponses) {
        this.trainingobjectResponses = trainingobjectResponses;
    }*/

    @Override
    public String toString() {
        return "TrainingObject{" +
                "trainingobjectID=" + trainingobjectID +
                ", trainingID=" + trainingID +
                ", trainingobjectLevel=" + trainingobjectLevel +
                ", trainingobjectAnswer=" + trainingobjectAnswer +
                ", trainingobjectOne=" + trainingobjectOne +
                ", trainingobjectTwo=" + trainingobjectTwo +
                ", trainingobjectThree=" + trainingobjectThree +
                ", trainingobjectFour=" + trainingobjectFour +
                ", trainingobjectFive=" + trainingobjectFive +
              //  ", trainingobjectResponses=" + trainingobjectResponses +
                '}';
    }
}
