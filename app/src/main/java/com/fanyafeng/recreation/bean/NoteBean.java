package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author： fanyafeng
 * Data： 16/12/7 17:17
 * Email: fanyafeng@live.cn
 */
public class NoteBean implements Parcelable{
    private boolean hasPic;
    private String title;
    private String desc;
    private String picUrl;

    public NoteBean() {
    }

    protected NoteBean(Parcel in) {
        hasPic = in.readByte() != 0;
        title = in.readString();
        desc = in.readString();
        picUrl = in.readString();
    }

    public static final Creator<NoteBean> CREATOR = new Creator<NoteBean>() {
        @Override
        public NoteBean createFromParcel(Parcel in) {
            return new NoteBean(in);
        }

        @Override
        public NoteBean[] newArray(int size) {
            return new NoteBean[size];
        }
    };

    public boolean isHasPic() {
        return hasPic;
    }

    public void setHasPic(boolean hasPic) {
        this.hasPic = hasPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "NoteBean{" +
                "hasPic=" + hasPic +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (hasPic ? 1 : 0));
        parcel.writeString(title);
        parcel.writeString(desc);
        parcel.writeString(picUrl);
    }
}
