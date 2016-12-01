package com.qf.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qf.demo.R;
import com.qf.demo.activity.CataLogActivity;
import com.qf.demo.entities.History;
import com.qf.demo.http.BasicAttributes;
import com.qf.demo.http.HttpReqData;
import com.qf.demo.view.SelectableRoundedImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/12.
 */
public class HistoryListAdapter extends BaseAdapter {
    private Context mContext;
    private List<History> mData;
    private ViewHolder holder;
    private String id;

    public HistoryListAdapter(Context context, List<History> data, String id) {
        mContext = context;
        mData = data;
        this.id = id;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.history_item, null);
            holder = new ViewHolder();
            holder.mComicAuthor = (TextView) convertView.findViewById(R.id.tv_author);
            holder.mComicCover = (SelectableRoundedImageView) convertView.findViewById(R.id.iv_item_img);
            holder.mComicName = (TextView) convertView.findViewById(R.id.tv_works);
            holder.mComicOrder = (TextView) convertView.findViewById(R.id.tv_order);

            holder.mContinueBtn = (Button) convertView.findViewById(R.id.btn_continued_read);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final History data = (History) getItem(position);
        holder.mComicAuthor.setText(data.getComicAuthor());
        holder.mComicName.setText(data.getComicName());
        String orderId = data.getOrderId();
        String comicPagNum = data.getComicPagNum();
        String order = orderId + "话/" + comicPagNum + "话";
        holder.mComicOrder.setText(order);
        String comicCover = data.getComicCover();
        comicCover = BasicAttributes.HTTPNAME + comicCover;
        Glide.with(mContext).load(comicCover).asBitmap().into(holder.mComicCover);

        holder.mContinueBtn.setOnClickListener(new View.OnClickListener() {//点击继续阅读
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CataLogActivity.class);
                HttpReqData.getInstance().getReqComicInfo(id, data.getComicId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


    private static class ViewHolder {
        TextView mComicName;
        TextView mComicAuthor;
        TextView mComicOrder;
        SelectableRoundedImageView mComicCover;
        Button mContinueBtn;
    }
}
