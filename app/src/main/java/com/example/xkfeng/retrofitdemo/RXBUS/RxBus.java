package com.example.xkfeng.retrofitdemo.RXBUS;

import android.support.v4.util.Pools;

import io.reactivex.Observable;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by initializing on 2018/7/29.
 */

public class RxBus {

    private static volatile RxBus rxBus ;

    //发送和接收事件的对象
    private static  Subject<Object> subject  ;

    private RxBus(){
        subject = PublishSubject.create().toSerialized() ;
    }

    /*
       单例  双重检测
     */
    public static RxBus getInstance()
    {
        if (rxBus == null)
        {
            synchronized (RxBus.class)
            {
                if (rxBus == null)
                {
                    rxBus = new RxBus() ;
                }
            }
        }
        return rxBus ;
    }

    /*
      发送事件
     */
    public void post(Object object)
    {
        subject.onNext(object);
    }

    /*
      接收事件
     */
    public<T> Observable<T> toObservable(Class<T> eventType)
    {
        return  subject.ofType(eventType) ;
    }

}
