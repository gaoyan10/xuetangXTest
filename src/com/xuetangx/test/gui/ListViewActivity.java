package com.xuetangx.test.gui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.xuetangx.test.R;

public class ListViewActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener{
	private ListView content;
	private String[] data;
	private ImageView header;
	private SwipeRefreshLayout swipe;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		initData();
		onCreateView();
	}
	public void onCreateView() {
		swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
		 swipe.setOnRefreshListener(this);  
         
	        // 顶部刷新的样式  
		 swipe.setColorScheme(android.R.color.holo_blue_bright, 
		            android.R.color.holo_green_light, 
		            android.R.color.holo_orange_light, 
		            android.R.color.holo_red_light);
		content = (ListView) findViewById(R.id.activity_listview);
		header = new ImageView(this);
		LayoutParams params = new ListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		header.setLayoutParams(params);
		header.setImageResource(R.drawable.ic_category_advanced);
		
		content.addHeaderView(header);
		try {
			content.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void initData() {
		data = new String[100];
		for (int i = 0 ; i < 100 ; i ++) {
			data[i] = new String("Hello world\nHello world\nHello world" + "\n" + Math.random());
		}
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {
	        @Override public void run() {
	        	swipe.setRefreshing(false);
	        }
	    }, 2000);
	}
}
