package example.wxx.com.framelibrary.skin.support;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import example.wxx.com.framelibrary.skin.attr.SkinAttr;
import example.wxx.com.framelibrary.skin.attr.SkinType;

/**
 * 皮肤属性解析的支持类
 * 作者：wengxingxia
 * 时间：2017/6/27 0027 21:05
 */

public class SkinAttrSupport {

    private static final String TAG = "TAG";
    /**
     * 获取SkinAttr的属性
     * @param context
     * @param attrs
     * @return
     */
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
//        background src textColor
        List<SkinAttr> skinAttrs = new ArrayList<>();
        int attrLength = attrs.getAttributeCount();
        for (int i = 0; i < attrLength; i++) {
//            获取名称
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);
//            Log.e(TAG,"attributeName-->"+ attributeName+"===;attributeValue--->"+attributeValue );
//            只获取重要的
            SkinType skinType = getSkinType(attributeName);

            if(skinType!=null){
//                资源名称 目前只有attributeValue 是一个@int类型
                String resName = getResName(context,attributeValue);
                if(TextUtils.isEmpty(resName))
                    continue;
                SkinAttr skinAttr = new SkinAttr(resName, skinType);
                skinAttrs.add(skinAttr);
            }
        }

        return skinAttrs;
    }

    /**
     * 获取资源名字
     * @param context
     * @param attributeValue
     * @return
     */
    private static String getResName(Context context, String attributeValue) {
        if(attributeValue.startsWith("@")){
            attributeValue = attributeValue.substring(1);

            int resId = Integer.parseInt(attributeValue);
            String name = context.getResources().getResourceEntryName(resId);
            return name;
        }
        return null;
    }

    /**
     * 通过名称获取SkinType
     * @param attributeName
     * @return
     */
    private static SkinType getSkinType(String attributeName) {
        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if(skinType.getResName().equals(attributeName)){
                return skinType;
            }
        }
        return null;
    }
}
