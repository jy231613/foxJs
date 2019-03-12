package cn.jhf.foxjs.example;

import cn.jhf.foxjs.anm.Async;
import cn.jhf.foxjs.anm.HtmlRequest;
import cn.jhf.foxjs.base.AnalysisHtml;
import cn.jhf.foxjs.base.AsynAnalysisHtml;
import cn.jhf.foxjs.x.Log;
import cn.jhf.foxjs.x.ThreadFactory;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 作者:ZFox
 * 创建:2019/3/12 0012
 * 所属包:cn.jhf.foxjs.example
 * 描述:例子
 **/
@Async
@HtmlRequest("https://wannianrili.51240.com/")
public class Example {

    public static void main(String[] args){

//        runA();

//        runB();

//        runC();

        runD();

    }

    private static void runD() {
        //匿名内部类的形式
        //注意,使用时在使用的类添加注解,然后使用带注解的构造参数进行初始化即可
        AsynAnalysisHtml<String> analysisHtml = new AsynAnalysisHtml<String>(Example.class) {
            @Override
            public void result(String s) {
                Log.a(s);
            }

            @Override
            public String analysis(Document document) {
                Elements elms = document.getElementsByClass("wnrl_k_you_id_wnrl_nongli_ganzhi");
                if (elms!=null&&elms.size()>0){
                    return elms.get(0).text();
                }
                return "!!!";
            }
        };
        Log.a(analysisHtml.getResult());
        //注意,执行完之后一定要记得关闭线程池工厂
        ThreadFactory.destory();
    }

    private static void runC() {
        //匿名内部类的形式
        //注意,使用时在使用的类添加注解,然后使用带注解的类的构造参数进行初始化即可
        AnalysisHtml<String> analysisHtml = new AnalysisHtml<String>(Example.class) {
            @Override
            public String analysis(Document document) {
                Elements elms = document.getElementsByClass("wnrl_k_you_id_wnrl_nongli_ganzhi");
                if (elms!=null&&elms.size()>0){
                    return elms.get(0).text();
                }
                return "where";
            }
        };
        Log.a(analysisHtml.getResult());
    }

    private static void runB() {
        //例子,来自网络请求,异步---getResult会是null,因为异步线程还没有执行完
        ExampleAsyncFromHttp exampleAsyncFromHttp = new ExampleAsyncFromHttp();
        Log.a(exampleAsyncFromHttp.getResult());
        //注意,执行完之后一定要记得关闭线程池工厂
        ThreadFactory.destory();
    }

    private static void runA() {
        //例子,来自网络请求
        ExampleFromHttp exampleFromHttp = new ExampleFromHttp();
        Log.a(exampleFromHttp.getResult());
    }

}
