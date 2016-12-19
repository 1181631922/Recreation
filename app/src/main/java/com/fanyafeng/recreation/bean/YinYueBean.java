package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author： fanyafeng
 * Data： 16/12/19 下午5:38
 * Email: fanyafeng@live.cn
 */
public class YinYueBean implements Parcelable{
    private String href;
    private String title;
    private String img;
    private String icon;

    public YinYueBean() {
    }

    protected YinYueBean(Parcel in) {
        href = in.readString();
        title = in.readString();
        img = in.readString();
        icon = in.readString();
    }

    public static final Creator<YinYueBean> CREATOR = new Creator<YinYueBean>() {
        @Override
        public YinYueBean createFromParcel(Parcel in) {
            return new YinYueBean(in);
        }

        @Override
        public YinYueBean[] newArray(int size) {
            return new YinYueBean[size];
        }
    };

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "YinYueBean{" +
                "href='" + href + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(href);
        dest.writeString(title);
        dest.writeString(img);
        dest.writeString(icon);
    }
}
