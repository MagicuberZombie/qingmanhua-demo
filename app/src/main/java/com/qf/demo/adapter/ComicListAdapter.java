package com.qf.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qf.demo.R;
import com.qf.demo.entities.ContentData;
import com.qf.demo.http.BasicAttributes;
import com.qf.demo.utils.PhoneTools;

import java.util.List;


public class ComicListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ContentData> mData;
    private ViewHolder holder;

    public ComicListAdapter(Context context, List<ContentData> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.comic_item, null);
            holder = new ViewHolder();
            holder.comicIv = (ImageView) convertView.findViewById(R.id.comic_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ContentData data = (ContentData) getItem(position);
        String picPath = data.getPager_pic();

        final String height = data.getPager_pic_height();
        String width = data.getPager_pic_width();

        int widthSize = Integer.parseInt(width);
        int heightSize = Integer.parseInt(height);

        float imgHeight = PhoneTools.getInstance().getPhoneScreenSize(mContext).x
                / widthSize * heightSize;
        int imgWidth = PhoneTools.getInstance().getPhoneScreenSize(mContext).x;

        ViewGroup.LayoutParams params = holder.comicIv.getLayoutParams();
        params.height = (int) imgHeight;
        params.width = imgWidth;

        holder.comicIv.setLayoutParams(params);
        picPath = BasicAttributes.HTTPNAME + picPath;
        Glide.with(mContext).load(picPath).asBitmap().into(holder.comicIv);
        return convertView;
    }

    private static class ViewHolder{
        ImageView comicIv;
    }
}
