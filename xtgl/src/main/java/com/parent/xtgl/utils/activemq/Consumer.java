package com.parent.xtgl.utils.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @JmsListener(destination = "sample.queue")
    public void receiveQueue(String text) {
        //接收到消息后的操作
        System.out.println(text+" Consumer receive");
    }

    /**
     * 邮件系统的接受者
     * @param MailInfo  用户的邮件消息 包含邮件和用户名
     */
    @JmsListener(destination = QueueName.Mail)
    public void receiveMailQueue(String MailInfo){
        System.out.println("邮件"+MailInfo);
    }
    /**
     * 电话系统的接受者
     * @param Phone  用户的电话消息 包含电话和用户名
     */
    @JmsListener(destination = QueueName.Phone)
    public void receivePhoneQueue(String Phone){
        System.out.println("电话"+Phone);
    }
    /**
    通知系统接受者
    * @param Notice 通知用户的消息 包含用户名和通知内容
    * */
    @JmsListener(destination = QueueName.Notice)
    public void receiveNoticeQueue(String Notice){
        System.out.println("通知"+Notice);
    }



}