package com.example.caneraydin.androidwithlogin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.caneraydin.androidwithlogin.domains.ObjectObject;
import com.example.caneraydin.androidwithlogin.domains.TrainingResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chico on 5/18/2016.
 */

public class CreateTrainingResponse extends AsyncTask<Integer, String, String> {

    ProgressDialog dialog;
    private Context context;
    String TAG = "Chic";

    JSONObject jsonObj;
    JSONArray jsonArray;
    JSONObject c;

    int trainingID;
    String URL = "http://oep.esy.es/create_training_response.php?";

    DatabaseHandler dbHandler;

    TrainingResponse trainingResponse;
    List<TrainingResponse> trainingResponseList;

    boolean ifAnyErrors = false;//// TODO: 5/22/2016 bunu koydum hata icin, diger yerlere de koymak lazim. bu false ise veritabanini localde güncellesin


    public CreateTrainingResponse(Context context) {
        Log.d(TAG, "CreateTrainingResponse consturct");
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "CreateTrainingResponsee onpre");
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Verileriniz sunucuya yükleniyor, lutfen bekleyiniz...", null, true, true);
    }


    protected String doInBackground(Integer... arg0) {
        Log.d(TAG, "CreateTrainingResponsee doin");

        trainingID = arg0[0].intValue();
        Log.d(TAG, "CreateTrainingResponsee trainingid: " +trainingID);

        dbHandler = new DatabaseHandler(context);

        trainingResponse = new TrainingResponse();

        trainingResponseList = new ArrayList<TrainingResponse>();
        trainingResponseList = dbHandler.getAllTrainingResponse(trainingID);

        //todo sil
        Log.d(TAG,"size: "+trainingResponseList.size());

        StringBuilder sb = new StringBuilder();

        for(int i=0 ; i<trainingResponseList.size() ; i++) {

            trainingResponse = null;
            trainingResponse = trainingResponseList.get(i);

            //todo sil
            Log.d(TAG,"i="+i+" response "+trainingResponse.toString());

            String uri = URL+"trainingid="+trainingID+"&studentusername="+trainingResponse.getStudentUserName()+"&objectid="+trainingResponse.getObjectID()
                    +"&trainingresponsefinishtime="+trainingResponse.getTrainingResponseFinishTime()
                    +"&trainingresponsescore="+trainingResponse.getTrainingResponseScore();
          //  Log.d(TAG,"createtrainingresponse url="+uri);

            try {
                BufferedReader bufferedReader = null;
                java.net.URL url = new URL(uri);
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
                Log.d(TAG, "createtrainingresponse hata doin ioexception");
                ifAnyErrors = true;
                return new String("Exception: " + e.getMessage());
            }
            catch (NullPointerException ex) {
                ex.printStackTrace();
                ifAnyErrors = true;
                Log.d(TAG, "createtrainingresponse nullpointer exception1: "+ex);
            }
           // Log.d(TAG, "createtrainingresponse sb="+sb.toString().trim());
}//for end
        return sb.toString().trim();
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog once done

        Log.d(TAG, "createtrainingresponse postexecute");

        //mark all responses sent
        if(!ifAnyErrors) {
            Log.d(TAG, "createtrainingresponse postexecute ifanyerrors false");
            dbHandler.setTrainingSent(trainingID);
        }
        else{
            Log.d(TAG, "createtrainingresponse postexecute ifanyerrors true");
        }
//// TODO: 5/20/2016 maine gitme belki 
        dialog.dismiss();

        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setCreateTrainingResponseAsyncExecuted(false);
    }//postexecuteend
}//class end
