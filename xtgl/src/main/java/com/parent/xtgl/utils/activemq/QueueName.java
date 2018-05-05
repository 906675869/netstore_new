package com.parent.xtgl.utils.activemq;

/**
 *用于存放消息队列的名字
 */
public interface QueueName {
    /*邮件*/
   static final String Mail = "mail.queue";
   /*电话序列名*/
   static final String Phone ="phone.queue";
   /*通知序列名*/
   static final String Notice = "notice.queue";
}
