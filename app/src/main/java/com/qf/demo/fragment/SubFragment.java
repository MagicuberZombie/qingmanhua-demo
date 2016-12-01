package com.qf.demo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qf.demo.R;
import com.qf.demo.activity.MyApp;
import com.qf.demo.adapter.HistoryListAdapter;
import com.qf.demo.db.DBManager;
import com.qf.demo.entities.History;
import com.qf.demo.entities.SubData;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏数据的Fragment
 */
public class SubFragment extends Fragment {
    private ListView mSubList;
    private DBManager mDBManager;
    private List<History> mSubData = new ArrayList<>();
    private HistoryListAdapter adapter;

    private MyApp app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        app = (MyApp) getActivity().getApplication();
        initData();
        setListener();
        setAdapter();
    }

    /**
     * 更新列表内容
     */
    @Override
    public void onResume() {
        super.onResume();
        List<SubData> subDataList = mDBManager.queryForRealSub();
        subToHistory(subDataList);
        adapter.notifyDataSetChanged();
    }

    /**
     * 取巧的办法 下次不要这么写
     */
    private void subToHistory(List<SubData> subDataList) {
        mSubData.clear();
        List<History> list = new ArrayList<>();
        for (int i = 0; i < subDataList.size(); i++) {
            History item = new History();
            SubData data = subDataList.get(i);
            item.setIsSub(data.isSub());
            item.setComicCover(data.getComicCover());
            item.setComicAuthor(data.getComicAuthor());
            item.setOrderId(data.getOrderId());
            item.setComicId(data.getComicId());
            item.setComicName(data.getComicName());
            item.setComicPagNum(data.getComicName());
            list.add(item);
        }
        mSubData.addAll(list);
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        String id = app.getPreferences().getString("id", "0");
        adapter = new HistoryListAdapter(getActivity(), mSubData, id);
        mSubList.setAdapter(adapter);
    }

    /**
     * 设置监听
     */
    private void setListener() {

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mDBManager = DBManager.getInstance(getActivity());
        List<SubData> subDataList = mDBManager.queryForRealSub();
        subToHistory(subDataList);
    }

    /**
     * 寻找控件
     *
     * @param view
     */
    private void initView(View view) {
        mSubList = (ListView) view.findViewById(R.id.list_sub);
    }

}
