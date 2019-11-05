package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestImport2 {
    public static void main(String[] args) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("E:/后期项目/user.xls")));
        HSSFSheet sheet = workbook.getSheet("学生信息");
        List<User> list = new ArrayList<>();
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            User user = new User();
            HSSFRow row = sheet.getRow(i);
            Class<User> userClass = User.class;
            Field[] fields = userClass.getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];
                String name = field.getName();
                String methodName = "set"+name.substring(0,1).toUpperCase()+name.substring(1);
                if (name.equals("bir")){
                    Method method = userClass.getDeclaredMethod(methodName, Date.class);
                    HSSFCell cell = row.getCell(j);
                    Date value = cell.getDateCellValue();
                    method.invoke(user,value);
                }else {
                    Method method = userClass.getDeclaredMethod(methodName,String.class);
                    HSSFCell cell =row.getCell(j);
                    String value = cell.getStringCellValue();

                    method.invoke(user,value);
                }
            }
           list.add(user);
        }
        for (User user : list) {
            System.out.println(user);
        }

    }
}
