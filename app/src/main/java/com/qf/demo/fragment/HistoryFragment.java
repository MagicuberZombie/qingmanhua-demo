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

import java.util.ArrayList;
import java.util.List;

/**
 * 浏览历史的Fragment
 */
public class HistoryFragment extends Fragment {

    private ListView mHistoryList;
    private DBManager mDBManager;
    private List<History> mHistoryData = new ArrayList<>();
    private HistoryListAdapter adapter;
    private MyApp app;
    private String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
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
        List<History> list = mDBManager.queryAll();
        mHistoryData.clear();
        mHistoryData.addAll(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        id = app.getPreferences().getString("id", "0");
        adapter = new HistoryListAdapter(getActivity(), mHistoryData,id);
        mHistoryList.setAdapter(adapter);
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
        mHistoryData = mDBManager.queryAll();
    }

    /**
     * 寻找控件
     *
     * @param view
     */
    private void initView(View view) {
        mHistoryList = (ListView) view.findViewById(R.id.list_book);
    }

}
