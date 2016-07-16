package com.LiYang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.LiYang.R;

import java.util.List;


public class CityAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;
    private int mResourceId;

    public CityAdapter(Context context, int viewResourceId, List<String> list) {//构造器
        this.mResourceId = viewResourceId;
        this.mContext = context;
        this.mList = list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int i) {
        return mList.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        String name = getItem(i);//获取当前选项的序号
        View view;
        ViewHolder viewHolder;//配合缓存
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(mResourceId, null);
            viewHolder = new ViewHolder();

            viewHolder.textView = (TextView) view.findViewById(R.id.chooseareaactivity_tv_cityname);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(name);
        return view;
    }


    class ViewHolder { //内部类
        TextView textView;
    }
}

