package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    Map<String,Object> findAll(Integer page,Integer rows);
    String save(Album album);
    void update(Album album);
}
