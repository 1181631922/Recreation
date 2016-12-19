package com.fanyafeng.recreation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author： fanyafeng
 * Data： 16/12/19 上午10:44
 * Email: fanyafeng@live.cn
 */
public class FodderBean implements Parcelable{
    private String fooderName;
    private String fooderMany;

    public FodderBean() {
    }

    protected FodderBean(Parcel in) {
        fooderName = in.readString();
        fooderMany = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fooderName);
        dest.writeString(fooderMany);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FodderBean> CREATOR = new Creator<FodderBean>() {
        @Override
        public FodderBean createFromParcel(Parcel in) {
            return new FodderBean(in);
        }

        @Override
        public FodderBean[] newArray(int size) {
            return new FodderBean[size];
        }
    };

    public String getFooderName() {
        return fooderName;
    }

    public void setFooderName(String fooderName) {
        this.fooderName = fooderName;
    }

    public String getFooderMany() {
        return fooderMany;
    }

    public void setFooderMany(String fooderMany) {
        this.fooderMany = fooderMany;
    }

    @Override
    public String toString() {
        return "FodderBean{" +
                "fooderName='" + fooderName + '\'' +
                ", fooderMany='" + fooderMany + '\'' +
                '}';
    }
}
