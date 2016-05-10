package com.dragosfotescu.myraco.rssviewer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dragosfotescu.myraco.R;


public class ViewPostActivity extends Activity {

    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        setContentView(R.layout.activity_view_post);

        progress = (ProgressBar) findViewById(R.id.webviewProgressBar);
        progress.setVisibility(View.GONE);

        Bundle bundle = this.getIntent().getExtras();
        String linkToLoad = bundle.getString("link");

        WebView mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.GONE);
                ViewPostActivity.this.progress.setProgress(100);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progress.setVisibility(View.VISIBLE);
                ViewPostActivity.this.progress.setProgress(0);
                super.onPageStarted(view, url, favicon);
            }
        });
        mWebView.loadUrl(linkToLoad);
    }
}
