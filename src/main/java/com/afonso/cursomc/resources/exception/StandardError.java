package com.afonso.cursomc.resources.exception;

import java.io.Serializable;

public class StandardError implements Serializable {

    private Integer status = -1;
    private String msg = "";
    private Long timeStamp = -1L;
    private String path;
    private String error;

    public StandardError() {
    }

    public StandardError(Integer status, String msg, Long timeStamp, String error, String path) {
        super();
        this.status = status;
        this.msg = msg;
        this.timeStamp = timeStamp;
        this.path = path;
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
