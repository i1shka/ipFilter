package com.example.ipFilter;

public class WrongIpException extends Exception {
    String ip;

    public WrongIpException(String ip, String message){
        super(message);
        this.ip = ip;
    }
}
