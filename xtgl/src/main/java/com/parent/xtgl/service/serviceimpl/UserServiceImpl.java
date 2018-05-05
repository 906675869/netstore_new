package com.parent.xtgl.service.serviceimpl;

import com.parent.xtgl.entity.*;
import com.parent.xtgl.mapper.FunctionMapper;
import com.parent.xtgl.mapper.LoginMapper;
import com.parent.xtgl.mapper.RoleMapper;
import com.parent.xtgl.utils.activemq.Producer;
import com.parent.xtgl.utils.activemq.QueueName;
import com.parent.xtgl.utils.redis.RedisUtils;
import com.parent.xtgl.mapper.UserMapper;
import com.parent.xtgl.service.UserServiceI;
import net.sf.json.JSONObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements UserServiceI {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private FunctionMapper functionMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private Producer producer;

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    public RedisUtils getRedisUtils() {
        return redisUtils;
    }

    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public FunctionMapper getFunctionMapper() {
        return functionMapper;
    }

    public void setFunctionMapper(FunctionMapper functionMapper) {
        this.functionMapper = functionMapper;
    }

    public RoleMapper getRoleMapper() {
        return roleMapper;
    }

    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public LoginMapper getLoginMapper() {
        return loginMapper;
    }

    public void setLoginMapper(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public List<User> getAll() {
        boolean b = redisUtils.exists("User");
        List<User> list;
       if(!b){
            list = userMapper.getAll();
            redisUtils.set("User",list);
        }
        return  (List<User>)redisUtils.get("User");
    }

    @Override
    public User getOne(Integer id) {
        String key = "User:"+id;
        boolean b = redisUtils.exists(key);
        User user;
        if(!b){
            user = userMapper.getOne(id);
            redisUtils.set(key,user);
        }
        return (User)redisUtils.get("key");
    }

    @Override
    public void insert(User user) {
        String key = "User:"+user.getId();
        redisUtils.set(key,user);
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        String key = "User:"+user.getId();
        redisUtils.set(key,user);
        userMapper.update(user);
    }

    @Override
    public void delete(Integer id) {
        String key = "User:"+id;
        redisUtils.remove(key);
        userMapper.delete(id);
    }

    @Override
    public User getCurrentUser() {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求
        HttpServletRequest request = attributes.getRequest();
        Object object  = request.getSession().getAttribute(USERSESSION);
        if(!StringUtils.isEmpty(object)){
            User user = (User)object;
            return user;
        }
        return null;
    }

    @Override
    public List<Role> getUserRoles(User user) {
        return roleMapper.getRolesByUser(user);
    }

    @Override
    public List<Function> getUserFunctions(User user) {
        return functionMapper.getFunctionsByUser(user);
    }

    @Override
    public List<Function> getCurrentUserFunctions() {
        return getUserFunctions(getCurrentUser());
    }

    @Override
    public User getUserByLoginName(Login login) {
        return userMapper.selectUserByLoginName(login);
    }

    @Override
    public boolean clearUserSession() {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求
        HttpServletRequest request = attributes.getRequest();
        //移除用户信息
        request.getSession().removeAttribute(USERSESSION);
        return true;
    }

    @Transactional
    @Override
    public String register(Register register) {
        // 【1】生成对应用户【2】生成对应登录用户（逻辑相关）
        // 【3】生成邮件系统消息【4】生成电话系统消息 【5】生成通知系统消息（逻辑无关调用消息队列）
        //【1】生成对应用户
            User user = new User(register.getUserName(), register.getAge());
            userMapper.insert(user);
            //【2】生成对应登录用户
            Login login = new Login(user.getId(), register.getUserName(), register.getPassword());
            loginMapper.insert(login);
            //【3】生成邮件系统消息
            String registerMsg = JSONObject.fromObject(register).toString();
            producer.send(registerMsg, QueueName.Mail);
            //【4】生成电话系统消息
            producer.send(registerMsg, QueueName.Phone);
            //【5】生成通知系统消息
            producer.send(registerMsg, QueueName.Notice);
            return "success";
    }

}
