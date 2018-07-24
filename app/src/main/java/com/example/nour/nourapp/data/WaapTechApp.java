package com.example.nour.nourapp.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nour.nourapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.example.nour.nourapp.data.DataStore;
import com.example.nour.nourapp.Models.AppUser;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WaapTechApp extends Application{// implements ConnectionCallbacks, OnConnectionFailedListener{

    public static WaapTechApp appContext;
    private static Gson sharedGsonParser;
//    private static GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        sharedGsonParser = new Gson();
        DataStore.getInstance().startScheduledUpdates();
    }

//	public static String getFormatedDateNow(){
//		Calendar c = Calendar.getInstance();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		String formattedDate = df.format(c.getTime());
//		return formattedDate ;
//	}
//

    public static WaapTechApp getAppContext() {
        return appContext;
    }

    public static Gson getSharedGsonParser() {
        return sharedGsonParser;
    }

    public static long getTimestampNow() {
        long res = 0;
        try {
            res = Calendar.getInstance().getTimeInMillis();
        } catch (Exception ignored) {
        }
        return res;
    }

    public static String getFormatedDateForDisplay(long timestamp) {
        String res = null;
        try {
            Date date = new Date(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            res = sdf.format(date);
        } catch (Exception ignored) {

        }
        return res;
    }

    private static final long oneDayMillies = 24 * 60 * 60 * 1000;
    private static final long oneHourMillies = 60 * 60 * 1000;
    private static final long oneMinuteMillies = 60 * 1000;



    public static String getDateString(long date) {

        String result = "";
        long now = Calendar.getInstance().getTimeInMillis();
        long timeLapsed = now - date;
        int days = (int) (timeLapsed / oneDayMillies);
        if (days == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            result = sdf.format(date);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            result = sdf.format(date);
        }
        return result;
    }

    public static int getPXSize(int dp) {
        int px = dp;
        try {
            float density = getAppContext().getResources().getDisplayMetrics().density;
            px = Math.round((float) dp * density);
        } catch (Exception ignored) {
        }
        return px;
    }

    public static int getDPSize(int px) {
        int dp = px;
        try {
            float density = getAppContext().getResources().getDisplayMetrics().density;
            dp = Math.round((float) px / density);
        } catch (Exception ignored) {
        }
        return dp;
    }

    public static void displayCustomToast(String txt) {
        try {
            //LayoutInflater inflater = (LayoutInflater) appContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            Toast toast = Toast.makeText(appContext, txt, Toast.LENGTH_LONG);
            View toastView = toast.getView();
            int padding = getPXSize(4);
        /* And now you can get the TextView of the default View of the Toast. */
            TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toastMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_icon_toast, 0);
            toastMessage.setGravity(Gravity.CENTER);
            toastMessage.setPadding(padding, padding, padding, padding);
            toastMessage.setCompoundDrawablePadding(16);
            //toastView.setBackgroundResource(ae.alphaapps.appshare.R.drawable.shape_custom_toast_bg);
            toast.show();

            // Snackbar.make(mCoordinator, "FAB Clicked", Snackbar.LENGTH_SHORT).setAction("DISMISS", null).show();
        } catch (Exception ignored) {
        }
    }

    public static void displayCustomToast(int strRes) {
        if (strRes != 0) {
            displayCustomToast(appContext.getString(strRes));
        }
    }




    public static void hideKeyboard(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) v.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }



    public static String getThumbnailURLFromVideoURL(String videoUrl) {

        if (videoUrl == null)
            return null;

        String imgUrl = null;
        if (videoUrl.contains("youtube")) { // if its a youtube Video

            //check if last character is backslash then remove it
            if (videoUrl.charAt(videoUrl.length() - 1) == '/') {
                videoUrl = videoUrl.substring(0, videoUrl.length() - 1);
            }
            int indexVideoIdParam = videoUrl.indexOf("v=", 0);
            int indexEndOfIdParam = videoUrl.indexOf("&", indexVideoIdParam);
            if (indexEndOfIdParam < 0)
                indexEndOfIdParam = videoUrl.length();
            String videoId = videoUrl.substring(indexVideoIdParam + 2, indexEndOfIdParam);
            imgUrl = "http://img.youtube.com/vi/" + videoId + "/hqdefault.jpg";

            return imgUrl;
        } else {
            // we dont support any other Video types for now
            return null;
        }
    }



    /**
     * opens a new User Profile Activity
     *
     * @param activity                 source context
     * @param user                     : user to show its profile
     * @param sharedImageForTransition mandatory image to animate during transitions "only for API > 20"
     * @param sharedNameForTransition  optional textView to animate during transitions "only for API > 20"
     */
//    public static void showUserProfile(Activity activity, AppUser user, View sharedImageForTransition, View sharedNameForTransition) {
//
//        if (user != null) {
//            Intent intent = new Intent(activity, ProfileActivity.class);
//            intent.putExtra("user", user.getJsonObject().toString());
//
//            // if view transitions Applied
//            if (sharedImageForTransition != null) {
//                Pair<View, String> pairImgAnim = new Pair(sharedImageForTransition, activity.getString(R.string.transition_name_circle));
//                Pair<View, String> pairNameAnim = (sharedNameForTransition == null) ? null : new Pair(sharedNameForTransition, activity.getString(R.string.transition_name_name));
//                ActivityOptionsCompat options;
//                if (pairNameAnim != null)
//                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairImgAnim, pairNameAnim);
//                else
//                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairImgAnim);
//
//                ActivityCompat.startActivity(activity, intent, options.toBundle());
//            } else
//                activity.startActivity(intent);
//        }
//    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
//    @Override
//    public void onConnected(Bundle connectionHint) {
//        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (mLastLocation != null) {
//            DataStore.getInstance().setMeLastLocation(mLastLocation);
//        }
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//         Log.i("Google Api connection", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
//    }
//
//    @Override
//    public void onConnectionSuspended(int cause) {
//        mGoogleApiClient.connect();
//    }
//
//    public static void requestLastUserKnownLocation() {
//        if(mGoogleApiClient == null) {
//            // init google apis client
//            mGoogleApiClient = new GoogleApiClient.Builder(SindbadApp.getAppContext())
//                    .addConnectionCallbacks(SindbadApp.getAppContext())
//                    .addOnConnectionFailedListener(SindbadApp.getAppContext())
//                    .addApi(LocationServices.API)
//                    .build();
//        }
//        if(!mGoogleApiClient.isConnected())
//            mGoogleApiClient.connect();
//    }
//
//    public static void disconnectGoogleApiClient(){
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
//    }

}
