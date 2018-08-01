package com.example.xkfeng.retrofitdemo.Service;

import com.example.xkfeng.retrofitdemo.POJO.IpModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by initializing on 2018/7/26.
 */

public interface IpService {
    @GET("{path}.php?ip=59.108.54.37")
    Call<IpModel> getIpMsg(@Path("path") String path) ;
}
