package com.example.caneraydin.androidwithlogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.caneraydin.androidwithlogin.domains.Color;
import com.example.caneraydin.androidwithlogin.domains.ObjectObject;
import com.example.caneraydin.androidwithlogin.domains.Shape;
import com.example.caneraydin.androidwithlogin.domains.Training;
import com.example.caneraydin.androidwithlogin.domains.TrainingObject;
import com.example.caneraydin.androidwithlogin.domains.TrainingResponse;
import com.example.caneraydin.androidwithlogin.domains.TrainingSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caneraydin on 23.04.2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    //// TODO: 30.04.2016 basharfleri kucult
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 100;
    private String TAG = "Chic";

    // Database Name
    private static final String DATABASE_NAME = "AuthismPlatform";

    private static final String TABLE_OBJECT = "object";
    private static final String TABLE_TRAINING_OBJECT = "trainingobject";
    private static final String TABLE_TRAINING = "training";
    private static final String TABLE_TRAINING_SET = "trainingset";
    private static final String TABLE_TRAINING_RESPONSE = "trainingresponse";
    private static final String TABLE_COLOR = "color";
    private static final String TABLE_SHAPE = "shape";

    // object Table column names
    private static final String KEY_COLOR_ID = "colorid";
    private static final String KEY_OBJECT_ID = "objectid";
    private static final String KEY_OBJECT_NAME = "objectname";
    private static final String KEY_OBJECT_IMAGE = "objectimage";
    private static final String KEY_OBJECT_NUMBER = "objectnumber";
    private static final String KEY_SHAPE_ID = "shapeid";
    private static final String KEY_CREATE_TIME = "createtime";
    private static final String KEY_OBJECT_IMAGE_BLOB = "objectImageBlob";//to keep image

    // trainingobject Table column names
    private static final String KEY_TRAINING_OBJECT_ID = "trainingobjectid";
    //  private static final String KEY_TRAINING_ID = "trainingid";
    private static final String KEY_TRAINING_OBJECT_LEVEL = "trainingobjectlevel";
    private static final String KEY_TRAINING_OBJECT_ANSWER = "trainingobjectanswer";
    private static final String KEY_TRAINING_OBJECT_ONE = "trainingobjectone";
    private static final String KEY_TRAINING_OBJECT_TWO = "trainingobjecttwo";
    private static final String KEY_TRAINING_OBJECT_THREE = "trainingobjectthree";
    private static final String KEY_TRAINING_OBJECT_FOUR = "trainingobjectfour";
    private static final String KEY_TRAINING_OBJECT_FIVE = "trainingobjectfive";
    //  private static final String KEY_TRAINING_OBJECT_RESPONSES = "trainingobjectresponses";

    // training Table column names
    private static final String KEY_TRAINING_ID = "trainingid";
    private static final String KEY_TRAINING_EVALUATION = "trainingevaluation";
    private static final String KEY_TRAINING_HOOD = "traininghood";
    private static final String KEY_TRAINING_AIM = "trainingaim";
    private static final String KEY_TRAINING_EXPLANATION = "trainingexplanation";
    private static final String KEY_BEHAVIOR_ID = "behaviorid";
    private static final String KEY_TRAINING_TOTAL_QUESTION = "trainingtotalquestion";
    private static final String KEY_TRAINING_OK = "trainingok";
    private static final String KEY_TRAINING_CREATE_TIME = "trainingcreatetime";

    // trainingset Table column names
    private static final String KEY_ID = "id";
    //  private static final String KEY_TRAINING_ID = "trainingid";
    private static final String KEY_STUDENT_ID = "studentid";
    private static final String KEY_TRAINING_SET_FINISH_TIME = "trainingsetfinishtime";
    private static final String KEY_TRAINING_SET_CREATE_TIME = "trainingsetcreatetime";

    // trainingresponse Table column names
    private static final String KEY_TRAINING_RESPONSE_ID = "trainingResponseid";
    //  private static final String KEY_TRAINING_ID = "";
     private static final String KEY_STUDENT_USERNAME = "studentUserName";
    //private static final String KEY_OBJ = "";
    private static final String KEY_TRAINING_RESPONSE_FINISH_TIME = "trainingResponseFinishTime";
    private static final String KEY_TRAINING_RESPONSE_SCORE = "trainingResponseScore";
    private static final String KEY_TRAINING_STARTED = "trainingStarted";
    private static final String KEY_TRAINING_COMPLETED = "trainingComleted";
    private static final String KEY_RESPONSE_SENT = "responseSent";

    // color Table column names
    // private static final String KEY_COLOR_ID = "colorID";
    private static final String KEY_COLOR_NAME = "colorName";

    // shape Table column names
    //private static final String KEY_SHAPE_ID = "shapeID";
    private static final String KEY_SHAPE_NAME = "shapeName";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "dbhandler construct");
    }

