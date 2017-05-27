package example.wxx.com.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Http引擎的规范
 * 作者：wengxingxia
 * 时间：2017/5/21 0021 15:11
 */

public interface IHttpEngine {

    /***
     * get请求
     * @param isCache 是否进行缓存和先调用缓存的数据
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    public void get(boolean isCache,Context context, String url, Map<String, Object> params, EngineCallback callback);

    /***
     * post请求
     * @param isCache 是否进行缓存和先调用缓存的数据
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    public void post(boolean isCache,Context context,String url, Map<String, Object> params, EngineCallback callback);

//    下载文件

//    上传文件

//    https 添加证书

}
