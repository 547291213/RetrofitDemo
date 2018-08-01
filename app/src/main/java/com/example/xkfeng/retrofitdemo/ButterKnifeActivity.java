package com.example.xkfeng.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.example.xkfeng.retrofitdemo.Dagger2.App;
import com.example.xkfeng.retrofitdemo.Dagger2.BaoZi;
import com.example.xkfeng.retrofitdemo.Dagger2.MianTao;
import com.example.xkfeng.retrofitdemo.POJO.Message;
import com.example.xkfeng.retrofitdemo.RXBUS.RxBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by initializing on 2018/7/28.
 */

/*
      Butterknife的优势
      1 强大的View绑定，资源绑定，事件绑定。简化代码 提升开发效率
      2 方便处理Adapter中Holder的问题
      3 运行时不会影响app的效率，使用配置方便   编译时注解。不影响运行
      4 代码清晰，可读性比较强

 */
public class ButterKnifeActivity extends AppCompatActivity {
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.tv_5)
    TextView tv5;

    @Inject
    BaoZi mianTao ;

    @Inject
    MianTao mianTao1 ;
    private static final String TAG = "ButterKnifeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_main);
        //ButterKnife插件的绑定
        ButterKnife.bind(this);

        ((App)getApplication()).getApplicationModule().inject(this);
        Toast.makeText(this, "HashCode : " + mianTao.hashCode() + "\nMiantao Hash "+mianTao1.hashCode(), Toast.LENGTH_SHORT).show();

    }
    @OnClick(R.id.tv_1)
    public void setTv1Click()
    {
        Toast.makeText(this, "TV1 Click", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_2)
    public void setTv2Click()
    {
        startActivity(new Intent(ButterKnifeActivity.this , MainActivity.class));
    }
    @OnClick(R.id.tv_3)
    public void setTv3Click()
    {
        Log.d(TAG, "setTv3Click: send message ");
    }
}