/*    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }*/

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        //object table
        String CREATE_OBJECT_TABLE = "CREATE TABLE " + TABLE_OBJECT + "("
                + KEY_OBJECT_ID + " INTEGER PRIMARY KEY," + KEY_OBJECT_NAME + " TEXT,"
                + KEY_OBJECT_IMAGE + " TEXT," + KEY_OBJECT_NUMBER + " INTEGER," + KEY_SHAPE_ID + " INTEGER," + KEY_COLOR_ID + " INTEGER," +
                KEY_CREATE_TIME + " TEXT," + KEY_OBJECT_IMAGE_BLOB + " BLOB" + ")";

        db.execSQL(CREATE_OBJECT_TABLE);

        //trainingobject table
        String CREATE_TRAINING_OBJECT_TABLE = "CREATE TABLE " + TABLE_TRAINING_OBJECT + "("
                + KEY_TRAINING_OBJECT_ID + " INTEGER PRIMARY KEY," + KEY_TRAINING_ID + " INTEGER,"
                + KEY_TRAINING_OBJECT_LEVEL + " INTEGER," + KEY_TRAINING_OBJECT_ANSWER + " INTEGER," + KEY_TRAINING_OBJECT_ONE + " INTEGER,"
                + KEY_TRAINING_OBJECT_TWO + " INTEGER," + KEY_TRAINING_OBJECT_THREE + " INTEGER," + KEY_TRAINING_OBJECT_FOUR + " INTEGER,"
                + KEY_TRAINING_OBJECT_FIVE + " INTEGER" +
                //KEY_TRAINING_OBJECT_RESPONSES + " INTEGER" +
                ")";

        db.execSQL(CREATE_TRAINING_OBJECT_TABLE);

        //training table
        String CREATE_TRAINING_TABLE = "CREATE TABLE " + TABLE_TRAINING + "("
                + KEY_TRAINING_ID + " INTEGER PRIMARY KEY," + KEY_TRAINING_EVALUATION + " INTEGER,"
                + KEY_TRAINING_AIM + " TEXT," + KEY_TRAINING_HOOD + " TEXT," + KEY_TRAINING_EXPLANATION + " TEXT," + KEY_BEHAVIOR_ID + " INTEGER,"
                + KEY_TRAINING_TOTAL_QUESTION + " INTEGER," + KEY_TRAINING_OK + " INTEGER," + KEY_TRAINING_CREATE_TIME + " TEXT" + ")";

        db.execSQL(CREATE_TRAINING_TABLE);

        //trainingset table
        String CREATE_TRAINING_SET_TABLE = "CREATE TABLE " + TABLE_TRAINING_SET + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TRAINING_ID + " INTEGER,"
                + KEY_STUDENT_ID + " INTEGER," + KEY_TRAINING_SET_CREATE_TIME + " INTEGER," + KEY_TRAINING_SET_FINISH_TIME + " STRING" + ")";

        db.execSQL(CREATE_TRAINING_SET_TABLE);

        //trainingresponsetable
        String CREATE_TRAINING_RESPONSE_TABLE = "CREATE TABLE " + TABLE_TRAINING_RESPONSE + "("
                + KEY_TRAINING_RESPONSE_ID + " INTEGER PRIMARY KEY," + KEY_TRAINING_ID + " INTEGER,"
                + KEY_STUDENT_USERNAME + " STRING," + KEY_OBJECT_ID + " INTEGER," + KEY_TRAINING_RESPONSE_FINISH_TIME + " STRING,"
                + KEY_TRAINING_RESPONSE_SCORE + " INTEGER," + KEY_TRAINING_STARTED + " INTEGER," + KEY_TRAINING_COMPLETED + " INTEGER," + KEY_RESPONSE_SENT + " INTEGER"
                + ")";

        db.execSQL(CREATE_TRAINING_RESPONSE_TABLE);

        //shapetable
        String CREATE_COLOR_TABLE = "CREATE TABLE " + TABLE_COLOR + "("
                + KEY_COLOR_ID + " INTEGER PRIMARY KEY," + KEY_COLOR_NAME + " STRING"
                + ")";

        db.execSQL(CREATE_COLOR_TABLE);

        //shapetable
        String CREATE_SHAPE_TABLE = "CREATE TABLE " + TABLE_SHAPE + "("
                + KEY_SHAPE_ID + " INTEGER PRIMARY KEY," + KEY_SHAPE_NAME + " STRING"
                + ")";

        db.execSQL(CREATE_SHAPE_TABLE);

    }//on create end

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(TAG, "dbhandler onupgrade");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING_OBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING_SET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING_RESPONSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHAPE);

        // Create tables again
        onCreate(db);
    }

    // Adding new object
    public void addObject(ObjectObject object) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_COLOR_ID, object.getColorID());
        values.put(KEY_OBJECT_ID, object.getObjectID());
        values.put(KEY_OBJECT_NAME, object.getObjectName());
        values.put(KEY_OBJECT_IMAGE, object.getObjectImage());
        values.put(KEY_OBJECT_NUMBER, object.getObjectNumber());
        values.put(KEY_SHAPE_ID, object.getShapeID());
        values.put(KEY_CREATE_TIME, object.getCreateTime());
        values.put(KEY_OBJECT_IMAGE_BLOB, object.getObjectImageBlob());

        // Inserting Row
        db.insert(TABLE_OBJECT, null, values);
        db.close(); // Closing database connection
    }

    //adding new training
    public void addTraining(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_ID, training.getTrainingID());
        values.put(KEY_TRAINING_EVALUATION, training.getTrainingEvaluation());
        values.put(KEY_TRAINING_AIM, training.getTrainingAim());
        values.put(KEY_TRAINING_EXPLANATION, training.getTrainingExplanation());
        values.put(KEY_BEHAVIOR_ID, training.getBehaviorID());
        values.put(KEY_TRAINING_TOTAL_QUESTION, training.getTrainingTotalQuestion());
        values.put(KEY_TRAINING_HOOD, training.getTrainingHood());
        values.put(KEY_TRAINING_CREATE_TIME, training.getTrainingCreateTime());
        values.put(KEY_TRAINING_OK, training.getTrainingOK());

        // Inserting Row
        db.insert(TABLE_TRAINING, null, values);
        db.close(); // Closing database connection
    }

    //adding new trainingobject
    public void addTrainingObject(TrainingObject trainingObject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_OBJECT_ID, trainingObject.getTrainingobjectID());
        values.put(KEY_TRAINING_ID, trainingObject.getTrainingID());
        values.put(KEY_TRAINING_OBJECT_LEVEL, trainingObject.getTrainingobjectLevel());
        values.put(KEY_TRAINING_OBJECT_ANSWER, trainingObject.getTrainingobjectAnswer());
        values.put(KEY_TRAINING_OBJECT_ONE, trainingObject.getTrainingobjectOne());
        values.put(KEY_TRAINING_OBJECT_TWO, trainingObject.getTrainingobjectTwo());
        values.put(KEY_TRAINING_OBJECT_THREE, trainingObject.getTrainingobjectThree());
        values.put(KEY_TRAINING_OBJECT_FOUR, trainingObject.getTrainingobjectFour());
        values.put(KEY_TRAINING_OBJECT_FIVE, trainingObject.getTrainingobjectFive());
        // values.put(KEY_TRAINING_OBJECT_RESPONSES, trainingObject.getTrainingobjectResponses());

        // Inserting Row
        db.insert(TABLE_TRAINING_OBJECT, null, values);
        db.close(); // Closing database connection
    }

    //adding new trainingset
    public void addTrainingSet(TrainingSet trainingSet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID, trainingSet.getId());
        values.put(KEY_TRAINING_ID, trainingSet.getTrainingID());
        values.put(KEY_STUDENT_ID, trainingSet.getStudentID());
        values.put(KEY_TRAINING_SET_CREATE_TIME, trainingSet.getTrainingsetCreateTime());
        values.put(KEY_TRAINING_SET_FINISH_TIME, trainingSet.getTrainingsetFinishTime());

        // Inserting Row
        db.insert(TABLE_TRAINING_SET, null, values);
        db.close(); // Closing database connection
    }

    //adding new trainingresponse
    public void addTrainingResponse(TrainingResponse trainingResponse) {
        //Log.d(TAG,"addtrainingresponse");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

       // values.put(KEY_TRAINING_RESPONSE_ID, trainingResponse.getTrainingResponseID());
        values.put(KEY_TRAINING_ID, trainingResponse.getTrainingID());
        values.put(KEY_STUDENT_USERNAME, trainingResponse.getStudentUserName());
        values.put(KEY_OBJECT_ID, trainingResponse.getObjectID());
        values.put(KEY_TRAINING_RESPONSE_FINISH_TIME, trainingResponse.getTrainingResponseFinishTime());
        values.put(KEY_TRAINING_RESPONSE_SCORE, trainingResponse.getTrainingResponseScore());
        values.put(KEY_TRAINING_STARTED, trainingResponse.getTrainingStarted());
        values.put(KEY_TRAINING_COMPLETED, trainingResponse.getTrainingCompleted());
        values.put(KEY_RESPONSE_SENT, trainingResponse.getResponseSent());

        // Inserting Row
        db.insert(TABLE_TRAINING_RESPONSE, null, values);
        db.close(); // Closing database connection
    }

    //adding new color
    public void addColor(Color color) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_COLOR_ID, color.getColorID());
        values.put(KEY_COLOR_NAME, color.getColorName());
        ;
        // Inserting Row
        db.insert(TABLE_COLOR, null, values);
        db.close(); // Closing database connection
    }

    //adding new shape
    public void addShape(Shape shape) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_SHAPE_ID, shape.getShapeID());
        values.put(KEY_SHAPE_NAME, shape.getShapeName());
        ;
        // Inserting Row
        db.insert(TABLE_SHAPE, null, values);
        db.close(); // Closing database connection
    }

    /***************************************************************************************************/

    // Getting All ObjectObjects
    public List<ObjectObject> getAllObjectObject() {
        List<ObjectObject> objectList = new ArrayList<ObjectObject>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OBJECT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ObjectObject object = new ObjectObject();

                object.setObjectID(Integer.parseInt(cursor.getString(0)));
                object.setObjectName(cursor.getString(1));
                object.setObjectImage(cursor.getString(2));
                object.setObjectNumber(Integer.parseInt(cursor.getString(3)));
                object.setShapeID(Integer.parseInt(cursor.getString(4)));
                object.setColorID(Integer.parseInt(cursor.getString(5)));
                object.setCreateTime(cursor.getString(6));
                object.setObjectImageBlob(cursor.getBlob(7));

                // Adding object to list
                objectList.add(object);
                //  Log.d(TAG,object.toString());//gecici bu
            } while (cursor.moveToNext());
        }

        // return object list
        return objectList;
    }

    // Getting All TrainingObjects
    public List<TrainingObject> getAllTrainingObject() {
        List<TrainingObject> trainingObjectList = new ArrayList<TrainingObject>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRAINING_OBJECT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TrainingObject trainingObject = new TrainingObject();

                trainingObject.setTrainingobjectID(Integer.parseInt(cursor.getString(0)));
                trainingObject.setTrainingID(Integer.parseInt(cursor.getString(1)));
                trainingObject.setTrainingobjectLevel(Integer.parseInt(cursor.getString(2)));
                trainingObject.setTrainingobjectAnswer(Integer.parseInt(cursor.getString(3)));
                trainingObject.setTrainingobjectOne(Integer.parseInt(cursor.getString(4)));
                trainingObject.setTrainingobjectTwo(Integer.parseInt(cursor.getString(5)));
                trainingObject.setTrainingobjectThree(Integer.parseInt(cursor.getString(6)));
                trainingObject.setTrainingobjectFour(Integer.parseInt(cursor.getString(7)));
                trainingObject.setTrainingobjectFive(Integer.parseInt(cursor.getString(8)));
                // trainingObject.setTrainingobjectResponses(Integer.parseInt(cursor.getString(9)));

                // Adding trainingObject to list
                trainingObjectList.add(trainingObject);
                //  Log.d(TAG,trainingObject.toString());//gecici bu
            } while (cursor.moveToNext());
        }

        // return trainingObject list
        return trainingObjectList;
    }

    // Getting All Trainings
    public List<Training> getAllTraining(String username) {Log.d(TAG,"dbhandlergetalltrainingh");
        List<Training> trainingList = new ArrayList<Training>();

        // Select All Query
        String selectQuery = "SELECT *  FROM "+TABLE_TRAINING+" WHERE "+KEY_TRAINING_ID+" NOT IN("+

                "SELECT "+KEY_TRAINING_ID+"  FROM " + TABLE_TRAINING_RESPONSE +" WHERE "+ KEY_STUDENT_USERNAME+"='"+username+"' AND "+
                KEY_TRAINING_STARTED+"=1);";//+" AND "+KEY_TRAINING_COMPLETED+"=0);";//// TODO: 5/17/2016 distinct olmali mi
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Training training = new Training();

                training.setTrainingID(Integer.parseInt(cursor.getString(0)));
                training.setTrainingEvaluation(Integer.parseInt(cursor.getString(1)));
                training.setTrainingAim(cursor.getString(2));
                training.setTrainingHood(cursor.getString(3));
                training.setTrainingExplanation(cursor.getString(4));
                training.setBehaviorID(Integer.parseInt(cursor.getString(5)));
                training.setTrainingTotalQuestion(Integer.parseInt(cursor.getString(6)));//Log.d(TAG,"tra "+training.toString());
                training.setTrainingOK(Integer.parseInt(cursor.getString(7)));
                training.setTrainingCreateTime(cursor.getString(8));

                // Adding training to list
                trainingList.add(training);
                  Log.d(TAG,training.toString());//gecici bu
            } while (cursor.moveToNext());
        }

        // return training list
        return trainingList;
    }

    // Getting Half Trainings
    public List<Training> getHalfTraining(String username) {Log.d(TAG,"dbhandler *gethalf*");
        List<Training> trainingList = new ArrayList<Training>();

        // Select All Query
        String selectQuery = "SELECT *  FROM "+TABLE_TRAINING+" WHERE "+KEY_TRAINING_ID+" IN("+

        "SELECT "+KEY_TRAINING_ID+"  FROM " + TABLE_TRAINING_RESPONSE +" WHERE "+ KEY_STUDENT_USERNAME+"='"+username+"' AND "+
                KEY_TRAINING_STARTED+"=1"+" AND "+KEY_TRAINING_COMPLETED+"=0);";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
       /* if (cursor.moveToFirst()) {
            do {
                TrainingResponse trainingResponse = new TrainingResponse();

                trainingResponse.setTrainingResponseID(Integer.parseInt(cursor.getString(0)));
                trainingResponse.setTrainingID(Integer.parseInt(cursor.getString(1)));
                trainingResponse.setStudentUserName(cursor.getString(2));
                trainingResponse.setObjectID(Integer.parseInt(cursor.getString(3)));
                trainingResponse.setTrainingResponseFinishTime((cursor.getString(4)));
                trainingResponse.setTrainingResponseScore(cursor.getString(5));
                trainingResponse.setTrainingStarted(Integer.parseInt(cursor.getString(6)));
                trainingResponse.setTrainingCompletedAll(Integer.parseInt(cursor.getString(7)));
                trainingResponse.setResponseSent(Integer.parseInt(cursor.getString(8)));

                // Adding trainingresponse to list
                //trainingResponseList.add(trainingResponse);
                Log.d(TAG,"**"+trainingResponse.toString());//gecici bua
            } while (cursor.moveToNext());
        }*/
        // looping through all rows and adding to list
       if (cursor.moveToFirst()) {
            do {//Log.d(TAG,(cursor.getString(0)));
                Training training = new Training();

                training.setTrainingID(Integer.parseInt(cursor.getString(0)));
                training.setTrainingEvaluation(Integer.parseInt(cursor.getString(1)));
                training.setTrainingAim(cursor.getString(2));
                training.setTrainingHood(cursor.getString(3));
                training.setTrainingExplanation(cursor.getString(4));
                training.setBehaviorID(Integer.parseInt(cursor.getString(5)));
                training.setTrainingTotalQuestion(Integer.parseInt(cursor.getString(6)));//Log.d(TAG,"tra "+training.toString());
                training.setTrainingOK(Integer.parseInt(cursor.getString(7)));
                training.setTrainingCreateTime(cursor.getString(8));

                // Adding training to list
                trainingList.add(training);
                  Log.d(TAG,training.toString());//gecici bu
                getAllTrainingResponse();//// TODO: 5/20/2016 sil
            } while (cursor.moveToNext());
        }

        // return training list
        return trainingList;
    }

    // Getting All Trainingsets
    public List<TrainingSet> getAllTrainingSet() {
        List<TrainingSet> trainingSetList = new ArrayList<TrainingSet>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRAINING_SET;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TrainingSet trainingSet = new TrainingSet();

                trainingSet.setId(Integer.parseInt(cursor.getString(0)));
                trainingSet.setTrainingID(Integer.parseInt(cursor.getString(1)));
                trainingSet.setStudentID(Integer.parseInt(cursor.getString(2)));
                trainingSet.setTrainingsetCreateTime(Integer.parseInt(cursor.getString(3)));
                trainingSet.setTrainingsetFinishTime(cursor.getString(4));

                // Adding trainingset to list
                trainingSetList.add(trainingSet);
                // Log.d(TAG,trainingSet.toString());//gecici bu
            } while (cursor.moveToNext());
        }

        // return trainingset list
        return trainingSetList;
    }

    // Getting All Trainingresponses
    public List<TrainingResponse> getAllTrainingResponse() {
        List<TrainingResponse> trainingResponseList = new ArrayList<TrainingResponse>();
        Log.d(TAG,"getAllTrainingResponse");
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRAINING_RESPONSE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TrainingResponse trainingResponse = new TrainingResponse();

                trainingResponse.setTrainingResponseID(Integer.parseInt(cursor.getString(0)));
                trainingResponse.setTrainingID(Integer.parseInt(cursor.getString(1)));
                trainingResponse.setStudentUserName(cursor.getString(2));
                trainingResponse.setObjectID(Integer.parseInt(cursor.getString(3)));
                trainingResponse.setTrainingResponseFinishTime((cursor.getString(4)));
              //  if(!(cursor.getString(5).equals(null))){
                trainingResponse.setTrainingResponseScore(Integer.parseInt(cursor.getString(5)));//}else Log.d(TAG,"null");
                trainingResponse.setTrainingStarted(Integer.parseInt(cursor.getString(6)));
                trainingResponse.setTrainingCompleted(Integer.parseInt(cursor.getString(7)));
                trainingResponse.setResponseSent(Integer.parseInt(cursor.getString(8)));

                // Adding trainingresponse to list
                trainingResponseList.add(trainingResponse);
                  Log.d(TAG,trainingResponse.toString());//gecici bua
            } while (cursor.moveToNext());
        }

        return trainingResponseList;
    }

    // Getting All Trainingresponses for single trainingid
    public List<TrainingResponse> getAllTrainingResponse(int trainingid) {
        Log.d(TAG,"getAllTrainingResponse sfir gözükür digerleri tariningid:"+trainingid);
        List<TrainingResponse> trainingResponseList = new ArrayList<TrainingResponse>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRAINING_RESPONSE+" WHERE "+KEY_TRAINING_ID+"="+trainingid;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TrainingResponse trainingResponse = new TrainingResponse();

                trainingResponse.setTrainingResponseID(Integer.parseInt(cursor.getString(0)));
                trainingResponse.setTrainingID(Integer.parseInt(cursor.getString(1)));
                trainingResponse.setStudentUserName(cursor.getString(2));
                trainingResponse.setObjectID(Integer.parseInt(cursor.getString(3)));
                trainingResponse.setTrainingResponseFinishTime((cursor.getString(4)));
                trainingResponse.setTrainingResponseScore(Integer.parseInt(cursor.getString(5)));
//digerlerini almiyor cunku servere atilacak

                // Adding trainingresponse to list
                trainingResponseList.add(trainingResponse);
                Log.d(TAG,trainingResponse.toString());//gecici bua
            } while (cursor.moveToNext());
        }

        // return trainingresponse list
        return trainingResponseList;
    }

    // Getting All colors
    public List<Color> getAllColor() {
        List<Color> colorList = new ArrayList<Color>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COLOR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Color color = new Color();

                color.setColorID(Integer.parseInt(cursor.getString(0)));
                color.setColorName(cursor.getString(1));

                // Adding color to list
                colorList.add(color);
                //  Log.d(TAG,color.toString());//gecici bu
            } while (cursor.moveToNext());
        }

        // return training list
        return colorList;
    }

    // Getting All shapes
    public List<Shape> getAllShape() {
        List<Shape> shapeList = new ArrayList<Shape>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SHAPE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Shape shape = new Shape();

                shape.setShapeID(Integer.parseInt(cursor.getString(0)));
                shape.setShapeName(cursor.getString(1));

                // Adding shape to list
                shapeList.add(shape);
                // Log.d(TAG,shape.toString());//gecici bu
            } while (cursor.moveToNext());
        }

        // return training list
        return shapeList;
    }

    /*****************************************************************************************************/

    // Getting All TrainingObjectsfor trainingid
    public List<TrainingObject> getAllTrainingObject(int trainingid) {
        List<TrainingObject> trainingObjectList = new ArrayList<TrainingObject>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRAINING_OBJECT + " WHERE " + KEY_TRAINING_ID + " = " + trainingid;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TrainingObject trainingObject = new TrainingObject();

                trainingObject.setTrainingobjectID(Integer.parseInt(cursor.getString(0)));
                trainingObject.setTrainingID(Integer.parseInt(cursor.getString(1)));
                trainingObject.setTrainingobjectLevel(Integer.parseInt(cursor.getString(2)));
                trainingObject.setTrainingobjectAnswer(Integer.parseInt(cursor.getString(3)));
                trainingObject.setTrainingobjectOne(Integer.parseInt(cursor.getString(4)));
                trainingObject.setTrainingobjectTwo(Integer.parseInt(cursor.getString(5)));
                trainingObject.setTrainingobjectThree(Integer.parseInt(cursor.getString(6)));
                trainingObject.setTrainingobjectFour(Integer.parseInt(cursor.getString(7)));
                trainingObject.setTrainingobjectFive(Integer.parseInt(cursor.getString(8)));
                // trainingObject.setTrainingobjectResponses(Integer.parseInt(cursor.getString(9)));

                // Adding trainingObject to list
                trainingObjectList.add(trainingObject);
                   Log.d(TAG,trainingObject.toString());//gecici bu
            } while (cursor.moveToNext());
        }

        // return trainingObject list
        return trainingObjectList;
    }

    /*****************************************************************************************************/

    // Getting Object Count
    public int getObjectCount() {
        String countQuery = "SELECT  * FROM " + TABLE_OBJECT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting Training Count
    public int getTrainingCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TRAINING;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting TrainingObject Count
    public int getTrainingObjectCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TRAINING_OBJECT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting Trainingset Count
    public int getTrainingSetCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TRAINING_SET;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting Trainingresponse Count
    public int getTrainingResponseCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TRAINING_RESPONSE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting COLOR Count
    public int getColorCount() {
        String countQuery = "SELECT  * FROM " + TABLE_COLOR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting shape Count
    public int getShapeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SHAPE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    /***************************************************************************************************/

    // Updating single object
    public int updateObjectObject(ObjectObject object) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_COLOR_ID, object.getColorID());
        values.put(KEY_OBJECT_ID, object.getObjectID());
        values.put(KEY_OBJECT_NAME, object.getObjectName());
        values.put(KEY_OBJECT_IMAGE, object.getObjectImage());
        values.put(KEY_OBJECT_NUMBER, object.getObjectNumber());
        values.put(KEY_SHAPE_ID, object.getShapeID());
        values.put(KEY_CREATE_TIME, object.getCreateTime());
        values.put(KEY_OBJECT_IMAGE_BLOB, object.getObjectImageBlob());

        // updating row
        return db.update(TABLE_OBJECT, values, KEY_OBJECT_ID + " = ?",
                new String[]{String.valueOf(object.getObjectID())});
    }

    // Updating single training
    public int updateTraining(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_ID, training.getTrainingID());
        values.put(KEY_TRAINING_EVALUATION, training.getTrainingEvaluation());
        values.put(KEY_TRAINING_AIM, training.getTrainingAim());
        values.put(KEY_TRAINING_HOOD, training.getTrainingHood());
        values.put(KEY_TRAINING_EXPLANATION, training.getTrainingExplanation());
        values.put(KEY_BEHAVIOR_ID, training.getBehaviorID());
        values.put(KEY_TRAINING_TOTAL_QUESTION, training.getTrainingTotalQuestion());
        values.put(KEY_TRAINING_OK, training.getTrainingOK());
        values.put(KEY_CREATE_TIME, training.getTrainingCreateTime());

        // updating row
        return db.update(TABLE_TRAINING, values, KEY_TRAINING_ID + " = ?",
                new String[]{String.valueOf(training.getTrainingID())});
    }

    // Updating single trainingobject
    public int updateTrainingObject(TrainingObject trainingObject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_OBJECT_ID, trainingObject.getTrainingobjectID());
        values.put(KEY_TRAINING_ID, trainingObject.getTrainingID());
        values.put(KEY_TRAINING_OBJECT_LEVEL, trainingObject.getTrainingobjectLevel());
        values.put(KEY_TRAINING_OBJECT_ANSWER, trainingObject.getTrainingobjectAnswer());
        values.put(KEY_TRAINING_OBJECT_ONE, trainingObject.getTrainingobjectOne());
        values.put(KEY_TRAINING_OBJECT_TWO, trainingObject.getTrainingobjectTwo());
        values.put(KEY_TRAINING_OBJECT_THREE, trainingObject.getTrainingobjectThree());
        values.put(KEY_TRAINING_OBJECT_FOUR, trainingObject.getTrainingobjectFour());
        values.put(KEY_TRAINING_OBJECT_FIVE, trainingObject.getTrainingobjectFive());
        // values.put(KEY_TRAINING_OBJECT_RESPONSES, trainingObject.getTrainingobjectResponses());

        // updating row
        return db.update(TABLE_TRAINING_OBJECT, values, KEY_TRAINING_OBJECT_ID + " = ?",
                new String[]{String.valueOf(trainingObject.getTrainingobjectID())});
    }

    // Updating single trainingset
    public int updateTrainingSet(TrainingSet trainingSet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID, trainingSet.getId());
        values.put(KEY_TRAINING_ID, trainingSet.getTrainingID());
        values.put(KEY_STUDENT_ID, trainingSet.getStudentID());
        values.put(KEY_TRAINING_SET_CREATE_TIME, trainingSet.getTrainingsetCreateTime());
        values.put(KEY_TRAINING_SET_FINISH_TIME, trainingSet.getTrainingsetFinishTime());

        // updating row
        return db.update(TABLE_TRAINING_SET, values, KEY_ID + " = ?",
                new String[]{String.valueOf(trainingSet.getId())});
    }

    // Updating single trainingresponse
    public int updateTrainingResponse(TrainingResponse trainingResponse) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_RESPONSE_ID, trainingResponse.getTrainingResponseID());
        values.put(KEY_TRAINING_ID, trainingResponse.getTrainingID());
        values.put(KEY_STUDENT_USERNAME, trainingResponse.getStudentUserName());
        values.put(KEY_OBJECT_ID, trainingResponse.getObjectID());
        values.put(KEY_TRAINING_RESPONSE_FINISH_TIME, trainingResponse.getTrainingResponseFinishTime());
        values.put(KEY_TRAINING_RESPONSE_SCORE, trainingResponse.getTrainingResponseScore());
        values.put(KEY_TRAINING_STARTED, trainingResponse.getTrainingStarted());
        values.put(KEY_TRAINING_COMPLETED, trainingResponse.getTrainingStarted());
        values.put(KEY_RESPONSE_SENT, trainingResponse.getResponseSent());
        ;
        // updating row
        return db.update(TABLE_TRAINING_SET, values, KEY_ID + " = ?",
                new String[]{String.valueOf(trainingResponse.getTrainingResponseID())});
    }

    // Updating single color
    public int updateColor(Color color) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_COLOR_ID, color.getColorID());
        values.put(KEY_COLOR_NAME, color.getColorName());

        // updating row
        return db.update(TABLE_COLOR, values, KEY_ID + " = ?",
                new String[]{String.valueOf(color.getColorID())});
    }

    // Updating single shape
    public int updateShape(Shape shape) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_SHAPE_ID, shape.getShapeID());
        values.put(KEY_SHAPE_NAME, shape.getShapeName());

        // updating row
        return db.update(TABLE_SHAPE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(shape.getShapeID())});
    }
