package com.zhanglemeng.www.f2503.tenant.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.db.MyDB;
import com.zhanglemeng.www.f2503.tenant.activities.TenantDetailActivity;
import com.zhanglemeng.www.f2503.tenant.adapters.TenantOffAdapter;
import com.zhanglemeng.www.f2503.tenant.bean.Tenant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 无名大强 on 2016/9/10.
 * 历史租客列表
 */
public class TenantRightFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View convertView;
    private Activity activity;
    private ListView lv_tenant;

    //数据库相关
    private List<Tenant> list_tenant;
    private MyDB myDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.public_list, container, false);
            initView(convertView);
        }
        ViewGroup viewParent = (ViewGroup) convertView.getParent();
        if (viewParent != null) {
            viewParent.removeView(convertView);
        }
        return convertView;
    }

    @Override
    public void onResume() {
        super.onResume();

        //每次显示Fragment，就刷新列表
        showTenantList();
    }

    //初始化控件
    public void initView(View v)
    {

        //获取控件
        lv_tenant = (ListView) v.findViewById(R.id.list);
        list_tenant = new ArrayList<>() ;
        activity = getActivity();

        //初始化数据库
        myDB = myDB.getInstance(activity);

    }

    /**
     * 查询并展现在住租客列表
     */
    private void showTenantList() {

        //查询数据库
        list_tenant = myDB.queryTenantOff();

        //适配器
        TenantOffAdapter adapter = new TenantOffAdapter(list_tenant, activity);
        lv_tenant.setAdapter(adapter);
        lv_tenant.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tenant tenant = list_tenant.get(position);
        Intent intent = new Intent(getActivity(), TenantDetailActivity.class);
        intent.putExtra("tenant_id", tenant.getId());
        startActivity(intent);
    }


}
