import com.quickdone.znwh.controller.CallTaskController;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author: zhum
 * @Date: 2018/7/25 10:12
 * @Description:读取excel生成xml文件
 */
public class ExcelReader {
    private static Logger logger = LoggerFactory.getLogger(CallTaskController.class);

    private static DecimalFormat df = new DecimalFormat("0");

    public static void main(String[] args) throws IOException {
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        //TODO 文件路径
        String filePath = "D:/xml/demo.xls";
        String fileName = filePath.substring(filePath.lastIndexOf("/"),filePath.lastIndexOf("."));
        String columns[] = {"id","sourceRef","targetRef","defaultRef","exceptionRef","answer","content"};
        wb = readExcel(filePath);
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
        createXml(fileName,list);
    }
    //读取excel
    public static Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
    private static String createXml(String title, List<Map<String, String>> nodeList) throws IOException {
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
        //TODO 输出文件路径配置读取
        filePath = "D:\\xml\\" + title + ".xml";
        out.output(document, new FileOutputStream(filePath));//或者FileWriter
        return filePath;
    }
}
