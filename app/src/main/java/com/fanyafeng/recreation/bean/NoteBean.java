package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author： fanyafeng
 * Data： 16/12/7 17:17
 * Email: fanyafeng@live.cn
 */
public class NoteBean implements Parcelable {
    private long id;
    private boolean hasPic;
    private String title;
    private long creatData;
    private long titleHeader;
    private String desc;
    private String picUrl;

    public NoteBean() {
    }

    protected NoteBean(Parcel in) {
        id = in.readLong();
        hasPic = in.readByte() != 0;
        title = in.readString();
        creatData = in.readLong();
        titleHeader = in.readLong();
        desc = in.readString();
        picUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeByte((byte) (hasPic ? 1 : 0));
        dest.writeString(title);
        dest.writeLong(creatData);
        dest.writeLong(titleHeader);
        dest.writeString(desc);
        dest.writeString(picUrl);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getCreatData() {
        return creatData;
    }

    public void setCreatData(long creatData) {
        this.creatData = creatData;
    }

    public long getTitleHeader() {
        return titleHeader;
    }

    public void setTitleHeader(long titleHeader) {
        this.titleHeader = titleHeader;
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
                "id=" + id +
                ", hasPic=" + hasPic +
                ", title='" + title + '\'' +
                ", creatData=" + creatData +
                ", titleHeader=" + titleHeader +
                ", desc='" + desc + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }
}
