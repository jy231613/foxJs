package cn.jhf.foxjs.exception;

/**
 * 作者:ZFox
 * 创建:2019/3/12 0012
 * 所属包:cn.jhf.foxjs.exception
 * 描述:加载网页失败异常
 **/
public class LoadHtmlDefeatedException extends Exception {

    public LoadHtmlDefeatedException(String msg){
        super(msg);
    }

}
