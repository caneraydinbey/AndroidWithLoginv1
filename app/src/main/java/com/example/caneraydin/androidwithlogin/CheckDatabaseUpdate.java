package com.example.caneraydin.androidwithlogin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.caneraydin.androidwithlogin.domains.Color;
import com.example.caneraydin.androidwithlogin.domains.ObjectObject;
import com.example.caneraydin.androidwithlogin.domains.Shape;
import com.example.caneraydin.androidwithlogin.domains.Training;
import com.example.caneraydin.androidwithlogin.domains.TrainingObject;
import com.example.caneraydin.androidwithlogin.domains.TrainingResponse;
import com.example.caneraydin.androidwithlogin.domains.TrainingSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by caneraydin on 25.04.2016.
 */
public class CheckDatabaseUpdate extends AsyncTask<String,Void,String> {
    ProgressDialog dialog;
    private Context context;
    String TAG = "Chic";
   // int lastTrainingSetUpdate, lastObjectUpdate;
    JSONObject jsonObj;
    JSONArray jsnAryTrainingSet;
    DatabaseHandler dbHandler;

    // object Table column names
    private static final String KEY_COLOR_ID = "colorID";
    private static final String KEY_OBJECT_ID = "objectID";
    private static final String KEY_OBJECT_NAME = "objectName";
    private static final String KEY_OBJECT_IMAGE = "objectImage";
    private static final String KEY_OBJECT_NUMBER = "objectNumber";
    private static final String KEY_SHAPE_ID = "shapeID";
    private static final String KEY_CREATE_TIME = "createTime";
    private static final String KEY_OBJECT_IMAGE_BLOB = "objectImageBlob";//to keep image

    // trainingobject Table column names
    private static final String KEY_TRAINING_OBJECT_ID = "trainingobjectID";
    //  private static final String KEY_TRAINING_ID = "trainingID";
    private static final String KEY_TRAINING_OBJECT_LEVEL = "trainingobjectLevel";
    private static final String KEY_TRAINING_OBJECT_ANSWER = "trainingobjectAnswer";
    private static final String KEY_TRAINING_OBJECT_ONE = "trainingobjectOne";
    private static final String KEY_TRAINING_OBJECT_TWO = "trainingobjectTwo";
    private static final String KEY_TRAINING_OBJECT_THREE = "trainingobjectThree";
    private static final String KEY_TRAINING_OBJECT_FOUR = "trainingobjectFour";
    private static final String KEY_TRAINING_OBJECT_FIVE = "trainingobjectFive";
    //  private static final String KEY_TRAINING_OBJECT_RESPONSES = "trainingobjectresponses";

    // training Table column names
    private static final String KEY_TRAINING_ID = "trainingID";
    private static final String KEY_TRAINING_EVALUATION = "trainingEvaluation";
    private static final String KEY_TRAINING_HOOD= "trainingHood";
    private static final String KEY_TRAINING_AIM = "trainingAim";
    private static final String KEY_TRAINING_EXPLANATION = "trainingExplanation";
    private static final String KEY_BEHAVIOR_ID = "behaviorID";
    private static final String KEY_TRAINING_TOTAL_QUESTION = "trainingTotalQuestion";
    private static final String KEY_TRAINING_OK = "trainingOK";
    private static final String KEY_TRAINING_CREATE_TIME = "trainingCreateTime";

    // trainingset Table column names
    private static final String KEY_ID = "id";
    //  private static final String KEY_TRAINING_ID = "trainingID";
    private static final String KEY_STUDENT_ID = "studentID";
    private static final String KEY_TRAINING_SET_FINISH_TIME = "trainingsetFinishTime";
    private static final String KEY_TRAINING_SET_CREATE_TIME = "trainingsetCreateTime";

    // color Table column names
    // private static final String KEY_COLOR_ID = "colorID";
    private static final String KEY_COLOR_NAME = "colorName";

