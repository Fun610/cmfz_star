package com.baizhi.service;

import com.baizhi.entity.Star;

import java.util.List;
import java.util.Map;

public interface StarService {
    Map<String,Object> findAll(Integer page,Integer rows);
    String save(Star star);
    void update(Star star);
    List<Star> find();
}
