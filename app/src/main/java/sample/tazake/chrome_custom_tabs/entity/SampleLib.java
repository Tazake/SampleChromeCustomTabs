package sample.tazake.chrome_custom_tabs.entity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import sample.tazake.chrome_custom_tabs.R;
import sample.tazake.chrome_custom_tabs.activity.CustomTabsSupportActivity;
import sample.tazake.chrome_custom_tabs.activity.CustomTabsSupportPreFetchActivity;

/**
 * Created by tazawakenji on 15/11/22.
 */
public enum SampleLib {

    CustomTabsSupportLibrary(R.string.title_custom_tabs_support_library, CustomTabsSupportActivity.class),
    CustomTabsSupportPreFetchLibrary(R.string.title_custom_tabs_support_library_prefetch, CustomTabsSupportPreFetchActivity.class);

    private int mNameResourceId;

    private Class<?> mNextActivity;

    SampleLib(int nameResourceId, Class<?> nextActivity) {
        mNameResourceId = nameResourceId;
        mNextActivity = nextActivity;
    }

    private int getNameResourceId() {
        return mNameResourceId;
    }

    private Intent createIntent(Activity activity) {
        return new Intent(activity, mNextActivity);
    }

    public String getName(Context context) {
        return context.getResources().getString(getNameResourceId());
    }

    public void transition(Activity activity) {
        activity.startActivity(createIntent(activity));
    }
}