    // shape Table column names
    //private static final String KEY_SHAPE_ID = "shapeID";
    private static final String KEY_SHAPE_NAME = "shapeName";

    public CheckDatabaseUpdate(Context context) {
        Log.d(TAG, "checkdatabaseupdate signinactivity consturct*******");
        this.context = context;
       // this.lastUpdate = lastUpdate;
    }

    protected void onPreExecute(){
        Log.d(TAG, "checkdatabaseupdate onpre");
        dialog = ProgressDialog.show(context, "Checking database for new trainings", null, true, true);
    }

    @Override
    protected String doInBackground(String... arg0) {
        Log.d(TAG, "checkdatabaseupdate doin");

      //  Log.d(TAG, "checkdatabaseupdate consturct try");
        //   String username = (String) arg0[0];
        String username = (String) arg0[0];
        String lastTrainingSetUpdate = (String) arg0[1];
     //   String lastObjectUpdate = (String) arg0[2];

        Log.d(TAG, "checkdatabaseupdate username: " + username + " lasttrainingsetupdate: " + lastTrainingSetUpdate);//+" lastobjectup: "+lastObjectUpdate);
//http://oep.esy.es/check_database_update.php?studentusername=t&lasttrainingsetupdate=1464028657&lastobjectupdate=1460857129
        //http://oep.esy.es/check_database_update.php?studentusername=t&lasttrainingsetupdate=1464196649
        String uri = "http://oep.esy.es/check_database_update.php?" + "studentusername=" + username + "&lastTrainingSetUpdate=" + lastTrainingSetUpdate;
                //"lastobjectupdate="+lastObjectUpdate;

        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader bufferedReader = null;
            URL url = new URL(uri);
            Log.d(TAG, "checkdatgabase url " + url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            //  Log.d(TAG," bufferedReader "+ bufferedReader);
            String json;

            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
            //    Log.d(TAG, "checkdatabaseupdate sb " + sb.toString()+" json"+json);//// TODO: 5/25/2016 sil
                //Log.d(TAG, "checkdatabaseupdate sb: " + sb);
            }
        } catch (IOException e) {
            Log.d(TAG, "checkdatabaseupdate hata doin");
            return new String("Exception: " + e.getMessage());
        }

/// TODO: 28.04.2016 bu kontrol yerine success 1 mi 0 mi ile yap jsonArray verisi kullanarak
       // if(!sb.equals("nonewtrainings")) {
            Log.d(TAG, "checkdatabaseupdate sb newtrainings: " + sb);
         //   Toast.makeText(context, "New trainings were added, they need to be added to local database..", Toast.LENGTH_LONG).show();
            // Intent intent = new Intent(context, MainActivity.class);
            //  intent.putExtra("username", username);
            // intent.putExtra("MESSAGE", "You have been successfully Registered");
            //context.startActivity(intent);
      //      Log.d(TAG, "sb: "+sb);

       // }
       // else{
          //  Log.d(TAG, "checkdatabaseupdate result unsuccess");
           // Toast.makeText(context,"no newtrainings, try again", Toast.LENGTH_LONG).show();
       // }

