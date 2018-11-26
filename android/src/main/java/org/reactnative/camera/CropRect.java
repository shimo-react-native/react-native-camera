package org.reactnative.camera;
import android.util.DisplayMetrics;

import com.facebook.react.bridge.ReadableMap;

/**
 *
 * @author chenqiang
 * @date 2018/11/15
 */
public class CropRect {

    public int left;
    public int top;
    public int width;
    public int height;
    public int rotation;
    private boolean hasFixed = false;

    public static CropRect fromReadableMap(DisplayMetrics metrics, ReadableMap map) {
        CropRect cropRect = new CropRect();
        int screenWidth = metrics.widthPixels;
        if (map != null) {
            cropRect.width = dip2px(metrics, map.getInt("width"));
            int left = map.getInt("left");
            if (left == -1) {
                cropRect.left = (screenWidth - cropRect.width) / 2;
            } else {
                cropRect.left = dip2px(metrics, left);
            }
            cropRect.top = dip2px(metrics, map.getInt("top"));
            cropRect.height = dip2px(metrics, map.getInt("height"));
        }
        return cropRect;
    }

    private static int dip2px(DisplayMetrics metrics, float dpValue) {
        float scale = metrics.density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 修复用户手动更改系统分辨率，导致的previewSize和ScreenSize不一致问题
     * @param aspect
     */
    public void fix(float aspect) {
        hasFixed = true;
        left *= aspect;
        top *= aspect;
        width *= aspect;
        height *= aspect;
    }

    public boolean hasFixed() {
        return hasFixed;
    }
}
