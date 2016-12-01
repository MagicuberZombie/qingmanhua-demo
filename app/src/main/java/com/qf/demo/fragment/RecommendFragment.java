package com.qf.demo.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qf.demo.activity.CataLogActivity;
import com.qf.demo.activity.MyApp;
import com.qf.demo.R;
import com.qf.demo.activity.TopicCollectionActivity;
import com.qf.demo.activity.TopicComicListActivity;
import com.qf.demo.activity.WeeklyUpdateActivity;
import com.qf.demo.adapter.ImagePagerAdapter;
import com.qf.demo.adapter.RecommendGridAdapter;
import com.qf.demo.entities.ComicData;
import com.qf.demo.entities.HeadData;
import com.qf.demo.entities.TopicData;
import com.qf.demo.factory.IndexComicDataFactory;
import com.qf.demo.factory.IndexHeadDataFactory;
import com.qf.demo.factory.IndexTopDataFactory;
import com.qf.demo.http.BasicAttributes;
import com.qf.demo.http.HttpReqData;
import com.qf.demo.utils.PhoneTools;
import com.qf.demo.view.AutoScrollViewPager;
import com.qf.demo.view.MyGridView;
import com.qf.demo.view.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐内容的那个碎片
 */
public class RecommendFragment extends Fragment
        implements ViewPager.OnPageChangeListener, View.OnScrollChangeListener
        , ImagePagerAdapter.ItemClickCallBack, AdapterView.OnItemClickListener, View.OnClickListener {

    private AutoScrollViewPager mViewPager;
    private ImageView mHotTitle;
    private MyGridView mHotGrid;
    private ImageView mWeeklyUpdate;
    private ImageView mEditorRecommendTitle;
    private MyGridView mEditorGrid;
    private LinearLayout mTopicGroup;
    private MyGridView mLikeGrid;
    private ImageView mLikeTitle;

    private List<String> indexHeadPics = new ArrayList<>();
    private List<HeadData> headDataList = new ArrayList<>();
    private ImagePagerAdapter adapter;
    private LinearLayout pointViewGroup;
    private ScrollView mScrollView;

    private MyApp app;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        app = (MyApp) getActivity().getApplication();
        setListener();
        initData();
        setAdapter();
    }

    /**
     * 寻找控件
     *
     * @param view
     */
    private void initView(View view) {
        mViewPager = (AutoScrollViewPager) view.findViewById(R.id.auto_vp);
        pointViewGroup = (LinearLayout) view.findViewById(R.id.viewGroup);
        mHotTitle = (ImageView) view.findViewById(R.id.iv_hot);
        mHotGrid = (MyGridView) view.findViewById(R.id.recommend_hot);
        mWeeklyUpdate = (ImageView) view.findViewById(R.id.iv_week_update);
        mEditorRecommendTitle = (ImageView) view.findViewById(R.id.iv_editor);
        mEditorGrid = (MyGridView) view.findViewById(R.id.recommend_editor);
        mTopicGroup = (LinearLayout) view.findViewById(R.id.ll_main);
        mLikeGrid = (MyGridView) view.findViewById(R.id.recommend_like);
        mLikeTitle = (ImageView) view.findViewById(R.id.iv_like);
        mScrollView = (ScrollView) view.findViewById(R.id.scroll);
    }

    /**
     * 给各大控件设置监听
     */
    private void setListener() {
        mViewPager.addOnPageChangeListener(this);
        mScrollView.setOnScrollChangeListener(this);
        mWeeklyUpdate.setOnClickListener(this);
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {

    }


    /**
     * 给各大控件设置数据
     */
    private void initData() {
        IndexHeadDataFactory.getInstance().setCallBack(new IndexHeadDataFactory.GetIndexHeadDataCallBack() {

            @Override
            public void getIndexHeadPics(List<HeadData> data) {
                headDataList = data;
                List<String> pics = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    String activePicUrl = data.get(i).getActive_pic_url_2();
                    activePicUrl = BasicAttributes.HTTPNAME + activePicUrl;
                    pics.add(activePicUrl);
                }
                initHead(pics);
            }
        });
        IndexComicDataFactory.getInstance().setCallBack(new IndexComicDataFactory.GetComicDataCallBack() {
            @Override
            public void getIndexComicData(String[] titlePicUrl,
                                          List<ComicData> hotComicArr, List<ComicData> likeComicArr,
                                          List<ComicData> recommendComicArr) {
                initHotTitle(titlePicUrl[0]);
                initHotGrid(hotComicArr);
                initLikeTitle(titlePicUrl[1]);
                initLikeGrid(likeComicArr);
                initEditorTitle(titlePicUrl[2]);
                initEditorGrid(recommendComicArr);
                initWeeklyTitle(titlePicUrl[3]);
            }
        });
        IndexTopDataFactory.getInstance().setCallBack(new IndexTopDataFactory.GetTopDataCallBack() {
            @Override
            public void getIndexTopicData(List<TopicData> topic) {
                initTopic(topic);
            }
        });
    }

    /**
     * 设置猜你喜欢的内容
     *
     * @param likeComicArr
     */
    private void initLikeGrid(List<ComicData> likeComicArr) {
        RecommendGridAdapter adapter = new RecommendGridAdapter(getActivity(), likeComicArr);
        mLikeGrid.setAdapter(adapter);
        mLikeGrid.setOnItemClickListener(this);
    }

    /**
     * 设置那个猜你喜欢的标题
     *
     * @param url
     */
    private void initLikeTitle(String url) {
        url = BasicAttributes.HTTPNAME + url;
        Glide.with(getActivity()).load(url).into(mLikeTitle);
    }

    /**
     * 初始化专题栏目数据
     *
     * @param topic
     */
    private void initTopic(List<TopicData> topic) {
        for (int i = 0; i < topic.size(); i++) {
            LinearLayout childGroup = new LinearLayout(getActivity());
            childGroup.setOrientation(LinearLayout.VERTICAL);
            SelectableRoundedImageView imageView = new SelectableRoundedImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    PhoneTools.getInstance().dip2px(getActivity(), 140),
                    PhoneTools.getInstance().dip2px(getActivity(), 90)
            );
            params.setMarginEnd(PhoneTools.getInstance().dip2px(getActivity(), 10));
            float corner = getResources().getDimension(R.dimen.img_rounded);
            imageView.setCornerRadiiDP(corner, corner, corner, corner);

            TextView topicName = new TextView(getActivity());
            final String name = topic.get(i).getTopic_name();
            topicName.setText(name);
            topicName.setTextColor(getResources().getColor(R.color.list_works));
            topicName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            imageView.setLayoutParams(params);

            childGroup.addView(imageView);
            childGroup.addView(topicName);

            String topicPicUrl = topic.get(i).getTopic_pic_url();
            topicPicUrl = BasicAttributes.HTTPNAME + topicPicUrl;
            Glide.with(getActivity()).load(topicPicUrl).into(imageView);

            final String topicId = topic.get(i).getId();

            childGroup.setClickable(true);
            if (i < topic.size() - 1) {
                childGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//处理专题栏的点击事件
                        HttpReqData.getInstance().getTopicComicList(topicId);
                        Intent intent = new Intent(getActivity(), TopicComicListActivity.class);
                        intent.putExtra("title", name);
                        startActivity(intent);
                    }
                });
            } else {
                childGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//点击更多
                        HttpReqData.getInstance().getTopicCollection("0");
                        Intent intent = new Intent(getActivity(), TopicCollectionActivity.class);
                        startActivity(intent);
                    }
                });
            }
            mTopicGroup.addView(childGroup);
        }
    }

    /**
     * 设置编辑推荐的内容
     *
     * @param recommendComicArr
     */
    private void initEditorGrid(List<ComicData> recommendComicArr) {
        RecommendGridAdapter gridAdapter = new RecommendGridAdapter(getActivity(), recommendComicArr);
        mEditorGrid.setAdapter(gridAdapter);
        mEditorGrid.setOnItemClickListener(this);
    }

    /**
     * 设置编辑推荐的那个标题
     *
     * @param url
     */
    private void initEditorTitle(String url) {
        url = BasicAttributes.HTTPNAME + url;
        Glide.with(getActivity()).load(url).into(mEditorRecommendTitle);
    }

    /**
     * 设置一周更新的那个标题
     *
     * @param url
     */
    private void initWeeklyTitle(String url) {
        url = BasicAttributes.HTTPNAME + url;
        Glide.with(getActivity()).load(url).into(mWeeklyUpdate);
    }

    /**
     * 初始化最热内容
     *
     * @param hotComicArr
     */
    private void initHotGrid(List<ComicData> hotComicArr) {
        RecommendGridAdapter gridAdapter = new RecommendGridAdapter(getActivity(), hotComicArr);
        mHotGrid.setAdapter(gridAdapter);
        mHotGrid.setOnItemClickListener(this);
    }

    /**
     * 初始化最热标题
     *
     * @param url
     */
    private void initHotTitle(String url) {
        url = BasicAttributes.HTTPNAME + url;
        Glide.with(getActivity()).load(url).into(mHotTitle);
    }

    /**
     * 初始化推广栏
     *
     * @param pics
     */
    private void initHead(List<String> pics) {
        indexHeadPics.addAll(pics);
        adapter = new ImagePagerAdapter(getActivity(), indexHeadPics).setIsInfinite(true);
        mViewPager.setAdapter(adapter);
        for (int i = 0; i < pics.size(); i++) {//针对每一个广告图设置一个Point
            ImageView point = new ImageView(getActivity());
            if (i == 0) {
                point.setImageResource(R.drawable.game_white);
            } else {
                point.setImageResource(R.drawable.game_black);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
            point.setLayoutParams(params);
            point.setTag(i);
            pointViewGroup.addView(point);
        }
        adapter.setItemClickCallBack(this);
        adapter.notifyDataSetChanged();
        mViewPager.startScroll();//自动ViewPager开始滚动
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 同步推广栏里面的图片和Point
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        if (indexHeadPics.size() != 0) {
            int index = position % indexHeadPics.size();
            for (int i = 0; i < pointViewGroup.getChildCount(); i++) {
                ImageView child = (ImageView) pointViewGroup.getChildAt(i);
                if ((int) child.getTag() == index) {
                    child.setImageResource(R.drawable.game_white);
                } else {
                    child.setImageResource(R.drawable.game_black);
                }

            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
    }

    /**
     * 点击推广栏里面的图片回调
     *
     * @param v
     */
    @Override
    public void click(View v) {
        String activeDescUrl = headDataList.get((Integer) v.getTag()).getActive_desc_url();
        if (!activeDescUrl.equals("")) {
            Uri uri = Uri.parse(activeDescUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW
                    , uri);
            startActivity(intent);
        } else {
            String comicId = headDataList.get((Integer) v.getTag()).getComic_id();
            String id = app.getPreferences().getString("id", "");
            HttpReqData.getInstance().getReqComicInfo(id, comicId);
            Intent intent = new Intent(getActivity(), CataLogActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 点击GridView里面的Item观看漫画
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String uid = app.getPreferences().getString("id", "");
        RecommendGridAdapter.ViewHolder holder = (RecommendGridAdapter.ViewHolder) view.getTag();
        String comicId = (String) holder.comicName.getTag();
        HttpReqData.getInstance().getReqComicInfo(uid, comicId);
        Intent intent = new Intent(getActivity(), CataLogActivity.class);
        intent.putExtra("id", uid);
        startActivity(intent);
    }

    /**
     * 点击了一周追番的那个图
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), WeeklyUpdateActivity.class);
        startActivity(intent);
    }
}
