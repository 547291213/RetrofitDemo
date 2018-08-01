package com.example.xkfeng.retrofitdemo.Dagger2;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by initializing on 2018/7/29.
 */

@Module
public class ZhaiNanModule {


    @ApplicationScope
    @Provides
    public BaoZi provideBaozi()
    {
        return new BaoZi(" Module Baozi ") ;
    }

    @Provides
    public MianTao provideMiantao()
    {
        return new MianTao( " Module MianTao") ;
    }

    @Provides
    @Named("value1")
    public int provideTestValue()
    {
        return 123;
    }


    @Provides
    @Named("value2")
    public int provideTestValue2()
    {
        return 456;
    }


}
