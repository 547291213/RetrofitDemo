package com.example.xkfeng.retrofitdemo.Service;


import com.example.xkfeng.retrofitdemo.POJO.IpModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by initializing on 2018/7/28.
 */

public interface RxJavaService {
   @GET("getIpInfo.php")
   Observable<IpModel> rxJavaRetrofitGetIpModel(@Query("ip") String ip) ;

}
