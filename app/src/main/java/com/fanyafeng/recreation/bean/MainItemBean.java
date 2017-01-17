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
    private int isGif;      //图片是gif为1，不是为0
    private String userName;
    private String userImg;
    private int videoWidth;
    private int videoHeight;

    public MainItemBean(JSONObject jsonObject) {
        JSONObject group = jsonObject.optJSONObject("group");
        setContent("此处为广告，已为您过滤");
        if (group != null) {
            setIsGif(group.optInt("is_gif"));
            setTitle(group.optString("title"));
            setId(group.optLong("id"));
            setContent(group.optString("content"));

            //用户信息
            JSONObject user = group.optJSONObject("user");
            if (user != null) {
                setUserName(user.optString("name"));//发帖用户姓名
                setUserImg(user.optString("avatar_url"));//发帖用户头像
            }

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

            JSONObject videoH = group.optJSONObject("720p_video");
            if (videoH != null) {
                setVideoWidth(videoH.optInt("width"));
                setVideoHeight(videoH.optInt("height"));
            }

            JSONObject videoM = group.optJSONObject("480p_video");
            if (videoM != null) {
                setVideoWidth(videoH.optInt("width"));
                setVideoHeight(videoH.optInt("height"));
            }

            JSONObject videoS = group.optJSONObject("360p_video");
            if (videoS != null) {
                setVideoWidth(videoH.optInt("width"));
                setVideoHeight(videoH.optInt("height"));
            }


        }
    }


    protected MainItemBean(Parcel in) {
        title = in.readString();
        image = in.readString();
        id = in.readLong();
        content = in.readString();
        mp4Url = in.readString();
        isGif = in.readInt();
        userName = in.readString();
        userImg = in.readString();
        videoWidth = in.readInt();
        videoHeight = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeLong(id);
        dest.writeString(content);
        dest.writeString(mp4Url);
        dest.writeInt(isGif);
        dest.writeString(userName);
        dest.writeString(userImg);
        dest.writeInt(videoWidth);
        dest.writeInt(videoHeight);
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

    public int getIsGif() {
        return isGif;
    }

    public void setIsGif(int isGif) {
        this.isGif = isGif;
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

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    @Override
    public String toString() {
        return "MainItemBean{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", id=" + id +
                ", content='" + content + '\'' +
                ", mp4Url='" + mp4Url + '\'' +
                ", isGif=" + isGif +
                ", userName='" + userName + '\'' +
                ", userImg='" + userImg + '\'' +
                ", videoWidth=" + videoWidth +
                ", videoHeight=" + videoHeight +
                '}';
    }
}
