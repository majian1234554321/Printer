package com.leyuan.printer.entry;


public class BaseBean<T> {

    private int code;
    private String msg;
    private T data;

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public int getStatus() {
        return code;
    }

    public void setStatus(int status) {
        this.code = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "status=" + code +
                ", message='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
