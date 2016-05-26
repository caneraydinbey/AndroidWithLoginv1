

package com.example.caneraydin.androidwithlogin;
        import android.app.ProgressDialog;
import android.content.Context;
        import android.content.ContextWrapper;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.drawable.Drawable;
        import android.os.AsyncTask;
        import android.os.Environment;
        import android.util.Base64;
        import android.util.Log;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ScrollView;

        import com.example.caneraydin.androidwithlogin.domains.Color;
        import com.example.caneraydin.androidwithlogin.domains.ObjectObject;
        import com.example.caneraydin.androidwithlogin.domains.Shape;
        import com.example.caneraydin.androidwithlogin.domains.Training;
        import com.example.caneraydin.androidwithlogin.domains.TrainingObject;
        import com.example.caneraydin.androidwithlogin.domains.TrainingResponse;
        import com.example.caneraydin.androidwithlogin.domains.TrainingSet;
        import com.squareup.picasso.Picasso;
        import com.squareup.picasso.Target;

        import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
import java.io.InputStreamReader;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by caneraydin on 25.04.2016.
 */
public class GetAllDatabase extends AsyncTask<String,Void,String> {

    ProgressDialog dialog;
    private Context context;
    String TAG = "Chic";
    String username;
    String imageURL = "http://oep.esy.es/images/object/";


//ScrollView sv;
    LinearLayout ll;
    private static int count =0;

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

    public GetAllDatabase(Context context) {
        Log.d(TAG, "getalldatabase consturct***********************");
        this.context = context;
       // this.ll = ll;
//this.username = username;
    }

    protected void onPreExecute(){
        Log.d(TAG, "getalldatabase onpre");
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Veriler olusturuluyor, lutfen bekleyiniz...", null, true, true);
    }

    @Override
    protected String doInBackground(String... arg0) {
        Log.d(TAG, "getalldatabase doin");

        //  Log.d(TAG, "getalldatabase consturct try");
        //   String username = (String) arg0[0];
        username = (String) arg0[0];
        Log.d(TAG, "getalldatabase username: " + username);

        String uri = "http://oep.esy.es/get_all_data.php?studentusername=" + username ;
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader bufferedReader = null;
            URL url = new URL(uri);
            Log.d(TAG, "getalldatgabase url " + url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            //  Log.d(TAG," bufferedReader "+ bufferedReader);
            String json;

            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
                //  Log.d(TAG, "getalldatabase sb " + sb.toString());
                //Log.d(TAG, "getalldatabase sb: " + sb);
            }
        } catch (IOException e) {
            Log.d(TAG, "getalldatabase hata doin ioexception");
            return new String("Exception: " + e.getMessage());
        }

/*        String s = sb.toString().trim();   Log.d(TAG,"s : "+s);
        try {
            jsonObj = new JSONObject(s);
        // Getting JSON Array node object
        jsonArray = jsonObj.getJSONArray("objectobject");


        // looping through All objects
        for (int i = 0; i < jsonArray.length(); i++) {
            c = null;
            c = jsonArray.getJSONObject(i);

//            Picasso.with(context).load(imageURL + c.getString(KEY_OBJECT_IMAGE)).into(target);
          //  Log.d(TAG, "picasso sonrası" + " objimg.: " + c.getString(KEY_OBJECT_IMAGE));
        }
}
        catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "hata,jsnary jsonexception1");

        }*/

        catch (NullPointerException ex) {
            ex.printStackTrace();
            ex.printStackTrace();
            Log.d(TAG, "GetAllDatabase nullpointer exception1: "+ex);
        }

/// TODO: 28.04.2016 bu kontrol yerine success 1 mi 0 mi ile yap jsonArray verisi kullanarak.burda successlere bakilacak tek tek
      //  if(!sb.equals("nonewtrainings")) {
           // Log.d(TAG, "getalldatabase sb newtrainings: " + sb);
            //   Toast.makeText(context, "New trainings were added, they need to be added to local database..", Toast.LENGTH_LONG).show();
            // Intent intent = new Intent(context, MainActivity.class);
            //  intent.putExtra("username", username);
            // intent.putExtra("MESSAGE", "You have been successfully Registered");
            //context.startActivity(intent);
               Log.d(TAG, "sb: "+sb);

      //  }
      //  else{
         //   Log.d(TAG, "getalldatabase result unsuccess");
            // Toast.makeText(context,"no newtrainings, try again", Toast.LENGTH_LONG).show();
       // }

