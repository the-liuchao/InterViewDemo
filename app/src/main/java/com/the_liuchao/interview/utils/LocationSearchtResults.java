package com.the_liuchao.interview.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.the_liuchao.interview.R;
import com.the_liuchao.interview.adapter.StringListAdapter;
import com.the_liuchao.interview.inter.ItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/5/19.
 */
public class LocationSearchtResults extends PopupWindow {

    private View contentView;
    private ListView lvResults;
    private List<String> list;
    private BaseAdapter adapter;
    private Context context;
    //下拉列表项被单击的监听器
    private ItemSelectedListener listener;

    public void refresh(final List<String> list){
         this.list = list;
        this.adapter = new StringListAdapter(context, this.list);
        lvResults.setAdapter(this.adapter);
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
                if (listener != null) {
                    Bundle data = new Bundle();
                    data.putString("address",((TextView) view.findViewById(R.id.tv_list_item)).getText().toString().trim());
                    if (listener!= null)
                        listener.onItemSelected(list.get(index),index);
                    dismiss();
                }
            }
        });
    }

    public LocationSearchtResults(final Context context, LayoutInflater layoutInflater,
                                  final ArrayList<String> list, int width,
                                  ItemSelectedListener itemClickListener) {
        super(context);
        this.context = context;
        this.list = list;
        this.listener = itemClickListener;
        contentView =layoutInflater.inflate(R.layout.pull_list_layout,null);
        lvResults = (ListView) contentView.findViewById(R.id.lv_data);
        this.adapter = new StringListAdapter(context, this.list);
        lvResults.setAdapter(this.adapter);


        //如果PopupWindow中的下拉列表项被单击了

        //则通知外部的下拉列表项单击监听器并传递当前单击项的数据
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
                if (listener != null) {
                    Bundle data = new Bundle();
                    data.putString("address",((TextView) view.findViewById(R.id.tv_list_item)).getText().toString().trim());
                    if (listener!= null)
                        listener.onItemSelected(list.get(index),index);
                    dismiss();
                }
            }
        });

        this.setContentView(contentView);  //设置悬浮窗体内显示的内容View
        this.setWidth(width - 5);   //设置悬浮窗体的宽度
        //this.setWidth(LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);   //设置悬浮窗体的高度
        this.setBackgroundDrawable(new ColorDrawable(0xffffff)); // 设置悬浮窗体背景
        //this.setAnimationStyle(R.style.PopupAnimation);
        this.setAnimationStyle(R.style.popup_enter);     //设置悬浮窗体出现和退出时的动画
//        this.setFocusable(true);    // menu菜单获得焦点 如果没有获得焦点menu菜单中的控件事件无法响应
        this.setOutsideTouchable(true);   //可以再外部点击隐藏掉PopupWindow
    }
}
