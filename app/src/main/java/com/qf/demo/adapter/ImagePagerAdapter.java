package com.qf.demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qf.demo.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class ImagePagerAdapter extends BasePageAdapter<String> implements View.OnClickListener {
    private List<String> mData;
    private Context mContext;
    private boolean isInfinite = false;

    public ImagePagerAdapter(Context context, List<String> data) {
        super(data);
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        if (isInfinite)
            return Integer.MAX_VALUE;
        return super.getCount();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(mContext);
        iv.setOnClickListener(this);
        int index = position % mData.size();
        Glide.with(mContext).load(mData.get(index))
                .into(iv);
        iv.setTag(index);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public ImagePagerAdapter setIsInfinite(boolean isInfinite) {
        this.isInfinite = isInfinite;
        return this;
    }

    private ItemClickCallBack itemClickCallBack = null;

    public interface ItemClickCallBack {
        void click(View v);
    }

    public void setItemClickCallBack(ItemClickCallBack itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }

    @Override
    public void onClick(View v) {
        itemClickCallBack.click(v);
    }
}
