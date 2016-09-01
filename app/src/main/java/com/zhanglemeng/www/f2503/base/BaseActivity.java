package com.zhanglemeng.www.f2503.base;

import android.support.v4.app.FragmentActivity;

//基础Activity
public abstract class BaseActivity extends FragmentActivity {
	@Override
	protected void onResume() {
		super.onResume();
		// onresume时，取消notification显示.
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
