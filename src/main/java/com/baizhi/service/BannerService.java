package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface BannerService {
    String save(Banner banner);
    List<Banner> findAll(Integer page,Integer rows);
    void update(Banner banner);
    Long findTotalCount();
    void delect(String id, HttpServletRequest request);
}
