package example.wxx.com.framelibrary.http;

/**
 * 缓存的实体类
 * 作者：wengxingxia
 * 时间：2017/5/26 0026 17:05
 */

public class CacheData {
//    请求链接
    private String mUrlKey;
//    后台返回的json
    private String mResultJson;

    public CacheData() {
    }

    public CacheData(String urlKey, String resultJson) {
        mUrlKey = urlKey;
        mResultJson = resultJson;
    }

    public String getUrlKey() {
        return mUrlKey;
    }

    public void setUrlKey(String urlKey) {
        mUrlKey = urlKey;
    }

    public String getResultJson() {
        return mResultJson;
    }

    public void setResultJson(String resultJson) {
        mResultJson = resultJson;
    }
}
