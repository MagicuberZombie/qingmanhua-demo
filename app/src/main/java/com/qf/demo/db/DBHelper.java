package com.qf.demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.qf.demo.entities.History;
import com.qf.demo.entities.HistoryTag;
import com.qf.demo.entities.SubData;

import java.sql.SQLException;

public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "qm.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, History.class);
            TableUtils.createTable(connectionSource, SubData.class);
            TableUtils.createTable(connectionSource, HistoryTag.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
