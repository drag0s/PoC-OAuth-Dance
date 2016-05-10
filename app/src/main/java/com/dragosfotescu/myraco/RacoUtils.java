package com.dragosfotescu.myraco;

import android.content.SharedPreferences;
import android.util.Log;

import com.dragosfotescu.myraco.OAuth.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import oauth.signpost.OAuth;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

/**
 * Created by dragos on 10/06/15.
 */
public class RacoUtils {
    public static boolean isLoged(SharedPreferences prefs) {
        String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
        String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

        if (token != "" && secret != "") return true;
        else return false;
    }

    public static String getNom(SharedPreferences prefs) {
        String TAG = "getNom";
        String json = "";
        try {
            String url = "https://raco.fib.upc.edu/api-v1/info-personal.json";
            json = requestURL(prefs, url);
        } catch (Exception e) {
            Log.e(TAG, " " + e.getMessage());
        }
        String nom = "";
        try {
            JSONObject info = new JSONObject(json);
            nom = info.getString("nom") + " " + info.getString("cognoms");
        } catch (Exception e) {
            Log.e(TAG, " " + e.getMessage());
        }
        return nom;
    }

    public static String horari_setmanal(SharedPreferences prefs) {
        String TAG = "Horari setmanal";
        String json = "";
        try {
            String url = "https://raco.fib.upc.edu/api-v1/calendari-portada.ics";
            json = requestURL(prefs, url);
        } catch (Exception e) {
            Log.e(TAG, "Error horari setmanal", e);
        }
        Log.i(TAG, json);
        return json;
    }

    private static String requestURL(SharedPreferences prefs, String url) throws Exception {
        String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
        String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
        String TAG = "Request URL";
        StringBuilder responseBuilder = new StringBuilder();

        CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
        consumer.setTokenWithSecret(token, secret);
        DefaultHttpClient httpclient = new DefaultHttpClient();

        HttpGet request = new HttpGet(url);
        Log.i(TAG, "Requesting URL : " + url + " with token: " + token + " and secret: " + secret);
        consumer.sign(request);

        HttpResponse response = httpclient.execute(request);
        Log.i(TAG, "Statusline : " + response.getStatusLine());
        InputStream data = response.getEntity().getContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data));
        String responseLine;

        while ((responseLine = bufferedReader.readLine()) != null) {
            responseBuilder.append(responseLine);
        }
        Log.i(TAG, "Response : " + responseBuilder.toString());
        return responseBuilder.toString();
    }




}
