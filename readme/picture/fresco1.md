# Fresco图片处理框架
* 1.这是我的Fresco的项目的地址，托管到github上了，并且有源码，[请用力戳我](https://github.com/1181631922/FrescoPicLoad)<br>
* 2.但是使用的话还是有点坑的，就是不确定图片的比例，下面会有处理方法。
### 1.关于fresco util的使用
* 1.1最重要的还是先说下不确定图片比例,ControllerListenerUtil,关于使用方法注释中都有介绍，[戳我查看util文件](https://github.com/1181631922/Recreation/blob/master/app/src/main/java/com/fanyafeng/recreation/util/ControllerListenerUtil.java)<br>
```
package com.fanyafeng.recreation.util;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * layoutParams不能为空
 * <p/>
 * Created by fanyafeng on 16/6/14.
 */
public class ControllerListenerUtil {
    public static void setControllerListener(final SimpleDraweeView simpleDraweeView, String imagePath, final int imageWidth) {
        final ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();


        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                int height = imageInfo.getHeight();
                int width = imageInfo.getWidth();
                layoutParams.width = imageWidth;
                layoutParams.height = (int) ((float) (imageWidth * height) / (float) width);
                simpleDraweeView.setLayoutParams(layoutParams);
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                Log.d("TAG", "Intermediate image received");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                throwable.printStackTrace();
            }
        };

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(Uri.parse(imagePath))
                .setAutoPlayAnimations(true)//支持gif图片加载
                .build();
        simpleDraweeView.setController(controller);
    }
}

```
* 1.2图片加载类，包括gif，本地，项目中，网络等，[戳我查看util文件](https://github.com/1181631922/Recreation/blob/master/app/src/main/java/com/fanyafeng/recreation/util/FrescoUtil.java)<br>
```
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
```
* 1.3还有其他功能，暂时没有使用就先不介绍了