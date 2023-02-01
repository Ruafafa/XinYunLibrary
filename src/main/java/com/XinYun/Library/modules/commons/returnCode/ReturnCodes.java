package com.XinYun.Library.modules.commons.returnCode;

/**
 * 返回信息枚举类
 */
public enum ReturnCodes {
    /*《手册》还有一条是规定错误码应该如何定义：
      错误码为字符串类型，共 5 位，分成两个部分：错误产生来源+四位数字编号。
      说明：错误产生来源分为 A/B/C
      A 表示错误来源于用户，比如参数错误，用户安装版本过低，用户支付超时等问题；
      B 表示错误来源于当前系统，往往是业务逻辑出错，或程序健壮性差等问题；
      C 表示错误来源于第三方服务，比如 CDN 服务出错，消息投递超时等问题；
      四位数字编号从 0001 到 9999，大类之间的步长间距预留 100。*/

    //这个只是模板，大家有更好的可以直接改！！！
    //xx成功
    SUCCESS( "B2000", "成功"),
    //xx请求
    FAILURE("A4040","失败");

    //状态码
    String code;
    //业务信息
    String message;

    ReturnCodes(String code,String message) {
        this.code = code;
        this.message = message;
    }
}
