package com.qf.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qf.demo.R;
import com.qf.demo.entities.CommentData;
import com.qf.demo.http.BasicAttributes;
import com.qf.demo.utils.Utils;
import com.qf.demo.view.SelectableRoundedImageView;

import java.util.List;


public class CommentListAdapter extends BaseAdapter {
    private Context mContext;
    private List<CommentData> mData;
    private ViewHolder holder;
    private boolean isReply = false;

    public CommentListAdapter(Context context, List<CommentData> data, boolean isReply) {
        mContext = context;
        mData = data;
        this.isReply = isReply;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.comment_item, null);
            holder = new ViewHolder();
            holder.mPortrait = (SelectableRoundedImageView) convertView.findViewById(R.id.iv_user_head);
            holder.mAlias = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.mTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.mComment = (TextView) convertView.findViewById(R.id.tv_comment_msg);
            holder.mGoodNum = (TextView) convertView.findViewById(R.id.tv_good_num);
            holder.mReplyNum = (TextView) convertView.findViewById(R.id.tv_reply);
            holder.mLikeIv = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.mReplyIv = (ImageView) convertView.findViewById(R.id.iv_reply_icon);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommentData data = (CommentData) getItem(position);
        String userPic = data.getUser_pic();
        holder.mAlias.setText(data.getUser_nickname());
        if (data.getUser_nickname().contains("游客")) {
            userPic = BasicAttributes.HTTPIMGNAME + userPic;
            System.out.println(userPic);
        }
        Glide.with(mContext).load(userPic).asBitmap().into(holder.mPortrait);
        String commentTime = data.getOrder_comment_time();
        holder.mTime.setText(Utils.getInstance().getTime(commentTime));
        holder.mComment.setText(data.getOrder_comment_msg());
        if (isReply) {
            holder.mGoodNum.setVisibility(View.INVISIBLE);
            holder.mReplyNum.setVisibility(View.INVISIBLE);
            holder.mLikeIv.setVisibility(View.INVISIBLE);
            holder.mReplyIv.setVisibility(View.INVISIBLE);
        } else {
            holder.mGoodNum.setText(data.getHot_number());
            holder.mReplyNum.setText(data.getReply_number());
        }
        String id = data.getId();
        holder.mComment.setTag(id);
        return convertView;
    }

    public static class ViewHolder {
        SelectableRoundedImageView mPortrait;
        TextView mAlias;
        TextView mTime;
        public TextView mComment;
        TextView mGoodNum;
        TextView mReplyNum;

        ImageView mLikeIv;
        ImageView mReplyIv;
    }
}
