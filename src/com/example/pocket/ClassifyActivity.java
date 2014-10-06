package com.example.pocket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ClassifyActivity extends Activity {
	private TextView textView_score;
	private boolean running = true;
	private int count = 0;
	ImageButton btn_rule;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (count < 94) {
				textView_score.setText("" + count++);
			}
		}
	};
	private Runnable r = new Runnable() {
		public void run() {
			while (running) {
				handler.sendEmptyMessage(0);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classification);
		btn_rule = (ImageButton) findViewById(R.id.btn_rules);
		textView_score = (TextView) findViewById(R.id.textView_score);
		AssetManager mgr=getAssets();//得到AssetManager
		Typeface tf=Typeface.createFromAsset(mgr, "fonts/yankaiti.TTF");//根据路径得到Typeface
		textView_score.setTypeface(tf);//设置字体
		new Thread(r).start();
		btn_rule.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(ClassifyActivity.this)
						.setTitle("RULES")
						.setItems(R.array.rule_body,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										String[] aryshop = getResources()
												.getStringArray(
														R.array.rule_body);
										new AlertDialog.Builder(
												ClassifyActivity.this)
												.setMessage(aryshop[arg1])
												.setNegativeButton(
														"ok",
														new DialogInterface.OnClickListener() {

															@Override
															public void onClick(
																	DialogInterface arg0,
																	int arg1) {
																// TODO
																// Auto-generated
																// method stub

															}
														}).show();
									}

									public void dismiss() {
										// TODO Auto-generated method stub

									}

									public void cancel() {
										// TODO Auto-generated method stub

									}
								}).show();

			}
		});
	}
	protected void onStop() {
		running = false;
		super.onStop();
	}
}
