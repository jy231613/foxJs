package cn.jhf.foxjs.x;

/**
 * 作者:ZFox
 * 创建:2019/3/12 0012
 * 所属包:cn.jhf.foxjs.x
 * 描述:打印输出log
 **/
public class Log {

    public static void e(String msg){
        System.out.println("\033[31;4m" + msg + "\033[0m");
    }

    public static void d(String msg){
        System.out.println("\033[36;4m" + msg + "\033[0m");
    }

    public static void a(String msg){
        System.out.println("\033[33;4m" + msg + "\033[0m");
    }

}
