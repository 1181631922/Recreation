package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fanyafeng.recreation.util.StringUtil;

import org.json.JSONObject;


public class MainItemBean implements Parcelable {

    private String title;   //title有可能为空
    private String image;   //http://p3.pstatp.com/需要加此前缀
    private long id;        //id
    private String content; //text 有就显示没有就不显示
    private String mp4Url;  //mp4_url

    public MainItemBean(JSONObject jsonObject) {
        JSONObject group = jsonObject.optJSONObject("group");
        setContent("此处为广告，已为您过滤");
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
