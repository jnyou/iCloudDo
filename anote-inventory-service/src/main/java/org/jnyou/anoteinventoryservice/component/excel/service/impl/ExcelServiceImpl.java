package org.jnyou.anoteinventoryservice.component.excel.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jnyou.anoteinventoryservice.component.excel.EasyExcelUtil;
import org.jnyou.anoteinventoryservice.component.excel.entity.TopicModel;
import org.jnyou.anoteinventoryservice.component.excel.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 分类名称
 *
 * @ClassName ExcelServiceImpl
 * @Description:
 * @Author: jnyou
 **/
@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {
    @Override
    public boolean excelImport(HttpServletRequest request, MultipartFile[] files) {
        if(files != null && files.length > 0){
            MultipartFile file = files[0];
            List<Object> list = null;
            try {
                list = EasyExcelUtil.readExcel(file, new TopicModel(),1,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(list != null && list.size() > 0){
                for(Object o : list){
                    TopicModel xfxx = (TopicModel) o;
                    // TODO： data handle start...
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void downloadExcel(HttpServletResponse response, HttpServletRequest request) {
        //方法一：直接下载路径下的文件模板
        try {
            //获取要下载的模板名称
            String fileName = "ApplicationImportTemplate.xlsx";
            //设置要下载的文件的名称
            response.setHeader("Content-disposition", "attachment;fileName=" + fileName);
            //通知客服文件的MIME类型
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            //获取文件的路径
            String filePath = this.getClass().getClassLoader().getResource("static/excel/" + fileName).getPath();
            log.info("文件路径，path：【{}】",filePath);
            FileInputStream input = new FileInputStream(filePath);
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[2048];
            int len;
            while ((len = input.read(b)) != -1) {
                out.write(b, 0, len);
            }
            //修正 Excel在“xxx.xlsx”中发现不可读取的内容。是否恢复此工作薄的内容？如果信任此工作簿的来源，请点击"是"
            response.setHeader("Content-Length", String.valueOf(input.getChannel().size()));
            input.close();
//            return Response.ok("应用导入模板下载完成");
        } catch (Exception ex) {
            log.error("getApplicationTemplate :", ex);
            //return Response.ok("应用导入模板下载失败！");
        }


        //方法二：可以采用POI导出excel，但是比较麻烦
		/*try {
			Workbook workbook = new HSSFWorkbook();
			request.setCharacterEncoding("UTF-8");
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/x-download");


	        String filedisplay = "外部案件导入模板.xls";

	        filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
	        response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);

			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			Sheet sheet = workbook.createSheet("外部案件导入模板");
			// 第三步，在sheet中添加表头第0行
			Row row = sheet.createRow(0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			CellStyle style = workbook.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER); // 创建一个居中格式

			Cell cell = row.createCell(0);
	        cell.setCellValue("商品名称");
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(0, (25 * 256));  //设置列宽，50个字符宽

	        cell = row.createCell(1);
	        cell.setCellValue("商品编码");
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(1, (20 * 256));  //设置列宽，50个字符宽

	        cell = row.createCell(2);
	        cell.setCellValue("商品价格");
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(2, (15 * 256));  //设置列宽，50个字符宽

	        cell = row.createCell(3);
	        cell.setCellValue("商品规格");
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(3, (15 * 256));  //设置列宽，50个字符宽

	        // 第五步，写入实体数据 实际应用中这些数据从数据库得到
			row = sheet.createRow(1);
			row.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(1);
			row.createCell(1, Cell.CELL_TYPE_STRING).setCellValue(2);
			row.createCell(2, Cell.CELL_TYPE_STRING).setCellValue(3);   //商品价格
			row.createCell(3, Cell.CELL_TYPE_STRING).setCellValue(4);  //规格

			// 第六步，将文件存到指定位置
	        try
	        {
	        	OutputStream out = response.getOutputStream();
	        	workbook.write(out);
	            out.close();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}*/
    }
}