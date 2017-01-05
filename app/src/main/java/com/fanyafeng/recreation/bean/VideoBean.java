package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Author： fanyafeng
 * Data： 17/1/5 上午10:37
 * Email: fanyafeng@live.cn
 */
public class VideoBean implements Parcelable {
    private int id;
    private String img;
    private String videoUrl;
    private String title;
    private String des;
    private String headerImg;

    public VideoBean(JSONObject jsonObject) {
        setId(jsonObject.optInt("id"));
        setImg(jsonObject.optString("img"));
        setVideoUrl(jsonObject.optString("video_url"));
        setTitle(jsonObject.optString("title"));
        setDes(jsonObject.optString("des"));
        setHeaderImg(jsonObject.optString("header_img"));
    }

    protected VideoBean(Parcel in) {
        id = in.readInt();
        img = in.readString();
        videoUrl = in.readString();
        title = in.readString();
        des = in.readString();
        headerImg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(img);
        dest.writeString(videoUrl);
        dest.writeString(title);
        dest.writeString(des);
        dest.writeString(headerImg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoBean> CREATOR = new Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel in) {
            return new VideoBean(in);
        }

        @Override
        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", title='" + title + '\'' +
                ", des='" + des + '\'' +
                ", headerImg='" + headerImg + '\'' +
                '}';
    }
}
