package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.entity.Star;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements  AlbumService {


    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private StarDao starDao;
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Album album = new Album();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Album> list = albumDao.selectByRowBounds(album, rowBounds);
        Chapter chapter = new Chapter();
        ArrayList<Album> albums = new ArrayList<>();
        for (Album album1 : list) {
            chapter.setAlbumId(album1.getId());
            Star star = starDao.selectByPrimaryKey(album1.getAuthor());
            album1.setStar(star);
            int i = chapterDao.selectCount(chapter);
            if (i!=0){
                album1.setCount(i);
            }
            albums.add(album1);
        }
        int count = albumDao.selectCount(album);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",albums);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public String save(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCount(0);
        album.setScore(8.1);
        int i = albumDao.insertSelective(album);
        if (i==0){
            throw  new RuntimeException("添加失败");
        }
        return album.getId();
    }
    @Override
    public void update(Album album) {
        if ("".equals(album.getCover())){
            album.setCover(null);
        }
        try {
            albumDao.updateByPrimaryKeySelective(album);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("修改失败");
        }

    }
}
