package sample.tazake.chrome_custom_tabs.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import sample.tazake.chrome_custom_tabs.R;
import sample.tazake.chrome_custom_tabs.entity.SampleLib;


/**
 * Created by tazawakenji on 15/11/22.
 */
public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<SampleLib> mSampleList = Arrays.asList(SampleLib.values());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.main_list);
        SampleAdapter sampleAdapter = new SampleAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mSampleList);
        mListView.setAdapter(sampleAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SampleLib sampleLib = (SampleLib) parent.getItemAtPosition(position);
                sampleLib.transition(MainActivity.this);
            }
        });
    }

    private class SampleAdapter extends ArrayAdapter<SampleLib> {

        private LayoutInflater mInflater;

        public SampleAdapter(Context context, int resource, List<SampleLib> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            View view = convertView;

            if (view == null) {
                view = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            SampleLib sampleLib = getItem(position);
            viewHolder.text.setText(sampleLib.getName(getContext()));

            return view;
        }
    }

    static class ViewHolder {

        TextView text;

        public ViewHolder(View view) {
            text = (TextView) view.findViewById(android.R.id.text1);
        }
    }
}
