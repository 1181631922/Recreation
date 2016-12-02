package com.fanyafeng.recreation.config;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Author： fanyafeng
 * Data： 16/10/26 19:16
 * Email: fanyafeng@live.cn
 */
public class AppConfig extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext(), FrescoConfig.getsImagePipelineConfig(this));
    }
}
