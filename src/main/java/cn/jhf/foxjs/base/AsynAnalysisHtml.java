package cn.jhf.foxjs.base;


import cn.jhf.foxjs.anm.Async;
import cn.jhf.foxjs.anm.HtmlRequest;
import cn.jhf.foxjs.x.Log;
import cn.jhf.foxjs.x.ThreadFactory;

/**
 * 作者:ZFox
 * 创建:2019/3/12 0012
 * 所属包:cn.jhf.foxjs.base
 * 描述:支持异步解析html
 **/
@SuppressWarnings("all")
public abstract class AsynAnalysisHtml<T> extends AnalysisHtml<T> implements Runnable{

    private Async async;

    /**
     * 默认的构造方法
     */
    public AsynAnalysisHtml(){
        super(System.currentTimeMillis());
        try {
            beforeOnCreate();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 默认的构造方法
     */
    public AsynAnalysisHtml(Class<?> cls){
        super(System.currentTimeMillis());
        this.htmlRequest = cls.getAnnotation(HtmlRequest.class);
        this.async = cls.getAnnotation(Async.class);
        try {
            beforeOnCreate();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 在onCreate()方法之前执行
     * @throws Throwable 异常
     */
    private void beforeOnCreate() throws Throwable {
        Async async = this.getClass().getAnnotation(Async.class);
        if (async==null){
            async = this.async;
        }
        if (async==null){
            Log.d("异步标记不存在,已同步处理");
            onCreate();
        }else{
            switch (async.threadType()){
                case 0:
                    ThreadFactory.runOnSingleThreadExecutor(this);
                    break;
                case 1:
                    ThreadFactory.runOnFixedThreadExecutor(this,async.size());
                    break;
                case 2:
                    ThreadFactory.runOnCachedExecutorExecutor(this);
                    break;
                    default:
                        break;
            }
        }
    }

    @Override
    public void run() {
        try {
            onCreate();
            result(getResult());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public abstract void result(T t);
}
