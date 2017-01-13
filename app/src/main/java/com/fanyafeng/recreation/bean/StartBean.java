package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Author： fanyafeng
 * Data： 17/1/13 上午11:38
 * Email: fanyafeng@live.cn
 */
public class StartBean implements Parcelable {
    private int id;
    private String imgUrl;
    private String linkUrl;
    private int seconds;
    private String type;
    private boolean isShow;

    public StartBean(JSONObject jsonObject) {
        setId(jsonObject.optInt("id"));
        setImgUrl(jsonObject.optString("img_url"));
        setLinkUrl(jsonObject.optString("link_url"));
        setSeconds(jsonObject.optInt("seconds"));
        setType(jsonObject.optString("jpg"));
        setShow(jsonObject.optBoolean("show"));
    }


    protected StartBean(Parcel in) {
        id = in.readInt();
        imgUrl = in.readString();
        linkUrl = in.readString();
        seconds = in.readInt();
        type = in.readString();
        isShow = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imgUrl);
        dest.writeString(linkUrl);
        dest.writeInt(seconds);
        dest.writeString(type);
        dest.writeByte((byte) (isShow ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StartBean> CREATOR = new Creator<StartBean>() {
        @Override
        public StartBean createFromParcel(Parcel in) {
            return new StartBean(in);
        }

        @Override
        public StartBean[] newArray(int size) {
            return new StartBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public String toString() {
        return "StartBean{" +
                "id=" + id +
                ", imgUrl='" + imgUrl + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", seconds=" + seconds +
                ", type='" + type + '\'' +
                ", isShow=" + isShow +
                '}';
    }
}
