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

//// TODO: 5/18/2016 level check

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

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak("Önce nesneleri taniyalim...", TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else{
            tts.speak("Önce nesneleri taniyalim...", TextToSpeech.QUEUE_FLUSH, null);
        }*/

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

                   // Log.d(TAG,"Bu nesnenin şekli "+dbHandler.getShapeName(demoObject.getShapeID())+
                           // " rengi ise"+dbHandler.getColorName(demoObject.getColorID())+"id: "+demoObject.getObjectID());

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
           // Log.d(TAG, "3000i sonrasi");

       /*  Thread  thread=  new Thread(){
               @Override
               public void run(){
                   try {

                           Log.d(TAG, " inside thread");
                           wait(demoObjectObject.size() * 5000);
                       setupForGame();
                   }
                       catch(InterruptedException e){
                           e.printStackTrace();
                       }
                   }
         };
           thread.start();*/
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
//// TODO: 5/20/2016 onresume onpause yap yska karısiyior 
/*    protected void onPause(){
        super.onPause(); //
        if(thread.isAlive()){
            threadPaused = true;
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onResume(){
        super.onResume();
        if(threadPause){
            thread.
        }
    }*/

    public void setTwoObjectLayout(){

        Log.d(TAG, "chossing game settwoobjectlayout");

        RelativeLayout.LayoutParams rLayParams = new RelativeLayout.LayoutParams(140,140);

        rLayParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rLayParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        imgBytes = objectResponse.getObjectImageBlob();
        bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        imageOne = new ImageView(this);
        correctImage = new ImageView[1];

        imageOne.setImageBitmap(bmp);
        imageOne.setTag(trainingObject.getTrainingobjectOne());
        imageOne.setId(R.id.imgOne);
        rLayout.removeAllViews();
        rLayout.addView(imageOne,rLayParams);

        rLayParams = null;
        objectResponse = null;
        bmp = null;
        imgBytes = null;

        /*******************************************************************************************/

        objectResponse = dbHandler.getObjectObject(trainingObject.getTrainingobjectTwo());

        rLayParams = new RelativeLayout.LayoutParams(140,140);

        rLayParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rLayParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        imgBytes = objectResponse.getObjectImageBlob();
        bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        imageTwo = new ImageView(this);

        imageTwo.setImageBitmap(bmp);
        imageTwo.setTag(trainingObject.getTrainingobjectTwo());
        imageTwo.setId(R.id.imgTwo);
        //rLayout.removeAllViews();
        rLayout.addView(imageTwo,rLayParams);

        imageOne.setOnClickListener(this);
        imageTwo.setOnClickListener(this);
    }

    public void setFourObjectLayout(){

        Log.d(TAG, "chossing game setfourobjectlayout");

        RelativeLayout.LayoutParams rLayParams = new RelativeLayout.LayoutParams(140,140);

        rLayParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rLayParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        imgBytes = objectResponse.getObjectImageBlob();
        bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        imageOne = new ImageView(this);
        correctImage = new ImageView[1];

        imageOne.setImageBitmap(bmp);
        imageOne.setTag(trainingObject.getTrainingobjectOne());
        imageOne.setId(R.id.imgOne);
        rLayout.removeAllViews();
        rLayout.addView(imageOne,rLayParams);

        rLayParams = null;
        objectResponse = null;
        bmp = null;
        imgBytes = null;

        /*******************************************************************************************/

        objectResponse = dbHandler.getObjectObject(trainingObject.getTrainingobjectTwo());

        rLayParams = new RelativeLayout.LayoutParams(140,140);

        rLayParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rLayParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        imgBytes = objectResponse.getObjectImageBlob();
        bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        imageTwo = new ImageView(this);

        imageTwo.setImageBitmap(bmp);
        imageTwo.setTag(trainingObject.getTrainingobjectTwo());
        imageTwo.setId(R.id.imgTwo);
        //rLayout.removeAllViews();
        rLayout.addView(imageTwo,rLayParams);

        rLayParams = null;
        objectResponse = null;
        bmp = null;
        imgBytes = null;

        /*******************************************************************************************/

        objectResponse = dbHandler.getObjectObject(trainingObject.getTrainingobjectThree());

        rLayParams = new RelativeLayout.LayoutParams(140,140);

        rLayParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rLayParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        imgBytes = objectResponse.getObjectImageBlob();
        bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        imageThree = new ImageView(this);

        imageThree.setImageBitmap(bmp);
        imageThree.setTag(trainingObject.getTrainingobjectThree());
        imageThree.setId(R.id.imgThree);
        //rLayout.removeAllViews();
        rLayout.addView(imageThree,rLayParams);

        rLayParams = null;
        objectResponse = null;
        bmp = null;
        imgBytes = null;

        /*******************************************************************************************/

        objectResponse = dbHandler.getObjectObject(trainingObject.getTrainingobjectFour());

        rLayParams = new RelativeLayout.LayoutParams(140,140);

        rLayParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rLayParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        imgBytes = objectResponse.getObjectImageBlob();
        bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        imageFour = new ImageView(this);

        imageFour.setImageBitmap(bmp);
        imageFour.setTag(trainingObject.getTrainingobjectFour());
        imageFour.setId(R.id.imgFour);
        //rLayout.removeAllViews();
        rLayout.addView(imageFour,rLayParams);

        imageOne.setOnClickListener(this);
        imageTwo.setOnClickListener(this);
        imageThree.setOnClickListener(this);
        imageFour.setOnClickListener(this);
    }

//// TODO: 5/21/2016 isspeaking stopa gerek yok hepsinde, zaten 5 saniyeden uzun olamaz ki
    void setupForGame(){
        Log.d(TAG,counter+" setupForGame");

        if(tts.isSpeaking()){
            tts.stop();
            Log.d(TAG,"tts speak, stopped");
        }

        if(isAnimationStarted) {
            isAnimationStarted = false;
            ChoosingGame.this.correctImage[0].clearAnimation();
        }

        isNextLevelToGo=false;
        isImageClicked = false;

        LEVEL_SCORE =0;

        speech = null;

        counter++;
        //get trainingobjects for trainingid
        trainingObjectList = dbHandler.getAllTrainingObject(trainingID);

        trainingResponse = new TrainingResponse();

        trainingObject = new TrainingObject();//localdi, onclickte alabileyim diye global

        trainingObject = null;

        //get trainingobject
        trainingObject = trainingObjectList.get(counter%trainingObjectList.size());//// TODO: 5/22/2016 mod aldım burda çünkü hata veriyor su an icin, ck syida var diye

        trainingResponse.setTrainingID(trainingID);
        trainingResponse.setObjectID(trainingObject.getTrainingobjectID());
        trainingResponse.setTrainingStarted(1);
        trainingResponse.setStudentUserName(username);
        trainingResponse.setTrainingCompleted(0);
        trainingResponse.setResponseSent(0);

        objectCount = 1+trainingObject.getTrainingobjectLevel() ;
        Log.d(TAG,"objcount: "+objectCount);

        // Log.d(TAG,"testicin trainingobjectid: "+trainingObject.getTrainingobjectID());
if(counter==0) {//todo sil
    for (int i = 0; i < trainingObjectList.size(); i++) {
        Log.d(TAG, "Bu nesnenin şekli " + dbHandler.getShapeName(trainingObjectList.get(i).getTrainingobjectAnswer()) +
                " rengi ise" + dbHandler.getColorName(trainingObjectList.get(i).getTrainingobjectAnswer()));
    }
}
        // if(trainingObject.getTrainingobjectThree()!=0) objectCount++;
        //  if(trainingObject.getTrainingobjectFour()!=0) objectCount++;
        // if(trainingObject.getTrainingobjectFive()!=0) objectCount++;
        //Log.d(TAG,"matchinggame objcount: "+objectCount);

        //those are standarst for each level so defining before switchcase

        /*******************************************************************************************/

        objectAnswer = new ObjectObject();//// TODO: 5/20/2016  ses için kullanılacak komutu objectanswer
        objectResponse = new ObjectObject();

        objectAnswer = dbHandler.getObjectObject(trainingObject.getTrainingobjectAnswer());
        objectResponse = dbHandler.getObjectObject(trainingObject.getTrainingobjectOne());

        switch(dbHandler.getTrainingAim(trainingID)) {
            case 1:
                Log.d(TAG,"choosinggame gettraningaim case1 renk");
                speech = dbHandler.getColorName(objectAnswer.getColorID())+KEY_AIM_NAME[1];
                Log.d(TAG,speech);
                break;
            case 0:
                Log.d(TAG,"choosinggame gettraningaim case1 sekil");
                speech = dbHandler.getShapeName(objectAnswer.getShapeID())+KEY_AIM_NAME[0];
                Log.d(TAG,speech);
                break;
            default:
                Log.d(TAG,"choosinggame gettraningaim case default");
                break;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else{
            tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }

Log.d(TAG,dbHandler.getColorName(objectAnswer.getColorID())+" - "+dbHandler.getShapeName(objectAnswer.getShapeID()));

        RelativeLayout.LayoutParams rLayParams;

        switch (objectCount){
            case 2://2 ve 3te ortak 2 tane var. o yuzden ortaklar. 3te sonra ekleme yapiliyor

                Log.d(TAG,"switchcase 2");
                //already defined

                setTwoObjectLayout();
                break;

            case 3:

                Log.d(TAG,"switchcase 3");

                setTwoObjectLayout();

                rLayParams = null;
                objectResponse = null;
                bmp = null;
                imgBytes = null;

                objectResponse = dbHandler.getObjectObject(trainingObject.getTrainingobjectThree());

                rLayParams = new RelativeLayout.LayoutParams(140,140);

                rLayParams.addRule(RelativeLayout.CENTER_IN_PARENT);//ortaya gelecek

                imgBytes = objectResponse.getObjectImageBlob();
                bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);

                imageThree = new ImageView(this);
                imageThree.setImageBitmap(bmp);
                imageThree.setTag(trainingObject.getTrainingobjectThree());
                imageThree.setId(R.id.imgThree);

                rLayout.addView(imageThree,rLayParams);

                imageOne.setOnClickListener(this);
                imageTwo.setOnClickListener(this);
                imageThree.setOnClickListener(this);

                break;

            case 4:

                Log.d(TAG,"switchcase 4");

                setFourObjectLayout();
                break;

            default:

                Log.d(TAG,"switchcase default");
                setFourObjectLayout();

                rLayParams = null;
                objectResponse = null;
                bmp = null;
                imgBytes = null;

                objectResponse = dbHandler.getObjectObject(trainingObject.getTrainingobjectFive());

                rLayParams = new RelativeLayout.LayoutParams(140,140);

                rLayParams.addRule(RelativeLayout.CENTER_IN_PARENT);//ortaya gelecek

                imgBytes = objectResponse.getObjectImageBlob();
                bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);

                imageFive = new ImageView(this);
                imageFive.setImageBitmap(bmp);
                imageFive.setTag(trainingObject.getTrainingobjectFive());
                imageFive.setId(R.id.imgFive);

                rLayout.addView(imageFive,rLayParams);

                imageOne.setOnClickListener(this);
                imageTwo.setOnClickListener(this);
                imageThree.setOnClickListener(this);
                imageFour.setOnClickListener(this);
                imageFive.setOnClickListener(this);

                break;
        }

        //dogru resmi buluyorum ki fade in out yapbileyim
        if(objectAnswer.getObjectID() ==(int)imageOne.getTag()){
            correctImage[0] = imageOne;
        }
        else{
            if(objectAnswer.getObjectID() ==(int)imageTwo.getTag()){
                correctImage[0] = imageTwo;
            }
            else{
                if(objectAnswer.getObjectID() ==(int)imageThree.getTag()){
                    correctImage[0] = imageThree;
                }
                else{
                    if(objectAnswer.getObjectID() ==(int)imageFour.getTag()){
                        correctImage[0] = imageFour;
                    }
                    else{
                        correctImage[0] = imageFive;
                    }
                }
            }
        }

        thread=  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        Log.d(TAG,counter+" waiting 5 seconds counter:");
                        wait(4000);
Log.d(TAG,"objectAnswer.getObjectID():"+objectAnswer.getObjectID()+"imageOne.getTag():"+imageOne.getTag()+"imageTwo.getTag()"+imageTwo.getTag());

                        wait(1000);
                        Log.d(TAG,counter+" waiting 5 seconds efekt counter:");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Animation fadeIn = new AlphaAnimation(0, 1);
                                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                                fadeIn.setDuration(2000);
                                Animation fadeOut = new AlphaAnimation(1, 0);
                                fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
                                fadeOut.setStartOffset(2000);
                                fadeOut.setDuration(2000);

                                AnimationSet animation = new AnimationSet(false); //change to false
                                animation.addAnimation(fadeIn);
                                animation.addAnimation(fadeOut);
                                isAnimationStarted = true;
                                ChoosingGame.this.correctImage[0].startAnimation(animation);
                            }
                        });

                        wait(5000);
                        if(!isNextLevelToGo) {
                           // Log.d(TAG,counter+" nextlevel  counter:");
                            isNextLevelToGo = true;

                           // counter++;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //süre bitti cevaplamadi. negative
                                    currentDate = new Date();

                                    trainingResponse.setTrainingResponseScore(NEGATIVE_SCORE);//// TODO: 5/22/2016 bunu niy yapmisim bilmiyorum
                                    trainingResponse.setTrainingResponseFinishTime(String.valueOf(currentDate.getTime() / 1000));
                                    dbHandler.addTrainingResponse(trainingResponse);
                               //     Log.d(TAG,counter-1+" run ui level  counter:");
                                    if(counter+1<trainingObjectList.size()) {
                                        setupForGame();
                                    }
                                    else{//bitir activity. main activitiye dönülebilir belki.
                                        dbHandler.setTrainingCompletedAll(trainingID);
                                        if (!CheckNetwork.isOnline(ChoosingGame.this)) {
                                            Log.d(TAG, "choosinggame no db no wifi, checknetworktrue");
                                            CheckNetwork.showNoConnectionDialog(ChoosingGame.this, false); //display dialog
                                        } else {
                                            new CreateTrainingResponse(ChoosingGame.this).execute(trainingID);//// TODO: 5/18/2016 async taska sadece trainid yolluyorum,orda cekecek responselari.aynı
                                            //// TODO: 5/18/2016 id ile baska olamaz, dogru seyi cekecektir
                                            //// TODO: 5/18/2016 ya bizimkin degil de baska bir tablette oyunu bitirdiyse ama bizim localde hala egitim gözküyorsa
                                            Intent intent = new Intent(ChoosingGame.this, MainActivity.class);
                                            intent.putExtra("username", username);
                                          //  startActivity(intent);
                                           // finish();
                                        }
                                    }
                                }
                            });

                        }//if end
                    }
                }
                catch(InterruptedException ex){
                   //Log.d(TAG,counter+" interrupts");
                    ex.printStackTrace();
                }

                // TODO
            }
        };

        thread.start();

