package com.example.caneraydin.androidwithlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.caneraydin.androidwithlogin.domains.Training;
import com.example.caneraydin.androidwithlogin.domains.TrainingResponse;
import com.example.caneraydin.androidwithlogin.games.ChoosingGame;
import com.example.caneraydin.androidwithlogin.games.CountingGame;
import com.example.caneraydin.androidwithlogin.games.MatchingGame;

import java.util.ArrayList;
import java.util.List;

//Todo geri tusuna basarsa login olmussa bu sayfaya geri gelecek.

/**
 * Created by caneraydin on 17.04.2016.
 */
public class MainActivity extends AppCompatActivity  {
    String TAG = "Chic";
    String username="t";//// TODO: 30.04.2016 kaldir degeri
    String imageURL = "http://oep.esy.es/images/object/";
   // int lastUpdate=1000000000;//// TODO: 28.04.2016 kaldir degeri

    TextView textMain, textHalfCompletedGames, textUnCompletedGames, textMatch;

   // ImageView imageChoose, imageCount, imageMatch;

    DatabaseHandler dbHandler;

    RadioGroup rdioGrpUncompleted,rdioGrpHalfCompleted;

    List<Training> trainingList,trainingHalfList;

    private boolean getAllDBAsyncExecuted =false,//not twice execute
            checkDBUpdateAsyncExecuted = false,//not twice
            createTrainingResponseAsyncExecuted = false;//not twice

    public void setCreateTrainingResponseAsyncExecuted(boolean createTrainingResponseAsyncExecuted) {
        this.createTrainingResponseAsyncExecuted = createTrainingResponseAsyncExecuted;
    }

    public void setCheckDBUpdateAsyncExecuted(boolean checkDBUpdateAsyncExecuted) {
        this.checkDBUpdateAsyncExecuted = checkDBUpdateAsyncExecuted;
    }

    public void setGetAllDBAsyncExecuted(boolean getAllDBAsyncExecuted) {
        this.getAllDBAsyncExecuted = getAllDBAsyncExecuted;
    }



    //ObjectObject object ;
   // ScrollView sv;
   // LinearLayout ll;

   /* private Target target = new Target() {
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
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File file =new File(directory,object.getObjectImage()+".png");
      //  count++;
        Log.d(TAG," objimg.: "+object.getObjectImage());
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }*/

