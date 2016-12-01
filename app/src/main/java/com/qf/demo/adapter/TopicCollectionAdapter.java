package com.qf.demo.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qf.demo.R;
import com.qf.demo.entities.TopicCollectionData;
import com.qf.demo.http.BasicAttributes;
import com.qf.demo.view.SelectableRoundedImageView;

import java.util.List;

/**
 * Created by ZombieFan on 2016/11/13.
 */
public class TopicCollectionAdapter extends BaseAdapter {
    private Context mContext;
    private List<TopicCollectionData> mData;
    private ViewHolder holder;

    public TopicCollectionAdapter(Context context, List<TopicCollectionData> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.topic_item, null);
            holder = new ViewHolder();
            holder.mComicCover = (SelectableRoundedImageView) convertView.findViewById(R.id.iv_img);
            holder.mComicName = (TextView) convertView.findViewById(R.id.tv_title);
            holder.mComicTag = (LinearLayout) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TopicCollectionData data = (TopicCollectionData) getItem(position);
        holder.mComicName.setText(data.getTopic_name());
        String topicPicUrl = data.getTopic_pic_url();
        topicPicUrl = BasicAttributes.HTTPNAME + topicPicUrl;
        Glide.with(mContext).load(topicPicUrl).asBitmap().into(holder.mComicCover);
        List<String> topicTagName = data.getTopic_tag_name();
        for (int i = 0; i < topicTagName.size(); i++) {
            if (i < 4) {
                TextView textView = (TextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.tag_text, null);
                textView.setText(topicTagName.get(i));
                holder.mComicTag.addView(textView);
            }
        }
        int childCount = holder.mComicTag.getChildCount();
        if (childCount > 4) {
            holder.mComicTag.removeViews(4,childCount - 4);
        }
        return convertView;
    }

    private static class ViewHolder {
        SelectableRoundedImageView mComicCover;
        TextView mComicName;
        LinearLayout mComicTag;
    }
}