//// TODO: 01.05.2016 close db yap 

    /***************************************************************************************************/

    //// TODO: 24.04.2016 checkdatabase metod olacak kontrol icin dondurecek yeni gelenleri

    //to get single objects in games
    public ObjectObject getObjectObject(int id) {

        ObjectObject object = new ObjectObject();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        try {

            cursor = db.rawQuery("SELECT  * FROM " + TABLE_OBJECT + " WHERE " + KEY_OBJECT_ID + "=?", new String[]{id + ""});

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                object.setObjectID(Integer.parseInt(cursor.getString(0)));
                object.setObjectName(cursor.getString(1));
                object.setObjectImage(cursor.getString(2));
                object.setObjectNumber(Integer.parseInt(cursor.getString(3)));
                object.setShapeID(Integer.parseInt(cursor.getString(4)));
                object.setColorID(Integer.parseInt(cursor.getString(5)));
                object.setCreateTime(cursor.getString(6));
                object.setObjectImageBlob(cursor.getBlob(7));
            }

            //for test
            //  Log.d(TAG,"obj: "+object.toString());
            return object;
        } finally {
            cursor.close();
        }
    }

    /***************************************************************************************************/

    //demo objects for each training
    public List<ObjectObject> getDemoObjectObject(int id) {

        Log.d(TAG, "dbhandlergetDemoObjectObject");

        List<ObjectObject> objectList = new ArrayList<ObjectObject>();

        //  select * from object where objectid in(SELECT trainingobjectanswer FROM trainingobject WHERE trainingid='$trainingid');
        // Select All demoQuery
        String selectQuery = "SELECT  * FROM " + TABLE_OBJECT + " WHERE " + KEY_OBJECT_ID +
                " IN ( SELECT " + KEY_TRAINING_OBJECT_ANSWER + " FROM " + TABLE_TRAINING_OBJECT + " WHERE " + KEY_TRAINING_ID + " = " + id + ");";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ObjectObject object = new ObjectObject();

                object.setObjectID(Integer.parseInt(cursor.getString(0)));
                object.setObjectName(cursor.getString(1));
                object.setObjectImage(cursor.getString(2));
                object.setObjectNumber(Integer.parseInt(cursor.getString(3)));
                object.setShapeID(Integer.parseInt(cursor.getString(4)));
                object.setColorID(Integer.parseInt(cursor.getString(5)));
                object.setCreateTime(cursor.getString(6));
                object.setObjectImageBlob(cursor.getBlob(7));

                // Adding object to list
                objectList.add(object);
                //    Log.d(TAG,object.toString());//gecici bu
            } while (cursor.moveToNext());
        }

        // return object list
        Log.d(TAG, "dbhandlergetDemoObjectObject end");
        return objectList;
    }

    /***************************************************************************************************/

    // Getting color name
    public String getColorName(int id) {

        // Select All Query
        String selectQuery = "SELECT " + KEY_COLOR_NAME + " FROM " + TABLE_COLOR + " WHERE " + KEY_COLOR_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            //  Log.d(TAG,"Color name: "+cursor.getString(0)+" id="+id);

            // return training list
            return cursor.getString(0);
        }
        return "";
    }

    // Getting color name
    public String getShapeName(int id) {

        // Select All Query
        String selectQuery = "SELECT " + KEY_SHAPE_NAME + " FROM " + TABLE_SHAPE + " WHERE " + KEY_SHAPE_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            // Log.d(TAG,"SHAPE name: "+cursor.getString(0)+" id="+id);

            // return training list
            return cursor.getString(0);
        }
        return "";
    }

    /***************************************************************************************************/
