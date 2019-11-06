package com.baizhi.service;

import com.baizhi.annotation.ClearRedisCache;
import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    @ClearRedisCache
    public String save(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        banner.setCreateDate(new Date());
        int i = bannerDao.insertSelective(banner);
        if (i==0){
            throw new RuntimeException("添加失败");
        }
        return banner.getId();

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @RedisCache
    public List<Banner> findAll(Integer page, Integer rows) {
        Banner banner = new Banner();
        int start = (page-1)*rows;
        RowBounds rowBounds = new RowBounds(start, rows);
        List<Banner> banners = bannerDao.selectByRowBounds(banner, rowBounds);
        return banners;

    }

    @Override
    @ClearRedisCache
    public void update(Banner banner) {
        if ("".equals(banner.getCover())){
            banner.setCover(null);
        }
        try {
            bannerDao.updateByPrimaryKeySelective(banner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }


    }

    @Override
    public Long findTotalCount() {
        Banner banner = new Banner();
        int count = bannerDao.selectCount(banner);
        Long value = Long.valueOf(count);
        return value;
    }

    @Override
    @ClearRedisCache
    public void delect(String id, HttpServletRequest request) {

        Banner banner = bannerDao.selectByPrimaryKey(id);
        int i = bannerDao.delete(banner);
        if (i==0){
            throw new RuntimeException("删除失败");
        }else{
            File file = new File(request.getServletContext().getRealPath("/image/banner"), banner.getCover());
            boolean b = file.delete();
            if (b==false){
                throw new RuntimeException("删除轮播图文件失败");
            }
        }

    }
}
