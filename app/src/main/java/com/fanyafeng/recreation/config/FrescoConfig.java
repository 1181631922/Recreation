package com.fanyafeng.recreation.config;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.config.instrumentation.InstrumentedDraweeView;

/**
 * Author： fanyafeng
 * Data： 16/10/26 19:17
 * Email: fanyafeng@live.cn
 */
public class FrescoConfig {
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();//分配的可用内存
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;//使用的缓存数量

    public static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 5 * ByteConstants.MB;//小图极低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    public static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 10 * ByteConstants.MB;//小图低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    public static final int MAX_SMALL_DISK_CACHE_SIZE = 20 * ByteConstants.MB;//小图磁盘缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）

    public static final int MAX_DISK_CACHE_VERYLOW_SIZE = 10 * ByteConstants.MB;//默认图极低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_LOW_SIZE = 30 * ByteConstants.MB;//默认图低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;//默认图磁盘缓存的最大值


    private static final String IMAGE_PIPELINE_SMALL_CACHE_DIR = "wine_image";//小图所放路径的文件夹名
    private static final String IMAGE_PIPELINE_CACHE_DIR = "Wine";//默认图所放路径的文件夹名

    private static ImagePipelineConfig sImagePipelineConfig;

    public FrescoConfig() {
    }

    /**
     * 初始化单例配置
     *
     * @param context
     * @return
     */
    public static ImagePipelineConfig getsImagePipelineConfig(Context context) {
        if (sImagePipelineConfig == null) {
            sImagePipelineConfig = configureCaches(context);

        }
        return sImagePipelineConfig;
    }

    private static ImagePipelineConfig configureCaches(Context context) {
//        内存配置
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                FrescoConfig.MAX_MEMORY_CACHE_SIZE,  //内存缓存中总图片的最大大小，以字节为单位
                Integer.MAX_VALUE,                   //内存缓存中图片的最大数量
                FrescoConfig.MAX_MEMORY_CACHE_SIZE,  //内存缓存中准备清除但尚未被删除的总图片的最大大小，以字节为单位
                Integer.MAX_VALUE,                   //内存缓存中准备清除的总图片的最大数量
                Integer.MAX_VALUE                    //内存缓存中单个图片的最大大小
        );

//        修改内存图片缓存数量，控件策略
        Supplier<MemoryCacheParams> supplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };

//        小图片的磁盘配置
        DiskCacheConfig diskSmallCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(context.getApplicationContext().getCacheDir()) //缓存图片路径
                .setBaseDirectoryName(IMAGE_PIPELINE_SMALL_CACHE_DIR)                //文件夹名
//                .setCacheErrorLogger(cacheErrorLogger)                               日志记录器用于日志错误的缓存
//                .setCacheEventListener(cacheEventListener)                           缓存事件监听器
//                .setDiskTrimmableRegistry()
                .setMaxCacheSize(FrescoConfig.MAX_DISK_CACHE_SIZE)                    //默认缓存的最大大小
                .setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)         //使用设备低磁盘空间时，缓存的最大大小
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)       //当磁盘极低的空间时，缓存的最大大小
//                .setVersion()
                .build();

        //        默认图片的磁盘配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
//                Environment.getExternalStorageDirectory().getPath()
//                Environment.getExternalStorageDirectory().getAbsoluteFile()
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory().getAbsoluteFile()) //缓存图片路径
                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)                //文件夹名
//                .setCacheErrorLogger(cacheErrorLogger)                               日志记录器用于日志错误的缓存
//                .setCacheEventListener(cacheEventListener)                           缓存事件监听器
//                .setDiskTrimmableRegistry()
                .setMaxCacheSize(FrescoConfig.MAX_DISK_CACHE_SIZE)                    //默认缓存的最大大小
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)         //使用设备低磁盘空间时，缓存的最大大小
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)       //当磁盘极低的空间时，缓存的最大大小
//                .setVersion()
                .build();

        //缓存图片配置
        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context)
//    		  .setAnimatedImageFactory(AnimatedImageFactory animatedImageFactory)//图片加载动画
                .setBitmapMemoryCacheParamsSupplier(supplierMemoryCacheParams)//内存缓存配置（一级缓存，已解码的图片）
//            .setCacheKeyFactory(cacheKeyFactory)//缓存Key工厂
//        	  .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)//内存缓存和未解码的内存缓存的配置（二级缓存）
//        	  .setExecutorSupplier(executorSupplier)//线程池配置
//        	  .setImageCacheStatsTracker(imageCacheStatsTracker)//统计缓存的命中率
//        	  .setImageDecoder(ImageDecoder imageDecoder) //图片解码器配置
//        	  .setIsPrefetchEnabledSupplier(Supplier<Boolean> isPrefetchEnabledSupplier)//图片预览（缩略图，预加载图等）预加载到文件缓存
                .setMainDiskCacheConfig(diskCacheConfig)//磁盘缓存配置（总，三级缓存）
