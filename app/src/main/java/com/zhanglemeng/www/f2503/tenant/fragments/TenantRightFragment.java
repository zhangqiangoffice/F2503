package com.zhanglemeng.www.f2503.tenant.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;

/**
 * Created by 无名大强 on 2016/9/6.
 * 历史租客列表
 */
public class TenantRightFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View convertView;
    private ListView lv;

    private View empty_view;
    private TextView tv_empty;
    private ImageView iv_empty;
    private ProgressBar pb_emptyview;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_bill_main, container, false);
            initView(convertView);
//            getLoanList();
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
//        getLoanList();
    }

    //初始化控件
    public void initView(View v)
    {

//        lv= (ListView) v.findViewById(R.id.activity_customer_lv);
//
//        empty_view=v.findViewById(R.id.layout_emptyview);
//        tv_empty= (TextView) v.findViewById(R.id.layout_tv_emptyview);
//        iv_empty= (ImageView) v.findViewById(R.id.layout_iv_emptyview);
//        pb_emptyview= (ProgressBar) v.findViewById(R.id.pb_emptyview);
//
//        mRefreshLayout.setOnRefreshListener(this);
//        lv.setOnItemClickListener(this);
//
//        httpUtils=new HttpUtils();
    }

//    //获取申请列表的方法
//    public void getLoanList()
//    {
//        RequestParams params=new RequestParams();
//        params.addBodyParameter("staffid", PreferenceUtils.loadUser(getActivity(),PreferenceUtils.STAFFID));
//        params.addBodyParameter("page",page+"");
//        httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL+WebServiceUrl.LOAN_URL, params, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String json=responseInfo.result;
//                Log.i("123", "json:" + json);
//                JSONObject obj= JSON.parseObject(json);
//                int result=obj.getInteger("result");
//                String message=obj.getString("message");
//                if (result==1)
//                {
//                    JSONArray array=obj.getJSONArray("list");
//                    List<Loan> list=JSON.parseArray(array.toJSONString(),Loan.class);
//                    if (page>1)
//                    {
//                        if (list.size()==0)
//                        {
//                            isHas=false;
//                        }
//                    }else if (page==1)
//                    {
//                        data.clear();
//                    }
//                    data.addAll(list);
//                    if (data.size()==0)
//                    {
//                        pb_emptyview.setVisibility(View.GONE);
//                        iv_empty.setVisibility(View.VISIBLE);
//                    }else {
//                        empty_view.setVisibility(View.GONE);
//                    }
//                    adapter = new ApplyAdapter(data, getActivity());
//                    lv.setAdapter(adapter);
//                }else {
//                    pb_emptyview.setVisibility(View.GONE);
//                    tv_empty.setText(message);
//                    tv_empty.setVisibility(View.VISIBLE);
//                }
//
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                pb_emptyview.setVisibility(View.GONE);
//                tv_empty.setText("服务器错误："+s);
//                tv_empty.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//


    //listview的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent=new Intent(getActivity(), AddLoanActivity.class);
//        intent.putExtra("loan_id", String.valueOf(data.get(position).getId()));
//        startActivity(intent);
    }


}
