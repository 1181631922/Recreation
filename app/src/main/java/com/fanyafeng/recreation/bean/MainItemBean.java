package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Author： fanyafeng
 * Data： 16/11/9 14:57
 * Email: fanyafeng@live.cn
 * <p/>
 * "format": "image",
 * "image": "app117928293.jpg",
 * "published_at": 1478537402,
 * "tag": "",
 * "user": {
 * "avatar_updated_at": 1472121371,
 * "uid": 32508110,
 * "last_visited_at": 1472121371,
 * "created_at": 1472121371,
 * "state": "active",
 * "last_device": "ios_10.2.11",
 * "role": "",
 * "login": "\u718a\u718a\u4e8c\u718a\u718a\u4e8c",
 * "id": 32508110,
 * "icon": "2016082518361153.JPEG"
 * },
 * "image_size": {
 * "s": [197, 352, 12571],
 * "m": [420, 750, 49153]
 * },
 * "id": 117928293,
 * "votes": {
 * "down": -115,
 * "up": 1382
 * },
 * "created_at": 1478530801,
 * "content": "\u8fd8\u6709\u671f\u5f85\u7684\u5417",
 * "state": "publish",
 * "comments_count": 49,
 * "allow_comment": true,
 * "share_count": 374,
 * "type": "hot"
 */
public class MainItemBean implements Parcelable {

    private String image;
    private int id;
    private String content;

    public MainItemBean(JSONObject jsonObject) {
        setId(jsonObject.optInt("id"));
        setImage(jsonObject.optString("image"));
        setContent(jsonObject.optString("content"));
    }

    protected MainItemBean(Parcel in) {
        image = in.readString();
        id = in.readInt();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeInt(id);
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MainItemBean> CREATOR = new Creator<MainItemBean>() {
        @Override
        public MainItemBean createFromParcel(Parcel in) {
            return new MainItemBean(in);
        }

        @Override
        public MainItemBean[] newArray(int size) {
            return new MainItemBean[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MainItemBean{" +
                "image='" + image + '\'' +
                ", id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