//        	  .setMemoryTrimmableRegistry(memoryTrimmableRegistry) //内存用量的缩减,有时我们可能会想缩小内存用量。比如应用中有其他数据需要占用内存，不得不把图片缓存清除或者减小 或者我们想检查看看手机是否已经内存不够了。
//        	  .setNetworkFetchProducer(networkFetchProducer)//自定的网络层配置：如OkHttp，Volley
//        	  .setPoolFactory(poolFactory)//线程池工厂配置
                .setProgressiveJpegConfig(pjpegConfig)//渐进式JPEG图
//        	  .setRequestListeners(requestListeners)//图片请求监听
                .setResizeAndRotateEnabledForNetwork(true)//调整和旋转是否支持网络图片
                .setSmallImageDiskCacheConfig(diskSmallCacheConfig)//磁盘缓存配置（小图片，可选～三级缓存的小图优化缓存）
                ;
        return configBuilder.build();

    }

    static ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
        @Override
        public int getNextScanNumberToDecode(int scanNumber) {
            return scanNumber + 2;
        }

        public QualityInfo getQualityInfo(int scanNumber) {
            boolean isGoodEnough = (scanNumber >= 5);
            return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
        }
    };

    public static GenericDraweeHierarchy genericDraweeHierarchy(Context context) {
        GenericDraweeHierarchy gdh = new GenericDraweeHierarchyBuilder(context.getResources())
//			  .reset()//重置
//	  		  .setActualImageColorFilter(colorFilter)//颜色过滤
//			  .setActualImageFocusPoint(focusPoint)//focusCrop, 需要指定一个居中点
//			  .setActualImageMatrix(actualImageMatrix)
//			  .setActualImageScaleType(actualImageScaleType)//fresco:actualImageScaleType="focusCrop"缩放类型
//			  .setBackground(background)//fresco:backgroundImage="@color/blue"背景图片
//			  .setBackgrounds(backgrounds)
//			  .setFadeDuration(fadeDuration)//fresco:fadeDuration="300"加载图片动画时间
                .setFailureImage(FrescoConfig.sErrorDrawable)//fresco:failureImage="@drawable/error"失败图
//			  .setFailureImage(failureDrawable, failureImageScaleType)//fresco:failureImageScaleType="centerInside"失败图缩放类型
//			  .setOverlay(overlay)//fresco:overlayImage="@drawable/watermark"叠加图
//			  .setOverlays(overlays)
                .setPlaceholderImage(FrescoConfig.sPlaceholderDrawable)//fresco:placeholderImage="@color/wait_color"占位图
//			  .setPlaceholderImage(placeholderDrawable, placeholderImageScaleType)//fresco:placeholderImageScaleType="fitCenter"占位图缩放类型
//			  .setPressedStateOverlay(drawable)//fresco:pressedStateOverlayImage="@color/red"按压状态下的叠加图
                .setProgressBarImage(new ProgressBarDrawable())//进度条fresco:progressBarImage="@drawable/progress_bar"进度条
//			  .setProgressBarImage(progressBarImage, progressBarImageScaleType)//fresco:progressBarImageScaleType="centerInside"进度条类型
//			  .setRetryImage(retryDrawable)//fresco:retryImage="@drawable/retrying"点击重新加载
//			  .setRetryImage(retryDrawable, retryImageScaleType)//fresco:retryImageScaleType="centerCrop"点击重新加载缩放类型
                .setRoundingParams(RoundingParams.asCircle())//圆形/圆角fresco:roundAsCircle="true"圆形
                .build();
        return gdh;
    }

    //SimpleDraweeControllerBuilder
    public static SimpleDraweeControllerBuilder getSimpleDraweeControllerBuilder(SimpleDraweeControllerBuilder sdcb, Uri uri, Object callerContext, DraweeController draweeController) {
        SimpleDraweeControllerBuilder controllerBuilder = sdcb
                .setUri(uri)
                .setCallerContext(callerContext)
//				.setAspectRatio(1.33f);//宽高缩放比
                .setOldController(draweeController);
        return controllerBuilder;
    }

    //图片解码
    public static ImageDecodeOptions getImageDecodeOptions() {
        ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder()
//			  .setBackgroundColor(Color.TRANSPARENT)//图片的背景颜色
//			  .setDecodeAllFrames(decodeAllFrames)//解码所有帧
//			  .setDecodePreviewFrame(decodePreviewFrame)//解码预览框
//			  .setForceOldAnimationCode(forceOldAnimationCode)//使用以前动画
//			  .setFrom(options)//使用已经存在的图像解码
//			  .setMinDecodeIntervalMs(intervalMs)//最小解码间隔（分位单位）
                .setUseLastFrameForPreview(true)//使用最后一帧进行预览
                .build();
        return decodeOptions;
    }

    //图片显示
    public static ImageRequest getImageRequest(InstrumentedDraweeView view, String uri) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                .setAutoRotateEnabled(true)//自动旋转图片方向
