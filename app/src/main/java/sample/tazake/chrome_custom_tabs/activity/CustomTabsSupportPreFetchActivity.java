package sample.tazake.chrome_custom_tabs.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.chromium.customtabsclient.shared.CustomTabsHelper;

import sample.tazake.chrome_custom_tabs.R;


/**
 * Created by tazawakenji on 15/11/22.
 */
public class CustomTabsSupportPreFetchActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String URL = "http://tech.recruit-mp.co.jp/";
    private static final Uri URI = Uri.parse(URL);

    private CustomTabsServiceConnection mCustomTabsServiceConnection;
    private CustomTabsClient mCustomTabsClient;
    private CustomTabsSession mCustomTabsSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_tabs_prefetch);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        findViewById(R.id.launch).setOnClickListener(this);
        findViewById(R.id.may_launch).setOnClickListener(this);
        findViewById(R.id.launch_web_view).setOnClickListener(this);
        findViewById(R.id.launch_browser).setOnClickListener(this);
    }

    private void mayLaunch() {

        //  NavigationEventが必要ない場合は引数にnullを指定
        //  mCustomTabsSession = mCustomTabsClient.newSession(null);
        mCustomTabsSession = mCustomTabsClient.newSession(new CustomTabsCallback() {
            @Override
            public void onNavigationEvent(final int navigationEvent, Bundle extras) {
                super.onNavigationEvent(navigationEvent, extras);

                // CustomTabsCallbackはCustomTab表示時のナビゲーションイベントを取得できる
                //   NAVIGATION_STARTED = 1;  読み込み開始したとき
                //   NAVIGATION_FINISHED = 2; 読み込み終了したとき
                //   NAVIGATION_FAILED = 3;
                //   NAVIGATION_ABORTED = 4;
                //   TAB_SHOWN = 5;  Tabが表示されたとき
                //   TAB_HIDDEN = 6; Tab閉じたとき

                switch (navigationEvent) {
                    case NAVIGATION_STARTED:
                        break;
                    case NAVIGATION_FINISHED:
                        break;
                    case NAVIGATION_FAILED:
                        break;
                    case NAVIGATION_ABORTED:
                        break;
                    case TAB_SHOWN:
                        break;
                    case TAB_HIDDEN:
                        break;
                }
            }
        });
        mCustomTabsSession.mayLaunchUrl(URI, null, null);
    }

    private void launch() {

        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(true)
                .enableUrlBarHiding()
                .setToolbarColor(getResources().getColor(R.color.brand_sub))
                .setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back))
                .build();

        String packageName = CustomTabsHelper.getPackageNameToUse(this);
        tabsIntent.intent.setPackage(packageName);
        tabsIntent.launchUrl(this, URI);
    }


    @Override
    protected void onStart() {
        super.onStart();

        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                Toast.makeText(CustomTabsSupportPreFetchActivity.this, "onCustomTabsServiceConnected", Toast.LENGTH_SHORT).show();
                // Chrome Serviceと接続したらChromeをWarm upする
                mCustomTabsClient = customTabsClient;
                mCustomTabsClient.warmup(0L);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Toast.makeText(CustomTabsSupportPreFetchActivity.this, "onServiceDisconnected", Toast.LENGTH_SHORT).show();
            }
        };
        String packageName = CustomTabsHelper.getPackageNameToUse(this);
        // Chrome Serviceと接続
        CustomTabsClient.bindCustomTabsService(this, packageName, mCustomTabsServiceConnection);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mCustomTabsServiceConnection);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.may_launch:
                mayLaunch();
                break;

            case R.id.launch:
                launch();
                break;

            case R.id.launch_web_view:
                Intent intentWebView = new Intent(CustomTabsSupportPreFetchActivity.this, WebViewActivity.class);
                intentWebView.putExtra(WebViewActivity.EXTRA_URL, URL);
                startActivity(intentWebView);
                break;

            case R.id.launch_browser:
                Intent intentBrowser = new Intent(Intent.ACTION_VIEW, URI);
                startActivity(intentBrowser);
                break;
        }
    }
}
