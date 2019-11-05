package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.dao.UserTrendDao;
import com.baizhi.entity.User;
import com.baizhi.entity.UserTrend;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class UserServiceImpl implements  UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserTrendDao userTrendDao;
    @Override
    public Map<String, Object> findByStarId(Integer page, Integer rows, String starId) {
        User user = new User();
        user.setStarId(starId);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users = userDao.selectByRowBounds(user, rowBounds);
        int i = userDao.selectCount(user);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",users);
        map.put("total",i%rows==0?i/rows:i/rows+1);
        map.put("records",i);
        return map;
    }

    @Override
    public Map<String, Object> findAll(Integer page, Integer rows) {
        User user = new User();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> list = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public List<User> export() {
        List<User> list = userDao.selectAll();
        return list;
    }

    @Override
    public List<UserTrend> findUserTrendAll(String sex) {
        List<UserTrend> list = userTrendDao.findAll(sex);
        return list;
    }

    @Override
    public Map<String, Object> login(User user, String code) {
        User one = userDao.selectOne(user);

        return null;
    }

    @Override
    public Map<String, Object> save(User user, String code) {

        return null;
    }
}
