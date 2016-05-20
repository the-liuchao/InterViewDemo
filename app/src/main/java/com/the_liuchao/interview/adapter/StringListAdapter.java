package com.the_liuchao.interview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.the_liuchao.interview.R;

import java.util.List;

/**
 * Created by hp on 2016/5/19.
 */
public class StringListAdapter extends BaseAdapter {

    private List<String> datas;
    private Context context;
    private LayoutInflater inflater;

    public StringListAdapter(Context context,List<String> datas) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return datas.size();
    }

    public Object getItem(int position) {
        return datas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.string_list_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_list_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(datas.get(position));
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
