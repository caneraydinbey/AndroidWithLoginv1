package com.example.caneraydin.androidwithlogin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by Chico on 5/19/2016.
 */
public class CheckNetwork {

    private static String TAG = "Chic";
    private static boolean connectDialogOpen= false;
    boolean isDialogLoopNeeded;

    public static boolean isOnline(Context context){
        Log.d(TAG,"checknetwork Isonline");
        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            Log.d(TAG,"checknetwork wifi on");//// TODO: 5/17/2016 internete bahlanmamissa text yaz internet gerekli egitiminiz yok yerelde
            return true;
        }
        else {
            Log.d(TAG,"checknetwork wifi off");
            return false;//// TODO: 5/17/2016 internete bahlanmamissa text yaz internet gerekli egitiminiz yok yerelde
        }
    }

    public static void showNoConnectionDialog(final Context ctx, final boolean isDialogLoopNeeded){//we need wifi so loop asking for wifi


        Log.d(TAG,"checknetwork showNoConnectionDialog connectDialogOpen:"+connectDialogOpen);

        if(!connectDialogOpen){

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setCancelable(true);
            builder.setTitle("Egitimlerin guncellenebilmesi icin internet baglantisi gerekmekte...");
            builder.setMessage("Simdi interneti aktiflestirip guncellemeye baslamak ister misiniz?");
            builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ctx.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                    connectDialogOpen= false;
                }
            });
            builder.setNegativeButton("Hayir", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    connectDialogOpen= false;
                    if(isDialogLoopNeeded){
                        showNoConnectionDialog(ctx,isDialogLoopNeeded);
                    }
                    return;
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    connectDialogOpen= false;
                    if(isDialogLoopNeeded){
                        showNoConnectionDialog(ctx,isDialogLoopNeeded);
                    }
                    return;
                }
            });
            connectDialogOpen= true;
            builder.show();
        }
    }
}