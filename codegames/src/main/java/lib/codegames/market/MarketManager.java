package lib.codegames.market;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lib.codegames.R;
import lib.codegames.ad.BannerAdCGModel;
import lib.codegames.debug.LogCG;

public final class MarketManager {

    private static CafeBazaar cafeBazaar = new CafeBazaar();
    private static Myket myket = new Myket();
    private static IranApps iranApps = new IranApps();
    private static IMarket targetMarket = cafeBazaar;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({IMarket.CAFE_BAZAAR, IMarket.IRAN_APPS, IMarket.MYKET})
    @interface MarketMode {}

    public static void setTargetMarket(@MarketMode String market) {
        switch (market) {
            case IMarket.CAFE_BAZAAR :
                targetMarket = cafeBazaar;
                break;
            case IMarket.MYKET :
                targetMarket = myket;
                break;
            case IMarket.IRAN_APPS :
                targetMarket = iranApps;
                break;
        }
    }

    private static int getVersionCode(@NonNull Activity activity){

        String packageName = activity.getApplicationContext().getPackageName();
        try {
            int versionCode = activity.getPackageManager().getPackageInfo(packageName, 0).versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }

    }

    private static boolean needAlert = false;
    public static void alertUpdate(@NonNull Context context){
        AlertDialog.Builder alertUpdate = new AlertDialog.Builder(context)
                .setTitle("بروز رسانی")
                .setMessage("نسخه جدیدی از برنامه منتشر شده است.")
                .setPositiveButton("بروز رسانی", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        needAlert = false;
                        targetMarket.openAppPage(context);
                    }
                })
                .setNeutralButton("بعدآ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        needAlert = false;
                    }
                })
                .setIcon(R.drawable.ic_update_black_24dp);
        alertUpdate.show();
    }

    private static void alertMarketNotInstall(@NonNull Context context){
        AlertDialog.Builder alertUpdate = new AlertDialog.Builder(context)
                .setTitle("Alert")
                .setMessage(MarketManager.getName() + " Is Not Installed!")
                .setCancelable(true)
                .setIcon(R.drawable.ic_error_outline_black_24dp);
        alertUpdate.show();
    }

    private static boolean updateChecked = false;
    public static void checkAppUpdate(@NonNull Activity activity){
        if(needAlert) alertUpdate(activity);
        if(updateChecked) return;

        String packageName = activity.getApplicationContext().getPackageName();
        String path = "https://codegames.ir/app/" + packageName + "/version.txt";

        GetVersionTask getVersionTask = new GetVersionTask(activity);
        getVersionTask.execute(path);

    }

    private static class GetVersionTask extends AsyncTask<String, Boolean, Boolean> {

        private Activity activity;

        public GetVersionTask(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            try {

                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int responseCode = connection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK) { // On Success

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    int vc = Integer.parseInt(response.toString());

                    return vc > getVersionCode(activity);
                }else { // On Failure
                    LogCG.d("Market Manager Error Connect To Url: " + responseCode + " Code");
                    return null;
                }

            } catch (NumberFormatException e) { // On Failure
                e.printStackTrace();
                LogCG.d("Market Manager Error Number Format Exception");
                return null;
            } catch (MalformedURLException e) { // On Failure
                e.printStackTrace();
                LogCG.d("Market Manager Error Malformed URL Exception");
                return null;
            } catch (IOException e) { // On Failure
                e.printStackTrace();
                LogCG.d("Market Manager Error IO Exception");
                return null;
            }

        }

        @Override
        protected void onPostExecute(Boolean haveNew) {
            super.onPostExecute(haveNew);

            if(haveNew == null)
                return;

            updateChecked = true;

            if (haveNew) {
                needAlert = true;
                alertUpdate(activity);
            }

        }

    }

    public static String getName() {
        return targetMarket.getName();
    }

    public static void openAppPage(@NonNull Context context) {
        try {
            targetMarket.openAppPage(context);
        }catch (Exception e) {
            e.printStackTrace();
            alertMarketNotInstall(context);
        }
    }

    public static void openAppPage(@NonNull Context context, String packageName) {
        try {
            targetMarket.openAppPage(context, packageName);
        }catch (Exception e) {
            e.printStackTrace();
            alertMarketNotInstall(context);
        }
    }

    public static void openVotePage(@NonNull Context context) {
        try {
            targetMarket.openVotePage(context);
        }catch (Exception e) {
            e.printStackTrace();
            alertMarketNotInstall(context);
        }
    }

    public static void openVotePage(@NonNull Context context, String packageName) {
        try {
            targetMarket.openVotePage(context, packageName);
        }catch (Exception e) {
            e.printStackTrace();
            alertMarketNotInstall(context);
        }
    }

    public static void openPublisherPage(@NonNull Context context) {
        try {
            targetMarket.openPublisherPage(context);
        }catch (Exception e) {
            e.printStackTrace();
            alertMarketNotInstall(context);
        }
    }

    public static void openPublisherPage(@NonNull Context context, String packageName) {
        try {
            targetMarket.openPublisherPage(context, packageName);
        }catch (Exception e) {
            e.printStackTrace();
            alertMarketNotInstall(context);
        }
    }

}
