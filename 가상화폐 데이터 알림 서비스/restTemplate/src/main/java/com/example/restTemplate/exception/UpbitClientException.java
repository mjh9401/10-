package com.example.restTemplate.exception;

public class UpbitClientException extends RuntimeException {

    public UpbitClientException(String message) {
        super(String.format("UPBIT 통신 중 에러 발생 =%s",message));
    }
}
