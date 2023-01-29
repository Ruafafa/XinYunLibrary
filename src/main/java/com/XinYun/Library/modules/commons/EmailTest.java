package com.XinYun.Library.modules.commons;

import cn.hutool.extra.mail.MailUtil;

public class EmailTest {
    public static void main(String[] args) {
        MailUtil.send("931211408@qq.com","你好","这是一条测试",false);
        System.out.println("发送成功");
    }
}
