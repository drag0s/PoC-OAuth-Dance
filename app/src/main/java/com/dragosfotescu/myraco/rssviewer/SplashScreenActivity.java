package com.dragosfotescu.myraco.rssviewer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragosfotescu.myraco.NotLogged;
import com.dragosfotescu.myraco.R;
import com.dragosfotescu.myraco.rssviewer.xml.RSSFeed;
import com.dragosfotescu.myraco.rssviewer.xml.RSSFeedXMLParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SplashScreenActivity extends Fragment {

    private final String RSS_FEED_URL = "http://www.fib.upc.edu/fib/rss.rss";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_splash_screen, container, false);

        ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() == null
                && !conMgr.getActiveNetworkInfo().isConnected()
                && !conMgr.getActiveNetworkInfo().isAvailable()) {
// No connectivity - Show alert
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
            builder.setMessage(
                    "Unable to reach server, \nPlease check your connectivity.")
                    .setTitle("TD RSS Reader")
                    .setCancelable(false)
                    .setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int id) {
//                                    finish();
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();

        } else {
            new XMLFeedLoadingTask().execute();
        }
        return rootView;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_splash_screen);
//
//    }




    private class XMLFeedLoadingTask extends AsyncTask<Void, Void, RSSFeed> {

        @Override
        protected RSSFeed doInBackground(Void... params) {
            try {
                return loadXmlFromNetwork(RSS_FEED_URL);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        private RSSFeed loadXmlFromNetwork(String urlString) throws IOException, XmlPullParserException {
            RSSFeedXMLParser rssXMLParser = new RSSFeedXMLParser();
            InputStream stream = null;
            RSSFeed feed;
            try {
                stream = downloadUrl(RSS_FEED_URL);
                feed = rssXMLParser.parse(stream);
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
            return feed;
        }


        private InputStream downloadUrl(String rss_feed_url) throws IOException {
            URL url = new URL(rss_feed_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            return conn.getInputStream();
        }

        @Override
        protected void onPostExecute(RSSFeed result) {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("feed", result);
//            Intent intent = new Intent(getActivity().getApplicationContext(), ListActivity.class);
//            intent.putExtras(bundle);
//            startActivity(intent);

            Log.i("SPLASH", "Saliendo");
            Fragment newFragment = new ListActivity();

            Bundle bundle = new Bundle();
            bundle.putSerializable("feed", result);
            newFragment.setArguments(bundle);

            // consider using Java coding conventions (upper first char class names!!!)
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack



            transaction.replace(NotLogged.mViewPager.getLayoutDirection(), newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
//            finish();
        }
    }
}
