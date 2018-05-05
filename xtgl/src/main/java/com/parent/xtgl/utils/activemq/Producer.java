package com.parent.xtgl.utils.activemq;
import javax.jms.Destination;
import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.util.annotation.Nullable;

@Component
public class Producer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    //目标对象
    @Autowired
    private Queue queue;

    private Destination destination;
    @Autowired
    private ActiveMQQueue activeMQQueue;


    //文本消息
    public void send( String msg,@Nullable String des) {
        if(StringUtils.isEmpty(des)){
            this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
            return;
        }
        activeMQQueue.setPhysicalName(des);
        this.jmsMessagingTemplate.convertAndSend(activeMQQueue, msg);
    }
    //对象消息 需配置
    public void send( Object msg,@Nullable String des) {
        if(StringUtils.isEmpty(des)){
            this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
            return;
        }
        activeMQQueue.setPhysicalName(des);
        this.jmsMessagingTemplate.convertAndSend(activeMQQueue, msg);
    }

    //对象消息
    public void send(Object msg){
        this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
    }


}
