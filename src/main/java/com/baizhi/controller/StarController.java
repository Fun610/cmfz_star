package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("star")
public class StarController {
    @Autowired
    private StarService starService;
    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer page,Integer rows){
        return starService.findAll(page,rows);
    }
    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, Star star, HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)){
                String id = starService.save(star);
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
    public Map<String,Object> upload(MultipartFile photo, HttpServletRequest request, String id){
        Map<String, Object> map = new HashMap<>();
        try {
            photo.transferTo(new File(request.getServletContext().getRealPath("/image/star"),photo.getOriginalFilename()));
            Star star = new Star();
            star.setId(id);
            star.setPhoto(photo.getOriginalFilename());
            starService.update(star);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;

    }
    @RequestMapping("find")
    public void find(HttpServletResponse response) throws IOException {
        List<Star> stars = starService.find();
        StringBuilder sb = new StringBuilder();
        sb.append("<select>");
        stars.forEach(dept -> {
            sb.append("<option value=").append(dept.getId()).append(">").append(dept.getRealname()).append("</option>");
        });
        sb.append("</select>");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(sb.toString());
    }
}
