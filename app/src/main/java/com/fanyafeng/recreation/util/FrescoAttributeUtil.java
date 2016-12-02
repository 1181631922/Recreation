package com.fanyafeng.recreation.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;

/**
 * Author： fanyafeng
 * Data： 16/7/25 17:10
 * Email: fanyafeng@live.cn
 */
public class FrescoAttributeUtil {
    private static GenericDraweeHierarchy fedInHierarchy;

    private static GenericDraweeHierarchy circleHierarchy;

    /**
     * 淡入淡出动画效果
     *
     * @param context
     * @return
     */
    public static GenericDraweeHierarchy setFedInHierarchy(Context context) {
        if (fedInHierarchy == null) {
            fedInHierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                    .setFadeDuration(300)
                    .build();
        }
        return fedInHierarchy;
    }

    /**
     * 获取圆形图片
     *
     * @param context
     * @return
     */
    public static GenericDraweeHierarchy setCircleHierarchy(Context context) {
        if (circleHierarchy == null) {
            circleHierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                    .setRoundingParams(RoundingParams.asCircle())
                    .build();
        }
        return circleHierarchy;
    }

    /**
     * 获取圆环图片
     *
     * @return
     */
    public static GenericDraweeHierarchy setCircleRingHierarchy(Context context, int ringColor, float ringWidth) {
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setBorder(ringColor, ringWidth);
        roundingParams.setRoundAsCircle(true);
        GenericDraweeHierarchy circleRingHierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setRoundingParams(roundingParams)
                .build();
        return circleRingHierarchy;
    }

    /**
     * 获取圆角图片
     *
     * @param context
     * @param topLeft
     * @param topRight
     * @param bottomRight
     * @param bottomLeft
     * @return
     */
    public static GenericDraweeHierarchy setCircleRadiusHierarchy(Context context, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setCornersRadii(topLeft, topRight, bottomRight, bottomLeft);
        GenericDraweeHierarchy circleRadiusHierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setRoundingParams(roundingParams)
                .build();
        return circleRadiusHierarchy;
    }

    /**
     * 获取圆角环图片
     *
     * @param context
     * @param topLeft
     * @param topRight
     * @param bottomRight
     * @param bottomLeft
     * @param ringColor
     * @param ringWidth
     * @return
     */
    public static GenericDraweeHierarchy setCircleRadiusRingHierarchy(Context context, float topLeft, float topRight, float bottomRight, float bottomLeft, int ringColor, float ringWidth) {
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setBorder(ringColor, ringWidth);
        roundingParams.setCornersRadii(topLeft, topRight, bottomRight, bottomLeft);
        GenericDraweeHierarchy circleRadiusRingHierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setRoundingParams(roundingParams)
                .build();
        return circleRadiusRingHierarchy;
    }

    /**
     * 加载图片过程中动图progressbar显示，可以为动画图，也可以为非动图
     *
     * @param context
     * @param resId
     * @return
     */
    public static GenericDraweeHierarchy setProgressBarHierarchy(Context context, int resId) {
        GenericDraweeHierarchy progressBarDraweeHierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setProgressBarImage(ContextCompat.getDrawable(context, resId))
                .build();
        return progressBarDraweeHierarchy;
    }

    /**
     * 加载图片过程中动图progressbar显示，可以为动画图，也可以为非动图
     * 图片为圆形图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static GenericDraweeHierarchy setCircleProgressBarHierarchy(Context context, int resId) {
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setRoundAsCircle(true);
        GenericDraweeHierarchy circleProgressBarDraweeHierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setProgressBarImage(ContextCompat.getDrawable(context, resId))
                .setRoundingParams(roundingParams)
                .build();
        return circleProgressBarDraweeHierarchy;
    }

    public static GenericDraweeHierarchy setLoadFailHierarchy(Context context, int holderResId, int failResId) {
        GenericDraweeHierarchy loadFailHierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setPlaceholderImage(holderResId)
                .setFailureImage(failResId)
                .setRetryImage(failResId)
//                .setRetryImage()   加载失败后的重试图片
                .build();
        return loadFailHierarchy;
    }

}
