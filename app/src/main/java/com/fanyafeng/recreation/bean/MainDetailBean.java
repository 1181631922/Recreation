package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Author： fanyafeng
 * Data： 17/1/17 上午11:44
 * Email: fanyafeng@live.cn
 */
public class MainDetailBean implements Parcelable {
    private long id;
    private String userName;
    private String userImg;
    private String text;

    public MainDetailBean(JSONObject jsonObject) {
        setId(jsonObject.optLong("id"));
        setUserName(jsonObject.optString("user_name"));
        setUserImg(jsonObject.optString("avatar_url"));
        setText(jsonObject.optString("text"));
    }

    protected MainDetailBean(Parcel in) {
        id = in.readLong();
        userName = in.readString();
        userImg = in.readString();
        text = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(userName);
        dest.writeString(userImg);
        dest.writeString(text);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MainDetailBean> CREATOR = new Creator<MainDetailBean>() {
        @Override
        public MainDetailBean createFromParcel(Parcel in) {
            return new MainDetailBean(in);
        }

        @Override
        public MainDetailBean[] newArray(int size) {
            return new MainDetailBean[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MainDetailBean{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userImg='" + userImg + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