/*        if(counter+1 == trainingObjectList.size()) {
            synchronized(
                    thread) {
                if (!CheckNetwork.isOnline(this)) {
                    Log.d(TAG, "choosinggame no db no wifi, checknetworktrue");
                    CheckNetwork.showNoConnectionDialog(this, false); //display dialog
                } else {
                    new CreateTrainingResponse(this).execute(trainingID);//// TODO: 5/18/2016 async taska sadece trainid yolluyorum,orda cekecek responselari.aynı
                    //// TODO: 5/18/2016 id ile baska olamaz, dogru seyi cekecektir
                    //// TODO: 5/18/2016 ya bizimkin degil de baska bir tablette oyunu bitirdiyse ama bizim localde hala egitim gözküyorsa
                }
            }//thread end
        }//if end*/

//// TODO: 5/20/2016 onresume filan lazim

    }
    @Override//oncreatede bir sey yok onibitte oluyor her sey.
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "choosingggame OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosegame);

        tts = new TextToSpeech(this,this);

        dbHandler = new DatabaseHandler(this);
        rLayout = new RelativeLayout(this);
        trainingObjectList = new ArrayList<TrainingObject>();
        demoObjectObject = new ArrayList<ObjectObject>();
        trainingResponseList = new ArrayList<TrainingResponse>();

 /*       trainingObjectList = dbHandler.getAllTrainingObject(trainingID);
        for ( int i = 0; i<trainingObjectList.size() ; i++) {      ObjectObject demoObject = new ObjectObject();
           // demoObject =
            Log.d(TAG,"Bu nesnenin şekli "+dbHandler.getShapeName(trainingObjectList.get(i).getTrainingobjectAnswer())+
                    " rengi ise"+dbHandler.getColorName(trainingObjectList.get(i).getTrainingobjectAnswer()));}*/

        //imageDemo=new ImageView(this);

        rLayout= (RelativeLayout) findViewById(R.id.relative_layout);
        rLayout.setBackgroundColor(Color.MAGENTA);

        trainingID = getIntent().getExtras().getInt("trainingid");
        level = getIntent().getExtras().getInt("level");
        username = getIntent().getExtras().getString("username");
        Log.d(TAG, "traininfid: "+trainingID+" level: "+level);

        counter = counter + level;

        demoObjectObject = dbHandler.getDemoObjectObject(trainingID);

