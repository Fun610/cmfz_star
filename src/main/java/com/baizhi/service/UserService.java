package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.UserTrend;
import org.apache.catalina.LifecycleState;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,Object> findByStarId(Integer page,Integer rows,String starId);
    Map<String,Object> findAll(Integer page,Integer rows);
    List<User> export();
    List<UserTrend> findUserTrendAll(String sex);
    Map<String,Object> login(User user,String code);
    Map<String,Object> save(User user,String code);
}
