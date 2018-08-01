package com.example.xkfeng.retrofitdemo.Service;

import com.example.xkfeng.retrofitdemo.POJO.IpModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by initializing on 2018/7/26.
 */

public interface IpQueryService {
    @GET("getIpInfo.php")
    Call<IpModel> getIpMsg(@Query("ip") String ip) ;
}
