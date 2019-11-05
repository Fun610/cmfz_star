package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.entity.UserTrend;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("findByStarId")
    public Map<String,Object> findByStarId(Integer page,Integer rows,String starId){
        return userService.findByStarId(page,rows,starId);
    }
    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer page,Integer rows){
        return userService.findAll(page,rows);
    }
    @RequestMapping("export")
    public void export(HttpServletResponse resp){
        List<User> users = userService.export();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有用户", "用户"), User.class, users);
        String fileName = "用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";
        //处理中文下载名乱码
        try {
            fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
            //设置 response
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("content-disposition","attachment;filename="+fileName);
            workbook.write(resp.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @RequestMapping("findUserTrend")
    public Map<String,Object> findUserTrend(){
        Map<String, Object> map = new HashMap<>();
        List<UserTrend> list = userService.findUserTrendAll("男");
        Integer[] nan ={0,0,0,0,0,0};
        for (UserTrend userTrend : list) {
            String month = userTrend.getMonth();
            for (int i = 1; i <= 6; i++) {
                if (String.valueOf(i).equals(month)){
                    nan[i-1]=userTrend.getCount();
                }
            }
        }
        map.put("nan",nan);
        List<UserTrend> list1 = userService.findUserTrendAll("女");
        Integer[] nv ={0,0,0,0,0,0};
        for (UserTrend userTrend : list1) {
            String month = userTrend.getMonth();
            for (int i = 1; i <= 6; i++) {
                if (String.valueOf(i).equals(month)){
                    nv[i-1]=userTrend.getCount();
                }
            }
        }
        map.put("nv",nv);
        return map;
    }
}
