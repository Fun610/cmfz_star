package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.apache.logging.log4j.message.StringFormattedMessage;

import java.util.Map;

public interface ChapterService {
    Map<String,Object> findByAlbumId(Integer page,Integer rows,String albumId);
    String save(Chapter chapter);
    void update(Chapter chapter);
}
