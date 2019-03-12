package cn.jhf.foxjs.example;

import cn.jhf.foxjs.anm.Async;
import cn.jhf.foxjs.anm.HtmlRequest;
import cn.jhf.foxjs.base.AnalysisHtml;
import cn.jhf.foxjs.base.AsynAnalysisHtml;
import cn.jhf.foxjs.x.Log;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 作者:ZFox
 * 创建:2019/3/12 0012
 * 所属包:cn.jhf.foxjs.base
 * 描述:例子,来自网络请求,异步
 **/
@Async
@HtmlRequest(value = "https://wannianrili.51240.com/")
public class ExampleAsyncFromHttp extends AsynAnalysisHtml<String> {

    @Override
    public String analysis(Document document) {
        Elements elms = document.getElementsByClass("wnrl_k_you_id_wnrl_nongli_ganzhi");
        if (elms!=null&&elms.size()>0){
            return elms.get(0).text();
        }
        return "xxxxx";
    }

    @Override
    public void result(String s) {
        Log.a(s);
    }
}
