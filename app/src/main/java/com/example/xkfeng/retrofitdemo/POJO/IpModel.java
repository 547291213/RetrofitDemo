package com.example.xkfeng.retrofitdemo.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by initializing on 2018/7/26.
 */

public class IpModel {
    private int code;

    private Data data;

    public int getCode() {
        return code;
    }

    public Data getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(Data data) {
        this.data = data;
    }


}
