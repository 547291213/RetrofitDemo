package com.example.xkfeng.retrofitdemo.Dagger2;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by initializing on 2018/8/1.
 */


@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationScope {

}
