package cn.jhf.foxjs.base;

import cn.jhf.foxjs.anm.HtmlRequest;
import cn.jhf.foxjs.exception.LoadHtmlDefeatedException;
import cn.jhf.foxjs.x.Log;
import com.sun.deploy.net.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

/**
 * 作者:ZFox
 * 创建:2019/3/12 0012
 * 所属包:cn.jhf.foxjs.base
 * 描述:Html解析封装类
 **/
@SuppressWarnings("all")
public abstract class AnalysisHtml<T> implements HtmlAnalysis<T>{

    protected T obj;//结果集对象

    protected String[] sirs = null;//占位值

    protected int tag = -1;//tag

    protected HtmlRequest htmlRequest;

    /**
     * 默认的构造方法
     */
    public AnalysisHtml(Class<?> cls){
        this.htmlRequest = cls.getAnnotation(HtmlRequest.class);
        try {
            onCreate();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 默认的构造方法
     */
    public AnalysisHtml(){
        try {
            onCreate();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 会抛出异常的默认构造方法
     * @param tag 标记
     * @throws Throwable
     */
    public AnalysisHtml(int tag)throws Throwable{
        this.tag = tag;
        onCreate();
    }

    /**
     * 带有占位值得构造方法
     * @param sirs 占位值
     */
    public AnalysisHtml(String... sirs){
        this.sirs = sirs;
        try {
            onCreate();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 带有占位值得构造方法,且抛异常
     * @param tag 标记
     * @param sirs 占位值
     * @throws Throwable
     */
    public AnalysisHtml(int tag,String... sirs)throws Throwable{
        this.tag = tag;
        this.sirs = sirs;
        onCreate();
    }

    /**
     * 不执行onCreate的构造方法
     * @param timer 时间戳
     */
    public AnalysisHtml(long timer){
    }

    /**
     * 插件入口
     * @throws Throwable 异常
     */
    protected void onCreate() throws Throwable {
        HtmlRequest request = this.getClass().getAnnotation(HtmlRequest.class);
        if (request==null){
            request = this.htmlRequest;
        }
        if (request==null){
            Log.e("类"+this.getClass().getName()+"不是一个FoxJs插件,无法进行初始化!");
            return;
        }
        if (!request.isBegin())return;
        switch (request.type()){
            case -1:
                doLoadBodyStringHtml();
                break;
            case 0:
                doLoadHttpHtml(request);
                break;
            case 1:
                doLoadLocationHtml(request);
                break;
            case 2:
                doLoadStringHtml();
                break;
                default:
                    break;
        }
    }

    /**
     * 加载网页Html
     * @param request HtmlRequest注解信息对象
     * @throws LoadHtmlDefeatedException 加载网页失败异常
     */
    protected void doLoadHttpHtml(HtmlRequest request) throws LoadHtmlDefeatedException {
        String url = request.value();
        if (url.length()>5&&!url.substring(0,4).equals("http")){
            url = "http://"+url;
        }
        if (!url.contains(".")){
            Log.e("不是有效的网络连接地址");
            return;
        }
        url = verificationPlaceholder(url);
        Document document = null;
        try {
            document = Jsoup
                    .connect(url)
                    .postDataCharset("UTF-8")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        verificationDocument(url, document);
    }

    /**
     * 加载本地网页
     * @param request HtmlRequest注解信息对象
     * @throws LoadHtmlDefeatedException 加载网页失败异常
     */
    protected void doLoadLocationHtml(HtmlRequest request) throws LoadHtmlDefeatedException {
        String url = request.value();
        if (!url.contains("/")&&!url.contains("\\")){
            Log.e("不是有效的资源地址");
            return;
        }
        url = verificationPlaceholder(url);
        Document document = null;
        File input = new File(url);
        try {
            document = Jsoup.parse(input, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        verificationDocument(url, document);
    }

    /**
     * 加载html文本
     * @throws LoadHtmlDefeatedException 加载网页失败异常
     */
    protected void doLoadStringHtml() throws LoadHtmlDefeatedException {
        String htmlStr = getTextFromHtml();
        if (htmlStr==null){
            Log.e("获取html文本资源失败,请检查确认是否重写了getTextFromHtml()方法,并正确的返回了html文本内容");
            return;
        }
        Document document = Jsoup.parse(htmlStr);
        verificationDocument("from html text",document);
    }

    /**
     * 加载html文本碎片
     * @throws LoadHtmlDefeatedException 加载网页失败异常
     */
    protected void doLoadBodyStringHtml() throws LoadHtmlDefeatedException {
        String htmlStr = getTextFromHtml();
        if (htmlStr==null){
            Log.e("获取html文本资源失败,请检查确认是否重写了getTextFromHtml()方法,并正确的返回了html文本内容");
            return;
        }
        Document document = Jsoup.parseBodyFragment(htmlStr);
        verificationDocument("from html text body",document);
    }

    /**
     * 校验Document是否有效
     * @param url 地址
     * @param document Document对象
     * @throws LoadHtmlDefeatedException 加载网页失败异常
     */
    private void verificationDocument(String url, Document document) throws LoadHtmlDefeatedException {
        if (document!=null){
            Log.d("加载完成 >>>");
            obj = analysis(document);
        }else{
            Log.e("加载网页失败,"+"地址为:"+url);
            throw new LoadHtmlDefeatedException("地址为:"+url);
        }
    }

    /**
     * 检测是否存在占位值
     * @param baseUrl 原url
     * @return 替换过后的请求地址
     */
    private String verificationPlaceholder(String baseUrl){
        String url = baseUrl;
        if (values()!=null&&values().length>0){
            for (String value:values()) {
                url = url.replaceFirst(placeholder(), value);
            }
        }
        return url;
    }

    /**
     * 获取结果
     * @return T
     */
    public T getResult() {
        return obj;
    }

    /**
     * 获取Tag
     * @return 默认为-1
     */
    public int getTag() {
        return tag;
    }

    /**
     * 获取一个text
     * @return String
     */
    public String getTextFromHtml() {
        return null;
    }

    /**
     * values
     * @return String[]
     */
    public String[] values() {
        return sirs ==null?new String[0]: sirs;
    }

    /**
     * 占位符
     * @return 默认@value
     */
    public String placeholder(){
        return "@value";
    }

}
