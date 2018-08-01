package com.example.xkfeng.retrofitdemo.Dagger2;

import com.example.xkfeng.retrofitdemo.ButterKnifeActivity;
import com.example.xkfeng.retrofitdemo.MainActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by initializing on 2018/7/29.
 */

/*
     @Inject注解

     1 @Inject给类中的属性进行注解，说明了他是一个依赖需求方
     2 @Inject给类的构造方法进行注解，说明了他是一个依赖提供方
     3 @Inject注解类中的方法，那么该方法会在构造方法调用之后随后调用

     @Component注解
     @Component相当于一个纽带，用于把依赖需求方和依赖提供方联系起来

     @Module and @Provides
     有些时候我们无法使用Inject注解来提供依赖（eg:第三方类库，接口等抽象，已经被@Inject注解过构造函数的类）
     这个时候我们需要用@Module和@Provides来提供对象的依赖
     需要注意的是：
     如果一个Module类没有实现任何构造方法，那么在Component中Dagger2会自动创建，如果这个Module实现了有参的构造方法，那么就
     需要我们在Component的时候手动创建，

    @Singleton注解
    解析：@Singleton是@Scope的一种默认实现
    @Scope
    @Documented
    @Retention(RUNTIME)
    public @interface @Singleton{
    }
    @Singleton实现单例
    用@Singleton标注在目标单例上，然后再@Component上也用@Singleton标注

    可以发现@Singleton需要同时在@Component(依赖关系纽带)和@Inject或@Provides (依赖提供方)进行注解，可以理解为:
    注解依赖提供方指明了需要实现单例的对象，注解依赖关系纽带指明了在单个@Component对象范围内单例

    如果采用一般的注入方式，那么@Singleton的单例是Activity的。如果需要实现Application上的单例还需要我们自定义继承Application的类，
    在类中实现@Component指定的接口，再给出一个getter方法，返回接口对象即可

    @Qualifiers和@Name
    Qulifiers原指修饰符，用标记依赖的唯一性
    @Name是Qualifiers的特殊实现

    @Qualifier
    @Documented
    @Retention(RUNTIME)
    public @interface Named {
         String value() default "";
        }


 */
//@Component
public interface PlatForm {

    public void waimai(MainActivity activity) ;

    public void waimai2(ButterKnifeActivity butterKnifeActivity) ;


}
