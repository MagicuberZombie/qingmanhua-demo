package com.qf.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qf.demo.R;
import com.qf.demo.entities.ComicData;
import com.qf.demo.http.BasicAttributes;
import com.qf.demo.view.SelectableRoundedImageView;

import java.util.List;

/**
 * 自定义GridView的适配器
 * Created by Administrator on 2016/11/4.
 */
public class RecommendGridAdapter extends BaseAdapter {
    private List<ComicData> mData;
    private Context mContext;
    private ViewHolder holder;

    public RecommendGridAdapter(Context context, List<ComicData> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return 6;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.recommend_list_item, null);
            holder = new ViewHolder();
            holder.comicCover = (SelectableRoundedImageView) convertView.findViewById(R.id.iv_item_img);
            holder.comicName = (TextView) convertView.findViewById(R.id.tv_work_name);
            holder.comicLength = (TextView) convertView.findViewById(R.id.tv_see_idx_last_idx);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ComicData data = (ComicData) getItem(position);
        String picPath = data.getComic_pic_240_320();
        picPath = BasicAttributes.HTTPNAME + picPath;
        //为了适应GridView的Item的大小 ImageView需要fitStart 来拉伸 adjustViewBounds来去除空白
        holder.comicCover.setImageResource(R.drawable.ydq_default_gb);
        Glide.with(mContext).load(picPath)
                .asBitmap()
                .into(holder.comicCover);
        holder.comicName.setText(data.getComic_name());
        String length = data.getComic_last_orderidx() + "话";
        holder.comicLength.setText(length);
        holder.comicName.setTag(data.getId());
        return convertView;
    }


    /**
     * 模板
     */
    public static class ViewHolder {
        public SelectableRoundedImageView comicCover;
        public TextView comicName;
        public TextView comicLength;
    }
}
