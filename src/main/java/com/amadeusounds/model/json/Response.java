package com.amadeusounds.model.json;

/**
 * Created by Vladica Jovanovski on 4/13/2016.
 */
public class Response {
    private ResponseType responseType;
    private Object message;

    public Response() {
    }

    public Response(ResponseType responseType, Object message) {
        this.responseType = responseType;
        this.message = message;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
