package cn.jhf.foxjs.anm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者:ZFox
 * 创建:2019/3/12 0012
 * 所属包:cn.jhf.foxjs.anm
 * 描述:异步操作标记
 **/
@Retention(RetentionPolicy.RUNTIME)//生命周期一直存在
@Target( { ElementType.TYPE})//应用于类
public @interface Async {

    /**
     * 线程池模型
     * 0:SingleThread
     * 1:FixedThread
     * 2:CachedThread
     * @return 默认单例线程
     */
    int threadType() default 0;

    /**
     * 线程池长度,仅在设置type为1时有效
     * @return 默认20
     */
    int size() default 20;

}
