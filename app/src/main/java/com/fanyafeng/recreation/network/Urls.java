package com.fanyafeng.recreation.network;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;

/**
 * Created by 365rili on 16/5/4.
 */
public class Urls {

    public final static String YINYUETAI = "http://mapiv2.yinyuetai.com/component/prefecture.json?&type=1";
    //    http://119.29.47.97/article/list/suggest?page=1&type=refresh&count=30&readarticles=[117917136,117916583]&rqcnt=13&r=519baad91478488978667
    //    http://m2.qiushibaike.com/article/list/suggest?page=1&type=refresh&count=30&readarticles=[117921665,117916048]&rqcnt=23&r=519baad91478488541893
    //    糗事百科的base url
    public final static String QIUBAI_BASE_URL = "http://m2.qiushibaike.com";
    //    糗百首页url
    public final static String ARTICLE_LIST = QIUBAI_BASE_URL + "/article/list/video?page=1&count=30&rqcnt=8";

    //    糗百首页刷新url
    public final static String ARTICLE_LIST_REFRESH = QIUBAI_BASE_URL + "/article/list/suggest?page=1&type=refresh&count=30";

    //    糗百加载更多
    public final static String ARTICLE_LIST_LOAD_MORE = QIUBAI_BASE_URL + "/article/list/suggest?";
    //    page=2&type=list&count=30&readarticles=[117944813,117938343,117944189,117944845,117938296,117937612,117938940,117937720]&rqcnt=22&r=519baad91478745407257";
    //    图片url
    public final static String PICTURE_ITEM = "http://pic.qiushibaike.com/system/pictures/";
//    11793/117936574/medium/";

    //    音悦台打榜
    public final static String GET_TOP_VIDEO_LIST = "http://mapiv2.yinyuetai.com/vchart/trend.json?&area=ML&offset=0&size=20";

}
