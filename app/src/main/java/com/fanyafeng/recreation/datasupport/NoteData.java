package com.fanyafeng.recreation.datasupport;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： fanyafeng
 * Data： 16/12/7 17:25
 * Email: fanyafeng@live.cn
 */
public class NoteData extends DataSupport {
    private long id;
    private boolean hasPic;
    private String title;
    private long createData;
    private long titleHeader;
    private String desc;
    private String picUrl;
    private List<NoteImgData> noteImgDataList = new ArrayList<>();

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

    public long getCreateData() {
        return createData;
    }

    public void setCreateData(long createData) {
        this.createData = createData;
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

    public List<NoteImgData> getNoteImgDataList() {
        return noteImgDataList;
    }

    public void setNoteImgDataList(List<NoteImgData> noteImgDataList) {
        this.noteImgDataList = noteImgDataList;
    }

    @Override
    public String toString() {
        return "NoteData{" +
                "id=" + id +
                ", hasPic=" + hasPic +
                ", title='" + title + '\'' +
                ", createData=" + createData +
                ", titleHeader=" + titleHeader +
                ", desc='" + desc + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", noteImgDataList=" + noteImgDataList +
                '}';
    }
}
