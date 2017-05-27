package example.wxx.com.baselibrary.http;

import android.content.Context;
import android.util.ArrayMap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 自定义Http框架
 * 作者：wengxingxia
 * 时间：2017/5/21 0021 15:10
 */

public class HttpUtils {

    //    url
    private String mUrl;
    //    请求方式
    private int mType = GET_TYPE;
    public static final int POST_TYPE = 0x0011;
    public static final int GET_TYPE = 0x0012;

    private Context mContext;

    private Map<String, Object> mParams;

//    是否读取缓存,false不读取
    private boolean mCache = false;

    //   默认OkHttpEngine
    private static IHttpEngine mHttpEngine = null;

    //    链式调用
    public HttpUtils(Context context) {
        mContext = context;
        mParams = new ArrayMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }

    public HttpUtils get() {
        mType = GET_TYPE;
        return this;
    }

    public HttpUtils cache(boolean isCache) {
        mCache = isCache;
        return this;
    }

    /**
     * 添加参数
     */
    public HttpUtils addParam(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    /**
     * 添加参数
     */
    public HttpUtils addParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    /**
     * 添加回调，执行
     *
     * @return
     */
    public void execute(EngineCallback callback) {
        if (callback == null) {
            callback = EngineCallback.DEFAULT_CALL_BACK;
        }

        callback.onPreExecute(mContext,mParams);

//        判断执行方法
        if (mType == POST_TYPE) {
            post(mUrl, mParams, callback);
        }else if (mType == GET_TYPE) {
            get(mUrl, mParams, callback);
        }

    }

    /**
     * 执行
     *
     * @return
     */
    public void execute() {
        execute(null);
    }

    /**
     * 在Application中初始化引擎
     *
     * @param httpEngine
     */
    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    /**
     * 每次可以自带引擎
     *
     * @param httpEngine
     */
    public HttpUtils exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
        return this;
    }

    private void get(String url, Map<String, Object> params, EngineCallback callback) {
        mHttpEngine.get(mCache,mContext,url, params, callback);
    }

    private void post(String url, Map<String, Object> params, EngineCallback callback) {
        mHttpEngine.post(mCache,mContext,url, params, callback);
    }

    /**
     * 拼接参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }

        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }

    /**
     * 解析一个类上面的class信息
     * 根据一个类的实体得到其Class对象
     */
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

}
