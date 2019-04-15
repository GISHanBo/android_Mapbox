package com.example.myapplication;

/**
 * MapView没有地图错误类
 */

public class NoMapException extends Exception {

    public NoMapException(String message) {
        super(message);
    }
}
