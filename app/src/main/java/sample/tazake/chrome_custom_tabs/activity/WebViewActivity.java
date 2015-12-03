package sample.tazake.chrome_custom_tabs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import sample.tazake.chrome_custom_tabs.R;


/**
 * Created by tazawakenji on 15/11/27.
 */
public class WebViewActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "extra.url";

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (getIntent().hasExtra(EXTRA_URL)) {
            mUrl = intent.getStringExtra(EXTRA_URL);
        } else {
            mUrl = "https://www.google.co.jp/";
        }

        WebView webView = (WebView) findViewById(R.id.web);
        webView.loadUrl(mUrl);
    }

}
