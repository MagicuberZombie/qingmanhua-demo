package com.qf.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qf.demo.R;
import com.qf.demo.entities.ComicData;
import com.qf.demo.http.BasicAttributes;
import com.qf.demo.view.SelectableRoundedImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/5.
 */
public class RecommendListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ComicData> mData;
    private ViewHolder holder;

    public RecommendListAdapter(Context context, List<ComicData> data) {
        mContext = context;
        mData = data;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.rank_list_item, null);
            holder = new ViewHolder();
            holder.comicCover = (SelectableRoundedImageView) convertView.findViewById(R.id.iv_img);
            holder.comicAuthor = (TextView) convertView.findViewById(R.id.tv_author);
            holder.comicName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.comicUpdate = (TextView) convertView.findViewById(R.id.tv_last_idx);
            holder.comicLikeNum = (TextView) convertView.findViewById(R.id.tv_zan);
            holder.comicCommentNum = (TextView) convertView.findViewById(R.id.tv_comment);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ComicData data = (ComicData) getItem(position);
        String comicPic = data.getComic_pic_240_320();
        comicPic = BasicAttributes.HTTPNAME + comicPic;
        Glide.with(mContext).load(comicPic).asBitmap().into(holder.comicCover);
        holder.comicAuthor.setText(data.getPainter_user_nickname());
        holder.comicCommentNum.setText(data.getComic_comment_num());
        String comicLastOrderidx = data.getComic_last_orderidx();
        comicLastOrderidx = "更新至" + comicLastOrderidx + "话";
        holder.comicUpdate.setText(comicLastOrderidx);
        holder.comicLikeNum.setText(data.getComic_like_num());
        holder.comicName.setText(data.getComic_name());
        holder.comicName.setTag(data.getId());
        return convertView;
    }

    public static class ViewHolder {
        SelectableRoundedImageView comicCover;
        public TextView comicName;
        TextView comicAuthor;
        TextView comicUpdate;
        TextView comicLikeNum;
        TextView comicCommentNum;
    }
}
