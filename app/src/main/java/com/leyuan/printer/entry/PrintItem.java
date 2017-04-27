package com.leyuan.printer.entry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2017/4/18.
 */

public class PrintItem implements Parcelable {

    String name;
    String value;

    public PrintItem(String name, String value) {
        this.name = name;
        this.value = value;
    }

    protected PrintItem(Parcel in) {
        name = in.readString();
        value = in.readString();
    }

    public static final Creator<PrintItem> CREATOR = new Creator<PrintItem>() {
        @Override
        public PrintItem createFromParcel(Parcel in) {
            return new PrintItem(in);
        }

        @Override
        public PrintItem[] newArray(int size) {
            return new PrintItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(value);
    }
}
