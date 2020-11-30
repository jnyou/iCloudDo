package org.jnyou.anoteinventoryservice.component.excel.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 分类名称
 *
 * @ClassName ExcelService
 * @Description:
 * @Author: jnyou
 **/
public interface ExcelService {

    /**
     * alibaba easyExcel excelImport
     * @param request
     * @param files
     * @return
     * @Author jnyou
     */
    boolean excelImport(HttpServletRequest request, MultipartFile[] files);

    /**
     * 下载外部案件导入模板
     * @param response
     * @param request
     * @return
     * @Author jnyou
     */
    void downloadExcel(HttpServletResponse response, HttpServletRequest request);

}