package com.parent.xtgl.utils.redis;

import com.parent.xtgl.service.RedisServiceI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RedisScheduledTask {
    private static final Log logger = LogFactory.getLog(RedisScheduledTask.class);
    @Autowired
    private RedisServiceI redisServiceI;

    public RedisServiceI getRedisServiceI() {
        return redisServiceI;
    }

    public void setRedisServiceI(RedisServiceI redisServiceI) {
        this.redisServiceI = redisServiceI;
    }

    @Scheduled(cron = "0 53 * * * ?")
    public boolean  redisTransfer(){
        try {
            try {
                redisServiceI.convertAllToRedis();
            } catch (Exception e) {
                logger.error("定时任务：redis一次同步失败  "+e.getMessage());
                redisServiceI.convertAll2Redis();
            }
        }catch (Exception e){
            logger.error("定时任务：redis二次同步失败  "+e.getMessage());
            return false;
        }
        return  true;
    }
}
