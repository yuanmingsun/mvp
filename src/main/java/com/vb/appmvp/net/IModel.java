package com.vb.appmvp.net;


/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-08-15
 */

public interface IModel {


    boolean isNull();       //空数据

    boolean isAuthError();  //登录验证错误

    boolean isBusinessError();   //业务错误

    boolean isInsideError(); //内部错误

    String getErrorMsg();   //后台返回的错误信息
}
