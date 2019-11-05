package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(String oper,Banner banner, HttpServletRequest request) throws IOException {
       Map<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)){
                String id = bannerService.save(banner);
                map.put("id",id);
            }
            if ("edit".equals(oper)){
                bannerService.update(banner);
            }
            if ("del".equals(oper)){
                bannerService.delect(banner.getId(),request);
            }
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @RequestMapping("findAll")
    @ResponseBody
    public Map<String,Object> findAll(Integer page, Integer rows){
        List<Banner> banners = bannerService.findAll(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("rows",banners);
        map.put("page",page);
        Long totalCount = bannerService.findTotalCount();
        Long totalPage = totalCount%rows==0?totalCount/rows:totalCount/rows+1;
        map.put("total",totalPage);
        map.put("records",totalCount);
        return map;
    }
    @ResponseBody
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile cover,HttpServletRequest request,String id){
        Map<String, Object> map = new HashMap<>();
        try {
            cover.transferTo(new File(request.getServletContext().getRealPath("/image/banner"),cover.getOriginalFilename()));
            Banner banner = new Banner();
            banner.setId(id);
            banner.setCover(cover.getOriginalFilename());
            bannerService.update(banner);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;

    }
}
