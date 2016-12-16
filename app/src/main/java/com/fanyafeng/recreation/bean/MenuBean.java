package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fanyafeng on 16/12/16.
 */

public class MenuBean implements Parcelable{
    private String title;
    private String pic;
    private String url;
    private String burden;

    public MenuBean() {
    }

    protected MenuBean(Parcel in) {
        title = in.readString();
        pic = in.readString();
        url = in.readString();
        burden = in.readString();
    }

    public static final Creator<MenuBean> CREATOR = new Creator<MenuBean>() {
        @Override
        public MenuBean createFromParcel(Parcel in) {
            return new MenuBean(in);
        }

        @Override
        public MenuBean[] newArray(int size) {
            return new MenuBean[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBurden() {
        return burden;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    @Override
    public String toString() {
        return "MenuBean{" +
                "title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", url='" + url + '\'' +
                ", burden='" + burden + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(pic);
        dest.writeString(url);
        dest.writeString(burden);
    }
}