/*
//todo sill
        final RelativeLayout.LayoutParams rLayParams = new RelativeLayout.LayoutParams(140,140);

        //putting middle to show demo of items in each iteration
        rLayParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rLayParams.addRule(RelativeLayout.CENTER_IN_PARENT);
     final Handler handler = new Handler();
     *//*   for ( int i = 0; i<demoObjectObject.size() ; i++) {
            Log.d(TAG, "for i="+ i);
            final int finalI = i;
            final RelativeLayout finalRLayout = rLayout;
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Log.d(TAG, "for finali="+ finalI);
                    ObjectObject demoObject = new ObjectObject();
                    demoObject = demoObjectObject.get(finalI);

                    Log.d(TAG,"Bu nesnenin şekli "+dbHandler.getShapeName(demoObject.getShapeID())+
                            " rengi ise"+dbHandler.getColorName(demoObject.getColorID()));

                    imgBytes = demoObject.getObjectImageBlob();
                    bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);

                    imageDemo.setImageBitmap(bmp);
                    finalRLayout.removeAllViews();
                   //// TODO: 5/17/2016 background color degistir ki uyumlu olsun nesne ile karismasin rengi,zittini al
                    finalRLayout.addView(imageDemo,rLayParams);
                }
            }, 3000 * i);
            Log.d(TAG, "3000i sonrasi");

        }//for end*//*
        for ( int i = 0; i< 20 ; i++) {
            Log.d(TAG, "for i2="+ i);
            final int finalI = i;
            final RelativeLayout finalRLayout = rLayout;
            final Runnable r=new Runnable() {

                @Override
                public void run() {
                    Log.d(TAG, "for finali2="+ finalI);
                    TrainingObject trainingObject = new TrainingObject();
                    trainingObject = trainingObjectList.get(finalI);
                    objectCount = 2;
//test icin
                    Log.d(TAG,"testicin trainingobjectid: "+trainingObject.getTrainingobjectID());
                    object = dbHandler.getObjectObject(trainingObject.getTrainingobjectAnswer());

                    if(trainingObject.getTrainingobjectThree()!=0) objectCount++;
                    if(trainingObject.getTrainingobjectFour()!=0) objectCount++;
                    if(trainingObject.getTrainingobjectFive()!=0) objectCount++;
                    Log.d(TAG,"matchinggame objcount: "+objectCount);

                    RelativeLayout.LayoutParams rLayParams = new RelativeLayout.LayoutParams(140,140);

                    rLayParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    rLayParams.addRule(RelativeLayout.CENTER_IN_PARENT);

                    imgBytes = object.getObjectImageBlob();
                    bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);

                    imageDemo.setImageBitmap(bmp);
                    imageDemo.setTag(trainingObject.getTrainingobjectAnswer());
                    imageDemo.setId(R.id.imgAnswer);
                    finalRLayout.removeAllViews();
                    finalRLayout.addView(imageDemo,rLayParams);
                }
            };
            Log.d(TAG, "3000i2 sonrasi");

            handler.postDelayed(r, 6000 * i);
            imageDemo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view == findViewById(R.id.imgAnswer)) {
                        Log.d(TAG, "clicked");handler.removeCallbacks(r); handler.postDelayed(r, 0);
                        //PUT IN CODE HERE TO GET NEXT IMAGE

                    }
                    else{
                        Log.d(TAG, "nonclick");
                        handler.removeCallbacks(r);  handler.postDelayed(r, 6000);
                    }
                }
            });
        }//for end

        trainingObjectList = dbHandler.getAllTrainingObject(trainingID);*/
