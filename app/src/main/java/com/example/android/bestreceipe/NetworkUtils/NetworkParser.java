package com.example.android.bestreceipe.NetworkUtils;

/**
 * Created by sunilkumar on 28/12/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkParser {

    private static final String LOG_TAG = NetworkParser.class.getSimpleName();
    private static final String MAIN_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static JSONArray getRecipeData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(MAIN_URL).build();
        try {
            Response response = client.newCall(request).execute();
            return new JSONArray(response.body().string());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "EXCEPTION IS " + e);
        }
        return null;
    }
}
