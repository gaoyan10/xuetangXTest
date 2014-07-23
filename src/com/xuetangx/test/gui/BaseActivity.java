package com.xuetangx.test.gui;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.xuetangx.test.R;
/**
 * menu class is used as the BASE Activity.
 * @author Yan
 *
 */
public class BaseActivity extends ActionBarActivity {
	private SlidingMenu menu;
	private TextView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		menu = new SlidingMenu(this);
		webView = (TextView) findViewById(R.id.activity_base_text);
		//webView.loadUrl("http://www.baidu.com");
		init(this);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("test");
	}
	public void getBundleData() {
		Bundle extras = this.getIntent().getExtras();
		if (extras == null) {
			return;
		}else {
			
		}
	}
	public void init(Activity context) {
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setBehindWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(context, SlidingMenu.SLIDING_WINDOW);
		CanvasTransformer canvas = new CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				canvas.scale(percentOpen, 1, 0, 0);
			}
		};
		menu.setMenu(R.layout.menu_layout);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			menu.toggle();
			Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
