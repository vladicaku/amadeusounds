package com.amadeusounds.model;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
public class Response {
    private ResponseType responseType;
    private String response;

    public Response() {
    }

    public Response(ResponseType responseType, String response) {
        this.responseType = responseType;
        this.response = response;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
