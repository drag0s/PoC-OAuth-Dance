package com.dragosfotescu.myraco.OAuth;

/**
 * Created by dragos on 9/06/15.
 */
public class Constants {
    public static final String CONSUMER_KEY = "1ef17b5a-dcc1-44a5-91e1-083640928c3e";
    public static final String CONSUMER_SECRET= "c751ab3a-bc41-4f19-9e45-f9ca7305b7ab";

    public static final String REQUEST_URL = "https://raco.fib.upc.edu/oauth/request_token";
    public static final String ACCESS_URL = "https://raco.fib.upc.edu/oauth/access_token";
    public static final String AUTHORIZE_URL = "https://raco.fib.upc.edu/oauth/protected/authorize";

    public static final String	OAUTH_CALLBACK_SCHEME	= "raco";
    public static final String	OAUTH_CALLBACK_HOST		= "raco";
    public static final String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

}
