package com.example.pocket;

import java.util.ArrayList;
import java.util.List;

import com.example.pocket.db.dao.AppLockDao;
import com.example.pocket.domain.AppInfo;
import com.example.pocket.engine.AppInfoProvider;
import com.example.pocket.service.WatchDogService;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class EncryptActivity extends Activity {
	private ImageButton setpassword;
	public static EncryptActivity a = null;
	public static boolean IsAbnormalExit = false;
	private List<String> listBoolean;
	private List<String> islockBoolean;
	private ListView lv;
	private List<AppInfo> appInfos;
	private AppInfoProvider provider;
	private MyAppLockAdapter adapter;
	private AppLockDao dao;
	private LinearLayout ll_app_manager_loading;
	private SharedPreferences sp;
	private TextView set_password;

	private List<String> lockappinfos; // 用来存放加载过的 加锁或未加锁的程序，来提高LIstView效率
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			ll_app_manager_loading.setVisibility(View.INVISIBLE);
			adapter = new MyAppLockAdapter();
			listBoolean = getListBoolean();
			islockBoolean = getListBoolean();
			lv.setAdapter(adapter);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encrypt);
		a = this;
		IsAbnormalExit = true;
		sp = this.getSharedPreferences("AppLockSetting", Context.MODE_PRIVATE);
		provider = new AppInfoProvider(this);
		dao = new AppLockDao(this);
		lockappinfos = dao.getPackName();
		lv = (ListView) findViewById(R.id.lv_app_lock);
		ll_app_manager_loading = (LinearLayout) findViewById(R.id.ll_app_manager_loading);
		listBoolean = new ArrayList<String>();
		islockBoolean = new ArrayList<String>();
		Intent startServiceIntent = new Intent(this, WatchDogService.class);
		startService(startServiceIntent);

		initUI();
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 动画效果
				TranslateAnimation ta = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0.0f,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.0f,
						Animation.RELATIVE_TO_SELF, 0.0f);
				ta.setDuration(500);
				view.setAnimation(ta);
//				ImageView iv = (ImageView) view
//						.findViewById(R.id.iv_app_lock_status);

				// 传递当前要锁定层序的包名
//				AppInfo info = (AppInfo) lv.getItemAtPosition(position);
//				String packName = info.getPackname();
//				if (dao.find(packName)) { // 如果在数据库内找到改包名 已经加锁
//					// dao.delete(packName);
//					// 通过内容提供者删除
//					getContentResolver()
//							.delete(Uri
//									.parse("content://cn.test.applockprovider/delete"),
//									null, new String[] { packName });
//					lockappinfos.remove(packName);
//					iv.setImageResource(R.drawable.icons_unlock);
//				} else {
//					// dao.add(packName);
//					Editor ed = sp.edit();
//					ed.putString("password", "000000");
//					ed.commit();
//					ContentValues values = new ContentValues();
//					values.put("packname", packName);
//					getContentResolver()
//							.insert(Uri
//									.parse("content://cn.test.applockprovider/insert"),
//									values);
//					lockappinfos.add(packName);
//					iv.setImageResource(R.drawable.icons_02);
//				}
				
				if (listBoolean.get(position).equals("false")){
					listBoolean.set(position, "true");
				}else{
					listBoolean.set(position, "false");
				}
				
				adapter.notifyDataSetChanged();

			}
		});

		setpassword = (ImageButton) findViewById(R.id.btn_encrypt);
		setpassword.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				
				
				Intent passIntent = new Intent(EncryptActivity.this,
						setPassWordActicity.class);
				Bundle bundle = new Bundle();
				Bundle lockBundle = new Bundle();
				bundle.putStringArrayList("isSelected", (ArrayList<String>) listBoolean);
				lockBundle.putStringArrayList("isLocked", (ArrayList<String>) islockBoolean);
				passIntent.putExtras(lockBundle);
				passIntent.putExtras(bundle);
				
				startActivityForResult(passIntent, 1);
			}
		});
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("result", "--------------------");
		// TODO Auto-generated method stub
		RelativeLayout Layout = (RelativeLayout) findViewById(R.id.lock_layout);
		View view = LayoutInflater.from(EncryptActivity.this).inflate(R.layout.lock_app_item, null);
		ImageView iv = (ImageView) view.findViewById(R.id.iv_app_lock_status);
		super.onActivityResult(requestCode, resultCode, data);
		for(int i = 0;i<appInfos.size();i++)
		{
			if(listBoolean.get(i).equals("true")){
				Layout.setBackgroundColor(Color.TRANSPARENT);
				iv.setImageResource(R.drawable.icons_02);
				listBoolean.set(i, "false");
				islockBoolean.set(i, "true");
			}
			adapter.notifyDataSetChanged();
		}
	}
	
	

	private List<String> getListBoolean() {
		List<String> listBoolean = new ArrayList<String>();
		for (int i=0;i<appInfos.size();i++)
			listBoolean.add("false");
		return listBoolean;
	}
	private void initUI() {
		ll_app_manager_loading.setVisibility(View.VISIBLE);
		new Thread() {
			@Override
			public void run() {
				appInfos = provider.getAllApps();
				handler.sendEmptyMessage(0);
			}

		}.start();
	}

	/**
	 * 适配器
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAppLockAdapter extends BaseAdapter {

//		List<String> listBoolean;
		public int getCount() {
			// TODO Auto-generated method stub
			return appInfos.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return appInfos.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.lock_app_item, null);
			} else {
				view = convertView;
			}
			// 更改View对象的状态
			AppInfo info = appInfos.get(position);
			ImageView iv = (ImageView) view.findViewById(R.id.iv_app_icon);
			TextView tv = (TextView) view.findViewById(R.id.tv_app_name);
			ImageView iv_lock_statue = (ImageView) view
					.findViewById(R.id.iv_app_lock_status);

			/*
			 * if(dao.find(info.getPackname())){
			 * iv_lock_statue.setImageResource(R.drawable.lock); }else{
			 * iv_lock_statue.setImageResource(R.drawable.unlock); }
			 */
			RelativeLayout itemLayout = (RelativeLayout) view.findViewById(R.id.lock_layout);
			if (listBoolean.get(position).equals("true")){
				itemLayout.setBackgroundColor(Color.RED);
				Log.d("adapter", "red");
			}else{
				itemLayout.setBackgroundColor(Color.TRANSPARENT);
				Log.d("adapter", "trans");
			}
			
			
			if (islockBoolean.get(position).equals("true")){
				iv_lock_statue.setImageResource(R.drawable.icons_02);
				Log.d("ivlock", "locked");
			}else{
				iv_lock_statue.setImageResource(R.drawable.icons_unlock);
			}
			
//			if (lockappinfos.contains(info.getPackname())) {
//				iv_lock_statue.setImageResource(R.drawable.icons_02);
//			} else {
//				iv_lock_statue.setImageResource(R.drawable.icons_unlock);
//			}
//
			iv.setImageDrawable(info.getIcon());
			tv.setText(info.getAppname());
			return view;
		}
	}

}
