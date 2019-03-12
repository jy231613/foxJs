package cn.jhf.foxjs.base;

import org.jsoup.nodes.Document;

/**
 * 作者:ZFox
 * 创建:2019/3/11 0011
 * 所属包:cn.jhf.foxjs.base
 * 描述:html解析接口
 **/
public interface HtmlAnalysis<T> {

    T analysis(Document document);

    String getTextFromHtml();

    String[] values();

    String placeholder();

    T getResult();
}
