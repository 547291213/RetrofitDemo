package com.example.xkfeng.retrofitdemo.RXBUS;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by initializing on 2018/7/29.
 */

public class RxBusFlowable {

    private static volatile RxBusFlowable rxBusFlowable ;

    private static FlowableProcessor<Object> _bus ;

    private RxBusFlowable()
    {
        _bus = PublishProcessor.create().toSerialized() ;

    }

    public static RxBusFlowable getInstance()
    {
          return Holder.rx ;
    }

    private static class Holder {
        private static final RxBusFlowable rx = new RxBusFlowable() ;
    }

    public void post(Object object)
    {
        _bus.onNext(object);
    }

    public <T>Flowable<T> toObservable(Class<T> tClass)
    {
        return _bus.ofType(tClass) ;
    }
}
