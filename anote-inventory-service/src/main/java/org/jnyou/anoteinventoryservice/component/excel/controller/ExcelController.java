package org.jnyou.anoteinventoryservice.component.excel.controller;

import com.alibaba.excel.EasyExcel;
import org.jnyou.anoteinventoryservice.common.R;
import org.jnyou.anoteinventoryservice.component.excel.entity.PaymentChangeRecord;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 分类名称
 *
 * @ClassName ExcelController
 * @Description:
 * @Author: jnyou
 **/
@RestController
@RequestMapping("/excel/operate")
public class ExcelController {

    /**
     * 导入excel
     *
     * @param request
     * @param files
     * @return
     * @Author jnyou
     */
    @RequestMapping(value = "excelImport", method = {RequestMethod.GET, RequestMethod.POST})
    public R excelImport(HttpServletRequest request, @RequestParam("uploadFile") MultipartFile[] files) {
        // TODO 调用service方法  xxxService.excelImport(request, files)
        return R.ok();
    }

    /**
     * 描述：下载外部案件导入模板
     *
     * @param response
     * @param request
     * @author jnyou
     */
    @RequestMapping(value = "/downloadExcel", method = {RequestMethod.GET, RequestMethod.POST})
    public void downloadExcel(HttpServletResponse response, HttpServletRequest request) {
        // TODO 调用service方法  xxxService.downloadExcel(response,request);
    }

    /**
     * 导出excel
     *
     * @throws IOException
     */
    @RequestMapping("/exportPaymentAdvance")
    public void getCollectionExportExcel(HttpServletResponse response) throws IOException {

        String fileName = "预收款统计" + ".xlsx";
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", ""
                .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
        List<PaymentChangeRecord> loadData = null; // TODO 获取导出的数据集
        EasyExcel.write(response.getOutputStream(),PaymentChangeRecord.class).sheet("预收款统计导出").doWrite(loadData);
    }

}