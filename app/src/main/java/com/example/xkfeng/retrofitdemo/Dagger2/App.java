package com.example.xkfeng.retrofitdemo.Dagger2;

import android.app.Application;
import android.content.Context;

/**
 * Created by initializing on 2018/8/1.
 */

public class App extends Application {
    ApplicationModule applicationModule ;

    /**
     * Called when the application is starting, before any other application
     * objects have been created.  Implementations should be as quick as
     * possible (for example using lazy initialization of state) since the time
     * spent in this function directly impacts the performance of starting the
     * first activity, service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        applicationModule = DaggerApplicationModule.builder()
                               .zhaiNanModule(new ZhaiNanModule())
                               .build() ;
    }

    public ApplicationModule getApplicationModule()
    {
        return applicationModule ;
    }

}
