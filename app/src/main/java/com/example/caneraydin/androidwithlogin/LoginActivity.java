package com.example.caneraydin.androidwithlogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caneraydin.androidwithlogin.games.MatchingGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
//todo geri tusunu ayarla

public class LoginActivity extends AppCompatActivity {

    String TAG = "Chic";
    String username,password;
    private Button btn_login;
    private TextView txt_username,txt_password,txt_attempts_left;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "login activity OnCreate");

       //geçici bu sil sonra//todo sil burayi
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = (Button) findViewById(R.id.button_login);
        txt_username = (TextView) findViewById(R.id.editText_username);
        txt_password = (TextView) findViewById(R.id.editText_password);
        txt_attempts_left = (TextView) findViewById(R.id.text_attempts_left);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "login activity butonlogin on click");

                username = txt_username.getText().toString();
                password = txt_password.getText().toString();
                txt_attempts_left.setBackgroundColor(Color.YELLOW);
                counter--;
                txt_attempts_left.setText(Integer.toString(counter));

                if (counter == 0) {
                    btn_login.setEnabled(false);
                };

                new SigninActivity(LoginActivity.this,username).execute(password);//username postexecuteta lazim

            }
        });
        Log.d(TAG, "login activity oncreate end");
    }


}

class SigninActivity  extends AsyncTask<String,Void,String> {
    ProgressDialog dialog;
    private Context context;
    String TAG = "Chic";
    private String username;

    public SigninActivity(Context context, String username) {
        Log.d(TAG, "login activity signinactivity consturct");
        this.context = context;
        this.username = username;
    }

    protected void onPreExecute(){
        Log.d(TAG, "login activity signinactivity onpre");
        dialog = ProgressDialog.show(context, "Lutfen bekleyiniz...", null, true, true);
    }

    @Override
    protected String doInBackground(String... arg0) {
        Log.d(TAG, "login activity signinactivity doin");

      //  Log.d(TAG, "login activity signinactivity consturct try");
     //   String username = (String) arg0[0];
        String password = (String) arg0[0];

        Log.d(TAG, "login activity signinactivity username: " + username + " password: " + password);

        String uri = "http://oep.esy.es/login.php?" + "username=" + username + "&password=" + password;
        StringBuilder sb = new StringBuilder();
//todo internet var mi diye kontrol
        try {
            BufferedReader bufferedReader = null;
            URL url = new URL(uri);
            Log.d(TAG, "login activity url " + url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

        bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        //  Log.d(TAG," bufferedReader "+ bufferedReader);
        String json;

            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
     //           Log.d(TAG, "login activity sb " + sb.toString());
                Log.d(TAG, "login activity sb: " + sb);
            }
        } catch (IOException e) {
            Log.d(TAG, "login activity hata doin");
            return new String("Exception: " + e.getMessage());
        }
        return sb.toString().trim();
    }

        @Override
    protected void onPostExecute(String result){
            dialog.dismiss();
            Log.d(TAG, "login activity onpostexe, result='"+result+"'");

            if(result.equals("success")){
                Log.d(TAG, "login activity onpostexe result success");
                Toast.makeText(context,"Login successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("username", username);
               // intent.putExtra("MESSAGE", "You have been successfully Registered");
                context.startActivity(intent);
                ((Activity) context).finish();
            }
            else{
                Log.d(TAG, "login activity onpostexe result unsuccess");
                Toast.makeText(context,"Login unsuccessful, try again", Toast.LENGTH_LONG).show();
            }
    }//postexecute end
}//async end