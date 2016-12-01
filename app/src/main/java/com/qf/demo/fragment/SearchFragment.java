package com.qf.demo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.demo.R;
import com.qf.demo.activity.MyApp;
import com.qf.demo.activity.SearchResultActivity;
import com.qf.demo.db.DBManager;
import com.qf.demo.entities.HistoryTag;
import com.qf.demo.entities.KeyTag;
import com.qf.demo.entities.KeyTagList;
import com.qf.demo.factory.KeyTagFactory;
import com.qf.demo.http.HttpReqData;
import com.qf.demo.utils.Utils;
import com.qf.demo.view.FlowLayout;
import com.qf.demo.view.KeywordsFlow;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 搜索界面
 */
public class SearchFragment extends Fragment implements View.OnClickListener, KeyTagFactory.GetKeyTagCallBack {

    private List<String> mTagList = new ArrayList<>();
    private KeywordsFlow mKeysFlow;
    private FlowLayout mFlow;

    private Button mUpdateTagBtn;
    private MyApp app;

    private Button mSearchBtn;
    private EditText mSearchEt;
    private ImageView mDeleteIv;
    private String keyword;

    private DBManager mDBManager;
    private Button mDeleteBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        app = (MyApp) getActivity().getApplication();
        mDBManager = DBManager.getInstance(getActivity());
        initData();
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        mUpdateTagBtn.setOnClickListener(this);
        mKeysFlow.setOnItemClickListener(this);
        mSearchBtn.setOnClickListener(this);
        mDeleteIv.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        HttpReqData.getInstance().getSearchTag();
        KeyTagFactory.getInstance().setCallBack(this);
        List<HistoryTag> tagList = mDBManager.queryAllTag();
        if (tagList != null) {
            initFlow(tagList);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        List<HistoryTag> tagList = mDBManager.queryAllTag();
        if (tagList != null) {
            initFlow(tagList);
        }
    }

    /**
     * 给Flow加载数据
     *
     * @param tagList
     */
    private void initFlow(List<HistoryTag> tagList) {
        if (mFlow.getChildCount() > 0) {
            mFlow.removeAllViews();
        }
        for (int i = 0; i < tagList.size(); i++) {
            TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.history_tag_text
                    , null);
            textView.setText(tagList.get(i).getTag());
            textView.setLayoutParams(new FlowLayout.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT
                    , ViewGroup.MarginLayoutParams.WRAP_CONTENT));
            textView.setOnClickListener(this);
            mFlow.addView(textView);
        }
    }

    /**
     * 给KeywordsFlow设置关键词数据
     */
    private void feedKeywords() {
        Random random = new Random();
        for (int i = 0; i < KeywordsFlow.MAX; i++) {
            int rand = random.nextInt(mTagList.size());
            String keyword = mTagList.get(rand);
            mKeysFlow.feedKeyword(keyword);
        }
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        mKeysFlow = (KeywordsFlow) view.findViewById(R.id.key_flow);
        mFlow = (FlowLayout) view.findViewById(R.id.flow_layout);
        mUpdateTagBtn = (Button) view.findViewById(R.id.btn_replace_hot);
        mSearchBtn = (Button) view.findViewById(R.id.btn_search);
        mSearchEt = (EditText) view.findViewById(R.id.et_search);
        mDeleteIv = (ImageView) view.findViewById(R.id.iv_delete);
        mDeleteBtn = (Button) view.findViewById(R.id.btn_delete_hit);
    }

    /**
     * 处理点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        String uid = app.getPreferences().getString("id", "0");
        switch (v.getId()) {
            case R.id.btn_replace_hot:
                mKeysFlow.rubKeywords();
                feedKeywords();
                mKeysFlow.go2Show(KeywordsFlow.ANIMATION_IN);
                break;
            case R.id.btn_search:
                keyword = mSearchEt.getText().toString();
                HistoryTag tag = new HistoryTag();
                tag.setTag(keyword);
                mDBManager.insertTag(tag);
                if (TextUtils.isEmpty(keyword)) {
                    Utils.getInstance().toast("搜索不能为空", getActivity());
                } else {
                    startSearch(uid, keyword);
                }
                break;
            case R.id.iv_delete:
                mSearchEt.setText("");
                break;
            case R.id.btn_delete_hit:
                mDBManager.deleteAllTag();
                if (mFlow.getChildCount() > 0) {
                    mFlow.removeAllViews();
                }
                break;
            default:
                if (v instanceof TextView) {
                    keyword = ((TextView) (v)).getText().toString();
                    startSearch(uid, keyword);
                }
                break;
        }
    }

    /**
     * 开始搜索
     *
     * @param uid
     * @param keyword
     */
    private void startSearch(String uid, String keyword) {
        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
        intent.putExtra("keyword", keyword);
        HttpReqData.getInstance().getSearchResult(uid, keyword, "0");
        getActivity().startActivity(intent);
    }

    /**
     * 成功获取到标签数据
     *
     * @param list
     */
    @Override
    public void getKeyTag(KeyTagList list) {
        List<KeyTag> tagArr = list.getTag_arr();
        for (int i = 0; i < tagArr.size(); i++) {
            KeyTag keyTag = tagArr.get(i);
            String tagName = keyTag.getTag_name();
            mTagList.add(tagName);
        }
        feedKeywords();
        mKeysFlow.go2Show(KeywordsFlow.ANIMATION_IN);
    }

    /**
     * 获取标签数据失败
     */
    @Override
    public void getError() {

    }
}
