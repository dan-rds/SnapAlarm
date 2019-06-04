package com.example.snapalarm;

import java.util.Map;

public interface AsyncRequester {
    public void onCompletedTask(String str);
    public void onCompletedTask(Map response);
}
