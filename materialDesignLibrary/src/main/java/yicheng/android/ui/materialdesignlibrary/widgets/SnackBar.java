package yicheng.android.ui.materialdesignlibrary.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import yicheng.android.ui.materialdesignlibrary.R;
import yicheng.android.ui.materialdesignlibrary.views.ButtonFlat;

public class SnackBar extends Dialog {
	boolean isPhone;
	final int SCREEN_TABLET = 140;
	final int SCREEN_PHONE = 120;
	int height;
	String text;
	float textSize = 14;// Roboto Regular 14sp
	String buttonText;
	View.OnClickListener onClickListener;
	Activity activity;
	View view;
	View rootView;
	ButtonFlat button;
	int backgroundSnackBar = Color.parseColor("#333333");
	int backgroundButton = Color.parseColor("#F44336");

	OnHideListener onHideListener;
	// Timer
	private boolean mIndeterminate = false;
	private boolean cancelableTouchOutside = false;

	private int mTimer = 3 * 1000;

	// With action button
	public SnackBar(Activity activity, String text, String buttonText,
			View.OnClickListener onClickListener, boolean isPhone) {
		super(activity, android.R.style.Theme_Translucent);
		this.activity = activity;
		this.text = text;
		this.buttonText = buttonText;
		this.onClickListener = onClickListener;
		this.isPhone = isPhone;
	}

	// Only text
	public SnackBar(Activity activity, String text) {
		super(activity, android.R.style.Theme_Translucent);
		this.activity = activity;
		this.text = text;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.snackbar);

		((TextView) findViewById(R.id.text)).setText(text);
		((TextView) findViewById(R.id.text)).setTextSize(textSize); // set
																	// textSize
		button = (ButtonFlat) findViewById(R.id.buttonflat);
		if (text == null || onClickListener == null) {
			button.setVisibility(View.GONE);
		}
		else {
			button.setText(buttonText);
			button.setBackgroundColor(backgroundButton);

			button.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dismiss();
					onClickListener.onClick(v);
				}
			});
		}

		view = findViewById(R.id.snackbar);

		view.setBackgroundColor(backgroundSnackBar);

		if (isPhone) {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
					.getLayoutParams();
			params.height = this.SCREEN_PHONE;
			view.setLayoutParams(params);

		}
		else {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
					.getLayoutParams();
			params.height = this.SCREEN_TABLET;
			view.setLayoutParams(params);
		}
		rootView = findViewById(R.id.snackbar_rootView);
		setCanceledOnTouchOutside(false);
	}

	@Override
	public void setCanceledOnTouchOutside(boolean cancel) {
		// TODO Auto-generated method stub
		super.setCanceledOnTouchOutside(cancel);

		if (cancel) {
			rootView.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub

					dismiss();

					return false;
				}
			});
		}
		else {
			rootView.setOnTouchListener(null);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return activity.dispatchTouchEvent(event);
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public void show() {
		super.show();
		view.setVisibility(View.VISIBLE);
		view.startAnimation(AnimationUtils.loadAnimation(activity,
				R.anim.snackbar_show_animation));
		if (!mIndeterminate) {
			dismissTimer.start();
		}
	}

	// Dismiss timer
	Thread dismissTimer = new Thread(new Runnable() {

		@Override
		public void run() {
			try {
				Thread.sleep(mTimer);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			handler.sendMessage(new Message());
		}
	});

	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			if (onHideListener != null) {
				onHideListener.onHide();
			}
			dismiss();
			return false;
		}
	});

	/**
	 * @author Jack Tony
	 */
	@Override
	public void dismiss() {
		Animation anim = AnimationUtils.loadAnimation(activity,
				R.anim.snackbar_hide_animation);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				SnackBar.super.dismiss();
			}
		});
		view.startAnimation(anim);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dismiss();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void setMessageTextSize(float size) {
		textSize = size;
	}

	public void setIndeterminate(boolean indeterminate) {
		mIndeterminate = indeterminate;
	}

	public boolean isIndeterminate() {
		return mIndeterminate;
	}

	public void setDismissTimer(int time) {
		mTimer = time;
	}

	public int getDismissTimer() {
		return mTimer;
	}

	/**
	 * Change background color of SnackBar
	 * @param color
	 */
	public void setBackgroundSnackBar(int color) {
		backgroundSnackBar = color;
		if (view != null)
			view.setBackgroundColor(color);
	}

	/**
	 * Chage color of FlatButton in Snackbar
	 * @param color
	 */
	public void setColorButton(int color) {
		backgroundButton = color;
		if (button != null)
			button.setBackgroundColor(color);
	}

	/**
	 * This event start when snackbar dismish without push the button
	 * @author Navas
	 *
	 */
	public interface OnHideListener {
		public void onHide();
	}

	public void setOnhideListener(OnHideListener onHideListener) {
		this.onHideListener = onHideListener;
	}
}
