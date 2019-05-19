package cn.dox;

import cn.dox.advice.Advice;

import java.lang.reflect.Proxy;

public class AOPFactory {

    public static Object getProxy(Object bean, Advice advice) {
        return Proxy.newProxyInstance(AOPFactory.class.getClassLoader(), bean.getClass().getInterfaces(), advice);

    }
}