    @Override
    public void onResume() {
        Log.d(TAG,"mainonresume*****************");

        super.onResume();
        DatabaseHandler dbHandler =  new DatabaseHandler(MainActivity.this);

        //dbHandler.deleteAllTrainingResponses();//// TODO: 5/22/2016 gecici sil
        //dbHandler.getAllTrainingResponse();
        //showTrainingsUI();
        //// TODO: 5/25/2016 toast mesjlar koy

        if (!dbHandler.ifDatabaseExists()) {
            Log.d(TAG, "mainonresume dbhandler dbexists false,getalldb will vbeexecuted");
            if ( CheckNetwork.isOnline(this)) {
                if(!getAllDBAsyncExecuted) {
                    Log.d(TAG, "mainonresume dbhandler dbexists false,getalldb will vbeexecuted async false");
                    getAllDBAsyncExecuted = true;//// TODO: 5/22/2016 bu hicnir yerde falseo olmuyor sanirim cünkü birkere aliniyor
                    new GetAllDatabase(MainActivity.this).execute(username);
                    //orda kendisi  showTrainingsUI(); yapiyor
                }
            } else {
                Log.d(TAG, "mainonresume dbhandler dbexists false,getalldb will vbeexecuted async true");

                CheckNetwork.showNoConnectionDialog(this,true);
                //no need  showTrainingsUI(); because no db
            }
        }
        //database exists but if there are new updates. burda cagiracak.
        else {
            Log.d(TAG, "mainonresume dbhandler dbexists true,");
            if (CheckNetwork.isOnline(this)) {
                Log.d(TAG, "mainonresume dbhandler dbexists true, ispnline");
                //sending unsend responses
                List<Integer> unsendResponseTrainingIDs = new ArrayList<>();
                unsendResponseTrainingIDs = dbHandler.getUnsendResponseTrainingIDs();

                if(unsendResponseTrainingIDs.size()>0)//// TODO: 5/19/2016 burda username atsak mi veritbanında baskasınızki var mıdır ki
                {
                    Log.d(TAG, "mainonresume dbhandler dbexists true, unsendsize>0:"+unsendResponseTrainingIDs.size());
                    for(int i=0 ; i<unsendResponseTrainingIDs.size() ; i++){
                        Log.d(TAG, "mainonresume dbhandler dbexists true, size>0 inside for executingfor traid: "+unsendResponseTrainingIDs.get(i));
                      //  dbHandler.setTrainingSent(unsendResponseTrainingIDs.get(i));
//// TODO: 5/22/2016 burda bunuyazmisim oysa önce internetten yollamam lazim göndermem lazim.orda zaten dogruysa databaselocal yaziyor.buna gerek yok.
                        if(!createTrainingResponseAsyncExecuted) {
                            Log.d(TAG, "mainonresume dbhandler dbexists true, size>0 inside for executingfor traid getAllDBAsyncExecuted false");
                            //her seferinde bir tane yollasin diye, birden fazla olmasin diye.
                            createTrainingResponseAsyncExecuted = true;
                        new CreateTrainingResponse(MainActivity.this).execute(unsendResponseTrainingIDs.get(i));
                        }//// TODO: 5/25/2016 ayni anda calissin diye bir sey var asynctasklar ama yapmiyorum.sirayla calisacaklar. zaten 2 tanesi cakisabiliyor
                    }//for end
                }
                else{
                    Log.d(TAG, "mainonresume dbhandler dbexists true size<0,");//// TODO: 5/22/2016 gecici bu else, sil
                }
                //  if(){
                // }
                if(!createTrainingResponseAsyncExecuted) {//öbürü yapilmamissa yap
                    if(!checkDBUpdateAsyncExecuted) {
                        checkDBUpdateAsyncExecuted = true;
                        new CheckDatabaseUpdate(MainActivity.this).execute(username, dbHandler.getLastTrainingSetUpdate());
                    }
                }
            }
            else {
                if(trainingList.size()==0&&trainingHalfList.size()==0){//hic ttraining yok o yüzdwen zorunlu check yapilmali
                    Log.d(TAG,"mainonresume trainings size: "+trainingList.size()+" "+trainingHalfList.size());
                    CheckNetwork.showNoConnectionDialog(this,true);
                }
                else{
                    CheckNetwork.showNoConnectionDialog(this,false);
                }

                Log.d(TAG, "mainonresume dbexist, no wifi,need new updates check");
            }
            showTrainingsUI();
        }
        Log.d(TAG,"mainonresume end******************");
    }
    TrainingResponse tr;
//// TODO: 25.04.2016 username name n si buyut 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"main oncreate");
//// TODO: database baglanacak 3 tablo icin cekecek verileri yeni olmayanlari.degistirme yok simdilik.jsonArray dönüştürücü de lazım. internet var mı diye sormali
        //sqlite database baglanip tarihe gore sıralayip onla karsılastricak. inner yerine baska class daha iyi. aysntask icin
        //önce createtimea bakacak yeni eklenen var mi diye.
//burdan asynctask cagirilacak parametre olrak username ve lastupdate gömderilecek sqliteden cekilen.eger newtraining yazısı gelirse databasea kaydecek direk.
     //   List<ObjectObject> objectList = new ArrayList<ObjectObject>();
//        objectList=    dbHandler.getAllObjectObject();
       // for(int i=0;i<objectList.size();i++){
          //  object = objectList.get(i);
           // Picasso.with(getApplicationContext()).load(imageURL+object.getObjectImage()).into(target);
           // Log.d(TAG, "picasso sonrası"+" objimg.: "+object.getObjectImage());
       // }

//// TODO: 25.04.2016 lastupdate alinmasi lazim SQLitedan. güncellemek için
     //   username = getIntent().getExtras().getString("username");

        //sqlitedakiler guncel mi diye bakacak
       // new CheckDatabaseUpdate(MainActivity.this, lastUpdate).execute(username);
        //// TODO: 28.04.2016 once database bosmu var mi kontrolu olmali. ilk kez mi aciyor
        Log.d(TAG, "main activity OnCreate*************");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ll = (LinearLayout) findViewById(R.id.linearlayoutleft);
        // sv = (ScrollView) findViewById(R.id.scrollviewleft);
     //   new GetAllDatabase(MainActivity.this,ll).execute(username);
//// TODO: 5/15/2016 sil linearlayout yollamayi ve üsttek slash

        textMain = (TextView) findViewById(R.id.text_main);
       textHalfCompletedGames = (TextView) findViewById(R.id.text_half_completed_trainings);
        textUnCompletedGames = (TextView) findViewById(R.id.text_uncompleted_trainings);
      //  textMatch = (TextView) findViewById(R.id.text_match);
        dbHandler = new DatabaseHandler(MainActivity.this);

        trainingList = new ArrayList<Training>();
        trainingHalfList = new ArrayList<Training>();

        rdioGrpUncompleted = new RadioGroup(this);
        rdioGrpHalfCompleted = new RadioGroup(this);

dbHandler.getAllTrainingResponse();
//dbHandler.getAllTraining("t");//// TODO: 5/25/2016 sil
  
      // Log.d(TAG,"LEVEL:"+ dbHandler.getCurrentLevel(86));

