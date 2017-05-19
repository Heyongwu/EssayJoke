package example.wxx.com.essayjoke.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 *  自定义ListView
 *  用途;解决ScrollView嵌套ListView时，ListView显示不全
 * 作者：wengxingxia
 * 时间：2017/5/18 0018 08:41
 */

public class ImplantListView extends ListView {
    public ImplantListView(Context context) {
        super(context);
    }

    public ImplantListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImplantListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        解决方案来自于源码
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
