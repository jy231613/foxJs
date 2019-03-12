package cn.jhf.foxjs.anm;

import cn.jhf.foxjs.base.HtmlType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者:ZFox
 * 创建:2019/3/11 0011
 * 所属包:cn.jhf.foxjs.anm
 * 描述:标识一个类是JsLoad插件
 **/
@Retention(RetentionPolicy.RUNTIME)//生命周期一直存在
@Target( { ElementType.TYPE,ElementType.FIELD,ElementType.LOCAL_VARIABLE})//应用于类
public @interface HtmlRequest {

    /**
     * 加载地址,地址中的变量可以使用@value代替,或者重写placeholder()类
     * @return 地址 例:http://www.baidu.com/@value
     */
    String value();

    /**
     * 加载类型
     * -1:body网页碎片
     * 0:网络连接地址
     * 1:本地网页
     * 3:文本
     * @return int
     */
    int type() default HtmlType.ON_HTTP;

    /**
     * 此插件是否启用
     * @return true启用
     */
    boolean isBegin() default true;
}
