package example.wxx.com.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * 作者：wengxingxia
 * 时间：2017/5/24 0024 08:52
 */

public class DaoSupportFactory {

    private static DaoSupportFactory mFactory;

    private SQLiteDatabase mSQLiteDatabase;

    private DaoSupportFactory() {

//        把数据放到内存卡中,判断是否有存储卡，6.0版本后要动态申请权限
        File dbRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "nhdz" + File.separator + "database");

        if (!dbRoot.exists()) {
            dbRoot.mkdirs();
        }

        File dbFile = new File(dbRoot, "nhdz.db");


//      打开或者创建一个数据库
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);

    }

    public static DaoSupportFactory getFactory() {
        synchronized (DaoSupportFactory.class) {
            if (mFactory == null) {
                mFactory = new DaoSupportFactory();
            }
        }
        return mFactory;
    }

    public <T> IDaoSupport getDao(Class<T> clazz) {
        IDaoSupport<T> daoSupport = new DaoSupport<T>();
        daoSupport.init(mSQLiteDatabase,clazz);
        return daoSupport;
    }
}