        return sb.toString().trim();
    }

    @Override
    protected void onPostExecute(String result){

      //  Log.d(TAG, "checkdatabaseupdateonpostexe, result='"+result+"'");
        Log.d(TAG, "checkdatabaseupdateonpostexe,");

       // if(result.equals("newtrainings")){
            Log.d(TAG, "checkdatabaseupdate onpostexe result newtrainings: "+result);

        try {
            JSONObject jsonObject = new JSONObject(result);

            //var mi diye kontrol yoksa almayacak
            if(jsonObject.getInt("success_trainingset")!=0){
                Log.d(TAG,"success_trainingset=1");

                TrainingSet trainingSet = new TrainingSet();
                Training training = new Training();
                Shape shape = new Shape();
                Color color = new Color();
                ObjectObject object = new ObjectObject();
                TrainingObject trainingObject = new TrainingObject();
                TrainingResponse trainingResponse = new TrainingResponse();
                dbHandler = new DatabaseHandler(context);

                JSONObject jsonObj;
                JSONArray jsonArray;
                JSONObject c;

                jsonObj = new JSONObject(result);

                /***************************************************************************************************/

                // Getting JSON Array node trainingset
                jsonArray = jsonObj.getJSONArray("trainingset");

                // looping through All trainingsets
                for (int i = 0; i < jsonArray.length(); i++) {
                    c = null;
                    c = jsonArray.getJSONObject(i);

                    trainingSet.setId(c.getInt(KEY_ID));
                    trainingSet.setTrainingID( c.getInt(KEY_TRAINING_ID));
                    trainingSet.setStudentID( c.getInt(KEY_STUDENT_ID));
                    trainingSet.setTrainingsetCreateTime(c.getInt(KEY_TRAINING_SET_CREATE_TIME));
                    trainingSet.setTrainingsetFinishTime(c.getString(KEY_TRAINING_SET_FINISH_TIME));

                    //  Log.d(TAG, "GetAllDatabasetrainingset: "+trainingSet.toString());
                    dbHandler.addTrainingSet(trainingSet);

                }//end of for
                jsonArray = null;

                /***************************************************************************************************/

                // Getting JSON Array node training
                jsonArray = jsonObj.getJSONArray("training");

                // looping through All trainings
                for (int i = 0; i < jsonArray.length(); i++) {
                    c = null;
                    c = jsonArray.getJSONObject(i);

                    training.setTrainingID(c.getInt(KEY_TRAINING_ID));
                    training.setTrainingEvaluation(c.getInt(KEY_TRAINING_EVALUATION));
                    training.setTrainingAim(c.getString(KEY_TRAINING_AIM));
                    training.setTrainingExplanation(c.getString(KEY_TRAINING_EXPLANATION));
                    training.setBehaviorID(c.getInt(KEY_BEHAVIOR_ID));
                    training.setTrainingTotalQuestion(c.getInt(KEY_TRAINING_TOTAL_QUESTION));
                    training.setTrainingHood(c.getString(KEY_TRAINING_HOOD));
                    training.setTrainingCreateTime(c.getString(KEY_TRAINING_CREATE_TIME));
                    training.setTrainingOK(c.getInt(KEY_TRAINING_OK));

                    //  Log.d(TAG, "GetAllDatabasetrainingset: "+trainingSet.toString());
                    dbHandler.addTraining(training);
                    //  Log.d(TAG,"GetAllDatabasetirainng: "+training.toString());

                }//end of for
                jsonArray = null;

                /***************************************************************************************************/

                // Getting JSON Array node trainingobject
                jsonArray = jsonObj.getJSONArray("trainingobject");

                // looping through All trainingobjects
                for (int i = 0; i < jsonArray.length(); i++) {
                    c = null;
                    c = jsonArray.getJSONObject(i);

                    trainingObject.setTrainingobjectID(c.getInt    (KEY_TRAINING_OBJECT_ID));
                    trainingObject.setTrainingID(c.getInt    (KEY_TRAINING_ID));
                    trainingObject.setTrainingobjectLevel(c.getInt     (KEY_TRAINING_OBJECT_LEVEL));
                    trainingObject.setTrainingobjectAnswer(c.getInt   (KEY_TRAINING_OBJECT_ANSWER));
                    trainingObject.setTrainingobjectOne(c.getInt(KEY_TRAINING_OBJECT_ONE));
                    trainingObject.setTrainingobjectTwo(c.getInt (KEY_TRAINING_OBJECT_TWO));
                    if(!c.getString(KEY_TRAINING_OBJECT_THREE).equals("null"))
                        trainingObject.setTrainingobjectThree(c.getInt  (KEY_TRAINING_OBJECT_THREE));
                    if(!c.getString (KEY_TRAINING_OBJECT_FOUR).equals("null"))
                        trainingObject.setTrainingobjectFour(c.getInt   (KEY_TRAINING_OBJECT_FOUR));
                    if(!c.getString (KEY_TRAINING_OBJECT_FIVE).equals("null"))
                        trainingObject.setTrainingobjectFive(c.getInt   (KEY_TRAINING_OBJECT_FIVE));

                    //  Log.d(TAG, "GetAllDatabasetrainingset: "+trainingSet.toString());
                    dbHandler.addTrainingObject(trainingObject);

                }//end of for
                jsonArray = null;

                /***************************************************************************************************/

                // Getting JSON Array node shape
                jsonArray = jsonObj.getJSONArray("shape");

                // looping through All shape
                for (int i = 0; i < jsonArray.length(); i++) {
                    c = null;
                    c = jsonArray.getJSONObject(i);

                    shape.setShapeID(c.getInt    (KEY_SHAPE_ID));
                    shape.setShapeName(c.getString (KEY_SHAPE_NAME));

                    //  Log.d(TAG, "GetAllDatabasetrainingset: "+trainingSet.toString());
                    dbHandler.addShape(shape);

                }//end of for
                jsonArray = null;

                /***************************************************************************************************/

                // Getting JSON Array node color
                jsonArray = jsonObj.getJSONArray("color");

                // looping through All color
                for (int i = 0; i < jsonArray.length(); i++) {
                    c = null;
                    c = jsonArray.getJSONObject(i);


                    color.setColorID(c.getInt (KEY_COLOR_ID));
                    color.setColorName(c.getString (KEY_COLOR_NAME));

                    //  Log.d(TAG, "GetAllDatabasetrainingset: "+trainingSet.toString());
                    dbHandler.addColor(color);

                }//end of for
                jsonArray = null;

                /***************************************************************************************************/

                // Getting JSON Array node object
                jsonArray = jsonObj.getJSONArray("objectobject");

                // looping through All objects
                for (int i = 0; i < jsonArray.length(); i++) {
                    c = null;
                    c = jsonArray.getJSONObject(i);
                    //    Log.d(TAG,"GetAllDatabaseobject: "+c.toString());
                    object.setColorID(c.getInt   (KEY_COLOR_ID));
                    object.setObjectID(c.getInt      (KEY_OBJECT_ID));
                    object.setObjectName(c.getString   (KEY_OBJECT_NAME));
                    object.setObjectImage(c.getString      (KEY_OBJECT_IMAGE));
                    object.setObjectNumber(c.getInt  (KEY_OBJECT_NUMBER));
                    object.setShapeID(c.getInt   (KEY_SHAPE_ID));
                    object.setCreateTime  (c.getString  (KEY_CREATE_TIME));
                    object.setObjectImageBlob(Base64.decode(c.getString(KEY_OBJECT_IMAGE_BLOB),Base64.DEFAULT));

        }//end of for
                 dbHandler.getAllTrainingSet();
            }
            else{
                Log.d(TAG,"success_trainingset=0");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "hata,jsnarytrainingset");
        }

        Log.d(TAG, "checkdatabaseupdate gettingtraningset:");


        //// TODO: 28.04.2016 alttaki toasi koyunca hata olıyor context yuzunden.coz
       //     Toast.makeText(context,"New trainings were added, they need to be added to local database..", Toast.LENGTH_LONG).show();
           // Intent intent = new Intent(context, MainActivity.class);
          //  intent.putExtra("username", username);
            // intent.putExtra("MESSAGE", "You have been successfully Registered");
            //context.startActivity(intent);
       // }
       // else{
          //  Log.d(TAG, "checkdatabaseupdate onpostexe result unsuccess");
           // Toast.makeText(context,"no newtrainings, try again", Toast.LENGTH_LONG).show();
       // }
       dialog.dismiss();
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.showTrainingsUI();
        mainActivity.setCheckDBUpdateAsyncExecuted(false);
        Log.d(TAG,"checkdatabaseupdate end*******");
    }//postexecute end
}//async end
