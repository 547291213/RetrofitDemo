package com.example.xkfeng.retrofitdemo.Dagger2;

import com.example.xkfeng.retrofitdemo.ButterKnifeActivity;
import com.example.xkfeng.retrofitdemo.MainActivity;

import dagger.Component;
import dagger.Module;

/**
 * Created by initializing on 2018/8/1.
 */
@ApplicationScope
@Component(modules = ZhaiNanModule.class)
public interface ApplicationModule {

    void inject(MainActivity mainActivity) ;
    void inject(ButterKnifeActivity butterKnifeActivity) ;
}
