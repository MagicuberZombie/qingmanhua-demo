package com.qf.demo.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.qf.demo.entities.History;
import com.qf.demo.entities.HistoryTag;
import com.qf.demo.entities.SubData;
import com.qf.demo.utils.Utils;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据库管理类
 * Created by Administrator on 2016/11/11.
 */
public class DBManager {
    private static DBManager mInstance = null;
    DBHelper helper;
    Dao mHistoryDao;
    Dao mSubDao;
    Dao mTagDao;
    Context mContext;

    private DBManager(Context context) {
        mContext = context;
        helper = new DBHelper(context);
        try {
            mHistoryDao = helper.getDao(History.class);
            mSubDao = helper.getDao(SubData.class);
            mTagDao = helper.getDao(HistoryTag.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBManager(context);
        }
        return mInstance;
    }

    /**
     * 插入一条数据
     *
     * @param history
     */
    public void insertOneData(History history) {
        try {
            String comicId = history.getComicId();
            boolean sub = this.isSub(comicId);
            if (mHistoryDao.isTableExists()) {//每个漫画只保持一条最新的记录
                DeleteBuilder deleteBuilder = mHistoryDao.deleteBuilder();
                deleteBuilder.where().in("comicId", comicId);
                deleteBuilder.delete();
            }
            if (mSubDao.isTableExists()) {
                DeleteBuilder deleteBuilder = mSubDao.deleteBuilder();
                deleteBuilder.where().in("comicId", comicId);
                deleteBuilder.delete();
            }
            history.setIsSub(sub);
            mHistoryDao.create(history);
            SubData subData = new SubData();
            getSubData(history, subData);
            mSubDao.create(subData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getSubData(History history, SubData subData) {
        subData.setIsSub(history.isSub());
        subData.setOrderId(history.getOrderId());
        subData.setComicName(history.getComicName());
        subData.setComicId(history.getComicId());
        subData.setComicAuthor(history.getComicAuthor());
        subData.setComicCover(history.getComicCover());
    }


    public List<SubData> queryForRealSub() {
        QueryBuilder queryBuilder = mSubDao.queryBuilder();
        try {
            List<SubData> list = queryBuilder.orderBy("id", false).where().in("isSub", true).query();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新浏览篇章的记录
     *
     * @param comicId
     * @param orderId
     */
    public void setCurPage(String comicId, String orderId) {
        UpdateBuilder updateBuilder = mHistoryDao.updateBuilder();
        try {
            updateBuilder.updateColumnValue("orderId", orderId);
            updateBuilder.where().in("comicId", comicId);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询上一次观看的页数
     *
     * @param comicId
     * @return
     */
    public String queryOrderId(String comicId) {
        QueryBuilder queryBuilder = mHistoryDao.queryBuilder();
        try {
            History history = (History) queryBuilder.where().in("comicId", comicId)
                    .query().get(0);
            return history.getOrderId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有数据
     */
    public List<History> queryAll() {
        try {
            QueryBuilder queryBuilder = mHistoryDao.queryBuilder();
            List<History> list = queryBuilder.orderBy("id", false).query();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新收藏数据
     *
     * @param comicId
     */
    public void setSub(String comicId, boolean isSub) {
        UpdateBuilder historyUpdate = mHistoryDao.updateBuilder();
        UpdateBuilder subUpdate = mSubDao.updateBuilder();
        try {
            historyUpdate.updateColumnValue("isSub", isSub);
            historyUpdate.where().in("comicId", comicId);
            historyUpdate.update();

            subUpdate.updateColumnValue("isSub", isSub);
            subUpdate.where().in("comicId", comicId);
            subUpdate.update();
            if (isSub) {
                Utils.getInstance().toast("已收藏", mContext);
            } else {
                Utils.getInstance().toast("取消收藏", mContext);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询是否收藏
     *
     * @param comicId
     * @return
     */
    public boolean isSub(String comicId) {
        QueryBuilder queryBuilder = mSubDao.queryBuilder();
        try {
            List<SubData> list = queryBuilder.where().in("comicId", comicId).query();
            if (list.size() != 0) {
                return list.get(0).isSub();
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据Id删除某项数据
     *
     * @param comicId
     */
    public void deleteOneData(String comicId, int type) {
        DeleteBuilder deleteBuilder = mHistoryDao.deleteBuilder();
        if (type == 1) {
            deleteBuilder = mSubDao.deleteBuilder();
        }
        try {
            deleteBuilder.where().in("comicId", comicId);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入一条标签
     *
     * @param tag
     */
    public void insertTag(HistoryTag tag) {
        try {
            if (mTagDao.isTableExists()) {
                DeleteBuilder deleteBuilder = mTagDao.deleteBuilder();
                deleteBuilder.where().in("tag", tag.getTag());
                deleteBuilder.delete();
            }
            mTagDao.create(tag);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回所有标签
     *
     * @return
     */
    public List<HistoryTag> queryAllTag() {
        try {
            List<HistoryTag> list = mTagDao.queryForAll();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除所有标签
     */
    public void deleteAllTag() {
        DeleteBuilder deleteBuilder = mTagDao.deleteBuilder();
        try {
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
