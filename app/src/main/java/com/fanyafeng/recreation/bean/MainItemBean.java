package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fanyafeng.recreation.util.StringUtil;

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

    private String title;   //title有可能为空
    private String image;   //http://p3.pstatp.com/需要加此前缀
    private long id;        //id
    private String content; //text 有就显示没有就不显示
    private String mp4Url;  //mp4_url

    public MainItemBean(JSONObject jsonObject) {
        JSONObject group = jsonObject.optJSONObject("group");
        if (group != null) {
            setTitle(group.optString("title"));
            setId(group.optLong("id"));
            setContent(group.optString("content"));
            if (!StringUtil.isNullOrEmpty(group.optString("mp4_url"))) {
                setMp4Url(group.optString("mp4_url"));
            }
            JSONObject gifvideo = group.optJSONObject("gifvideo");
            if (gifvideo != null) {
                setMp4Url(group.optString("mp4_url"));
            }

            JSONObject middle_image = group.optJSONObject("middle_image");
            if (middle_image != null) {
                setImage("http://p3.pstatp.com/" + middle_image.optString("uri"));
            }
            JSONObject large_image = group.optJSONObject("large_image");
            if (large_image != null) {
                setImage("http://p3.pstatp.com/" + large_image.optString("uri"));
            }
        }
    }


    protected MainItemBean(Parcel in) {
        title = in.readString();
        image = in.readString();
        id = in.readLong();
        content = in.readString();
        mp4Url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeLong(id);
        dest.writeString(content);
        dest.writeString(mp4Url);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMp4Url() {
        return mp4Url;
    }

    public void setMp4Url(String mp4Url) {
        this.mp4Url = mp4Url;
    }

    @Override
    public String toString() {
        return "MainItemBean{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", id=" + id +
                ", content='" + content + '\'' +
                ", mp4Url='" + mp4Url + '\'' +
                '}';
    }
}
