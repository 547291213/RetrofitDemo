package com.example.xkfeng.retrofitdemo.Dagger2;

import javax.inject.Inject;

/**
 * Created by initializing on 2018/7/29.
 */

public class MianTao {

    String string ;

    @Inject
    public MianTao()
    {

    }

    public MianTao(String str)
    {
        string = str ;
    }

    @Override
    public String toString() {

        if (string == null)
        return " 面条" ;

        return string ;
     }
}
