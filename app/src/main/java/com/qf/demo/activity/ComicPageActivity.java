package com.qf.demo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qf.demo.R;
import com.qf.demo.adapter.ComicListAdapter;
import com.qf.demo.adapter.MyCataLogAdapter;
import com.qf.demo.entities.ComicInfo;
import com.qf.demo.entities.ContentArr;
import com.qf.demo.entities.ContentData;
import com.qf.demo.factory.ComicContentFactory;
import com.qf.demo.http.HttpReqData;
import com.qf.demo.utils.PhoneTools;
import com.qf.demo.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;

/**
 * 显示漫画每页内容的页面
 */
public class ComicPageActivity extends AppCompatActivity
        implements ComicContentFactory.GetContentArrCallBack,
        SwipeRefreshLayout.OnRefreshListener,
        AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener {

    private MyApp app;
    private ListView mContentList;
    private SwipeRefreshLayout mSwipeLayout;
    private ComicListAdapter adapter;
    private String uid;
    private String comicId;
    private String orderIdx;
    private List<ContentData> contentArr = new ArrayList<>();
    private ContentArr mArr;

    private PopupWindow mPopUpWin;

    private TextView mOrderName;
    private int curPage;
    private int pageNum;
    private Intent mIntent;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_page);
        app = (MyApp) getApplication();
        init();
    }

    /**
     * 初始化工作
     */
    private void init() {
        initView();
        setListener();
        initData();
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        mSwipeLayout.setOnRefreshListener(this);
        mContentList.setOnScrollListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mIntent = getIntent();
        uid = app.getPreferences().getString("id", "");
        comicId = mIntent.getStringExtra("comicId");
        orderIdx = mIntent.getStringExtra("orderIdx");
        curPage = Integer.parseInt(orderIdx);
        initTitle();
        initPopUpWin();

        HttpReqData.getInstance().getReqContentData(uid, comicId, orderIdx);
        ComicContentFactory.getInstance().setCallBack(this);

        adapter = new ComicListAdapter(ComicPageActivity.this, contentArr);
        mContentList.setAdapter(adapter);
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        String title = "第" + curPage + "话";
        mOrderName.setText(title);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mContentList = (ListView) findViewById(R.id.lv_con);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mOrderName = (TextView) findViewById(R.id.tv_order_name_one);
    }

    /**
     * 初始化PopUpWindow
     */
    private void initPopUpWin() {
        View root = LayoutInflater.from(ComicPageActivity.this)
                .inflate(R.layout.popup_layout, null);
        ComicInfo info = (ComicInfo) mIntent.getSerializableExtra("arr");
        MyCataLogAdapter adapter = new MyCataLogAdapter(ComicPageActivity.this,
                info.getComic_order_arr());
        int width = PhoneTools.getInstance().getPhoneScreenSize(ComicPageActivity.this).x;
        mPopUpWin = new PopupWindow(root, width, 1000);
        mGridView = (GridView) root.findViewById(R.id.gv_connect);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
    }

    /**
     * 获取到解析数据时回调
     *
     * @param arr
     */
    @Override
    public void getContentArr(ContentArr arr) {
        mArr = arr;
        initList();
    }

    /**
     * 初始化ListView
     */
    private void initList() {
        contentArr.clear();
        if (mSwipeLayout.isRefreshing()) {
            mSwipeLayout.setRefreshing(false);
        }
        contentArr.addAll(mArr.getContent_arr());
        adapter.notifyDataSetChanged();
        String lastOrderIdx = mIntent.getStringExtra("lastOrderIdx");
        pageNum = Integer.parseInt(lastOrderIdx);
        MyCataLogAdapter gridAdapter = (MyCataLogAdapter) mGridView.getAdapter();
        gridAdapter.setCurItem(curPage + "");
        initTitle();
    }

    /**
     * 获取数据失败时回调
     */
    @Override
    public void getError() {

    }

    /**
     * 下拉刷新时调用
     */
    @Override
    public void onRefresh() {
        showPre();
    }

    /**
     * 显示上一话内容
     */
    private void showPre() {
        curPage--;
        if (curPage == 0) {
            Utils.getInstance().toast("已经是第一话了", ComicPageActivity.this);
        } else {
            HttpReqData.getInstance().getReqContentData(uid, comicId, curPage + "");
            initList();
        }
    }


    /**
     * 处理按返回键时的情境
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPopUpWin.isShowing()) {
                mPopUpWin.dismiss();
                return false;
            }
            Intent intent = new Intent();
            intent.putExtra("curPage", curPage + "");
            setResult(RESULT_OK,intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 点击查看目录
     *
     * @param view
     */
    public void clickCataLog(View view) {
        mPopUpWin.showAtLocation(findViewById(R.id.ll_menu), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 点击评论
     *
     * @param view
     */
    public void clickComment(View view) {
        Intent intent = new Intent(ComicPageActivity.this, ComicCommentActivity.class);
        intent.putExtra("id", uid);
        intent.putExtra("comicId", comicId);
        startActivity(intent);
    }


    /**
     * 点击看上一话
     *
     * @param view
     */
    public void clickPre(View view) {
        showPre();
    }

    /**
     * 点击看下一话
     *
     * @param view
     */
    public void clickNext(View view) {
        showNext();
    }

    /**
     * 点击分享
     *
     * @param view
     */
    public void clickShare(View view) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();

        oks.setTitle("测试分享");
        oks.setText("轻漫画");
        oks.setUrl("http://m.comicq.cn/index.php/Index/read/comic_id/" + comicId + "/order_idx/" +
                orderIdx + ".html");
        oks.show(this);
    }

    private int clickMode = 0;

    /**
     * 点击切换横竖屏
     *
     * @param view
     */
    public void clickChangeMode(View view) {
        clickMode++;
        if (clickMode % 2 == 1) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * 点击返回
     *
     * @param view
     */
    public void clickBack(View view) {
        Intent intent = new Intent();
        intent.putExtra("curPage", curPage + "");
        setResult(RESULT_OK, intent);
        finish();
    }

    //标记ListView是否滑到了底部
    private Boolean isBottom = false;

    /**
     * ListView滚动状态改变时调用
     *
     * @param view
     * @param scrollState
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isBottom && scrollState == SCROLL_STATE_IDLE) {
            showNext();
        }
    }

    /**
     * 展示下一话
     */
    private void showNext() {
        curPage++;
        System.out.println(pageNum);
        if (curPage >= pageNum + 1) {
            Utils.getInstance().toast("已到最新一话", ComicPageActivity.this);
        } else {
            HttpReqData.getInstance().getReqContentData(uid, comicId, curPage + "");
            initList();
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        isBottom = firstVisibleItem + visibleItemCount == totalItemCount;
    }

    /**
     * 点击目录时的处理
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyCataLogAdapter.ViewHolder holder = (MyCataLogAdapter.ViewHolder) view.getTag();
        String orderId = (String) holder.catalogName.getTag();
        MyCataLogAdapter adapter = (MyCataLogAdapter) mGridView.getAdapter();
        adapter.setCurItem(orderId);
        HttpReqData.getInstance().getReqContentData(uid, comicId, orderId);
        curPage = Integer.parseInt(orderId);
        if (mPopUpWin.isShowing()) {
            mPopUpWin.dismiss();
        }
    }
}
