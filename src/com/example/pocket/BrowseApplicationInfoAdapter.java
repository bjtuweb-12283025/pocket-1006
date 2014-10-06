package com.example.pocket;
	import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

	//自定义适配器类，提供给listView的自定义view
	public class BrowseApplicationInfoAdapter extends BaseAdapter {
		
		private List<AppInfo> mlistAppInfo = null;
		
		LayoutInflater infater = null;
	    
		public BrowseApplicationInfoAdapter(Context context,  List<AppInfo> apps) {
			infater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mlistAppInfo = apps ;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			System.out.println("size" + mlistAppInfo.size());
			return mlistAppInfo.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mlistAppInfo.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertview, ViewGroup arg2) {
			System.out.println("getView at " + position);
			View view = null;
			ViewHolder Filterholder = null;
			if (convertview == null || convertview.getTag() == null) {
				view = infater.inflate(R.layout.browse_app_item, null);
				Filterholder = new ViewHolder(view);
				view.setTag(Filterholder);
			} 
			else{
				view = convertview ;
				Filterholder = (ViewHolder) convertview.getTag() ;
			}
			final AppInfo appInfo = (AppInfo) getItem(position);
			Filterholder.appIcon.setImageDrawable(appInfo.getAppIcon());
			Filterholder.tvAppLabel.setText(appInfo.getAppLabel());
//			Filterholder.tvPkgName.setText(appInfo.getPkgName());
			return view;
		}

		class ViewHolder {
			ImageView appIcon;
			TextView tvAppLabel;
//			TextView tvPkgName;

			public ViewHolder(View view) {
				this.appIcon = (ImageView) view.findViewById(R.id.imgApp);
				this.tvAppLabel = (TextView) view.findViewById(R.id.tvAppLabel);
			}
		}
	
}
