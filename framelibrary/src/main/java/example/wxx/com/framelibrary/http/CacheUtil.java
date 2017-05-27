package example.wxx.com.framelibrary.http;

import java.util.List;

import example.wxx.com.framelibrary.db.DaoSupportFactory;
import example.wxx.com.framelibrary.db.IDaoSupport;

/**
 * 作者：wengxingxia
 * 时间：2017/5/26 0026 18:39
 */

public class CacheUtil {

    private static IDaoSupport<CacheData> cacheDataDaoSupport = DaoSupportFactory.getFactory()
            .getDao(CacheData.class);

    /**
     * 从数据库获取缓存数据
     *
     * @param urlKey
     * @return
     */
    public static String getCacheResultJson(String urlKey) {
        String resultJson = null;

        List<CacheData> cacheDatas = cacheDataDaoSupport.querySupport()
                .selection("mUrlKey=?").selectionArgs(urlKey).query();

        if (cacheDatas.size() > 0) {
//                代表有数据
            CacheData cacheData = cacheDatas.get(0);
            resultJson = cacheData.getResultJson();
            return resultJson;
        }
        return resultJson;
    }

    /**
     * 缓存数据
     *
     * @return
     */
    public static long cacheData(String urlKey, String resultJson) {

//                删除数据
        cacheDataDaoSupport.delete("mUrlKey=?", urlKey);
//                插入数据
        long num = cacheDataDaoSupport.insert(new CacheData(urlKey, resultJson));
        return num;
    }
}
