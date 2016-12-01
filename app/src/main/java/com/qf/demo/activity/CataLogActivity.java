package com.qf.demo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.R;
import com.qf.demo.adapter.MyCataLogAdapter;
import com.qf.demo.db.DBManager;
import com.qf.demo.entities.ComicInfo;
import com.qf.demo.entities.History;
import com.qf.demo.factory.ComicInfoFactory;
import com.qf.demo.http.BasicAttributes;
import com.qf.demo.http.HttpReqData;
import com.qf.demo.utils.FastBlurUtil;
import com.qf.demo.utils.Utils;
import com.qf.demo.view.SelectableRoundedImageView;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 显示漫画信息的页面
 */
public class CataLogActivity extends AppCompatActivity
        implements ComicInfoFactory.GetComicInfoCallBack, AdapterView.OnItemClickListener {


    private SelectableRoundedImageView mComicImg;
    private ImageView mBgImg;

    private TextView mWorksName;
    private TextView mAuthorName;
    private TextView mReadNum;

    private TextView mComicDesc;
    private TextView mGoRead;

    private ImageView mSpread;
    private ImageView mShrink;

    private GridView mCataLogGrid;

    private String comicId;
    private MyCataLogAdapter adapter;
    private ComicInfo comicInfo;
    private String lastOrderidx;

    private int curPage = -1;
    private History history;
    private DBManager mDBManager;

    private ImageView mSubImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        init();
    }

    /**
     * 初始化工作
     */
    private void init() {
        initView();
        initData();
        setListener();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mDBManager = DBManager.getInstance(CataLogActivity.this);
        ComicInfoFactory.getInstance().setCallBack(this);
        mCataLogGrid.setNumColumns(4);
    }

    /**
     * 设置监听
     */
    private void setListener() {

    }

    /**
     * 寻找控件
     */
    private void initView() {
        mComicImg = (SelectableRoundedImageView) findViewById(R.id.iv_top_img);
        mBgImg = (ImageView) findViewById(R.id.iv_top_img_bg);
        mReadNum = (TextView) findViewById(R.id.tv_read_num_catalog);
        mWorksName = (TextView) findViewById(R.id.tv_works_name);
        mAuthorName = (TextView) findViewById(R.id.tv_author);
        mComicDesc = (TextView) findViewById(R.id.text_content);
        mGoRead = (TextView) findViewById(R.id.tv_go_on_read);
        mSpread = (ImageView) findViewById(R.id.spread);
        mShrink = (ImageView) findViewById(R.id.shrink_up);
        mCataLogGrid = (GridView) findViewById(R.id.grid_catalog);
        mSubImage = (ImageView) findViewById(R.id.tv_sub);
    }

    /**
     * 初始化收藏控件
     */
    private void initSubImage() {
        if (mDBManager.isSub(comicId)) {
            mSubImage.setSelected(true);
        }

    }

    /**
     * 有ComicInfo数据时回调
     *
     * @param info
     */
    @Override
    public void getComicInfo(ComicInfo info) {
        comicId = info.getId();
        try {
            String orderId = DBManager.getInstance(CataLogActivity.this).queryOrderId(comicId);
            if (orderId != null) {
                curPage = Integer.parseInt(orderId);
            }
        } catch (IndexOutOfBoundsException e) {
            curPage = -1;
        }

        lastOrderidx = info.getComic_last_orderidx();
        history = new History();
        history.setComicPagNum(lastOrderidx);
        history.setComicAuthor(info.getPainter_user_nickname());
        history.setComicCover(info.getComic_pic_240_320());
        history.setComicId(info.getId());
        history.setComicName(info.getComic_name());
        initSubImage();
        initHead(info);
        initGrid(info);
    }

    /**
     * 给目录GridView设置数据
     *
     * @param info
     */
    private void initGrid(ComicInfo info) {
        comicInfo = info;
        adapter = new MyCataLogAdapter(
                CataLogActivity.this, comicInfo.getComic_order_arr());
        mCataLogGrid.setAdapter(adapter);
        if (curPage != -1) {
            adapter.setCurItem(curPage + "");
        }
        mCataLogGrid.setOnItemClickListener(this);
    }

    /**
     * 初始化顶部视图
     *
     * @param info
     */
    private void initHead(ComicInfo info) {
        String comicName = info.getComic_name();
        mWorksName.setText(comicName);
        String painterUserNickname = info.getPainter_user_nickname();
        mAuthorName.setText(painterUserNickname);
        String comicPic = info.getComic_pic_300_300();
        comicPic = BasicAttributes.HTTPNAME + comicPic;
        Glide.with(CataLogActivity.this).load(comicPic).asBitmap().into(mComicImg);
        String comicPicBg = info.getComic_pic_720_520();
        comicPicBg = BasicAttributes.HTTPNAME + comicPicBg;
        Glide.with(CataLogActivity.this).load(comicPicBg).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bitmap = FastBlurUtil.doBlur(resource, 10, false);//设置毛玻璃效果
                mBgImg.setImageBitmap(bitmap);
            }
        });
        String score = info.getScore();
        float scoreFloat = Float.parseFloat(score);
        float num = scoreFloat / 10000;
        String rel = String.format("%.2f", num) + "万";
        mReadNum.setText(rel);

        String comicDesc = info.getComic_desc();
        mComicDesc.setText(comicDesc);
        if (curPage == -1) {
            mGoRead.setText("开始阅读");
        } else {
            mGoRead.setText("继续阅读");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (curPage == -1) {
            mGoRead.setText("开始阅读");
        } else {
            mGoRead.setText("继续阅读");
        }
    }

    /**
     * 获取数据失败时回调
     */
    @Override
    public void getError() {

    }

    private int clickNum = 0;

    /**
     * 点击简介时回调
     *
     * @param view
     */
    public void clickText(View view) {
        clickNum++;
        if (clickNum % 2 == 1) {
            mComicDesc.setMaxLines(8);
            mSpread.setVisibility(View.GONE);
            mShrink.setVisibility(View.VISIBLE);
        } else {
            mComicDesc.setMaxLines(2);
            mSpread.setVisibility(View.VISIBLE);
            mShrink.setVisibility(View.GONE);
        }

    }

    /**
     * 点击阅读时回调
     */
    public void goRead(View view) {
        Intent intent = new Intent(CataLogActivity.this, ComicPageActivity.class);
        if (curPage != -1) {
            intent.putExtra("orderIdx", curPage + "");
        } else {
            intent.putExtra("orderIdx", "1");
            curPage = 1;
        }
        intent.putExtra("comicId", comicId);
        intent.putExtra("arr", comicInfo);
        intent.putExtra("lastOrderIdx", lastOrderidx);
        if (isFirst) {
            history.setOrderId("1");
            mDBManager.insertOneData(history);
            isFirst = false;
        }
        List<History> list = mDBManager.queryAll();
        System.out.println(list);
        startActivityForResult(intent, REQUEST_PAGE);
    }

    /**
     * 点击喜欢
     *
     * @param view
     */
    public void clickLike(View view) {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        HttpReqData.getInstance().getLikeComicData(id, comicId, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.getInstance().toast("点赞成功", CataLogActivity.this);
                    }
                });
            }
        });
    }

    /**
     * 点击收藏
     *
     * @param view
     */
    public void clickSub(View view) {
        if (mDBManager.isSub(comicId)) {
            mDBManager.setSub(comicId, false);
            mSubImage.setSelected(false);
        } else {
            mDBManager.setSub(comicId, true);
            mSubImage.setSelected(true);
        }
    }

    /**
     * 点击一键分享
     *
     * @param view
     */
    public void clickShare(View view) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();

        oks.setTitle("测试分享");
        oks.setText("轻漫画");
        oks.setUrl("http://m.comicq.cn/index.php/Index/read/comic_id/" + comicId + "/order_idx/" +
                1 + ".html");
        oks.show(this);
    }

    /**
     * 点击下载
     *
     * @param view
     */
    public void clickDL(View view) {
        Utils.getInstance().toast("这个轻漫画app原本就没有把下载功能做出来,我为什么要做!?,╭(╯^╰)╮"
                , CataLogActivity.this);
    }

    /**
     * 点击返回
     *
     * @param view
     */
    public void clickBack(View view) {
        finish();
    }

    //请求返回目录的请求码
    private static final int REQUEST_PAGE = 0;

    private boolean isFirst = true;

    /**
     * 点击目录选择章节
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
        MyCataLogAdapter adapter = (MyCataLogAdapter) mCataLogGrid.getAdapter();
        adapter.setCurItem(orderId);
        adapter.notifyDataSetChanged();
        curPage = Integer.parseInt(orderId);
        if (isFirst) {
            history.setOrderId(orderId);
            mDBManager.insertOneData(history);
            isFirst = false;
        }
        mDBManager.setCurPage(comicId, orderId);
        Intent intent = new Intent(CataLogActivity.this, ComicPageActivity.class);
        intent.putExtra("orderIdx", curPage + "");
        intent.putExtra("comicId", comicId);
        intent.putExtra("arr", comicInfo);
        intent.putExtra("lastOrderIdx", lastOrderidx);
        startActivityForResult(intent, REQUEST_PAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PAGE && resultCode == RESULT_OK && data != null) {
            String curPageStr = data.getStringExtra("curPage");
            System.out.println(curPageStr);
            curPage = Integer.parseInt(curPageStr);
            mDBManager.setCurPage(comicId, curPageStr);
            MyCataLogAdapter adapter = (MyCataLogAdapter) mCataLogGrid.getAdapter();
            adapter.setCurItem(curPageStr);
            adapter.notifyDataSetChanged();
        }
    }
}
