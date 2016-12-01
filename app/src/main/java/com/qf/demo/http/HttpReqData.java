package com.qf.demo.http;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.factory.ComicContentFactory;
import com.qf.demo.factory.ComicInfoFactory;
import com.qf.demo.factory.CommentDataFactory;
import com.qf.demo.factory.KeyTagFactory;
import com.qf.demo.factory.MainDataFactory;
import com.qf.demo.factory.MonthRankDataFactory;
import com.qf.demo.factory.ReplyCommentDataFactory;
import com.qf.demo.factory.SearchResultFactory;
import com.qf.demo.factory.TopicCollectionFactory;
import com.qf.demo.factory.TopicComicDataFactory;
import com.qf.demo.factory.WeeklyUpdateDataFactory;

/**
 * 所有的Http请求
 * Created by Administrator on 2016/11/4.
 */
public class HttpReqData {
    private static HttpReqData mInstance = null;

    private HttpReqData() {

    }

    public static HttpReqData getInstance() {
        if (mInstance == null) {
            mInstance = new HttpReqData();
        }
        return mInstance;
    }

    public void getReqMainData() {
        String requestUrl = HttpReq.getInstance().RequestMainData();
        OkHttpUtils.get(requestUrl).execute(MainDataFactory.getInstance());
    }

    public void getReqMonthRank() {
        String requestUrl = HttpReq.getInstance().RequestMonthRank();
        OkHttpUtils.get(requestUrl).execute(MonthRankDataFactory.getInstance());
    }

    public void getReqComicInfo(String uid, String comicId) {
        String requestUrl = HttpReq.getInstance().RequestCataLogData(uid, comicId);
        OkHttpUtils.get(requestUrl).execute(ComicInfoFactory.getInstance());
    }

    public void getReqContentData(String uid, String comicId, String orderIdx) {
        String requestUrl = HttpReq.getInstance().ReqContentData(uid, comicId, orderIdx);
        OkHttpUtils.get(requestUrl).execute(ComicContentFactory.getInstance());
    }

    public void getReqCommentData(String id, String comicId, String pageNum) {
        String requestUrl = HttpReq.getInstance().ReqCommentData(id, comicId, pageNum);
        OkHttpUtils.get(requestUrl).execute(CommentDataFactory.getInstance());
    }

    public void sendCommentData(String id, String comicId, String orderId, String comment, StringCallback
            callback) {
        String requestUrl = HttpReq.getInstance().ReqSendComment(id, comicId, orderId, comment);
        OkHttpUtils.get(requestUrl).execute(callback);
    }

    public void replyComment(String id, String commentId, String comment, StringCallback callback) {
        String requestUrl = HttpReq.getInstance().ReqReplyComment(id, commentId, comment);
        OkHttpUtils.get(requestUrl).execute(callback);
    }

    public void getReplyCommentList(String id, String commentId, String pageNum) {
        String requestUrl = HttpReq.getInstance().ReqReplyCommentListData(id, commentId, pageNum);
        OkHttpUtils.get(requestUrl).execute(ReplyCommentDataFactory.getInstance());
    }

    public void getLikeComicData(String id, String comicId, StringCallback callback) {
        String requestUrl = HttpReq.getInstance().ReqLikeComic(id, comicId);
        OkHttpUtils.get(requestUrl).execute(callback);
    }

    public void getWeeklyUpdate() {
        String requestUrl = HttpReq.getInstance().ReqWeeklyUpdateData();
        OkHttpUtils.get(requestUrl).execute(WeeklyUpdateDataFactory.getInstance());
    }

    public void getTopicComicList(String topicId) {
        String requestUrl = HttpReq.getInstance().ReqToPicComicList(topicId);
        OkHttpUtils.get(requestUrl).execute(TopicComicDataFactory.getInstance());
    }

    public void getTopicCollection(String pageNum) {
        String requestUrl = HttpReq.getInstance().ReqTopPicData(pageNum);
        OkHttpUtils.get(requestUrl).execute(TopicCollectionFactory.getInstance());
    }

    public void getSearchTag(){
        String requestUrl = HttpReq.getInstance().RequestSearchTag();
        OkHttpUtils.get(requestUrl).execute(KeyTagFactory.getInstance());
    }

    public void getSearchResult(String uid,String keyword,String pageNum){
        String resultUrl = HttpReq.getInstance().RequestSearch(uid,keyword,pageNum);
        OkHttpUtils.get(resultUrl).execute(SearchResultFactory.getInstance());
    }

}
