package example.wxx.com.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * 请求回调
 * 作者：wengxingxia
 * 时间：2017/5/21 0021 15:13
 */

public interface EngineCallback {

    //    开始执行,在执行之前会回调的方法
    public void onPreExecute(Context context, Map<String,Object> params);

    //  错误
    void onError(Exception e);

    //  成功
    void onSuccess(String result);

    //    默认的
    public final EngineCallback DEFAULT_CALL_BACK = new EngineCallback() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };

}
