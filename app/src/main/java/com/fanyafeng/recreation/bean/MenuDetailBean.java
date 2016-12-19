package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author： fanyafeng
 * Data： 16/12/16 下午6:18
 * Email: fanyafeng@live.cn
 */
public class MenuDetailBean implements Parcelable{
    private String menuDetailImg;
    private String menuDetail;

    public MenuDetailBean() {
    }

    protected MenuDetailBean(Parcel in) {
        menuDetailImg = in.readString();
        menuDetail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(menuDetailImg);
        dest.writeString(menuDetail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MenuDetailBean> CREATOR = new Creator<MenuDetailBean>() {
        @Override
        public MenuDetailBean createFromParcel(Parcel in) {
            return new MenuDetailBean(in);
        }

        @Override
        public MenuDetailBean[] newArray(int size) {
            return new MenuDetailBean[size];
        }
    };

    public String getMenuDetailImg() {
        return menuDetailImg;
    }

    public void setMenuDetailImg(String menuDetailImg) {
        this.menuDetailImg = menuDetailImg;
    }

    public String getMenuDetail() {
        return menuDetail;
    }

    public void setMenuDetail(String menuDetail) {
        this.menuDetail = menuDetail;
    }

    @Override
    public String toString() {
        return "MenuDetailBean{" +
                "menuDetailImg='" + menuDetailImg + '\'' +
                ", menuDetail='" + menuDetail + '\'' +
                '}';
    }
}
