package com.gofun.cloudbox.android.ui.bind;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.base.BaseActivity;
import com.gofun.cloudbox.android.entity.CarBrandList;
import com.gofun.cloudbox.android.entity.CarDict;
import com.gofun.cloudbox.android.utils.HttpConstant;
import com.gofun.cloudbox.android.utils.IHttpResponse;
import com.gofun.cloudbox.android.utils.RequestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class CarModelActivity extends BaseActivity {
    private int pageNumber;
    RecyclerView recyclerView;
    private List<CarDict> data = new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_model_list);
        initTitle(true, false);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(R.layout.item_car_model_list, data);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {

            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent();
                i.putExtra("dictName", data.get(position).getDictName());
                i.putExtra("dictCode", data.get(position).getDictCode());
                setResult(1, i);
                finish();
            }
        });
        getData();
    }

    public void getData() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("level", 1);
        paras.put("pageNumber", pageNumber);
        RequestUtil requestUtil = new RequestUtil();
        requestUtil.requestGetByParmarts(HttpConstant.CAR_MODEL_LIST_URL, paras, new IHttpResponse() {
            @Override
            public void response(boolean success, String result) {
                if (success && !TextUtils.isEmpty(result)) {
                    List<CarDict> carDictList = null;
                    try {
                        carDictList = JSONObject.parseObject(result,  new TypeReference<List<CarDict>>(){});
                        Log.d("","carBrandList->"+carDictList);
                        if (null != carDictList ) {
                            data.addAll(carDictList);
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public static class MyAdapter extends BaseQuickAdapter<CarDict, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<CarDict> data) {
            super(R.layout.item_car_model_list, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CarDict item) {
            //可链式调用赋值
            helper.setText(R.id.tv_title, item.getDictName())
//                    .setText(R.id.tv_content, item.getLevel() + "")
                    .addOnClickListener(R.id.content);
            int position = helper.getLayoutPosition();
        }
    }
}
