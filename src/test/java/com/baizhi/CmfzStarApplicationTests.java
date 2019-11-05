package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Banner;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@SpringBootTest
class CmfzStarApplicationTests {
    @Autowired
    private BannerDao bannerDao;

    @Test
    void contextLoads() {
        /*Admin admin = new Admin();
        RowBounds rowBounds = new RowBounds(1,2);
        List<Admin> admins = adminDao.selectByRowBounds(admin, rowBounds);
        for (Admin admin1 : admins) {
            System.out.println(admin1);
        }*/

        /*Example example = new Example(Admin.class);
        example.createCriteria().andEqualTo("username","xiaobai")
                .andEqualTo("password","111111");
        List<Admin> admins = adminDao.selectByExample(example);
        System.out.println(admins);*/
        /*Admin admin = adminDao.selectByPrimaryKey("2");
        System.out.println(admin);*/
        /*List<Admin> admins = adminDao.select(admin);
        admins.forEach(s -> System.out.println(s));*/

        /*int i = adminDao.selectCount(admin);
        System.out.println(i);*/
        /*Admin admin = new Admin();
        admin.setId("1");

        admin.setNickname("叫我国王");
        int i = adminDao.updateByPrimaryKeySelective(admin);
        System.out.println(i);*/
       /* Banner banner = new Banner();
        banner.setId("3").setCover("dghgfds").setCreateDate(new Date()).setName("lin").setStatus("ok").setDescription("dsfdsfdsf");

        int insert = bannerDao.insert(banner);
        System.out.println(insert);*/
        Integer[] nan ={0,0,0,0,0,0};
        nan[1]=2;
        for (int i = 0; i < nan.length; i++) {
            System.out.println(nan[i]);
        }
    }
    @Test
    public void pub(){
        //第一个参数：REST Host
        //第二个参数：发布消息的App Key
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-74e8ef1ccfff4845a701da800691c786");
        //第一个参数：channel的名称
        //第二个参数：发布的内容
        goEasy.publish("test-channel", " Make love!");
    }
    @Test
    public void suiji(){
        System.out.println((int)((Math.random()*9+1)*100000));
    }

}
