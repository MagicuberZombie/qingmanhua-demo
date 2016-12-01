package com.qf.demo.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.demo.R;
import com.qf.demo.entities.ComicCataLog;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class MyCataLogAdapter extends BaseAdapter {
    private Context mContext;
    private List<ComicCataLog> mData;
    private ViewHolder holder;
    private String curItem;

    public MyCataLogAdapter(Context context, List<ComicCataLog> data) {
        mContext = context;
        mData = data;
        curItem = "-1";
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.catalog_item, null);
            holder = new ViewHolder();
            holder.makerIv = (ImageView) convertView.findViewById(R.id.iv_jb);
            holder.catalogName = (TextView) convertView.findViewById(R.id.tv_catalog_word);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ComicCataLog data = (ComicCataLog) getItem(position);
        String orderId = data.getOrder_idx();
        holder.catalogName.setText(orderId);
        holder.catalogName.setTag(orderId);
        if (position == 0) {
            holder.makerIv.setTag(0);
            holder.makerIv.setVisibility(View.VISIBLE);
        } else if (holder.makerIv.getTag() != null) {
            holder.makerIv.setVisibility(View.INVISIBLE);
        }
        if (curItem.equals(holder.catalogName.getTag())) {//设置被选择的Item样式
            holder.catalogName.setBackground(mContext
                    .getResources().getDrawable(R.drawable.catalog_item_selected));
            holder.catalogName.setTextColor(mContext.getResources().getColor(R.color.comic));
        } else {
            holder.catalogName.setBackground(mContext
                    .getResources().getDrawable(R.drawable.catalog_item_txt));
            holder.catalogName.setTextColor(mContext.getResources().getColor(R.color.black_overlay));
        }
        return convertView;
    }

    public void setCurItem(String curItem) {
        this.curItem = curItem;
    }

    public static class ViewHolder {
        public TextView catalogName;
        ImageView makerIv;
    }
}
