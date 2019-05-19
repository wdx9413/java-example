package cn.dox;

import cn.dox.advice.Advice;
import cn.dox.advice.BeforeAdvice;
import cn.dox.aspect.MethodInvocation;
import cn.dox.service.HelloService;
import cn.dox.service.HelloServiceImpl;

public class App {
    public static void main( String[] args ) {
        MethodInvocation methodInvocation = () -> System.out.println("before hello");
        HelloService helloService = new HelloServiceImpl();
        Advice before = new BeforeAdvice(helloService, methodInvocation);
        HelloService helloServiceProxy = (HelloService) AOPFactory.getProxy(helloService, before);
        helloServiceProxy.hello();
    }
}
