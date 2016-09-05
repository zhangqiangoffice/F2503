package com.zhanglemeng.www.f2503.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

/**
 * 作者：无名小强
 * 邮箱：zhangqiangoffice@163.com
 * 日期：2016/7/7
 */
public class PopupWindowUtils {

    /*
     * 销毁PopipWinsow实例
     */
    public static PopupWindow destroy(PopupWindow popupWindow){
        if (null != popupWindow){
            popupWindow.dismiss();
        }
        return null;
    }

    /*
     * 设置背景遮罩颜色
     */
    public static void setBg(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /*
     * 关闭软键盘
     */
    public static void closeKeyBoard(Activity activity) {
        if(activity.getCurrentFocus()!=null) {
            ((InputMethodManager) activity.getSystemService(activity.getBaseContext().INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 生成新的PopupWindow
     *
     * @param activity
     * @param layout
     * @param v
     * @return
     */
    public static PopupWindow newPop(final Activity activity, int layout, View v) {
        closeKeyBoard(activity);
        setBg(0.4f, activity);

        //加载布局文件
        final View popipWindow_view = activity.getLayoutInflater().inflate(layout,null,false);
        final PopupWindow pop = new PopupWindow(popipWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);

        //销毁时回复背景颜色
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBg(1f, activity);
            }
        });

        //点空白处销毁PopupWindow
        popipWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popipWindow_view != null && popipWindow_view.isShown()) {
                    destroy(pop);
                }
                return false;
            }
        });

        //显示在底部
        pop.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        return pop;
    }
}
