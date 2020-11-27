package com.blithe.cms.common.tools;

import com.guoyin.amtp.exception.AmtpException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: EXCEL导出
 * @outhor wanghao
 * @create 2018-12-19 上午10:39
 */
public class ExcelUtils {

    //导出文件的名字
    private String fileName;
    //显示的导出表的标题
    private String title;
    //导出表的列名
    private String[] rowName;
    //标记
    private String flag;

    private List<Object[]> dataList;
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    private HSSFWorkbook workbook = null;

    private OutputStream out = null;

    public ExcelUtils() {

    }

    public ExcelUtils(String fileName, String title, String[] rowName, List<Object[]> dataList, HttpServletRequest request, HttpServletResponse response) {
        this.fileName = fileName;
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.request = request;
        this.response = response;
    }

    public ExcelUtils(String fileName, String title, String[] rowName, List<Object[]> dataList, String flag, HttpServletRequest request, HttpServletResponse response) {
        this.fileName = fileName;
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.flag = flag;
        this.request = request;
        this.response = response;
    }

    public void export() throws Exception {
        HSSFWorkbook createExcel = this.createExcel();
        this.writeInOutputStream(createExcel);
    }

    /**
     * 导出数据
     *
     * @return
     * @throws Exception
     */
    public HSSFWorkbook createExcel() {
        try {
            workbook = new HSSFWorkbook();                                    //创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title);                    //创建工作表
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);  //获取列头样式对象
            HSSFPalette palette = workbook.getCustomPalette();
            //头部样式设置
            palette.setColorAtIndex(IndexedColors.GREY_40_PERCENT.index, (byte) 192, (byte) 192, (byte) 192);
            columnTopStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
            columnTopStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            //奇数行样式
            HSSFCellStyle style1 = this.getStyle(workbook);

            //偶数行样式
            HSSFCellStyle style2 = this.getStyle(workbook);
            palette.setColorAtIndex(IndexedColors.GREY_25_PERCENT.index, (byte) 235, (byte) 235, (byte) 235);
            style2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(0);                  //在索引2的位置创建行(最顶端的行开始的第二行)

            int index = 0;//记录额外创建的sheet数量
            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                createHeader(sheet, columnTopStyle, rowRowName, n);
            }

