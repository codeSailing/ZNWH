package com.quickdone.znwh.utils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: zhum
 * @Date: 2018/7/25 10:09
 * @Description: 解析Excel并生成xml
 */
public class ExcelToXMLUtil {

    private static final Logger log = Logger.getLogger(ExcelToXMLUtil.class);

    private static DecimalFormat df = new DecimalFormat("0");

    public static String execute(HttpServletRequest request,String XMLPATH) throws IOException {
        //解析文件流
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("filename");
        InputStream inputStream = multipartFile.getInputStream();
        String filePath = multipartFile.getOriginalFilename();
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String fileName = filePath.substring(0,filePath.lastIndexOf("."));
        String columns[] = {"id","sourceRef","targetRef","defaultRef","exceptionRef","answer","content"};
        wb = readExcel(filePath,inputStream);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        }
        //生成xml文件
        return createXml(fileName,list,XMLPATH);
    }

    //读取excel
    public static Workbook readExcel(String filePath, InputStream inputStream) throws IOException {
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        try {
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(inputStream);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(inputStream);
            }else{
                return wb = null;
            }
        } catch (IOException e) {
            log.info("文件解析出错！");
        } finally {
            inputStream.close();
        }
        return wb;
    }

    //格式化cell数据
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:{
                    cellValue = df.format(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA:{
                    //判断cell是否为日期格式
                    if(DateUtil.isCellDateFormatted(cell)){
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    }else{
                        //数字
                        cellValue = df.format(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:{
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }


    //创建xml文件
    private static String createXml(String title, List<Map<String, String>> nodeList,String XMLPATH) throws IOException {
        //构建文件路径
        String filePath = "";
        //创建文档
        Document document = new Document();
        //创建根元素
        Element root = new Element("definitions");
        //把根元素加入到document中
        document.addContent(root);
        //父元素proccess
        Element proccess = new Element("proccess");
        //获取id
        proccess.setAttribute("id", "1");
        //获取title
        proccess.setAttribute("name", title);
        //把父元素加到根元素中
        root.addContent(proccess);
        //遍历解析出来的list
        for (Map<String,String> map : nodeList) {
            //遍历获取所有node 创建节点
            Element node = new Element("node");
            for (Map.Entry<String,String> entry : map.entrySet()) {
                if(!"content".equals(entry.getKey())){
                    //生成节点属性
                    node.setAttribute(entry.getKey(), entry.getValue());
                }
            }
            //把当前节点添加到父节点中
            proccess.addContent(node);
            Element node_context = new Element("content");
            //获取当前节点context内容并添加到子节点中
            node_context.setText(map.get("content"));
            node_context.setAttribute("type", "text");
            node.addContent(node_context);
        }

        //设置xml输出格式
        Format format = Format.getPrettyFormat();
        format.setEncoding("utf-8");//设置编码
        format.setIndent("    ");//设置缩进
        //得到xml输出流
        XMLOutputter out = new XMLOutputter(format);
        //如果文件目录不存在，则创建
        File descriptionPath = new File("");
        if (!descriptionPath.exists() && !descriptionPath.isDirectory()) {
            descriptionPath.mkdir();
        }
        //把数据输出到xml中
        filePath = XMLPATH + title + ".xml";
        out.output(document, new FileOutputStream(filePath));//或者FileWriter
        return filePath;
    }

}
