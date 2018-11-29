package lib.codegames.ad;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lib.codegames.debug.LogCG;

final class BannerAdCGManager {

    private static boolean bannerLoaded = false;
    private static List<BannerAdCGModel> bannerList;

    private static GetBannerTask bannerTask;
    private static BannerAdCGClient bannerClient;

    public static void getBanners() {

        if(bannerTask == null) {
            GetBannerTask getBannerTask = new GetBannerTask();
            getBannerTask.execute("https://codegames.ir/app/ad.codegames/banner.json");
        }

    }

    private static class GetBannerTask extends AsyncTask<String, List<BannerAdCGModel>, List<BannerAdCGModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bannerTask = this;
        }

        @Override
        protected List<BannerAdCGModel> doInBackground(String... strings) {

            if(bannerLoaded)
                return bannerList;

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

                    return parseJsonBanners(response.toString());
                }else { // On Failure
                    LogCG.d("Banner Ad CG Error Connect To Url: " + responseCode + " Code");
                    return null;
                }

            } catch (MalformedURLException e) { // On Failure
                e.printStackTrace();
                LogCG.d("Banner Ad CG Error Malformed URL Exception");
                return null;
            } catch (IOException e) { // On Failure
                e.printStackTrace();
                LogCG.d("Banner Ad CG Error IO Exception");
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<BannerAdCGModel> bannerList) {
            super.onPostExecute(bannerList);

            if(bannerList != null && !bannerList.isEmpty()) {
                BannerAdCGManager.bannerList = bannerList;
                bannerLoaded = true;
            }

            bannerTask = null;

            showRandomAd();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            bannerTask = null;
        }

        private List<BannerAdCGModel> parseJsonBanners(@Nullable String json) {

            try {

                List<BannerAdCGModel> bannerList = new ArrayList<>();
                JSONArray jsonBannerArray = new JSONArray(json);

                for(int i=0; i<jsonBannerArray.length(); i++) {
                    BannerAdCGModel banner = new BannerAdCGModel();
                    JSONObject jsonBannerObject = jsonBannerArray.getJSONObject(i);

                    banner.setId(jsonBannerObject.getInt("id"));
                    banner.setImage(jsonBannerObject.getString("image"));
                    banner.setLink(jsonBannerObject.getString("link"));

                    bannerList.add(banner);
                }

                return bannerList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

    }

    public static void showRandomAd() {

        if(bannerList == null || bannerList.isEmpty()) return;

        Random random = new Random();
        int num = random.nextInt(bannerList.size());

        BannerAdCGModel banner = bannerList.get(num);

        if(bannerClient != null) {
            bannerClient.showAd(banner);
        }

    }

    public static BannerAdCGClient getBannerClient() {
        return bannerClient;
    }

    public static void setBannerClient(BannerAdCGClient bannerClient) {
        BannerAdCGManager.bannerClient = bannerClient;
    }

    public static boolean isBannerLoaded() {
        return bannerLoaded;
    }

}
