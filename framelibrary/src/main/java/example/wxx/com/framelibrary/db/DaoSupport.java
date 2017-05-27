package example.wxx.com.framelibrary.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import example.wxx.com.framelibrary.db.curd.QuerySupport;

/**
 * 作者：wengxingxia
 * 时间：2017/5/24 0024 08:50
 */

public class DaoSupport<T> implements IDaoSupport<T> {

    private static final String TAG = "DaoSupport";

    private SQLiteDatabase mSQLiteDatabase;
    private Class<T> mClazz;
    //     contentValues执行put方法时传入的参数
    private static final Object[] mPutMethodArgs = new Object[2];
    //    缓存Put方法
    private static final Map<String, Method> mPutMethods = new ArrayMap<>();

    private QuerySupport<T> mQuerySupport;

    public void init(SQLiteDatabase sqliteDatabase, Class<T> clazz) {
        this.mSQLiteDatabase = sqliteDatabase;
        this.mClazz = clazz;

//       创建表
        StringBuffer sb = new StringBuffer();
        sb.append("create table if not exists ")
                .append(DaoUtil.getTableName(mClazz))
                .append(" (id integer primary key autoincrement, ");
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();

            //"$change"和"serialVersionUID"这两个字段是Android studio2.0的Instant Run（用于加快应用安装）功能自动生成的
//            Android Studio2.0之前没有这两个字段，或者把Instant Run功能关闭也不会产生这两个字段
            if (name.equalsIgnoreCase("$change") || name.equalsIgnoreCase("serialVersionUID")) {
                continue;
            }

//            type需要进行转换
            String type = field.getType().getSimpleName();//返回int 、 String 、 boolean

            sb.append(name).append(DaoUtil.getColumnType(type)).append(", ");
        }

        sb.replace(sb.length() - 2, sb.length(), ") ");

        String createTableSql = sb.toString();

        Log.e(TAG, "创建表语句---->" + createTableSql);
//        执行创建表语句
        mSQLiteDatabase.execSQL(createTableSql);
    }

    @Override
    public void insertList(List<T> datas) {
//        批量插入采用事务
        mSQLiteDatabase.beginTransaction();
        for (T data : datas) {
//            遍历单条插入
            insert(data);
        }
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();
    }

    @Override
    public QuerySupport<T> querySupport() {
        if(mQuerySupport==null){
            mQuerySupport = new QuerySupport<T>(mSQLiteDatabase,mClazz);
        }
        return mQuerySupport;
    }


    /**
     * 根据字段类型获取方法名
     *
     * @param fieldType
     * @return
     */
    private String getColumnMethodName(Class<?> fieldType) {
        String typeName;
        if (fieldType.isPrimitive()) {//判断是否是基本类型
            typeName = DaoUtil.capitalize(fieldType.getName());
        } else {
            typeName = fieldType.getSimpleName();
        }
        String methodName = "get" + typeName;
        if ("getBoolean".equals(methodName)) {
            methodName = "getInt";
        } else if ("getChar".equals(methodName) || "getCharacter".equals(methodName)) {
            methodName = "getString";
        } else if ("getDate".equals(methodName)) {
            methodName = "getLong";
        } else if ("getInteger".equals(methodName)) {
            methodName = "getInt";
        }
        return methodName;
    }

    /**
     * 插入数据，是任意对象
     *
     * @param obj
     * @return
     */
    @Override
    public long insert(T obj) {
//        使用的语法是原生的使用方式，只是封装一下
//        obj 转成 ContentValues
        ContentValues values = createContentValuesByObj(obj);
        return mSQLiteDatabase.insert(DaoUtil.getTableName(mClazz), null, values);
    }

    /**
     * obj转成 ContentValues
     *
     * @param obj
     * @return
     */
    private ContentValues createContentValuesByObj(T obj) {
        ContentValues values = new ContentValues();

//        封装values
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            try {
//              设置访问权限
                field.setAccessible(true);

                String key = field.getName();
                //"$change"和"serialVersionUID"这两个字段是Android studio2.0的Instant Run（用于加快应用安装）功能自动生成的
//            Android Studio2.0之前没有这两个字段，或者把Instant Run功能关闭也不会产生这两个字段
                if (key.equalsIgnoreCase("$change") || key.equalsIgnoreCase("serialVersionUID")) {
                    continue;
                }
//                获取value
                Object value = field.get(obj);
                mPutMethodArgs[0] = key;
                mPutMethodArgs[1] = value;

//              使用反射 获取方法 反射一定程度上影响性能 需要缓存方法
                String fieldTypeName = field.getType().getName();
                Method putMethod = mPutMethods.get(fieldTypeName);
                if (putMethod == null) {
                    putMethod = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());
                    mPutMethods.put(fieldTypeName, putMethod);
                }
//                通过反射执行方法
                putMethod.invoke(values, mPutMethodArgs[0], mPutMethodArgs[1]);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mPutMethodArgs[0] = null;
                mPutMethodArgs[1] = null;
            }

        }
        return values;
    }

    /**
     * 删除
     */
    @Override
    public int delete(String whereClause, String... whereArgs) {
        return mSQLiteDatabase.delete(DaoUtil.getTableName(mClazz), whereClause, whereArgs);
    }

    /**
     * 更新  这些你需要对  最原始的写法比较明了
     */
    @Override
    public int update(T obj, String whereClause, String... whereArgs) {
        ContentValues values = createContentValuesByObj(obj);
        return mSQLiteDatabase.update(DaoUtil.getTableName(mClazz),
                values, whereClause, whereArgs);
    }
}
