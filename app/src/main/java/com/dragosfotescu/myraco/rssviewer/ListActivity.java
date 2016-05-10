package com.dragosfotescu.myraco.rssviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dragosfotescu.myraco.R;
import com.dragosfotescu.myraco.lazylist.ImageLoader;
import com.dragosfotescu.myraco.rssviewer.xml.RSSFeed;


public class ListActivity extends Fragment {
    RSSFeed feed;
    ListView lv;
    CustomListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_list, container, false);
        // Get feed form the file
//        feed = (RSSFeed) getActivity().getIntent().getExtras().get("feed");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            feed = (RSSFeed) bundle.get("feed");
        }

        LinearLayout Ll    = (LinearLayout) inflater.inflate(R.layout.activity_list, container, false);
        // Initialize the variables:


        lv = (ListView) Ll.findViewById(R.id.listView);
        lv.setVerticalFadingEdgeEnabled(true);

        // Set an Adapter to the ListView
        adapter = new CustomListAdapter(this);
        lv.setAdapter(adapter);

        // Set on item click listener to the ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle postInfo = new Bundle();
                postInfo.putString("link", feed.getFeedList().get(arg2).getItemLink());
                Intent postviewIntent = new Intent(getActivity().getApplicationContext(), ViewPostActivity.class);
                postviewIntent.putExtras(postInfo);
                startActivity(postviewIntent);
            }
        });
        return rootView;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_list);
//
//        // Get feed form the file
//        feed = (RSSFeed) getIntent().getExtras().get("feed");
//
//        // Initialize the variables:
//        lv = (ListView) findViewById(R.id.listView);
//        lv.setVerticalFadingEdgeEnabled(true);
//
//        // Set an Adapter to the ListView
//        adapter = new CustomListAdapter(this);
//        lv.setAdapter(adapter);
//
//        // Set on item click listener to the ListView
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView arg0, View arg1, int arg2,
//                                    long arg3) {
//                Bundle postInfo = new Bundle();
//                postInfo.putString("link", feed.getFeedList().get(arg2).getItemLink());
//                Intent postviewIntent = new Intent(ListActivity.this, ViewPostActivity.class);
//                postviewIntent.putExtras(postInfo);
//                startActivity(postviewIntent);
//            }
//        });
//
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.imageLoader.clearCache();
        adapter.notifyDataSetChanged();
    }

    class CustomListAdapter extends BaseAdapter {

        public ImageLoader imageLoader;
        private LayoutInflater layoutInflater;

        public CustomListAdapter(ListActivity activity) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            imageLoader = new ImageLoader(getActivity().getApplicationContext());
        }

        @Override
        public int getCount() {
            // Set the total list item count
            return feed.getItemCount();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            // Inflate the item layout and set the views
            View listItem = convertView;
            if (listItem == null) {
                listItem = layoutInflater.inflate(R.layout.list_item, null);
            }

            // Initialize the views in the layout
            ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);
            TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
            TextView tvDesc = (TextView) listItem.findViewById(R.id.description);

            // Set the views in the layout
            imageLoader.DisplayImage(feed.getFeedList().get(pos).getItemImage(), iv);
            tvTitle.setText(feed.getFeedList().get(pos).getItemTitle());
            tvDesc.setText(feed.getFeedList().get(pos).getItemDescription());

            return listItem;
        }
    }
}
