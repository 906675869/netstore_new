package com.parent.xtgl;


import org.apache.activemq.command.ActiveMQQueue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.Queue;


@SpringBootApplication
@ComponentScan(value="com.parent.*")
@MapperScan("com.parent.xtgl.mapper")
/*开启JMS*/
@EnableJms
/*事务开启*/
@EnableTransactionManagement
/*定时任务开启*/
@EnableScheduling
public class XtglApplication {
    /*注入消息队列 默认名字为sample.queue*/
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("sample.queue");
    }

    public static void main(String[] args) {
        SpringApplication.run(XtglApplication.class, args);
    }
}
