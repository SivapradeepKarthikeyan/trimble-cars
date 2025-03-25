package com.trimble.trimblecars.responses;


public class TrimbleCarResponse {

    public TrimbleCarResponse(String status, String message, int statusCode, Object data) {
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    private String status;
    private String message;
    private int statusCode;
    Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TrimbleCarResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", statusCode=" + statusCode +
                ", data=" + data +
                '}';
    }
}
