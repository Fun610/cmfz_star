package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class TestPoi {
    public static void main(String[] args) throws Exception {
        //准备excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建合并区域，并指定合并区域
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 1, 0, 5);
        //设置日期格式
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        //获取单元格样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //绑定日期格式
        cellStyle.setDataFormat(format);
        //设置字体居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //获取设置字体对象
        HSSFFont font = workbook.createFont();
        //设置字体加粗
        font.setBold(true);
        //设置字体颜色为红色
        font.setColor(Font.COLOR_RED);
        //绑定字体样式
        cellStyle.setFont(font);
        //创建工作表
        HSSFSheet sheet = workbook.createSheet("测试");
        //合并单元格
        sheet.addMergedRegion(cellRangeAddress);
        //当前工作表设置列宽
        sheet.setColumnWidth(0,25*256);
        //创建第一行
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell = row.createCell(0);
        //写值
        cell.setCellValue(new Date());
        //设置单元格格式
        cell.setCellStyle(cellStyle);
        //将当前文件通过io写入本地磁盘
        workbook.write(new FileOutputStream(new File("E:/后期项目/text.xls")));
    }
}
