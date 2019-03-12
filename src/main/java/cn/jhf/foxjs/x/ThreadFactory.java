package cn.jhf.foxjs.x;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者:ZFox
 * 创建:2019/3/12 0012
 * 所属包:cn.jhf.foxjs.x
 * 描述:线程工具类
 **/
public class ThreadFactory {
    private static ExecutorService singleExecutor;
    private static ExecutorService fixedExecutor;
    private static ExecutorService cachedExecutor;

    public static void runOnSingleThreadExecutor(Runnable runnable){
        if (singleExecutor==null||singleExecutor.isShutdown())singleExecutor = Executors.newSingleThreadExecutor();
        singleExecutor.execute(runnable);
    }

    public static void runOnFixedThreadExecutor(Runnable runnable,int size){
        if (fixedExecutor==null||singleExecutor.isShutdown())fixedExecutor = Executors.newFixedThreadPool(size);
        fixedExecutor.execute(runnable);
    }

    public static void runOnCachedExecutorExecutor(Runnable runnable){
        if (cachedExecutor==null||singleExecutor.isShutdown())cachedExecutor = Executors.newCachedThreadPool();
        cachedExecutor.execute(runnable);
    }

    public static void destory(){
        if (singleExecutor!=null)singleExecutor.shutdown();
        if (fixedExecutor!=null)fixedExecutor.shutdown();
        if (cachedExecutor!=null)cachedExecutor.shutdown();
    }
}
