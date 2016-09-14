package com.zhanglemeng.www.f2503.base.activities;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.zhanglemeng.www.f2503.R;

//基础Activity
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener{
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

	@Override
	public void onClick(View v) {
        switch (v.getId()) {

			//点击顶部标题栏左侧的返回按钮，即销毁当前activity
			case R.id.go_back:
				finish();
				break;

			default:
				break;

		}
	}
}
