package com.xiaomi.be;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerMain {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("##Init spring context...");
        ClassPathXmlApplicationContext containerContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        containerContext.start();
        System.out.println("##Server start...");

        while (true) {
            Thread.sleep(30* 1000L);
        }
    }
}
