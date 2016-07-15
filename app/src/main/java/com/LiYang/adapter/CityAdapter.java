package com.LiYang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.LiYang.R;

import java.util.List;

/**
 * Created by A555LF on 2016/7/15.
 */
public class CityAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;
    private int mResourceId;

    public CityAdapter(Context context , int textViewResourceId , List<String> list){
        this.mResourceId = textViewResourceId;
        this.mContext = context;
        this.mList = list;
    }

    /**
     * 返回数据大小
     * @return
     */
    @Override
    public int getCount() {
        return mList.size();//listView在开始绘制的时候，系统首先调用getCount（）获取长度
    }

    /**
     * 返回具体项
     * @param i
     * @return
     */
    @Override
    public String getItem(int i) {
        return mList.get(i);
    }

    /**
     * 返回每项ID
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {//调用getView（）逐一绘制每一行
        String name = getItem(i);//获取当前项
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(mResourceId, null);
            viewHolder = new ViewHolder();

            viewHolder.textView = (TextView) view.findViewById(R.id.chooseareaactivity_tv_cityname);
            view.setTag(viewHolder);//把HolderView对象存储在View中
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(name);

        return view;
    }

    /**
     * 内部类对控件实例进行缓存
     */
    class ViewHolder {

        TextView textView;
    }
}

