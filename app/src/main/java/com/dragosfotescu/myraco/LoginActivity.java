package com.dragosfotescu.myraco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dragosfotescu.myraco.OAuth.Constants;
import com.dragosfotescu.myraco.OAuth.PrepareRequestTokenActivity;

import oauth.signpost.OAuth;


public class LoginActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class LoginActivityFragment extends Fragment {

        static SharedPreferences prefs;
        private final Handler mRacoHandler = new Handler();


        private void clearCredentials() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            final SharedPreferences.Editor edit = prefs.edit();
            edit.remove(OAuth.OAUTH_TOKEN);
            edit.remove(OAuth.OAUTH_TOKEN_SECRET);
            edit.commit();
        }



        public LoginActivityFragment() {

        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (RacoUtils.isLoged(prefs)) {
                startActivity(new Intent(getActivity().getApplicationContext(),MainActivity.class));
            }
            View v = inflater.inflate(R.layout.fragment_login, container, false);

            Button connect = (Button) v.findViewById(R.id.connect);
            connect.setOnClickListener(connect_clicked);

            Button connectlater = (Button) v.findViewById(R.id.connectlater);
            connectlater.setOnClickListener(connectlater_clicked);


            return v;
        }




        public void onResume() {
            super.onResume();
            Uri uri = this.getActivity().getIntent().getData();
            //recuperaTokens(); // Este es el caso en que volvemos con token. Si no, no hacemos nada
            if (uri != null && uri.toString().startsWith(Constants.OAUTH_CALLBACK_URL)) {
                Log.i("DemanaAcces", "Request Access");

//                new DemanaAccessTokenAsync().execute();
            }
        }


        View.OnClickListener connect_clicked = new View.OnClickListener() {
            public void onClick(View v) {
                Toast t = Toast.makeText(v.getContext(), "Conectando", Toast.LENGTH_SHORT);
                t.show();
                if (!RacoUtils.isLoged(prefs)) {
                    Intent i = new Intent(getActivity().getApplicationContext(), PrepareRequestTokenActivity.class);
                    startActivity(i);
                }
            }
        };



        View.OnClickListener connectlater_clicked = new View.OnClickListener() {
            public void onClick(View v) {
                Toast t = Toast.makeText(v.getContext(), "Nos conectamos despues ", Toast.LENGTH_SHORT);
                t.show();
                Intent intent = new Intent(getActivity(), NotLogged.class);
                startActivity(intent);
            }
        };




    }
}

