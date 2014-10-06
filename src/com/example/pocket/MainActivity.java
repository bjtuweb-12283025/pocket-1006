package com.example.pocket;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	private TabHost tabHost; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.activity_main);
		tabHost = this.getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		intent=new Intent().setClass(this, AppListActivity.class);  
        spec=tabHost.newTabSpec("AppList").setIndicator("AppList").setContent(intent);  
        tabHost.addTab(spec);  
          
        intent=new Intent().setClass(this,EncryptActivity.class);  
        spec=tabHost.newTabSpec("Encrypt").setIndicator("Encrypt").setContent(intent);  
        tabHost.addTab(spec);  
          
        intent=new Intent().setClass(this, HideActivity.class);  
        spec=tabHost.newTabSpec("Hide").setIndicator("Hide").setContent(intent);  
        tabHost.addTab(spec);  
        
        intent=new Intent().setClass(this, ClassifyActivity.class);  
        spec=tabHost.newTabSpec("Classify").setIndicator("Classify").setContent(intent);  
        tabHost.addTab(spec); 
          
       
        intent=new Intent().setClass(this, AboutActivity.class);  
        spec=tabHost.newTabSpec("About").setIndicator("About").setContent(intent);  
        tabHost.addTab(spec);  
        
        
      
        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				 switch (checkedId) {  
	                case R.id.menu_applist: 
	                    tabHost.setCurrentTabByTag("AppList");  
	                    break;  
	                case R.id.menu_encrypt: 
	                    tabHost.setCurrentTabByTag("Encrypt");  
	                    break;  
	                case R.id.menu_hide:
	                    tabHost.setCurrentTabByTag("Hide");  
	                    break;  
	                case R.id.menu_classify:
	                    tabHost.setCurrentTabByTag("Classify");  
	                    break;  
	                case R.id.menu_about:
	                    tabHost.setCurrentTabByTag("About");  
	                    break;  
	                default:  
	                    break;  
	                }  
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