            for (int i = 0; i < dataList.size(); i++) {
                if ((i) % 60000 == 0 && i!=0) {
                    sheet = workbook.createSheet("sheet" + index);
                    rowRowName = sheet.createRow(0);
                    for (int n = 0; n < columnNum; n++) {
                        createHeader(sheet, columnTopStyle, rowRowName, n);
                    }
                    index++;
                }

                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row =  sheet.createRow((i + 1) - (index * 60000));
                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = row.createCell(j, CellType.STRING);
                    if (!"".equals(obj[j]) && obj[j] != null) {
                        //如果对象为decimal double 和 integer只是设置不转换为字符串
                        //判断是否为decimal
                        if (obj[j] instanceof BigDecimal) {
                            BigDecimal o = (BigDecimal) obj[j];
                            //将decimal转为double赋值
                            cell.setCellValue(Double.parseDouble(o.toString()));
                            //判断是否为double
                        } else if (obj[j] instanceof Double) {
                            //直接转换为double
                            Double d = (Double) obj[j];
                            cell.setCellValue(d);
                            //判断是否为integer
                        } else if (obj[j] instanceof Integer) {
                            //直接转为integer
                            Integer d = (Integer) obj[j];
                            cell.setCellValue(d);
                        } else {
                            //设置单元格的值
                            cell.setCellValue(obj[j].toString());
                        }
                    }
                    if(i%2!=0) {//偶数行
                        cell.setCellStyle(style2); //设置单元格样式
                    } else {
                        cell.setCellStyle(style1); //设置单元格样式
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    private void createHeader(HSSFSheet sheet, HSSFCellStyle columnTopStyle, HSSFRow rowRowName, int n) {
        HSSFCell cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
        cellRowName.setCellType(CellType.STRING);                       //设置列头单元格的数据类型
        cellRowName.setCellStyle(columnTopStyle);                       //设置列头单元格样式
        HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
        cellRowName.setCellValue(text);                                 //设置列头单元格的值
        HSSFRow row = sheet.getRow(0);
        row.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));

        if("cjjl".equals(flag)) {  //成交记录导出列宽度设置
        	sheet.createFreezePane(0,1,0,1);  //成交记录导出冻结表头
            if(n==0){
                sheet.setColumnWidth(n, 15 * 2 * 256);     //订单编号
            } else if(n==1) {
                sheet.setColumnWidth(n, 6 * 2 * 256);      //用户编号
            }else if(n==2) {
                sheet.setColumnWidth(n, 7 * 2 * 256);      //用户名称
            } else if(n==3) {
                sheet.setColumnWidth(n, 14 * 2 * 256);     //成交时间
            } else if(n==4) {
                sheet.setColumnWidth(n, 6 * 2 * 256);     //证券代码
            } else if(n==5) {
                sheet.setColumnWidth(n, 6 * 2 * 256);     //证券名称
            } else if(n==6) {
                sheet.setColumnWidth(n, 7 * 2 * 256);     //操作类型
            } else if(n==19) {
                sheet.setColumnWidth(n, 20 * 2 * 256);     //报单分组
            }
            else {
                sheet.setColumnWidth(n, 10 * 2 * 256);     //其它列宽度列宽度
            }
        }else if("tfs".equals(flag)){
            if(n==0){
                sheet.setColumnWidth(n, 6 * 2 * 256);      //用户编号/用户名称
            }else if(n==1){
                sheet.setColumnWidth(n, 6 * 2 * 256);      //证券代码/证券名称
            }else if(n==2){
                sheet.setColumnWidth(n, 10 * 2 * 256);      //报单通道
            }else if(n==3){
                sheet.setColumnWidth(n, 6 * 2 * 256);      //操作类型
            }else if(n==4){
                sheet.setColumnWidth(n, 10 * 2 * 256);     //调整持仓
            } else if(n==5) {
                sheet.setColumnWidth(n, 12 * 2 * 256);     //单价
            }else if(n==6){
                sheet.setColumnWidth(n, 12 * 2 * 256);      //总成本
            }else if(n==7){
                sheet.setColumnWidth(n, 10 * 2 * 256);      //创建时间
            }else if(n==8){
                sheet.setColumnWidth(n, 12 * 2 * 256);     //调整人/调整人ip
            }else {
                sheet.setColumnWidth(n,10 * 2 * 256);     //其它列宽度列宽度
            }
        }else if("fif".equals(flag)){
            sheet.setColumnWidth(n,10 * 2 * 256);     //其它列宽度列宽度
            if(n==0){
                sheet.setColumnWidth(n, 6 * 2 * 256);      //用户编号/名称
            }
            if(n==1){
                sheet.setColumnWidth(n, 6 * 2 * 256);      //操作类型
            }
            if(n==2){
                sheet.setColumnWidth(n, 10 * 2 * 256);      //保证金
            }
            if(n==3){
                sheet.setColumnWidth(n, 10 * 2 * 256);      //配资金额
            }
            if(n==4){
                sheet.setColumnWidth(n, 5 * 2 * 256);      //利率
            }
            if(n==5){
                sheet.setColumnWidth(n, 10 * 2 * 256);      //实收利息
            }
            if(n==6){
                sheet.setColumnWidth(n, 7 * 2 * 256);      //收息日期
            }
            if(n==7){
                sheet.setColumnWidth(n, 8 * 2 * 256);      //合同期限
            }
            if(n==8){
                sheet.setColumnWidth(n, 7 * 2 * 256);      //是否返息
            }
            if(n==10){
                sheet.setColumnWidth(n, 3 * 2 * 256);      //类型
            }
            if(n==11){
                sheet.setColumnWidth(n, 10 * 2 * 256);      //提盈金额
            }
            if(n==13){
                sheet.setColumnWidth(n, 6 * 2 * 256);      //审核状态
            }
            if(n==14){
                sheet.setColumnWidth(n, 6 * 2 * 256);      //提交人
            }
            if(n==15){
                sheet.setColumnWidth(n, 8 * 2 * 256);      //提交时间
            }
            if(n==14){
                sheet.setColumnWidth(n, 10 * 2 * 256);      //创建时间
            }
        }  else if("bwList".equals(flag)) {
            if(n==0){
                sheet.setColumnWidth(n, 4 * 2 * 256);      //序号
            } else {
                sheet.setColumnWidth(n,10 * 2 * 256);     //其它列宽度列宽度
            }
        } else {
            sheet.setColumnWidth(n, 10 * 2 * 256);         //设置列宽度
        }
    }


