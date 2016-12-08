package com.fanyafeng.recreation.datasupport;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Author： fanyafeng
 * Data： 16/12/8 15:09
 * Email: fanyafeng@live.cn
 */
public class NoteImgData extends DataSupport {
    private NoteData noteData;
    private String imgUrl;
    private String imgDesc;

    public NoteData getNoteData() {
        return noteData;
    }

    public void setNoteData(NoteData noteData) {
        this.noteData = noteData;
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
                "noteData=" + noteData +
                ", imgUrl='" + imgUrl + '\'' +
                ", imgDesc='" + imgDesc + '\'' +
                '}';
    }
}
