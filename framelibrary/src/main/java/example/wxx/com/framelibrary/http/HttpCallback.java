package example.wxx.com.framelibrary.http;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Map;

import example.wxx.com.baselibrary.http.EngineCallback;
import example.wxx.com.baselibrary.http.HttpUtils;

/**
 * 作者：wengxingxia
 * 时间：2017/5/21 0021 23:20
 */

public abstract class HttpCallback<T> implements EngineCallback {
    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
//        添加公用参数，跟项目业务逻辑相关
        // 项目名称  context
        params.put("app_name", "joke_essay");
        params.put("version_name", "5.7.0");
        params.put("ac", "wifi");
        params.put("device_id", "30036118478");
        params.put("device_brand", "Xiaomi");
        params.put("update_version_code", "5701");
        params.put("manifest_version_code", "570");
        params.put("longitude", "113.000366");
        params.put("latitude", "28.171377");
        params.put("device_platform", "android");

        onPreExecute();

    }

    // 开始执行了
    public void onPreExecute() {

    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        T objResult = (T) gson.fromJson(result, HttpUtils.analysisClazzInfo(this));
        onSuccess(objResult);
    }

    /**
     *  返回可以直接操作的对象
     * @param result
     */
    public abstract void onSuccess(T result);
}

