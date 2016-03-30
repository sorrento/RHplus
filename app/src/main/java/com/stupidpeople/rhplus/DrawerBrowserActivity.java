package com.stupidpeople.rhplus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Milenko on 30/03/2016.
 */
public class DrawerBrowserActivity extends BaseActivity {
    private String url = "https://rhplus-1251.appspot.com/test_course/";
    private WebView wb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_browser);
        Initalize();

        //Load url
        //TODO get the browser and load the url
        //TODO implement back?
        try {
            wb = (WebView) this.findViewById(R.id.webView); //TODO set a progress bar for loading
//        wb.setWebChromeClient(new WebChromeClient()); //TODO fetching or cache
//        wb.setWebViewClient(new WebViewClient());
            wb.getSettings().setJavaScriptEnabled(true);
            wb.setWebViewClient(new Callback());// THis avoid opening browser when url changes
            wb.loadUrl(url);
        } catch (Exception e) {
            Log.d("MY", e.getMessage());
        }
    }

    private class Callback extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }

    }
}
