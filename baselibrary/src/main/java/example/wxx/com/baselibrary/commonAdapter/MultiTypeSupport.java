package example.wxx.com.baselibrary.commonAdapter;

/**
 * 多布局支持
 * 作者：wengxingxia
 * 时间：2017/6/9 0009 10:39
 */

public interface MultiTypeSupport<DATA> {

    int getLayoutId(DATA item);
}