//			  .setImageDecodeOptions(getImageDecodeOptions())//  图片解码库
//			  .setImageType(ImageType.SMALL)//图片类型，设置后可调整图片放入小图磁盘空间还是默认图片磁盘空间
//			  .setLocalThumbnailPreviewsEnabled(true)//缩略图预览，影响图片显示速度（轻微）
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)//请求经过缓存级别  BITMAP_MEMORY_CACHE，ENCODED_MEMORY_CACHE，DISK_CACHE，FULL_FETCH
//			  .setPostprocessor(postprocessor)//修改图片
//			  .setProgressiveRenderingEnabled(true)//渐进加载，主要用于渐进式的JPEG图，影响图片显示速度（普通）
                .setResizeOptions(new ResizeOptions(view.getLayoutParams().width, view.getLayoutParams().height))//调整大小
//			  .setSource(Uri uri)//设置图片地址
                .build();
        return imageRequest;
    }

    //图片显示
    public static ImageRequest getDraweeViewImageRequest(SimpleDraweeView view, String uri) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                .setAutoRotateEnabled(true)//自动旋转图片方向
//			  .setImageDecodeOptions(getImageDecodeOptions())//  图片解码库
//			  .setImageType(ImageType.SMALL)//图片类型，设置后可调整图片放入小图磁盘空间还是默认图片磁盘空间
                .setLocalThumbnailPreviewsEnabled(true)//缩略图预览，影响图片显示速度（轻微）
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)//请求经过缓存级别  BITMAP_MEMORY_CACHE，ENCODED_MEMORY_CACHE，DISK_CACHE，FULL_FETCH
//			  .setPostprocessor(postprocessor)//修改图片
                .setProgressiveRenderingEnabled(true)//渐进加载，主要用于渐进式的JPEG图，影响图片显示速度（普通）
                .setResizeOptions(new ResizeOptions(view.getLayoutParams().width, view.getLayoutParams().height))//调整大小
//			  .setSource(Uri uri)//设置图片地址
                .build();
        return imageRequest;
    }

    //DraweeController 控制 DraweeControllerBuilder
    public static DraweeController getDraweeController(ImageRequest imageRequest, InstrumentedDraweeView view) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
//			  .reset()//重置
                .setAutoPlayAnimations(true)//自动播放图片动画
//			  .setCallerContext(callerContext)//回调
                .setControllerListener(view.getListener())//监听图片下载完毕等
//			  .setDataSourceSupplier(dataSourceSupplier)//数据源
//			  .setFirstAvailableImageRequests(firstAvailableImageRequests)//本地图片复用，可加入ImageRequest数组
                .setImageRequest(imageRequest)//设置单个图片请求～～～不可与setFirstAvailableImageRequests共用，配合setLowResImageRequest为高分辨率的图
//			  .setLowResImageRequest(ImageRequest.fromUri(lowResUri))//先下载显示低分辨率的图
                .setOldController(view.getController())//DraweeController复用
                .setTapToRetryEnabled(true)//点击重新加载图
                .build();
        return draweeController;
    }

    //DraweeController 控制 DraweeControllerBuilder
    public static DraweeController getSimpleDraweeController(ImageRequest imageRequest, SimpleDraweeView view) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
//			  .reset()//重置
                .setAutoPlayAnimations(true)//自动播放图片动画
//			  .setCallerContext(callerContext)//回调
//			  .setDataSourceSupplier(dataSourceSupplier)//数据源
//			  .setFirstAvailableImageRequests(firstAvailableImageRequests)//本地图片复用，可加入ImageRequest数组
                .setImageRequest(imageRequest)//设置单个图片请求～～～不可与setFirstAvailableImageRequests共用，配合setLowResImageRequest为高分辨率的图
//			  .setLowResImageRequest(ImageRequest.fromUri(lowResUri))//先下载显示低分辨率的图
                .setOldController(view.getController())//DraweeController复用
                .setTapToRetryEnabled(true)//点击重新加载图，算上首次尝试加载，未手动点击的一共可以尝试五次，手动可以点击四次
                .build();
        return draweeController;
    }

    //默认加载图片和失败图片
    public static Drawable sPlaceholderDrawable;
    public static Drawable sErrorDrawable;

    @SuppressWarnings("deprecation")
    public static void init(final Resources resources) {
        if (sPlaceholderDrawable == null) {
            sPlaceholderDrawable = resources.getDrawable(R.color.colorPrimary);
        }
        if (sErrorDrawable == null) {
            sErrorDrawable = resources.getDrawable(R.color.colorPrimary);
        }
    }
}