//// TODO: 02.05.2016 egitim kismi oalcak burda tek tek gosterilecek

   // setupDemo();

/*************************************************************************************
 **************************************************************
 * **************************************************************
 * **************************************************************
 * **************************************************************
 * **************************************************************
 * **************************************************************
 * **************************************************************
 * **************************************************************
 * **************************************************************
 */

//// TODO: 5/18/2016 response tek tek eklenecek listeye for içinde
        //// TODO: 5/18/2016 ,zaten hepsi eklenmisse for sonunda

    /*    trainingResponse.setTrainingID(trainingID);
        trainingResponse.setStudentUserName(username);
    //    trainingResponse.setObjectID(Integer.parseInt(trainingObject.getTrainingobjectAnswer()));
        trainingResponse.setTrainingResponseFinishTime(String.valueOf(currentDate.getTime() / 1000));
        trainingResponse.setTrainingResponseScore(score);//}else Log.d(TAG,"null");
        trainingResponse.setTrainingStarted(1);
        trainingResponse.setTrainingCompletedAll(0);
        trainingResponse.setResponseSent(0);

        dbHandler.addTrainingResponse(trainingResponse);
     //   trainingResponseList.add(trainingResponse);//to sent server

//// TODO: 5/18/2016 if total questions all sent:büyük ihtimalle gerek yok buna çünkü for sonrası olacak bu, yani tüm hepsi bitmis olacak
dbHandler.setTrainingCompletedAll(trainingID);
        //// TODO: 5/18/2016 internet bagliysa yolla ya da önce maine mi gitse.mainde de kontrol yapilir normal*/

        ////
        Log.d(TAG, "choosingggame OnCreate ends");
    }//oncreate end

    @Override
    public void onClick(View v) {

        //isImageDragged = true;
        synchronized(
                thread){

            if (v.getTag().equals (trainingObject.getTrainingobjectAnswer())) {
                Log.d(TAG, counter+" clicked "+v.getTag()+" - "+trainingObject.getTrainingobjectAnswer());

                    if(!isNextLevelToGo && !isImageClicked){
                      //  Log.d(TAG,counter+" nextlevel  counter:");
                        thread.interrupt();
                        isNextLevelToGo = true;
                        isImageClicked = true ;
                        currentDate = new Date();

                        trainingResponse.setTrainingResponseScore(POSITIVE_SCORE);
                        trainingResponse.setTrainingResponseFinishTime(String.valueOf(currentDate.getTime() / 1000));
                        dbHandler.addTrainingResponse(trainingResponse);

                       // counter++;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               // Log.d(TAG,counter-1+" run ui level  counter:");
                                if(counter+1<trainingObjectList.size()) {
                                    setupForGame();
                                }
                                else{//bitir activity. main activitiye dönülebilir belki.
                                    dbHandler.setTrainingCompletedAll(trainingID);
                                    if (!CheckNetwork.isOnline(ChoosingGame.this)) {
                                        Log.d(TAG, "choosinggame no db no wifi, checknetworktrue");
                                        CheckNetwork.showNoConnectionDialog(ChoosingGame.this, false); //display dialog
                                    } else {
                                        new CreateTrainingResponse(ChoosingGame.this).execute(trainingID);//// TODO: 5/18/2016 async taska sadece trainid yolluyorum,orda cekecek responselari.aynı
                                        //// TODO: 5/18/2016 id ile baska olamaz, dogru seyi cekecektir
                                        //// TODO: 5/18/2016 ya bizimkin degil de baska bir tablette oyunu bitirdiyse ama bizim localde hala egitim gözküyorsa
                                        Intent intent = new Intent(ChoosingGame.this, MainActivity.class);
                                        intent.putExtra("username", username);
                                     //   startActivity(intent);
                                       // finish();
                                    }
                                }
                            }

                        });

                    }//if end
                    else{
                        if(!isNextLevelToGo){
                            isNextLevelToGo = true;
                            //bu durumda 1 kez tiklanmistir yanlis. //// TODO: 5/20/2016 positive ver sonra reset
                            thread.interrupt();
                            isImageClicked = true;

                            currentDate = new Date();

                            trainingResponse.setTrainingResponseScore(HALF_SCORE);
                            trainingResponse.setTrainingResponseFinishTime(String.valueOf(currentDate.getTime() / 1000));
                            dbHandler.addTrainingResponse(trainingResponse);

                         //   counter++;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(counter+1<trainingObjectList.size()) {
                                        setupForGame();
                                    }
                                    else{//bitir activity. main activitiye dönülebilir belki.
                                        dbHandler.setTrainingCompletedAll(trainingID);
                                        if (!CheckNetwork.isOnline(ChoosingGame.this)) {
                                            Log.d(TAG, "choosinggame no db no wifi, checknetworktrue");
                                            CheckNetwork.showNoConnectionDialog(ChoosingGame.this, false); //display dialog
                                        } else {
                                            new CreateTrainingResponse(ChoosingGame.this).execute(trainingID);//// TODO: 5/18/2016 async taska sadece trainid yolluyorum,orda cekecek responselari.aynı
                                            //// TODO: 5/18/2016 id ile baska olamaz, dogru seyi cekecektir
                                            //// TODO: 5/18/2016 ya bizimkin degil de baska bir tablette oyunu bitirdiyse ama bizim localde hala egitim gözküyorsa
                                            Intent intent = new Intent(ChoosingGame.this, MainActivity.class);
                                            intent.putExtra("username", username);
                                         //   startActivity(intent);
                                           // finish();
                                        }
                                    }
                                }
                            });
                        }//if ends, else zaten yeni gidilmistir level
                    }
                }
            else{
                Log.d(TAG, "nonclick"+ counter+" -  "+v.getTag()+" - "+trainingObject.getTrainingobjectAnswer());

                    if(!isNextLevelToGo && !isImageClicked){
                        //ilk yanlis cevabi
                        isImageClicked = true ;
                        //isNextLevelToGo = false;
                        //  Log.d(TAG,counter+" nextlevel  counter:");
                       // thread.interrupt();
                        //todo efekt

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Animation fadeIn = new AlphaAnimation(0, 1);
                                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                                fadeIn.setDuration(2000);
                                Animation fadeOut = new AlphaAnimation(1, 0);
                                fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
                                fadeOut.setStartOffset(2000);
                                fadeOut.setDuration(2000);

                                AnimationSet animation = new AnimationSet(false); //change to false
                                animation.addAnimation(fadeIn);
                                animation.addAnimation(fadeOut);
                                isAnimationStarted = true;
                                ChoosingGame.this.correctImage[0].startAnimation(animation);
                            }
                        });

                        // isNextLevelToGo = true;
                      //  counter++;
                    //todo isclicked

                    }//if end
                    else{
                        if(!isNextLevelToGo){//2. kez yanlis cevap
                            isNextLevelToGo = true;
                            //bu durumda 1 kez tiklanmistir yanlis. //// TODO: 5/20/2016 negative ver sonra reset

                             thread.interrupt();
                            isNextLevelToGo = true;
                          //  counter++;

                            currentDate = new Date();

                            trainingResponse.setTrainingResponseScore(NEGATIVE_SCORE);
                            trainingResponse.setTrainingResponseFinishTime(String.valueOf(currentDate.getTime() / 1000));
                            dbHandler.addTrainingResponse(trainingResponse);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Log.d(TAG,counter-1+" run ui level  counter:");
                                    if(counter+1<trainingObjectList.size()) {
                                        setupForGame();
                                    }
                                    else{//bitir activity. main activitiye dönülebilir belki.
                                        dbHandler.setTrainingCompletedAll(trainingID);
                                        if (!CheckNetwork.isOnline(ChoosingGame.this)) {
                                            Log.d(TAG, "choosinggame no db no wifi, checknetworktrue");
                                            CheckNetwork.showNoConnectionDialog(ChoosingGame.this, false); //display dialog
                                        } else {
                                            new CreateTrainingResponse(ChoosingGame.this).execute(trainingID);//// TODO: 5/18/2016 async taska sadece trainid yolluyorum,orda cekecek responselari.aynı
                                            //// TODO: 5/18/2016 id ile baska olamaz, dogru seyi cekecektir
                                            //// TODO: 5/18/2016 ya bizimkin degil de baska bir tablette oyunu bitirdiyse ama bizim localde hala egitim gözküyorsa
                                            Intent intent = new Intent(ChoosingGame.this, MainActivity.class);
                                            intent.putExtra("username", username);
                                           // startActivity(intent);
                                            //finish();
                                        }
                                    }
                                }
                            });
                        }//if ends, else zaten yeni gidilmistir level
                    }
                }
    }//thread sync
    }//onclick end

    @Override
    public void onInit(int status) {
        Log.d(TAG, "choosinggame oninit");

        if (status == TextToSpeech.SUCCESS) {
            Log.d(TAG, "choosinggame OnInit - Status suces");
            //  tts.setLanguage(Locale.getDefault());
            // Log.d(TAG, "locale: "+Locale.getDefault());
           // Locale locale = new Locale("tr", "TR");
            tts.setLanguage(Locale.getDefault());
          //  Log.d(TAG, "locale: "+locale.toString());
        }
        else{
            Log.d(TAG,"choosing game oninit status else");
        }

        if(level == 0) {
            setupDemo();//// TODO: 5/21/2016 hata olursa mne olcak sessiz mi, belki yazili olan yapilabilir
        }
        else{
            setupForGame();
        }
    }

}//class end
