package com.liee.core.utils;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class ExcelUtil {

	
	/**
	 * 根据Map数据导出
	 * @param data
	 * @param titles
	 * @param columnNames
	 * @return
	 */
	public static HSSFWorkbook getExcelFromMapData(List<Map<String,Object>> data ,String[] titles,String[] columnNames){
		HSSFWorkbook wb = new HSSFWorkbook();
		
		HSSFSheet sheet = wb.createSheet("Campaign");
		// 表头
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		for (int i = 0; i < titles.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(style);
			sheet.setColumnWidth(i, titles[i].length()* 2 * 256); 
			//sheet.autoSizeColumn(i,true);
		}
		
		// 数据
		int rowIndex = 1;
		for (Map<String,Object>  rdata : data) {
			row = sheet.createRow(rowIndex);
			for (int i = 0; i < columnNames.length; i++) {
				Object value = rdata.get(columnNames[i]);
				if(value instanceof String){
					row.createCell(i).setCellValue((String)rdata.get(columnNames[i]));
				}else if(value instanceof Double){
					row.createCell(i).setCellValue((Double)rdata.get(columnNames[i]));
				}else if(value instanceof Integer){
					row.createCell(i).setCellValue((Integer)rdata.get(columnNames[i]));
				}else{
					// 默认用String 
					row.createCell(i).setCellValue((String)rdata.get(columnNames[i]));
				}
			}
			rowIndex++;
		}
		
		return wb;
	}
	
}
