package com.fanyafeng.recreation.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Author： fanyafeng
 * Data： 17/1/9 下午4:40
 * Email: fanyafeng@live.cn
 */
public class CustomWebView extends WebView {

    /**
     * js接口
     */
    public interface JSInterface {

        /**
         * 关闭键盘
         */
        void hideSoftKeyboard();

        /**
         * 关闭当前webview
         */
        void close();

        /**
         * 在新的webview中打开url
         *
         * @param url
         */
        void onOpenUrl(String url);

        /**
         * 图片预览接口
         *
         * @param urls       图片数组
         * @param current    当前点击的图片url
         */
        @JavascriptInterface
        void previewImage(String urls, String current);

        /**
         * 设置titlebar左侧按钮
         *
         * @param type 返回键功能 1:返回上一页面，2.退出webview
         * @param back 返回按钮的文字，空时为箭头
         */
        void setTitle(String back, int type, String callBack);

        /**
         * 设置titlebar右侧按钮
         *
         * @param text
         * @param callBack
         */
        void setRightTitle(String text, String callBack);

        @JavascriptInterface
        String getUserInfo();
    }

    public void setCurrentActiviy(Activity currentActiviy) {
        this.currentActiviy = currentActiviy;
    }

    private Activity currentActiviy;

    private String errorUrl;

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }


    public CustomWebView(Context context) {
        super(context);
        init();
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        requestFocusFromTouch();
        setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAllowFileAccess(true);
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(false);
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        getSettings().setUserAgentString(getSettings().getUserAgentString());
        getSettings().setDomStorageEnabled(true);
        // mWebView.getSettings().setAppCachePath(appCachePath);
        getSettings().setAppCacheEnabled(false);

        getSettings().setTextZoom(100);
    }
}
