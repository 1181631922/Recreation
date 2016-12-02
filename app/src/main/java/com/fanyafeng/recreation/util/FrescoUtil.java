package com.fanyafeng.recreation.util;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

/**
 * Author： fanyafeng
 * Data： 16/7/9 12:04
 * Email: fanyafeng@live.cn
 */
public class FrescoUtil {

    /**
     * 加载app内非动图
     *
     * @param simpleDraweeView view控件
     * @param resId            资源比例
     * @param aspectRatio      图片长宽比例
     */
    public static void loadPicInApp(@NonNull SimpleDraweeView simpleDraweeView, @NonNull int resId, float aspectRatio) {
        if (simpleDraweeView == null)
            return;
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
        if (aspectRatio > 0) {
            simpleDraweeView.setAspectRatio(aspectRatio);
        }
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载app内非动图
     *
     * @param simpleDraweeView
     * @param resId
     */
    public static void loadPicInApp(@NonNull SimpleDraweeView simpleDraweeView, @NonNull int resId) {
        if (simpleDraweeView == null)
            return;
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * @param simpleDraweeView
     * @param resId
     * @param aspectRatio
     */
    public static void loadGifPicInApp(@NonNull SimpleDraweeView simpleDraweeView, @NonNull int resId, float aspectRatio) {
        if (simpleDraweeView == null) {
            return;
        }
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        if (aspectRatio > 0) {
            simpleDraweeView.setAspectRatio(aspectRatio);
        }
        simpleDraweeView.setController(draweeController);
    }

    /**
     * @param simpleDraweeView
     * @param resId
     */
    public static void loadGifPicInApp(@NonNull SimpleDraweeView simpleDraweeView, @NonNull int resId) {
        if (simpleDraweeView == null) {
            return;
        }
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setController(draweeController);
    }

    /**
     * @param simpleDraweeView
     * @param gifUrl
     * @param aspectRatio
     */
    public static void loadGifPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String gifUrl, float aspectRatio) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(gifUrl))
                .setAutoPlayAnimations(true)
                .build();
        if (aspectRatio > 0) {
            simpleDraweeView.setAspectRatio(aspectRatio);
        }
        simpleDraweeView.setController(draweeController);
    }

    /**
     * @param simpleDraweeView
     * @param gifUrl
     */
    public static void loadGifPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String gifUrl) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(gifUrl))
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setController(draweeController);
    }

    public static void loadPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String imgUrl) {
        simpleDraweeView.setImageURI(imgUrl);
    }

    public static void loadPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull Uri imgUri) {
        simpleDraweeView.setImageURI(imgUri);
    }

    public static void loadPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String imgUri, float aspectRatio) {
        simpleDraweeView.setAspectRatio(aspectRatio);
        simpleDraweeView.setImageURI(imgUri);
    }

    /**
     * 多图加载
     *
     * @param simpleDraweeView
     * @param highUrl          高分辨率
     * @param lowUrl           低分辨率
     */
    public static void loadPicOnNet(SimpleDraweeView simpleDraweeView, Uri lowUri, Uri highUri) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(lowUri))
                .setImageRequest(ImageRequest.fromUri(highUri))
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(draweeController);
    }

    public static void loadPicOnNet(SimpleDraweeView simpleDraweeView, String lowUrl, String highUrl) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(lowUrl))
                .setImageRequest(ImageRequest.fromUri(highUrl))
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(draweeController);
    }

    public static void loadFilePic(SimpleDraweeView simpleDraweeView, String imgUrl) {
        simpleDraweeView.setImageURI("file://" + imgUrl);
    }

    public static void loadFilePic(SimpleDraweeView simpleDraweeView, String imgUrl, float aspectRatio) {
        if (aspectRatio > 0) {
            simpleDraweeView.setAspectRatio(aspectRatio);
        }
        simpleDraweeView.setImageURI("file://" + imgUrl);
    }

    public static void loadContentPic(SimpleDraweeView simpleDraweeView, String imgUrl) {
        simpleDraweeView.setImageURI("content://" + imgUrl);
    }

    public static void loadContentPic(SimpleDraweeView simpleDraweeView, String imgUrl, float aspectRatio) {
        if (aspectRatio > 0) {
            simpleDraweeView.setAspectRatio(aspectRatio);
        }
        simpleDraweeView.setImageURI("content://" + imgUrl);
    }

    public static void loadResPic(SimpleDraweeView simpleDraweeView, String imgUrl) {
        simpleDraweeView.setImageURI("res://" + imgUrl);
    }

    public static void loadResPic(SimpleDraweeView simpleDraweeView, String imgUrl, float aspectRatio) {
        if (aspectRatio > 0) {
            simpleDraweeView.setAspectRatio(aspectRatio);
        }
        simpleDraweeView.setImageURI("res://" + imgUrl);
    }

}
