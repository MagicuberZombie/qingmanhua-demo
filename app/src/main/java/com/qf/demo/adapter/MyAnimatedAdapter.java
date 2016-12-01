package com.qf.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qf.demo.R;
import com.qf.demo.activity.CataLogActivity;
import com.qf.demo.entities.ComicData;
import com.qf.demo.http.BasicAttributes;
import com.qf.demo.http.HttpReqData;
import com.qf.demo.view.AnimatedExpandableListView;
import com.qf.demo.view.SelectableRoundedImageView;

import java.util.List;


public class MyAnimatedAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private Context mContext;
    private List<List<ComicData>> mData;
    private List<String> mPicList;
    private String uid;

    public MyAnimatedAdapter(Context context, List<List<ComicData>> data
            , List<String> picList, String uid) {
        mContext = context;
        mData = data;
        mPicList = picList;
        this.uid = uid;
    }

    private static class GroupHolder {
        ImageView mImageView;
    }

    private static class ChildHolder {
        SelectableRoundedImageView comicCover;
        TextView comicName;
        TextView comicAuthor;
        TextView comicUpdate;
        TextView comicLikeNum;
        TextView comicCommentNum;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
        ChildHolder holder;
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            holder.comicCover = (SelectableRoundedImageView) convertView.findViewById(R.id.iv_img);
            holder.comicAuthor = (TextView) convertView.findViewById(R.id.tv_author);
            holder.comicName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.comicUpdate = (TextView) convertView.findViewById(R.id.tv_last_idx);
            holder.comicLikeNum = (TextView) convertView.findViewById(R.id.tv_zan);
            holder.comicCommentNum = (TextView) convertView.findViewById(R.id.tv_comment);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        final ComicData data = (ComicData) getChild(groupPosition, childPosition);
        String comicPic = data.getComic_pic_240_320();
        comicPic = BasicAttributes.HTTPNAME + comicPic;
        Glide.with(mContext).load(comicPic).asBitmap().into(holder.comicCover);
        holder.comicAuthor.setText(data.getPainter_user_nickname());
        holder.comicCommentNum.setText(data.getComic_comment_num());
        String orderidx = data.getComic_last_orderidx();
        orderidx = "更新至" + orderidx + "话";
        holder.comicUpdate.setText(orderidx);
        holder.comicLikeNum.setText(data.getComic_like_num());
        holder.comicName.setText(data.getComic_name());

        convertView.setOnClickListener(new View.OnClickListener() {//漫画条目被点击F
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CataLogActivity.class);
                HttpReqData.getInstance().getReqComicInfo(uid, data.getId());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        int size = mData.get(groupPosition).size();
        return size;
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mPicList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.weekly_head_item, parent, false);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.iv_img);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
//        int height = PhoneTools.getInstance().getPhoneScreenSize(mContext).y;
//
//        holder.mImageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
//                , height / mData.size()));
        String picPath = (String) getGroup(groupPosition);
        picPath = BasicAttributes.HTTPNAME + picPath;
        Glide.with(mContext).load(picPath).into(holder.mImageView);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
