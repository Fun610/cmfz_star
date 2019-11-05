package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestExcle {
    public static void main(String[] args) throws Exception {
        List<User> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            User user = new User();
            user.setId(i+"");
            user.setName("张三"+i);
            user.setPassword(i+"");
            user.setBir(new Date());
            list.add(user);
        }



        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setDataFormat(format);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFSheet sheet = workbook.createSheet("学生信息");
        sheet.setColumnWidth(3,25*256);
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            HSSFRow row = sheet.createRow(i);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(user.getId());
            cell.setCellStyle(cellStyle);
            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(user.getName());
            cell1.setCellStyle(cellStyle);
            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(user.getPassword());
            cell2.setCellStyle(cellStyle);
            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(user.getBir());
            cell3.setCellStyle(cellStyle);
        }
        workbook.write(new FileOutputStream(new File("E:/后期项目/user.xls")));
    }
}