//to check sqlite is null or not
    public boolean ifDatabaseExists() {

        Log.d(TAG,"dbhandlerifdbexists");

        SQLiteDatabase db = this.getWritableDatabase();//object tablosuna bakiliyor bos olup olmadigi
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_OBJECT, null);
        Boolean dbExists;

    if(mCursor.moveToFirst())
    {
        Log.d(TAG,"dbhandlerifdbexists true");
        dbExists = true;
    }

    else
    {
        Log.d(TAG,"dbhandlerifdbexists false");
        dbExists = false;
    }

        return dbExists;
}

    /***************************************************************************************************/
//todo yanlis bu galiba
    public boolean ifNewTrainingsExists() {

        Log.d(TAG,"dbhandlerifnewtrainingexists");

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DATABASE_NAME, null);
        boolean trainingExists;

        if(mCursor.moveToFirst())
        {
            Log.d(TAG,"dbhandlerifnewtrainingexists true");
            trainingExists = true;
    }

        else
        {
            Log.d(TAG,"dbhandlerifnewtrainingexists false");
            trainingExists = false;
        }

        return trainingExists;
    }

    /***************************************************************************************************/
//uncompeleted games, which level to continue
public int getCurrentLevel(int trainingID){

    //int trainingCount = 0, trainingResponseCount;
    SQLiteDatabase db ;

    String countQuery = "SELECT " + KEY_TRAINING_RESPONSE_SCORE+" FROM "+TABLE_TRAINING_RESPONSE+" WHERE "+KEY_TRAINING_ID+" = "+trainingID;
    db = this.getReadableDatabase();
    Cursor  cursor = db.rawQuery(countQuery, null);
    return cursor.getCount();
}

    /***************************************************************************************************/
    //training completed so set completed 1 for all//todo need to check
    public void setTrainingCompletedAll(int trainingID){
        Log.d(TAG,"dbhandler settrainingcopmpleted");
        //getAllTrainingResponse();
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_COMPLETED, 1);

        // updating row
         db.update(TABLE_TRAINING_RESPONSE, values, KEY_TRAINING_ID + " = ?",
                new String[]{String.valueOf(trainingID)});
      //  getAllTrainingResponse();
        return;
    }

    /***************************************************************************************************/
    //mark all trainings sent
    public void setTrainingSent(int trainingID){//todo need to check
        Log.d(TAG,"dbhandler settrainingset");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_ID, trainingID);
        values.put(KEY_RESPONSE_SENT, 1);

        // updating row
       db.update(TABLE_TRAINING_RESPONSE, values, KEY_TRAINING_ID + " = ?",
                new String[]{String.valueOf(trainingID)});
       // getAllTrainingResponse();
        return;
    }

    /***************************************************************************************************/

    public List<Integer> getUnsendResponseTrainingIDs(){//// TODO: 5/19/2016 need check
        Log.d(TAG,"dbhandler getUnsendResponses");
        List<Integer> unsendResponseTrainingIDs = new ArrayList<>();

        String selectQuery =  "SELECT DISTINCT "+KEY_TRAINING_ID+" FROM "+TABLE_TRAINING_RESPONSE+" WHERE "+KEY_TRAINING_STARTED+" = 1 "+"AND "
                +KEY_TRAINING_COMPLETED+" = 1 AND "+ KEY_RESPONSE_SENT+" = 0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            // Log.d(TAG,"dbhandler getUnsendResponses trainnigid: "+cursor.getString(0));

            // return training list
            unsendResponseTrainingIDs.add(Integer.parseInt(cursor.getString(0)));
        }

        return unsendResponseTrainingIDs;
    }

