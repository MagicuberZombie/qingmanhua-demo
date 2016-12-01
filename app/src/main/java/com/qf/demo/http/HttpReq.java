package com.qf.demo.http;

import android.net.Uri;

import com.qf.demo.utils.Encryption;


public class HttpReq {
    private static HttpReq mInstance = null;

    private HttpReq() {

    }

    public static HttpReq getInstance() {
        if (mInstance == null) {
            mInstance = new HttpReq();
        }
        return mInstance;
    }

    public String RequestMainData() {
        long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.0.5" + currentTime + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=appindex" + "&version=" + "4.0.5"
                + "&apitime=" + currentTime + "&sign=" + sign
                + "&from_os=" + "android";
    }

    public String RequestMonthRank() {
        long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.0.5" + currentTime + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=monthrank" + "&version=" + "4.0.5"
                + "&apitime=" + currentTime + "&sign=" + sign
                + "&from_os=" + "android";
    }

    public String RequestCataLogData(String uid, String comicId) {
        long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.5" + currentTime + uid + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=comicinfo" + "&version=" + "4.5" + "&apitime=" + currentTime
                + "&comic_id=" + comicId + "&uid=" + uid + "&sign=" + sign
                + "&from_os=" + "android";
    }

    public String ReqContentData(String uid, String comicId, String orderId) {
        long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.5" + currentTime +
                comicId + orderId + uid + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=comiccontent" + "&version=" + "4.5" + "&apitime=" + currentTime
                + "&comicid=" + comicId + "&orderidx=" + orderId + "&uid=" + uid
                + "&sign=" + sign + "&from_os=" + "android";
    }

    public String ReqCommentData(String id, String comicId, String pageNum) {
        Long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.0.5" + currentTime + comicId + id + pageNum
                + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=comiccomment" + "&version=" + "4.0.5" + "&apitime=" + currentTime
                + "&comicid=" + comicId + "&uid=" + id + "&page_num="
                + pageNum + "&sign=" + sign + "&from_os=" + "android";
    }

    public String ReqSendComment(String id, String comicId, String orderId, String
            comment) {
        Long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.0.5" + currentTime + id + "android" + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=writecomment" + "&version=" + "4.0.5" + "&apitime=" + currentTime
                + "&comicid=" + comicId + "&orderidx=" + orderId + "&from_os="
                + "android" + "&uid=" + id + "&content=" + Uri.parse(comment)
                + "&sign=" + sign + "&from_os=" + "android";
    }

    public String ReqReplyComment(String id, String commentId, String comment) {
        Long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.0.5" + currentTime + id + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=writereplycomment" + "&version=" + "4.0.5" + "&apitime=" + currentTime
                + "&comment_id=" + commentId + "&uid=" + id + "&content="
                + Uri.encode(comment) + "&sign=" + sign + "&from_os=" + "android";
    }

    public String ReqReplyCommentListData(String id, String commentId, String pageNum) {
        long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.0.5" + currentTime + commentId + id + pageNum +
                BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=replycomment" + "&version=" + "4.0.5" + "&apitime=" + currentTime + "&uid="
                + id + "&page_num=" + pageNum + "&sign=" + sign +
                "&comment_id=" + commentId + "&from_os=" + "android";
    }

    public String ReqLikeComic(String id, String comicId) {
        long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.0.5" + comicId + currentTime + id + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME +
                "&a=userlikecomic" + "&version=" + "4.0.5"
                + "&apitime=" + currentTime + "&comicid=" + comicId
                + "&uid=" + id + "&sign=" + sign + "&from_os=" + "android";
    }

    public String ReqWeeklyUpdateData() {
        long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.0.5" + currentTime + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=weekupdatelist" + "&version=" + "4.0.5" + "&apitime="
                + currentTime + "&sign=" + sign + "&from_os=" + "android";
    }

    public String ReqToPicComicList(String topId) {
        long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.0.5" + currentTime + topId + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME +
                "&a=topiccomiclist" + "&version=" + "4.0.5" + "&apitime="
                + currentTime + "&topic_id=" + topId + "&sign=" + sign + "&from_os=" + "android";
    }

    public String ReqTopPicData(String pageNum) {
        long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.0.5" + currentTime + pageNum + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=topiclist" + "&version=" + "4.0.5" + "&apitime="
                + currentTime + "&sign=" + sign + "&page_num=" + pageNum + "&from_os=" + "android";
    }

    public String RequestSearchTag() {
        long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.0.5" + currentTime + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=searchtag" + "&version=" + "4.0.5" + "&apitime="
                + currentTime + "&sign=" + sign + "&from_os=" + "android";
    }

    public String RequestSearch(String uid, String keyword, String pageNum) {
        long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5("4.0.5" + currentTime + uid + BasicAttributes.SIGNKEY);
        return BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=searchcomic" + "&version=" + "4.0.5" + "&apitime="
                + currentTime + "&keyword=" + Uri.encode(keyword) + "&page_num="
                + pageNum + "&uid=" + uid + "&sign=" + sign + "&from_os=" + "android";
    }

}
