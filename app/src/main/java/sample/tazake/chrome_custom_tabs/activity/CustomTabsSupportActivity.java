package sample.tazake.chrome_custom_tabs.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.chromium.customtabsclient.shared.CustomTabsHelper;

import sample.tazake.chrome_custom_tabs.R;


/**
 * Created by tazawakenji on 15/11/22.
 */
public class CustomTabsSupportActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String URL = "http://tech.recruit-mp.co.jp/";
    private static final Uri URI = Uri.parse(URL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_tabs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        findViewById(R.id.launch).setOnClickListener(this);
        findViewById(R.id.launch_custom_style).setOnClickListener(this);
    }

    private void launch() {
        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();

        String packageName = CustomTabsHelper.getPackageNameToUse(this);
        tabsIntent.intent.setPackage(packageName);
        tabsIntent.launchUrl(this, URI);
    }

    private void launchCustomStyle() {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        setupMenu(builder);
        setupActionButton(builder);

        CustomTabsIntent tabsIntent = builder.setShowTitle(true)
                .enableUrlBarHiding()
                .setToolbarColor(getResources().getColor(R.color.brand_sub))
                .setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back))
                .setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
                .setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .build();

        String packageName = CustomTabsHelper.getPackageNameToUse(this);
        tabsIntent.intent.setPackage(packageName);
        tabsIntent.launchUrl(this, URI);
    }


    private void setupMenu(CustomTabsIntent.Builder builder) {

        Intent menuIntent = new Intent();
        menuIntent.setAction(Intent.ACTION_SEND);
        menuIntent.setType("text/plain");
        menuIntent.putExtra(Intent.EXTRA_TEXT, URL);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, menuIntent, 0);
        builder.addMenuItem("Menu Share Sample ", pendingIntent);
        builder.addMenuItem("Menu Share Sample2 ", pendingIntent);
        builder.addMenuItem("Menu Share Sample3 ", pendingIntent);
        builder.addMenuItem("Menu Share Sample4 ", pendingIntent);
        builder.addMenuItem("Menu Share Sample5 ", pendingIntent);
        builder.addMenuItem("Menu Share Sample6 ", pendingIntent);
    }

    private void setupActionButton(CustomTabsIntent.Builder builder) {

        Intent actionIntent = new Intent(Intent.ACTION_SEND);
        actionIntent.setAction(Intent.ACTION_SEND);
        actionIntent.setType("text/plain");
        actionIntent.putExtra(Intent.EXTRA_TEXT, URL);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, actionIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_my_calendar);
        builder.setActionButton(icon, "Action Share Sample", pendingIntent);
        // builder.setActionButton(icon, "Action Share Sample", pendingIntent, true /* tintの指定も可能 */);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.launch:
                launch();
                break;

            case R.id.launch_custom_style:
                launchCustomStyle();
                break;
        }
    }
}
