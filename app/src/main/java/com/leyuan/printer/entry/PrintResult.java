package com.leyuan.printer.entry;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 2017/4/17.
 */
public class PrintResult {

    @SerializedName("lessonType")
    int lessonType; //课程类型(1-APP 0-微信),
    @SerializedName("code")
    String code;  //"验证码"
    @SerializedName("isPrint")
    int isPrint;//是否已经打印,(0-未打印 1-已打印过)
    @SerializedName("data")
    LessonInfo lessonInfo;

    public int getLessonType() {
        return lessonType;
    }

    public void setLessonType(int lessonType) {
        this.lessonType = lessonType;
    }

    public int getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(int isPrint) {
        this.isPrint = isPrint;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LessonInfo getLessonInfo() {
        return lessonInfo;
    }

    public void setLessonInfo(LessonInfo lessonInfo) {
        this.lessonInfo = lessonInfo;
    }

    public class LessonInfo {
        String title;
        ArrayList<PrintItem> item;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<PrintItem> getItem() {
            return item;
        }

        public void setItem(ArrayList<PrintItem> item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return "LessonInfo{" +
                    "title='" + title + '\'' +
                    ", item=" + item +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PrintResult{" +
                "lessonType=" + lessonType +
                ", code='" + code + '\'' +
                ", isPrint=" + isPrint +
                ", lessonInfo=" + lessonInfo +
                '}';
    }
}