        textMain.setText("Hoşgeldin " + username);
//// TODO: 30.04.2016 once database ayarla sonra layout duzelt
        //imageChoose = (ImageView) findViewById(R.id.img_choose);
       // imageCount = (ImageView) findViewById(R.id.img_count);
       // imageMatch = (ImageView) findViewById(R.id.img_match);

        //// TODO: 30.04.2016 if sqlite=nullsa alttakini cagir. ama ya baska kullanıcı girdiyse dolduduysa db ne olacak. bu ihtimali göz ardı ediyorum
        
            if (!dbHandler.ifDatabaseExists()) {
                Log.d(TAG, "main activity dbhandler dbexists false,getalldb will vbeexecuted");
                if ( CheckNetwork.isOnline(this)) {
                    if(!getAllDBAsyncExecuted) {
                        getAllDBAsyncExecuted = true;
                        new GetAllDatabase(MainActivity.this).execute(username);
                    }
                } else {
                    CheckNetwork.showNoConnectionDialog(this,true);
                }
            }
            //database exists but if there are new updates. burda cagiracak.
            else {
                if (CheckNetwork.isOnline(this)) {
                    Log.d(TAG, "maindbhandler dbexists true isonline");
                    // dbHandler.getUnsendResponseTrainingIDs();//// TODO: 5/19/2016 burda username atsak mi veritbanında baskasınızki var mıdır ki
                    List<Integer> unsendResponseTrainingIDs = new ArrayList<>();
                    unsendResponseTrainingIDs = dbHandler.getUnsendResponseTrainingIDs();

                    if (unsendResponseTrainingIDs.size() > 0)//// TODO: 5/19/2016 burda username atsak mi veritbanında baskasınızki var mıdır ki
                    {
                        Log.d(TAG, "main dbhandler dbexists true size>0:"+unsendResponseTrainingIDs.size());
                        for (int i = 0; i < unsendResponseTrainingIDs.size(); i++) {
                            Log.d(TAG, "main dbhandler dbexists true size>0insidefor exevuting createtrainnigrsponse fortrainingid: "+unsendResponseTrainingIDs.get(i));
                            if(!createTrainingResponseAsyncExecuted) {
                                createTrainingResponseAsyncExecuted = true;//todo acaba checkdb baslsmis olabilri mi, nasil olur ki öyle
                                new CreateTrainingResponse(MainActivity.this).execute(unsendResponseTrainingIDs.get(i));
                            }
                        }//for end
                    } else {
                        Log.d(TAG, "main dbhandler dbexists true size<0,");//// TODO: 5/22/2016 gecici bu else, sil
                    }
                    //  if(){
                    
                    //update trainings
                    if(!createTrainingResponseAsyncExecuted) {//öbürü yapilmamissa yap
                        if(!checkDBUpdateAsyncExecuted) {
                            checkDBUpdateAsyncExecuted = true;
                            new CheckDatabaseUpdate(MainActivity.this).execute(username, dbHandler.getLastTrainingSetUpdate());
                        }
                    }
                } else {
                    Log.d(TAG, "main dbexist no wifi,neednew updates check");
                    CheckNetwork.showNoConnectionDialog(this,false);//// TODO: 5/25/2016 hic egitim yoksa kontrol lazim sanki
                }
                showTrainingsUI();
            }


//// TODO: 02.05.2016 halfcompelted yap-yapildi

/*
        for (int row = 0; row < 1; row++) {
            RadioGroup ll = new RadioGroup(this);
            ll.setOrientation(LinearLayout.VERTICAL);

            for (int i = 1; i <= 5; i++) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId((row * 2) + i);
                rdbtn.setText("Radio " + rdbtn.getId());
                //ll.addView(rdbtn);
            }
            //((ViewGroup) findViewById(R.id.radio_half_completed_games)).addView(ll);
        }

        for (int row = 0; row < 1; row++) {
            RadioGroup ll = new RadioGroup(this);
            ll.setOrientation(LinearLayout.VERTICAL);

            for (int i = 1; i <= 111; i++) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId((row * 2) + i);
                rdbtn.setText("Radio " + rdbtn.getId());
                ll.addView(rdbtn);
            }
          //  ((ViewGroup) findViewById(R.id.radio_uncompleted_games)).addView(ll);
        }*/
      /*  imageChoose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "main activity imagechoose on click");
                Intent intent = new Intent(MainActivity.this, ChoosingGame.class);
               // startActivity(intent);
            }
        });

        textChoose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "main activity txtchoose on click");
                Intent intent = new Intent(MainActivity.this, ChoosingGame.class);
                //startActivity(intent);
            }
        });

        imageMatch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "main activity imagematch on click");
                Intent intent = new Intent(MainActivity.this, MatchingGame.class);
              //  startActivity(intent);
            }
        });

        textMatch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "main activity txtmatch on click");
                Intent intent = new Intent(MainActivity.this, MatchingGame.class);
               // startActivity(intent);
            }
        });

        imageCount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "main activity imagecnt on click");
                Intent intent = new Intent(MainActivity.this, CountingGame.class);
               // startActivity(intent);
            }
        });

        textCount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "main activity txtcnt on click");
                Intent intent = new Intent(MainActivity.this, CountingGame.class);
             //   startActivity(intent);
            }
        });*/
        Log.d(TAG,"main oncreate ends*************");
    }//end of oncreate

    public void showTrainingsUI(){
        Log.d(TAG,"showtrainingui");

      /*  tr = new TrainingResponse();
        for(int i=0;i<1;i++){//todo sil geicic
            tr.setTrainingCompletedAll(0);
            tr.setTrainingStarted(1);
            tr.setStudentUserName("t");
            tr.setTrainingResponseScore(551);
            tr.setTrainingResponseFinishTime("44444");
            tr.setTrainingID(86);
            tr.setObjectID(666+i);
            tr.setResponseSent(0);
            dbHandler.addTrainingResponse(tr);
        }*/
        //dbHandler.getAllTrainingResponse();


        trainingList = dbHandler.getAllTraining(username);//todo getalltraning yerine kontrol yeni alanlari alsa mi
        trainingHalfList = dbHandler.getHalfTraining(username);
//        trainingHalfList = dbHandler.getHalfTraining(username);
//dbHandler.getHalfTraining(username);
        // dbHandler.getAllTraining();

        rdioGrpUncompleted.removeAllViews();
        rdioGrpHalfCompleted.removeAllViews();

        //new trainings being taken
        for (int i = 0; i < trainingList.size(); i++) {
            Log.d(TAG,"main traininglist adding for");
            Training training = trainingList.get(i);

            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            radioButton.setText( training.getTrainingAim()+" - egitim "+training.getTrainingID());
            rdioGrpUncompleted.addView(radioButton);
        }
//dbHandler.getDemoObjectObject(88);test icindi bu

        (( ViewGroup) findViewById(R.id.radio_uncompleted_trainings)).removeAllViews();
        (( ViewGroup) findViewById(R.id.radio_uncompleted_trainings)).addView(rdioGrpUncompleted);

        rdioGrpUncompleted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG,"main activitythis radio selected: "+checkedId);

                Training training = trainingList.get(checkedId);

                if(training.getBehaviorID()==3){
                    Intent intent = new Intent(MainActivity.this, ChoosingGame.class);
                    intent.putExtra("trainingid", training.getTrainingID());
                    intent.putExtra("level",0);
                    intent.putExtra("username",username);
                    startActivity(intent);
                }//// TODO: 5/20/2016 intentleri finish yapmaya öldürmeye gerek var mi geri tusuyal gelisnsin bence
                else{
                    if(training.getBehaviorID()==1){
                        Intent intent = new Intent(MainActivity.this, MatchingGame.class);
                        intent.putExtra("trainingid", training.getTrainingID());
                        intent.putExtra("level",0);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this, CountingGame.class);
                        intent.putExtra("trainingid", training.getTrainingID());
                        intent.putExtra("level",0);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }
                }
            }//oncheckecchanged end
        });//listener end

        //half trainings being taken
        for (int i = 0; i < trainingHalfList.size(); i++) {

            Training training = trainingHalfList.get(i);

            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            radioButton.setText( training.getTrainingAim()+" - egitim "+training.getTrainingID());
            rdioGrpHalfCompleted.addView(radioButton);
        }

        (( ViewGroup) findViewById(R.id.radio_half_completed_trainings)).removeAllViews();
        (( ViewGroup) findViewById(R.id.radio_half_completed_trainings)).addView(rdioGrpHalfCompleted);

        rdioGrpHalfCompleted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG,"main activitythis radio selected: "+checkedId);

                Training training = trainingHalfList.get(checkedId);

                if(training.getBehaviorID()==3){
                    Intent intent = new Intent(MainActivity.this, ChoosingGame.class);
                    intent.putExtra("trainingid", training.getTrainingID());
                    intent.putExtra("level",dbHandler.getCurrentLevel(training.getTrainingID()));
                    intent.putExtra("username",username);
                    startActivity(intent);
                }
                else{
                    if(training.getBehaviorID()==1){
                        Intent intent = new Intent(MainActivity.this, MatchingGame.class);
                        intent.putExtra("trainingid", training.getTrainingID());
                        intent.putExtra("level",dbHandler.getCurrentLevel(training.getTrainingID()));
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this, CountingGame.class);
                        intent.putExtra("trainingid", training.getTrainingID());
                        intent.putExtra("level",dbHandler.getCurrentLevel(training.getTrainingID()));
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }
                }
            }//oncheckecchanged end
        });//listener end

        Log.d(TAG,"showtrainingui end");
    }//showtraining end



}//end of class