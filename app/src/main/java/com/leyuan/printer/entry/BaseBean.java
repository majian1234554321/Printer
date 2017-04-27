package com.leyuan.printer.entry;


public class BaseBean<T> {

    private int code;
    private String message;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
