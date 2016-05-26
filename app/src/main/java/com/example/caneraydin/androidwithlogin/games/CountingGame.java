package com.example.caneraydin.androidwithlogin.games;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.caneraydin.androidwithlogin.CheckNetwork;
import com.example.caneraydin.androidwithlogin.CreateTrainingResponse;
import com.example.caneraydin.androidwithlogin.DatabaseHandler;
import com.example.caneraydin.androidwithlogin.MainActivity;
import com.example.caneraydin.androidwithlogin.R;
import com.example.caneraydin.androidwithlogin.domains.ObjectObject;
import com.example.caneraydin.androidwithlogin.domains.TrainingObject;
import com.example.caneraydin.androidwithlogin.domains.TrainingResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by caneraydin on 17.04.2016.
 */
public class ChoosingGame extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    String TAG = "Chic";

    DatabaseHandler dbHandler;

    int trainingID,objectCount,level,score=0;

    //for demo
    String username, speech,
            KEY_THIS = "Bu", KEY_AN = " bir";//// TODO: 5/21/2016 merhaba ghosgeldiniz username denebilir belki
    // KEY_CHOOSE_WHICH_IS = " olani seciniz...";

    String [] KEY_AIM_NAME = {" şeklinde olanı seçiniz", " renginde olanı seçiniz", " sayısında olanı seçiniz"};

    List<TrainingObject> trainingObjectList;

    List<ObjectObject> demoObjectObject;

    List<TrainingResponse> trainingResponseList;

    byte[] imgBytes;

    Bitmap bmp;

    ImageView imageDemo, //// TODO: 5/20/2016 çünkü burda resme gerek yok answer
            imageOne,imageTwo,imageThree,imageFour,imageFive;

    RelativeLayout rLayout;

    ObjectObject objectAnswer,objectResponse;//// TODO: 5/20/2016 ayrı ayrı da olabilirdi ama yapmadım

    TrainingResponse trainingResponse;

    Date currentDate ;

    int counter=-1, aim;

    boolean isNextLevelToGo = false,//cakisma olmasın diye
            isImageClicked = false,//resme tiklatyinca cakisma olmasin diye
            isAnimationStarted = false;

    private Thread thread;

    int POSITIVE_SCORE = 1, NEGATIVE_SCORE = -1, HALF_SCORE = 0, LEVEL_SCORE = 0;

    TrainingObject trainingObject;

    TextToSpeech tts;

    ImageView [] correctImage ;//cevabi tutayim diye
    int firstImgId, secondImgId, thirdImgId, fourthImgId, fifthImgId;

    public void setupDemo(){

        Log.d(TAG,"choosing gamesetupdemo");

        imageDemo=new ImageView(this);

        rLayout= (RelativeLayout) findViewById(R.id.relative_layout);
        rLayout.setBackgroundColor(Color.MAGENTA);

        demoObjectObject = dbHandler.getDemoObjectObject(trainingID);

        final RelativeLayout.LayoutParams rLayParams = new RelativeLayout.LayoutParams(140,140);

        //putting middle to show demo of items in each iteration
        rLayParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rLayParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        final Handler handler = new Handler();

        for ( int i = 0; i<demoObjectObject.size() ; i++) {
            // Log.d(TAG, "for i="+ i);

            speech = null;
            final int finalI = i;
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    ObjectObject demoObject = new ObjectObject();
                    demoObject = demoObjectObject.get(finalI);

                    speech = KEY_THIS+", "+dbHandler.getColorName(demoObject.getColorID())+" "+KEY_AN+" "+dbHandler.getShapeName(demoObject.getShapeID());
                    Log.d(TAG,speech);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                    else{
                        tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
                    }

                    imgBytes = demoObject.getObjectImageBlob();
                    bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);

                    imageDemo.setImageBitmap(bmp);
                    rLayout.removeAllViews();
                    //// TODO: 5/17/2016 background color degistir ki uyumlu olsun nesne ile karismasin rengi,zittini al
                    rLayout.addView(imageDemo,rLayParams);
                }
            }, 5000 * i);
        }//for end

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Log.d(TAG,"SECond handler");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak("Eğitiminiz şimdi başlıyor...", TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else{//// TODO: 5/22/2016 bunlari okuyamiyor süre yetmiyor cunku. bir wait lazim sanki
                    tts.speak("Eğitiminiz şimdi başlıyor...", TextToSpeech.QUEUE_FLUSH, null);
                }
                // setupForGame();
            }
        }, 5000 * demoObjectObject.size());

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Log.d(TAG, "third handler");
                setupForGame();
            }
        }, 5000 * demoObjectObject.size()+6000);

        //  setupForGame();
        Log.d(TAG, "setupdemo end");
    }//setupdemoend

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "counting ggame OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosegame);

        dbHandler = new DatabaseHandler(this);
        rLayout = new RelativeLayout(this);
        trainingObjectList = new ArrayList<TrainingObject>();
        demoObjectObject = new ArrayList<ObjectObject>();
        imageAnswer=new ImageView(this);

        rLayout = (RelativeLayout) findViewById(R.id.relative_layout);
        rLayout.setBackgroundColor(Color.MAGENTA);

        trainingID = getIntent().getExtras().getInt("trainingid");
        Log.d(TAG, "traininfid: "+trainingID);

        demoObjectObject = dbHandler.getDemoObjectObject(trainingID);

        RelativeLayout.LayoutParams rLayParams = new RelativeLayout.LayoutParams(140,140);

        rLayParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rLayParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        for (int i = 0; i<demoObjectObject.size() ;i++) {
            Log.d(TAG, "for i="+i);
            ObjectObject demoObject = new ObjectObject();
            demoObject = demoObjectObject.get(i);

            Log.d(TAG,"Bu nesnenin şekli "+dbHandler.getShapeName(demoObject.getShapeID())+
                    " rengi ise"+dbHandler.getColorName(demoObject.getColorID()));

            imgBytes = demoObject.getObjectImageBlob();
            bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);

            imageAnswer.setImageBitmap(bmp);
            rLayout.removeAllViews();
            rLayout.addView(imageAnswer,rLayParams);
        }



        trainingObjectList = dbHandler.getAllTrainingObject(trainingID);
//// TODO: 02.05.2016 egitim kismi oalcak burda tek tek gosterilecek




        Log.d(TAG, "counting ggame OnCreate ends");
    }//oncreate end

    @Override
    public boolean onDrag(View v, DragEvent event) {
        return false;
    }
}//class end
