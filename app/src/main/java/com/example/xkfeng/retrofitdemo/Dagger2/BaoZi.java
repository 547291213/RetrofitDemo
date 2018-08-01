package com.example.xkfeng.retrofitdemo.Dagger2;

import javax.inject.Inject;

/**
 * Created by initializing on 2018/7/29.
 */

public class BaoZi {

    String str ;
    @Inject
    public BaoZi()
    {

    }


    public BaoZi(String s)
    {
        str = s ;
    }

    @Override
    public String toString() {
        if (str==null)
        return " 包子";

        return  str ;
    }
}
