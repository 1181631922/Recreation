package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author： fanyafeng
 * Data： 16/12/2 14:30
 * Email: fanyafeng@live.cn
 */
public class ScanCodeBean implements Parcelable {
    private String title;
    private String detail;

    public ScanCodeBean(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public ScanCodeBean() {
    }

    protected ScanCodeBean(Parcel in) {
        title = in.readString();
        detail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(detail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ScanCodeBean> CREATOR = new Creator<ScanCodeBean>() {
        @Override
        public ScanCodeBean createFromParcel(Parcel in) {
            return new ScanCodeBean(in);
        }

        @Override
        public ScanCodeBean[] newArray(int size) {
            return new ScanCodeBean[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ScanCodeBean{" +
                "title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