    //让列宽随着导出的列长自动适应
    private void autoColoumWith(HSSFSheet sheet, int columnNum) {
        for (int colNum = 0; colNum < columnNum; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(colNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        String stringCellValue = null;
                        try {
                            stringCellValue = currentCell.getStringCellValue();
                        } catch (Exception e) {
                            continue;
                        }
                        String strCellValue[] = {};
                        if (stringCellValue.contains("\r\n")) {
                            strCellValue = stringCellValue.split("\r\n");
                        }
                        int length = stringCellValue == null ? 20 : stringCellValue.getBytes().length;
                        if (strCellValue.length > 0) {
                            length = stringCellValue.getBytes().length / strCellValue.length;
                        }
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            if (colNum == 0) {
                sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
            } else {
                sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
            }
            sheet.autoSizeColumn((short) 2);
        }
    }

    public void writeInOutputStream(HSSFWorkbook workbook) throws Exception {
        //设置响应类型、与头信息
        response.setContentType("application/x-msdownload");
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident") || null != agent && -1 != agent.indexOf("edge")) {  //IE浏览器和Edge浏览器
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {  //其他浏览器
            fileName = new String(fileName.getBytes(), "iso-8859-1");
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
        out = response.getOutputStream();
        workbook.write(out);
        //清除资源
        out.close();
    }

    /**
     * 列头单元格样式
     *
     * @param workbook
     * @return
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
        //设置字体
        HSSFFont font = workbook.createFont();
        //设置粗体
        font.setBold(true);
        //设置字体名字
        font.setFontName("黑体");
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //设置样式
        HSSFCellStyle style = workbook.createCellStyle();
        //设置居中
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置底边框颜色
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        //设置左边框颜色
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        //设置右边框颜色
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        //设置顶边框颜色
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        //在样式应用设置的字体
        style.setFont(font);
        //不设置自动换行
        style.setWrapText(true);
        return style;

    }

    /**
     * 列数据信息单元格样式
     *
     * @param workbook
     * @return
     */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        //设置字体
        HSSFFont font = workbook.createFont();
        //设置字体名字
        font.setFontName("Courier New");
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置居中
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置底边框颜色
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        //设置左边框颜色
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        //设置右边框颜色
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        //设置顶边框颜色
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        //在样式应用设置的字体
        style.setFont(font);
        //设置自动换行
        style.setWrapText(true);
        return style;
    }


    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @return
     * @throws IOException
     */
    public List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception {
        List<List<Object>> list = new ArrayList<>();
        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new AmtpException("创建Excel工作薄为空！");
        }
        Sheet sheet = null;  //页数
        Row row = null;      //行数
        Cell cell = null;    //列数

        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            //遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (null == row ) {
                    continue;
                }

                //遍历所有的列
                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(this.getValue(cell));
                }
                list.add(li);
            }
        }
        return list;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        if (isExcel2003(fileName)) {
            wb = new HSSFWorkbook(inStr);  //2003-
        } else if (isExcel2007(fileName)) {
            wb = new XSSFWorkbook(inStr);  //2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    //解决excel类型问题，获得数值
    public String getValue(Cell cell) {
        String value = "";
        if (null == cell) {
            return value;
        }
        switch (cell.getCellType()) {
            //数值型
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //如果是date类型则 ，获取该cell的date值
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    value = format.format(date);
                } else {// 纯数字
                    BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                    value = big.toString();
                    //解决1234.0  去掉后面的.0
                    if (null != value && !"".equals(value.trim())) {
                        String[] item = value.split("[.]");
                        if (1 < item.length && "0".equals(item[1])) {
                            value = item[0];
                        }
                    }
                }
                break;
            //字符串类型
            case STRING:
                value = cell.getStringCellValue().toString();
                break;
            // 公式类型
            case FORMULA:
                //读公式计算值
                value = String.valueOf(cell.getNumericCellValue());
                if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
                    value = cell.getStringCellValue().toString();
                }
                break;
            // 布尔类型
            case BOOLEAN:
                value = " " + cell.getBooleanCellValue();
                break;
            default:
                value = cell.getStringCellValue().toString();
        }
        if ("null".endsWith(value.trim())) {
            value = "";
        }
        return value;
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

}