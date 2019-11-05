package com.baizhi.service;

import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import javafx.beans.binding.ObjectBinding;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class StarServiceImpl implements StarService {
    @Autowired
    private StarDao starDao;
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Star star = new Star();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Star> stars = starDao.selectByRowBounds(star, rowBounds);
        int i = starDao.selectCount(star);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",stars);
        map.put("total",i%rows==0?i/rows:i/rows+1);
        map.put("records",i);
        return map;
    }

    @Override
    public String save(Star star) {
        star.setId(UUID.randomUUID().toString());
        int i = starDao.insertSelective(star);
        if (i==0){
            throw new RuntimeException("添加失败");
        }
        return star.getId();
    }

    @Override
    public void update(Star star) {
        if ("".equals(star.getPhoto())){
            star.setPhoto(null);
        }
        try {
            starDao.updateByPrimaryKeySelective(star);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public List<Star> find() {
        List<Star> stars = starDao.selectAll();
        return stars;
    }
}