        return sb.toString().trim();
    }

    @Override
    protected void onPostExecute(String result){

        Log.d(TAG, "getalldatabase onpostexe,");
        TrainingSet trainingSet = new TrainingSet();
        Training training = new Training();
        Shape shape = new Shape();
        Color color = new Color();
        ObjectObject object = new ObjectObject();
        TrainingObject trainingObject = new TrainingObject();
        TrainingResponse trainingResponse = new TrainingResponse();
        DatabaseHandler dbHandler = new DatabaseHandler(context);

        JSONObject jsonObj;
        JSONArray jsonArray;
        JSONObject c;

        //  Log.d(TAG, "getalldatabaseonpostexe, result='"+result+"'");
        // if(result.equals("newtrainings")){

        Log.d(TAG, "******");
        Log.d(TAG, "******");
        // if(result.equals("newtrainings")){
     //   Log.d(TAG, "getalldatabase onpostexe result newtrainings: ");
        int maxLogSize = 1000;
        for(int i = 0; i <= result.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > result.length() ? result.length() : end;
        //    Log.v(TAG, result.substring(start, end));
        }

        Log.d(TAG, "******");
        Log.d(TAG, "******");
        Log.d(TAG, "******");
        Log.d(TAG, "******");

        try {
            JSONObject jsonObject = new JSONObject(result);

            //var mi diye kontrol yoksa almayacak
            if(jsonObject.getInt("success_trainingset")!=0){//// TODO: 5/25/2016 burda araya or koyup hepsini tek tek kontrol mü daha iyi olur 

            jsonObj = new JSONObject(result);
//// TODO: 5/25/2016 iki tane jsona gerek var mi checkdbde de düzelt

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
//Log.d(TAG,"GetAllDatabasestring "+c.getString(KEY_OBJECT_IMAGE_BLOB));

//burası test icindi resmi alabiliyor muyum diye
                /*byte[] bytes = Base64.decode(c.getString(KEY_OBJECT_IMAGE_BLOB), Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                // byte[] bytes = c.getString(KEY_OBJECT_IMAGE_BLOB).getBytes("UTF-8");
                //  byte[] bytes = java.net.URLDecoder.decode(c.getString(KEY_OBJECT_IMAGE_BLOB), "UTF-8");
                Log.d(TAG,bytes.toString());
                //Bitmap bmp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                ImageView image=new ImageView(context);
                image.setImageBitmap(bmp);
                // image.setImageResource(R.drawable.ic_launcher);
                //  image.setBackgroundResource(R.drawable.ic_launcher);
                ll.addView(image);*/

                //  String b = null;
//                    for(int k=0;k<bytes.length;k++){b=b.concat (String.valueOf(bytes[k]));}
                //  Log.d(TAG,"stringblob: "+b);

                //  Log.d(TAG, "trainingset: "+trainingSet.toString());
                dbHandler.addObject(object);

            }//end of for
                dbHandler.getAllTrainingSet();
            }//if end
            else{
                Log.d(TAG,"success=0");//// TODO: 5/25/2016 toast ile ver hatayi.checjdbde de yap
            }
        }//end of try
        catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "GetAllDatabasehata,jsnary jsonexception");
        }
        catch (NullPointerException ex) {
            ex.printStackTrace();
            ex.printStackTrace();
            Log.d(TAG, "GetAllDatabasenullpointer exception: "+ex);
        }

       // Log.d(TAG, "getting all data:");

    /*    dbHandler.getAllTrainingSet();
        dbHandler.getAllTraining();
        dbHandler.getAllColor();
        dbHandler.getAllShape();
        dbHandler.getAllObjectObject();
        dbHandler.getAllTrainingObject();*/

        //// TODO: 28.04.2016 alttaki toasti koyunca hata olıyor context yuzunden.coz
        //     Toast.makeText(context,"New trainings were added, they need to be added to local database..", Toast.LENGTH_LONG).show();
        // Intent intent = new Intent(context, MainActivity.class);
        //  intent.putExtra("username", username);
        // intent.putExtra("MESSAGE", "You have been successfully Registered");
        //context.startActivity(intent);
        // }
        // else{
        //  Log.d(TAG, "getalldatabase onpostexe result unsuccess");
        // Toast.makeText(context,"no newtrainings, try again", Toast.LENGTH_LONG).show();
        // }
        dialog.dismiss();
        //to update ui
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.showTrainingsUI();
        mainActivity.setGetAllDBAsyncExecuted(false);
        Log.d(TAG,"getalldatabase end**********");
    }//postexecute end


    /*private Target target = new Target() {
        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {Log.d(TAG, "target onbitmaploaded"+" objimg.: "+object.getObjectImage());
            new Thread(new Runnable() {
                @Override
                public void run() {Log.d(TAG, "thread run"+"objimg.: "+object.getObjectImage());

                    File file = getFile();
                    try {
                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 75, ostream);
                        ostream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {Log.d(TAG, "onbitfaled"+" objimg.: "+object.getObjectImage());
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {Log.d(TAG, "onprepardeload"+" objimg.: "+object.getObjectImage());
            if (placeHolderDrawable != null) {
            }
        }
    };
    private File getFile() {Log.d(TAG, "getfile"+"objimg.: "+object.getObjectImage());
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File file =new File(directory,object.getObjectImage());
        //  count++;
        Log.d(TAG,"directory: "+file.getPath());
        Log.d(TAG,"directory1: "+directory);
        Log.d(TAG," objimg.: "+object.getObjectImage());
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }
*/
}//async end
