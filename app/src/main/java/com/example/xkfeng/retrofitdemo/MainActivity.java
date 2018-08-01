package com.example.xkfeng.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.xkfeng.retrofitdemo.Dagger2.App;
import com.example.xkfeng.retrofitdemo.Dagger2.BaoZi;
import com.example.xkfeng.retrofitdemo.Dagger2.DaggerApplicationModule;
import com.example.xkfeng.retrofitdemo.Dagger2.MianTao;
import com.example.xkfeng.retrofitdemo.Dagger2.ZhaiNan;
import com.example.xkfeng.retrofitdemo.Dagger2.ZhaiNanModule;
import com.example.xkfeng.retrofitdemo.POJO.Data;
import com.example.xkfeng.retrofitdemo.POJO.IpModel;
import com.example.xkfeng.retrofitdemo.POJO.Message;
import com.example.xkfeng.retrofitdemo.RXBUS.RxBus;
import com.example.xkfeng.retrofitdemo.Service.IpQueryService;
import com.example.xkfeng.retrofitdemo.Service.IpService;
import com.example.xkfeng.retrofitdemo.Service.RxJavaService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    /*
       Retrofit Configuration
       Retrofit is the class through which your API interfaces are turned into callable objects .
       By default , Retrofit will give you sane defaults for your platform but it allows for customization .


       CONVERTERS
       By default , Retrofit can only deserialize HTTP bodies into OkHttp's ResponseBody type and it can only
       accept its ResquestBody type for @Body .

       @QueryMap注解会把参数拼接到url后面，所以它适用于GET请求；
       @Body会把参数放到请求体中，所以适用于POST请求。


     */

    /*
       RxBus
       背压：back_pressure: 网络拥塞信息逆流通过一个internet网络

       RXjava中理解为：
       被观察者发送信息太快以至于他的操作符或者订阅者信息不能及时处理相关的信息，出现操作消息阻塞的情况

       RxJava中解决阻塞问题的方法：
       减少事件发送的频率，减少事件发送的数量

       RxJava在2.0后的背压处理：
       BackpressureStrategy

     * OnNext events are written without any buffering or dropping.
     * Downstream has to deal with any overflow.
     * <p>Useful when one applies one of the custom-parameter onBackpressureXXX operators.
      MISSING,    //丢失 MissingBackpressureException或IllegalStateException。


     * Signals a MissingBackpressureException in case the downstream can't keep up.
     ERROR,  //报错 MissingBackpressureException。

     * Buffers <em>all</em> onNext values until the downstream consumes it.
     BUFFER, //缓存，知道可用

      * Drops the most recent onNext value if the downstream can't keep up.
      DROP  //删除后端


     * Keeps only the latest onNext value, overwriting any previous value if the
     * downstream can't keep up.
     LATEST  //会一直保留最新的onNext的值，直到被下游消费掉。如果下流不能跟上，那么会重写先前的值

     */


    private static final String TAG = "MainActivity";
    private static final String URL = "http://ip.taobao.com/service/";
    private TextView textView;
    private Retrofit retrofit;
    private OkHttpClient mOkHttpClient;
    private static StringBuilder stringBuilder = new StringBuilder();

    @Inject
    ZhaiNan zhaiNan ;

    @Inject
    @Named("value1")
    int testValue ;

    @Inject
    @Named("value2")
    int testValue2 ;

    @Inject
    BaoZi baoZi1 ;

    @Inject
    BaoZi baoZi2 ;

    @Inject
    MianTao mianTao1 ;

    @Inject
    MianTao mianTao2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.tv_ipMsg);

        ((App)getApplication()).getApplicationModule().inject(this);

        Toast.makeText(this, "Hashcode :" + baoZi1.hashCode() + "\nHashcode :"+baoZi2.hashCode() + "\nMianTao1 " + mianTao1.hashCode()
                + "\nMiantao2 " + mianTao2.hashCode(), Toast.LENGTH_SHORT).show();

    }

    public void btnClickGainIpMsg(View view) {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IpQueryService ipService = retrofit.create(IpQueryService.class);

        Call<IpModel> call = ipService.getIpMsg("59.108.54.37");
        call.enqueue(new Callback<IpModel>() {
            @Override
            public void onResponse(Call<IpModel> call, Response<IpModel> response) {
                Toast.makeText(getApplicationContext(), "成功获取了ip数据", Toast.LENGTH_SHORT).show();
                Data data = response.body().getData();
                textView.setText(data.getArea() + data.getCity() + data.getCountry());
            }

            @Override
            public void onFailure(Call<IpModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void backPressureClick(View view) {

        Toast.makeText(MainActivity.this, "  backPressureClick ", Toast.LENGTH_SHORT).show();
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 135; i++) {
                    Log.d(TAG, "emit " + i);
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                        s.request(Long.MAX_VALUE); //设置downstream处理事件的能力
                    }

                    @Override
                    public void onNext(Integer integer) {

                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                        Log.d(TAG, "onError: " + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
//
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0;; i++) {   //无限循环发事件
//                    emitter.onNext(i);
//                }
//            }
//        }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.d(TAG, "onNext: " + integer);
//                Toast.makeText(MainActivity.this, "Interger "+integer, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
    }

    public void dagger2TestClick(View view)
    {


    }


    public void rxBusEventClick(View view)
    {

        RxBus.getInstance().post(new Message("RxJava use to EventBus !"));
    }

    public void retrofitRxJava(View view) {
        rXjavaRetrofitQuest("59.108.54.37");
    }



    private void rXjavaRetrofitQuest(final String ip) {
        String url = "http://ip.taobao.com/service/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        RxJavaService rxJavaService = retrofit.create(RxJavaService.class);
        Observable<IpModel> observable = rxJavaService.rxJavaRetrofitGetIpModel(ip);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IpModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(IpModel ipModel) {
                        textView.setText(ipModel.getData().getCountry() + "  " + ipModel.getData().getCity());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: " + " Retrofit run with rxjava ais successed .");
                    }
                });

    }

    public void rXjavaBtnClick(View view) {


        List<String> lists = new ArrayList<>();
        lists.add("hello");
        lists.add("eeeee");
        lists.add("rrrrr");
        lists.add("wwwww");
        Observable.fromArray(lists).flatMap(
                new Function<List<String>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<String> strings) throws Exception {
                        return Observable.just(strings);
                    }
                }
        ).cast(String.class).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: FLATMAP is success  " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(5);
                emitter.onNext(15);
                emitter.onNext(52);
                emitter.onNext(-5);
                emitter.onNext(12);
                emitter.onNext(22);
                emitter.onNext(35);
                emitter.onNext(35);
            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer > 10;
            }
        }).buffer(3, 3).map(new Function<List<Integer>, String>() {
            @Override
            public String apply(List<Integer> integers) throws Exception {
                String str = "";
                for (int i : integers) {
                    str += "" + i + "  ";
                }
                return "Data is " + str + "\n";
            }

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(String s) {
                        stringBuilder.append(s);
                        textView.setText(stringBuilder.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    public void OkhttpRxJava(View view) {
        postAsyncHttp("59.108.54.37");
    }

    private void postAsyncHttp(String string) {
        getObservable(string).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, Data>() {
                    @Override
                    public Data apply(String response) throws Exception {
                        try {
                            Gson gson = new Gson();
                            Data data = new Data();
                            Log.d(TAG, "apply: response is " + response);
                            data = gson.fromJson(response, Data.class);
                            return data;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                })
                .filter(new Predicate<Data>() {
                    @Override
                    public boolean test(Data data) throws Exception {
                        return data != null;
                    }
                })
                .subscribe(new Observer<Data>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Data s) {
                        Log.d(TAG, "onNext: ");
                        textView.setText(s.getCountry() + "  " + s.getCity());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Observable<String> getObservable(final String ipaddr) {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                mOkHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("ip", ipaddr)
                        .build();
                final Request request = new Request.Builder()
                        .url("http://ip.taobao.com/service/getIpInfo.php")
                        .post(requestBody)
                        .build();
                okhttp3.Call call = mOkHttpClient.newCall(request);
                call.enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            emitter.onNext(jsonObject.getString("data").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        emitter.onComplete();

                    }
                });
            }
        });
        return observable;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
