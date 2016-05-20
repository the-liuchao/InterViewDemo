package com.the_liuchao.interview.main;

import android.app.Application;
import android.os.Environment;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created by hp on 2016/5/19.
 */
public class InterApplication extends Application {
    private final static int DB_VERSION = 1;
    private static InterApplication application;
    static DbManager db;
    static DbManager.DaoConfig daoConfig;

    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        initDbManager();
    }

    private void initDbManager() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("interview_db")    //设置数据库名称
                .setDbDir(new File(Environment.getDataDirectory() + "/interview/db"))  //数据库路径
                .setDbVersion(DB_VERSION)  //数据库版本
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // db.addColumn(...);
                        // db.dropTable(...);
                    }
                });
    }

    public static DbManager getDb() {
        if (db == null) {
            db = x.getDb(daoConfig);
        }
        return db;
    }
}
