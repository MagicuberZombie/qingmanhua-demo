package com.qf.demo.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class BasePageAdapter<T> extends PagerAdapter {

	private List<T> data;

	public BasePageAdapter(List<T> data) {
		super();
		this.data = data;
	}

	@Override
	public abstract Object instantiateItem(ViewGroup container,
			int position);

	@Override
	public abstract void destroyItem(ViewGroup container,
			int position, Object object);

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
