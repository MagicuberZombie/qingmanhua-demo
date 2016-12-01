package com.qf.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qf.demo.R;
import com.qf.demo.db.DBManager;
import com.qf.demo.entities.History;
import com.qf.demo.http.BasicAttributes;
import com.qf.demo.view.SelectableRoundedImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/12.
 */
public class ManageListAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private List<History> mData;
    private ViewHolder holder;

    private boolean allChecked = false;

    public ManageListAdapter(Context context, List<History> data) {
        mContext = context;
        mData = data;
        if (context instanceof ClickDeleteListener) {
            listener = (ClickDeleteListener) context;
        }
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.mange_book_item, null);
            holder = new ViewHolder();
            holder.mComicAuthor = (TextView) convertView.findViewById(R.id.tv_author);
            holder.mComicCover = (SelectableRoundedImageView) convertView.findViewById(R.id.iv_item_img);
            holder.mComicName = (TextView) convertView.findViewById(R.id.tv_works);
            holder.mComicOrder = (TextView) convertView.findViewById(R.id.tv_order);
            holder.mDeleteBtn = (Button) convertView.findViewById(R.id.btn_delete);
            holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.cb_manage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mCheckBox.setChecked(allChecked);
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
        holder.mCheckBox.setTag(data.getComicId());
        holder.mDeleteBtn.setOnClickListener(this);
        holder.mDeleteBtn.setTag(data.getComicId());
        return convertView;
    }

    public void setAllChecked(boolean isChecked) {
        allChecked = isChecked;
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        listener.click(v);
    }

    private ClickDeleteListener listener = null;

    public interface ClickDeleteListener {
        void click(View view);
    }

    public static class ViewHolder {
        TextView mComicName;
        TextView mComicAuthor;
        TextView mComicOrder;
        SelectableRoundedImageView mComicCover;
        Button mDeleteBtn;
        public CheckBox mCheckBox;
    }
}
