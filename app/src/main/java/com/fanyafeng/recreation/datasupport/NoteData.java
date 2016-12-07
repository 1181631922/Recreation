package com.fanyafeng.recreation.datasupport;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Author： fanyafeng
 * Data： 16/12/7 17:25
 * Email: fanyafeng@live.cn
 */
public class NoteData extends DataSupport {
    @Column(unique = true)
    private int id;
    private boolean hasPic;
    private String title;
    private String desc;
    private String picUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return "NoteData{" +
                "id=" + id +
                ", hasPic=" + hasPic +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }
}
