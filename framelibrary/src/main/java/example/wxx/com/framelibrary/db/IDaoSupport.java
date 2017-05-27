package example.wxx.com.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import example.wxx.com.framelibrary.db.curd.QuerySupport;

/**
 * 作者：wengxingxia
 * 时间：2017/5/23 0023 14:11
 */

public interface IDaoSupport<T> {

    /***
     *插入数据
     */
    long insert(T t);

    void init(SQLiteDatabase sqliteDatabase, Class<T> clazz);

    void insertList(List<T> datas);

    /**
     * 获取专门查询的支持类
     * @return
     */
    QuerySupport<T> querySupport();

    /**
     * 删除
     * @param whereClause where语句
     * @param whereArgs where语句中的参数
     * @return
     */
    int delete(String whereClause, String... whereArgs);

    /**
     * 修改
     * @param obj 表对应的对象
     * @param whereClause  where语句
     * @param whereArgs where语句中的参数
     * @return
     */
    int update(T obj, String whereClause, String... whereArgs);

}
