package com.baizhi.controller;

import com.baizhi.entity.Chapter;

import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("findByAlbumId")
    public Map<String,Object> findByAlbumId(Integer page, Integer rows,String albumId){
        return chapterService.findByAlbumId(page,rows,albumId);
    }
    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, Chapter chapter){
        Map<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)){
                String id = chapterService.save(chapter);
                map.put("id",id);
            }
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile name, String id, HttpServletRequest request,String albumId){
        Map<String, Object> map = new HashMap<>();
        try {
            name.transferTo(new File(request.getServletContext().getRealPath("/image/chapter"),name.getOriginalFilename()));
            Chapter chapter = new Chapter();
            chapter.setName(name.getOriginalFilename());
            long size = name.getSize();
            String sizes = size/1024/1024+"MB";
            BigDecimal decimal = new BigDecimal(name.getSize());
            BigDecimal bigDecimal = new BigDecimal(1024);
            BigDecimal scale = decimal.divide(bigDecimal).divide(bigDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
            Encoder encoder = new Encoder();
            long length = encoder.getInfo(new File(request.getServletContext().getRealPath("/image/chapter"), name.getOriginalFilename())).getDuration();
            chapter.setDuration(length/1000/60+"分"+length/1000%60+"秒");
            chapter.setId(id);
            chapter.setAlbumId(albumId);
            chapter.setSize(scale+"MB");
            chapterService.update(chapter);
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}
