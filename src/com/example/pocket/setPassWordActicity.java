package com.example.pocket;

import java.util.List;

import com.example.pocket.domain.AppInfo;
import com.example.pocket.engine.AppInfoProvider;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IInterface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class setPassWordActicity extends Activity{

	private Button bt_normalt_dialog_ok,bt_normal_dialog_cancle;
	private EditText et_normal_entry_pwd;
	private SharedPreferences sp;
	private List<String> booleanList;
	private List<String> booleanislock;
	private AppInfoProvider provider;
	private List<AppInfo> appInfos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setpass);
		
		sp=getSharedPreferences("AppLockSetting",Context.MODE_PRIVATE);
		booleanList = getIntent().getExtras().getStringArrayList("isSelected");
		booleanislock = getIntent().getExtras().getStringArrayList("isLocked");
		provider = new AppInfoProvider(this);
		
		initUI();
		
		bt_normalt_dialog_ok=(Button) findViewById(R.id.bt_normalt_dialog_ok);
		bt_normal_dialog_cancle=(Button) findViewById(R.id.bt_normal_dialog_cancle);
		et_normal_entry_pwd=(EditText) findViewById(R.id.et_normal_entry_pwd);
		bt_normalt_dialog_ok.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String password=et_normal_entry_pwd.getText().toString().trim();
//				Editor ed = sp.edit();
//				ed.putString("password", password);
//				ed.commit();
				ImageView iv = (ImageView) findViewById(R.id.iv_app_lock_status);
				
				for (int i=0;i<appInfos.size();i++){
					if(booleanList.get(i).equals("true")){
						AppInfo info = appInfos.get(i);
						String packName = info.getPackname();
//						if (dao.find(packName)) { // 如果在数据库内找到改包名 已经加锁
//							// dao.delete(packName);
//							// 通过内容提供者删除
//							getContentResolver()
//									.delete(Uri
//											.parse("content://cn.test.applockprovider/delete"),
//											null, new String[] { packName });
//							lockappinfos.remove(packName);
//							iv.setImageResource(R.drawable.icons_unlock);
//						} else {
							// dao.add(packName);
							Editor ed = sp.edit();
							ed.putString("password", password);
							ed.commit();
							ContentValues values = new ContentValues();
							values.put("packname", packName);
							getContentResolver()
									.insert(Uri
											.parse("content://cn.test.applockprovider/insert"),
											values);
//							lockappinfos.add(packName);
//							iv.setImageResource(R.drawable.icons_02);
//						}
					}
					
				}
				setResult(1);
				setPassWordActicity.this.finish();
				
//				Log.d("AppInfo", appInfos.toString());
			}
		});
		
		bt_normal_dialog_cancle.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
					finish();
			}
		});
	}
	
	
	private void initUI() {
		// TODO Auto-generated method stub
		new Thread() {
			@Override
			public void run() {
				appInfos = provider.getAllApps();
			}

		}.start();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		setResult(1);
	}
}
