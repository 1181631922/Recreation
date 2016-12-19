package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author： fanyafeng
 * Data： 16/12/19 上午11:19
 * Email: fanyafeng@live.cn
 */
public class FodderArtBean implements Parcelable {
    private String fodderArtTitle;
    private String fodderArtDetail;

    public FodderArtBean() {
    }

    protected FodderArtBean(Parcel in) {
        fodderArtTitle = in.readString();
        fodderArtDetail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fodderArtTitle);
        dest.writeString(fodderArtDetail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FodderArtBean> CREATOR = new Creator<FodderArtBean>() {
        @Override
        public FodderArtBean createFromParcel(Parcel in) {
            return new FodderArtBean(in);
        }

        @Override
        public FodderArtBean[] newArray(int size) {
            return new FodderArtBean[size];
        }
    };

    public String getFodderArtTitle() {
        return fodderArtTitle;
    }

    public void setFodderArtTitle(String fodderArtTitle) {
        this.fodderArtTitle = fodderArtTitle;
    }

    public String getFodderArtDetail() {
        return fodderArtDetail;
    }

    public void setFodderArtDetail(String fodderArtDetail) {
        this.fodderArtDetail = fodderArtDetail;
    }

    @Override
    public String toString() {
        return "FodderArtBean{" +
                "fodderArtTitle='" + fodderArtTitle + '\'' +
                ", fodderArtDetail='" + fodderArtDetail + '\'' +
                '}';
    }
}
