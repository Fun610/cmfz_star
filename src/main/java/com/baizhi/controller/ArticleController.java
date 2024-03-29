package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import javafx.beans.binding.ObjectBinding;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @RequestMapping("findAll")
    public Map<String, Object> findAll(Integer page,Integer rows){
        return articleService.findAll(page,rows);
    }
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile articleImg, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        File file = new File(request.getServletContext().getRealPath("image/article"), articleImg.getOriginalFilename());
        try {
            articleImg.transferTo(file);
            map.put("error",0);
            map.put("url","http://localhost:8989/star/image/article/"+articleImg.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",1);
        }
        return map;
    }
    @RequestMapping("browse")
    public Map<String,Object> browse(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        File file = new File(request.getServletContext().getRealPath("image/article"));
        File[] files = file.listFiles();
        List<Object> list = new ArrayList<>();
        for (File img : files) {
            Map<String, Object> imgObject = new HashMap<>();
            imgObject.put("is_dir",false);
            imgObject.put("has_file",false);
            imgObject.put("filesize",img.length());
            imgObject.put("is_photo",true);
            imgObject.put("filetype", FilenameUtils.getExtension(img.getName()));
            imgObject.put("filename",img.getName());
            imgObject.put("datetime","2018-06-06 00:36:39");
            list.add(imgObject);
        }
        map.put("file_list",list);
        map.put("total_count",list.size());
        map.put("current_url","http://localhost:8989/star/image/article/");
        return map;
    }
    @RequestMapping("save")
    public void save(Article article){
        articleService.save(article);
    }
    @RequestMapping("update")

    public void update(Article article){
        articleService.update(article);
    }
    @RequestMapping("delete")
    public void delete(String id){
        articleService.delete(id);
    }

}