/***************************************************************************************************/
//renk mi şekil mi egiitimi olduğunu görmek için
    public int getTrainingAim(int trainingID){

String aim = null;

        // Select All Query
        String selectQuery = "SELECT " + KEY_TRAINING_AIM + " FROM " + TABLE_TRAINING + " WHERE " + KEY_TRAINING_ID + " = " + trainingID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            // Log.d(TAG,"SHAPE name: "+cursor.getString(0)+" id="+id);

            // return training list
            aim = cursor.getString(0);
            Log.d(TAG, "getTrainingAim aim: " + aim);//// TODO: 5/21/2016 sil gecici
            if(aim.contains("enk")){
                return 1;//// TODO: 5/21/2016 sayma gelince ekle
            }
            else{
                if(aim.contains("kil")){
                    return 0;
                }
            }
        }
        else{
            Log.d(TAG,"getTrainingAim error");
            }
       return -1;
    }
    
    public void deleteAllTrainingResponses(){//// TODO: 5/22/2016 gecici sil.internet yok ondan böyle yaptim. tekrar alamiyorum
        String selectQuery = "DELETE "+TABLE_TRAINING_RESPONSE+";";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
    }

    /***************************************************************************************************/

    public String getLastTrainingSetUpdate() {

        String lastTrainingSetUpdate = null;

        // Select All Query
        //SELECT trainingsetcreatetime FROM `trainingset` ORDER BY trainingsetcreatetime DESC LIMIT 1
        String selectQuery = "SELECT " + KEY_TRAINING_SET_CREATE_TIME + " FROM " + TABLE_TRAINING_SET + " ORDER BY "
                + KEY_TRAINING_SET_CREATE_TIME + " DESC LIMIT 1 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            // Log.d(TAG,"SHAPE name: "+cursor.getString(0)+" id="+id);

            // return training list
            lastTrainingSetUpdate = cursor.getString(0);
            Log.d(TAG, "getLastTrainingSetUpdate: " + lastTrainingSetUpdate);//// TODO: 5/21/2016 sil gecici

            return lastTrainingSetUpdate;
        } else {
            Log.d(TAG, "getLastTrainingSetUpdate error");
            return null;
        }
    }

        /***************************************************************************************************/
//// TODO: 5/25/2016 kullanilmiyor artik ihtiac yok
        public String getLastObjectUpdate() {

            String LastObjectUpdate = null;

            // Select All Query
            //SELECT createtime FROM `object` ORDER BY createtime DESC LIMIT 1

            String selectQuery = "SELECT " + KEY_CREATE_TIME + " FROM " + TABLE_OBJECT + " ORDER BY "
                    + KEY_CREATE_TIME + " DESC LIMIT 1 ";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {

                // Log.d(TAG,"SHAPE name: "+cursor.getString(0)+" id="+id);

                // return training list
                LastObjectUpdate = cursor.getString(0);
                Log.d(TAG, "getLastObjectUpdate: " + LastObjectUpdate);//// TODO: 5/21/2016 sil gecici

                return LastObjectUpdate;
            } else {
                Log.d(TAG, "getLastObjectUpdate error");
                return null;
            }
        }

        /***************************************************************************************************/




        /***************************************************************************************************/




}//class end
