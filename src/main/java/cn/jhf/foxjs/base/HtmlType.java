package cn.jhf.foxjs.base;

/**
 * 作者:ZFox
 * 创建:2019/3/12 0012
 * 所属包:cn.jhf.foxjs.base
 * 描述:网页类型
 **/
public class HtmlType {

    /**
     * 来自网页
     */
    public static final int ON_HTTP = 0;

    /**
     * 来自本地
     */
    public static final int ON_LOCATION = 1;

    /**
     * 来自字符串
     */
    public static final int ON_STRING = 2;

    /**
     * 来自字符串网页内部碎片
     */
    public static final int ON_STRING_BODY = -1;

}
