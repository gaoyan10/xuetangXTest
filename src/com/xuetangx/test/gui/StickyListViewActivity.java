package com.xuetangx.test.gui;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.ContentValues;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xuetangx.test.R;
import com.xuetangx.test.ini.AllCourseAnalyzer;
import com.xuetangx.test.ini.ResponseMessage;
import com.xuetangx.test.ui.PinnedHeaderExpandableListView;
import com.xuetangx.test.ui.PinnedHeaderExpandableListView.OnHeaderUpdateListener;
import com.xuetangx.test.ui.StickyLayout;
import com.xuetangx.test.ui.StickyLayout.OnGiveUpTouchEventListener;

public class StickyListViewActivity extends ActionBarActivity implements
		ExpandableListView.OnChildClickListener,
		ExpandableListView.OnGroupClickListener, OnHeaderUpdateListener,
		OnGiveUpTouchEventListener {
	private StickyLayout layout;
	private PinnedHeaderExpandableListView listView;
	private ImageView header;
	private BaseExpandableListAdapter adapter;
	private List<String> group;
	private List<List<String>> child;
	private DisplayImageOptions options, smallOptions;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private String topImage;
	private Handler httpHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				adapter
						.notifyDataSetChanged();
				/*if (topImage != null) {
					imageLoader.displayImage(
							topImage, header,
							options);
				}*/
				for (int i = 0; i < group.size(); i ++) {
					listView.expandGroup(i);
				}
			} else {
				Toast.makeText(StickyListViewActivity.this, msg.what + "error",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stickylistview);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loadingcourse)
				.showImageForEmptyUri(R.drawable.loadingcourse)
				.showImageOnFail(R.drawable.loadingcourse).cacheInMemory()
				.cacheOnDisc().build();
		smallOptions = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_avatar_loading)
				.showImageForEmptyUri(R.drawable.ic_avatar_loading)
				.showImageOnFail(R.drawable.ic_avatar_loading).cacheInMemory()
				.cacheOnDisc().build();
	
		initData();
		onCreateView();
	}

	public void initData() {
		group = new ArrayList<String>();
		child = new ArrayList<List<String>>();
	}

	public void onCreateView() {
		layout = (StickyLayout) findViewById(R.id.sticky_layout);
		listView = (PinnedHeaderExpandableListView) findViewById(R.id.expandablelist);
		header = (ImageView) findViewById(R.id.header_image);
		adapter = new BaseExpandableListAdapter() {

			@Override
			public void registerDataSetObserver(DataSetObserver observer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void unregisterDataSetObserver(DataSetObserver observer) {
				// TODO Auto-generated method stub

			}

			@Override
			public int getGroupCount() {
				// TODO Auto-generated method stub
				return group.size();
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				return child.get(groupPosition).size();
			}

			@Override
			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return group.get(groupPosition);
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return child.get(groupPosition).get(childPosition);
			}

			@Override
			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return groupPosition;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return childPosition;
			}

			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView tx = null;
				if (convertView == null) {
					convertView = LayoutInflater.from(
							StickyListViewActivity.this).inflate(
							R.layout.sticky_group, null);
					tx = (TextView) convertView
							.findViewById(R.id.sticky_group_text);
					convertView.setTag(tx);
				} else {
					tx = (TextView) convertView.getTag();
				}
				tx.setText(group.get(groupPosition));
				return convertView;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				ImageView image = null;
				if (convertView == null) {
					convertView = LayoutInflater.from(
							StickyListViewActivity.this).inflate(
							R.layout.sticky_child, null);
					image = (ImageView) convertView
							.findViewById(R.id.sticky_child_image);
					convertView.setTag(image);
				} else {
					image = (ImageView) convertView.getTag();
				}
				imageLoader.displayImage(
						child.get(groupPosition).get(childPosition), image,
						options);
				return null;
			}

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean areAllItemsEnabled() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onGroupExpanded(int groupPosition) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGroupCollapsed(int groupPosition) {
				// TODO Auto-generated method stub

			}

			@Override
			public long getCombinedChildId(long groupId, long childId) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getCombinedGroupId(long groupId) {
				// TODO Auto-generated method stub
				return 0;
			}

		};
		listView.setAdapter(adapter);
		 listView.setOnHeaderUpdateListener(this);
	     listView.setOnChildClickListener(this);
	     listView.setOnGroupClickListener(this);
	     layout.setOnGiveUpTouchEventListener(this);
	     createTestData();

	}
	public void createTestData() {
		String[] kinds = this.getResources().getStringArray(
				R.array.category_name);
		group.clear();
		child.clear();
		for (int i = 0; i < kinds.length; i++) {
			group.add(kinds[i]);
			child.add(new ArrayList<String>());
		}
		for (int i = 0; i < kinds.length; i ++) {
			ArrayList<String> childOne = (ArrayList<String>)child.get(i);
			for (int j = 0; j < 10; j ++) {
				childOne.add("assets://loadingcourse.png");
			}
		}
	}
	public void getData() {
		new Thread() {
			@Override
			public void run() {
				AllCourseAnalyzer analyzer = new AllCourseAnalyzer(
						StickyListViewActivity.this, "", true, true, false);
				ResponseMessage msg = analyzer.connect();
				Object obj = analyzer.analyseJson(msg.message, msg.code);
				if (obj instanceof List) {
					List<ContentValues> courses = (List<ContentValues>) obj;
					getChild(courses);
					httpHandler.sendEmptyMessage(0);
				} else {
					httpHandler.sendEmptyMessage(-1);
				}
			}
		}.start();
	}

	public void getChild(List<ContentValues> data) {
		String[] kinds = this.getResources().getStringArray(
				R.array.category_name);
		group.clear();
		child.clear();
		for (int i = 0; i < kinds.length; i++) {
			group.add(kinds[i]);
			child.add(new ArrayList<String>());
		}
		for (int i = 0; i < data.size(); i++) {
			ContentValues value = data.get(i);
			String url = value.getAsString("course_image_url");
			if (topImage == null) {
				topImage = url;
			}
			String json = value.getAsString("category");
			try {
				JSONArray obj = new JSONArray(json);
				for (int j = 0; j < obj.length(); j++) {
					int category = obj.getJSONObject(j).getInt("id");
					if (category >= 0 && category < kinds.length) {
						child.get(category).add(url);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	@Override
	public View getPinnedHeader() {
		// TODO Auto-generated method stub
		View headerView = (ViewGroup) getLayoutInflater().inflate(
				R.layout.sticky_group, null);
		headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		return headerView;
	}

	@Override
	public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
		if (firstVisibleGroupPos >= 0 && firstVisibleGroupPos < group.size()) {
			String firstVisibleGroup = (String) adapter
					.getGroup(firstVisibleGroupPos);
			TextView textView = (TextView) headerView.findViewById(R.id.sticky_group_text);
			textView.setText(firstVisibleGroup);
		}
		
	}

	@Override
	public boolean giveUpTouchEvent(MotionEvent event) {
		if (listView.getFirstVisiblePosition() == 0) {
			View view = listView.getChildAt(0);
			if (view != null && view.getTop() >= 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		return false;
	}
}
