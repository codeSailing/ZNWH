import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: zhum
 * @Date: 2018/7/25 14:39
 * @Description:
 */
public class TestCreateXml {
    //创建xml文件
    private String createXml(String title,String callFlowInfo) throws IOException {
        //构建文件路径
        String filePath = "";
        //格式化数据
        JSONArray jsonArray = JSONArray.fromObject(callFlowInfo);
        String processId = "";
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            processId = jsonObject.getString("@id");
//            title = jsonObject.getString("@name");
            //创建文档
            Document document = new Document();
            //创建根元素
            Element root = new Element("definitions");
            //把根元素加入到document中
            document.addContent(root);
            //创建注释
            Comment rootComment = new Comment("将数据从程序输出到XML中！");
            root.addContent(rootComment);
            //父元素proccess
            Element proccess =  new Element("proccess");
            //获取id
            proccess.setAttribute("id",processId);
            //获取title
            proccess.setAttribute("name",title);
            //把父元素加到根元素中
            root.addContent(proccess);
            JSONArray nodeAry = jsonObject.getJSONArray("node");
            for (int j=0;j<nodeAry.size();j++){
                JSONObject nodeObject = nodeAry.getJSONObject(j);
                //遍历获取所有node 创建节点
                Element node = new Element("node");
                //获取当前节点id
                node.setAttribute("id",nodeObject.getString("@id"));
                //获取当前节点sourceRef
                node.setAttribute("sourceRef",nodeObject.getString("@sourceRef"));
                //获取当前节点targetRef
                node.setAttribute("targetRef",nodeObject.getString("@targetRef"));
                //获取当前节点defaultRef
                node.setAttribute("defaultRef",nodeObject.getString("@defaultRef"));
                //获取当前节点exceptionRef
                node.setAttribute("exceptionRef",nodeObject.getString("@exceptionRef"));
//                if(nodeObject.getString("@answer")!=null && !"".equals(nodeObject.getString("@answer"))){
                //获取当前节点answer
                node.setAttribute("answer",nodeObject.getString("@answer"));
//                }
//                Attribute nodeAttribute = new Attribute("name","vale");
                //把当前节点添加到父节点中
                proccess.addContent(node);
                Element node_context = new Element("content");
                //获取当前节点context内容并添加到子节点中
                node_context.setText(nodeObject.getString("content"));
                node_context.setAttribute("type","text");
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
            filePath = ""+title+".xml";
            out.output(document, new FileOutputStream(filePath));//或者FileWriter
        }
        return filePath;
    }
}
