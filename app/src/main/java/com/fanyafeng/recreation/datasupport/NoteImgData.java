package com.fanyafeng.recreation.datasupport;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Author： fanyafeng
 * Data： 16/12/8 15:09
 * Email: fanyafeng@live.cn
 */
public class NoteImgData extends DataSupport {
    @Column(unique = true)
    private int id;
    private String imgUrl;
    private String imgDesc;

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

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    @Override
    public String toString() {
        return "NoteImgData{" +
                "id=" + id +
                ", imgUrl='" + imgUrl + '\'' +
                ", imgDesc='" + imgDesc + '\'' +
                '}';
    }
}
