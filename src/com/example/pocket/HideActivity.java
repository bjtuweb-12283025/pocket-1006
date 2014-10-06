package com.example.pocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.pocket.R.color;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class HideActivity extends Activity {
	public static final int FILTER_ALL_APP = 0; // 所有应用程序
	private ListView listview_hide = null;
	private PackageManager pm;
	private int filter = FILTER_ALL_APP;
	private List<AppInfo> mlistAppInfo;
	private HideApplicationInfoAdapter hideAppAdapter = null;
	private List<String> listBoolean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hide);

		listview_hide = (ListView) findViewById(R.id.listView_hide);
		if (getIntent() != null) {
			filter = getIntent().getIntExtra("filter", 0);
		}
		mlistAppInfo = queryFilterAppInfo(filter); // 查询所有应用程序信息
		// 构建适配器，并且注册到listView
		listBoolean = new ArrayList<String>();
		listBoolean = getListBoolean();
		hideAppAdapter = new HideApplicationInfoAdapter(this, mlistAppInfo,listBoolean);
		
		listview_hide.setAdapter(hideAppAdapter);
		listview_hide.setOnItemClickListener(itemListener);

	}
	private List<String> getListBoolean() {
		List<String> listBoolean = new ArrayList<String>();
		for (int i=0;i<mlistAppInfo.size();i++)
			listBoolean.add("false");
		return listBoolean;
	}
	private List<AppInfo> queryFilterAppInfo(int filter) {
		pm = this.getPackageManager();
		// 查询所有已经安装的应用程序
		List<ApplicationInfo> listAppcations = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		Collections.sort(listAppcations,
				new ApplicationInfo.DisplayNameComparator(pm));// 排序
		List<AppInfo> appInfos = new ArrayList<AppInfo>(); // 保存过滤查到的AppInfo
		appInfos.clear();
		for (ApplicationInfo app : listAppcations) {
			appInfos.add(getAppInfo(app));
		}
		return appInfos;
	}

	// 构造一个AppInfo对象 ，并赋值
	private AppInfo getAppInfo(ApplicationInfo app) {
		AppInfo appInfo = new AppInfo();
		appInfo.setAppLabel((String) app.loadLabel(pm));
		appInfo.setAppIcon(app.loadIcon(pm));
		appInfo.setPkgName(app.packageName);
		return appInfo;
	}

	OnItemClickListener itemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> l, View v, int position, long id) {
//			LinearLayout view1 = (LinearLayout) l.getChildAt(position);
//			RelativeLayout view = (RelativeLayout) view1.getChildAt(1);  //滑动到底点击，程序崩溃之后显示这一行有错。。不知道为什么
//			CheckBox checkBox = (CheckBox) view.getChildAt(4);
//			checkBox.toggle();
//			
//			System.out.println("clickPosition    " + position);

			Boolean checked = HideApplicationInfoAdapter.getIsSelected().get(position);
			HideApplicationInfoAdapter.getIsSelected().put(position,
					!checked);

			if (listBoolean.get(position).equals("false")){
				listBoolean.set(position, "true");
			}else{
				listBoolean.set(position, "false");
			}
			hideAppAdapter.notifyDataSetChanged();
		}
		
	};

}
