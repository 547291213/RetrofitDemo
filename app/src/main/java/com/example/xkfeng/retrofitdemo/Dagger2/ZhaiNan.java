package com.example.xkfeng.retrofitdemo.Dagger2;

import android.widget.Toast;

import javax.inject.Inject;

/**
 * Created by initializing on 2018/7/29.
 */

public class ZhaiNan {

    @Inject
    BaoZi baoZi ;

    @Inject
    MianTao mianTao ;

    @Inject
    public ZhaiNan()
    {

    }

    public String eat()
    {
      StringBuilder sb = new StringBuilder() ;
      sb.append("Eating :") ;
      if (baoZi != null)
      {
          sb.append(baoZi.toString()) ;
      }

      if (mianTao != null)
      {
          sb.append(mianTao.toString()) ;
      }

      return sb.toString() ;

    }
}
