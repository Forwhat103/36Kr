package com.zhuxiaoming.kr36.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nostra13.universalimageloader.utils.L;
import com.zhuxiaoming.kr36.base.MyApplication;

/**
 * Created by zhuxiaoming on 16/5/23.
 */
public class GreendaoSingle {
    private SQLiteDatabase database;// 数据库
    private DaoMaster daoMaster;// 管理者
    private DaoSession daoSession;// 会话者
    private LoginBeanDao loginBeanDao;// 数据库内相应表的操作对象
    private FavoriteBeanDao favoriteBeanDao;
    private Context context;
    private DaoMaster.DevOpenHelper helper;

    public DaoMaster.DevOpenHelper getHelper() {
        if (helper == null) {
            helper = new DaoMaster.DevOpenHelper(context, "Login.db", null);
        }
        return helper;
    }

    private static GreendaoSingle ourInstance = new GreendaoSingle();

    public static GreendaoSingle getInstance() {
        return ourInstance;
    }

    private GreendaoSingle() {
        context = MyApplication.getContext();
    }

    public SQLiteDatabase getDatabase() {
        if (database == null) {
            database = getHelper().getWritableDatabase();
        }
        return database;
    }

    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(getDatabase());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }

    public LoginBeanDao getLoginBeanDao() {
        if (loginBeanDao == null) {
            loginBeanDao = getDaoSession().getLoginBeanDao();
        }
        return loginBeanDao;
    }

    public FavoriteBeanDao getFavoriteBeanDao() {
        if (favoriteBeanDao == null) {
            favoriteBeanDao = getDaoSession().getFavoriteBeanDao();
        }
        return favoriteBeanDao;
    }
}
