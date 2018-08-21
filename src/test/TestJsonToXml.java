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
 * @Date: 2018/7/27 10:25
 * @Description:
 */
public class TestJsonToXml {
    //创建xml文件
    private static String createXml(String title, String callFlowInfo) throws IOException {
        title = "demo";
        callFlowInfo = "{ \"class\": \"go.GraphLinksModel\",\n" +
                "  \"modelData\": {\"position\":\"-5 -5.366108197396159\"},\n" +
                "  \"nodeDataArray\": [ \n" +
                "{\"text\":\"开始\", \"figure\":\"RoundedRectangle\", \"fill\":\"#66C99C\", \"start\":\"1\", \"id\":\"\", \"answer\":\"\", \"defaultRef\":\"\", \"exceptionRef\":\"\", \"content\":\"张三监护人您好，为更好了解张三的用药情况，请您如实回答下面几个问题：患者患病多久？\", \"key\":-1, \"loc\":\"360 20\"},\n" +
                "{\"text\":\"判断\", \"fill\":\"#7270AC\", \"figure\":\"Diamond\", \"id\":\"\", \"answer\":\"\", \"defaultRef\":\"\", \"exceptionRef\":\"\", \"content\":\"患者是否坚持服药\", \"key\":-3, \"loc\":\"360 150\"},\n" +
                "{\"text\":\"节点\", \"fill\":\"#49A7C5\", \"id\":\"\", \"answer\":\"\", \"defaultRef\":\"\", \"exceptionRef\":\"\", \"content\":\"您认为精神障碍患者最需要得到哪方面的救助\", \"key\":-2, \"loc\":\"150 150\"},\n" +
                "{\"text\":\"节点\", \"fill\":\"#49A7C5\", \"id\":\"\", \"answer\":\"\", \"defaultRef\":\"\", \"exceptionRef\":\"\", \"content\":\"请问药品是否容易买到\", \"key\":-4, \"loc\":\"580 150\"},\n" +
                "{\"text\":\"节点\", \"fill\":\"#49A7C5\", \"id\":\"\", \"answer\":\"\", \"defaultRef\":\"\", \"exceptionRef\":\"\", \"content\":\"我是默认节点\", \"key\":-5, \"loc\":\"360 250\"},\n" +
                "{\"text\":\"节点\", \"fill\":\"#49A7C5\", \"id\":\"\", \"answer\":\"\", \"defaultRef\":\"\", \"exceptionRef\":\"\", \"content\":\"感谢您的宝贵建议，我们会竭尽所能为您提供更好的服务，预祝张三病人早日康复！再次感谢您对我们工作的配合与支持，再见\", \"key\":-6, \"loc\":\"360 340\"},\n" +
                "{\"text\":\"结束\", \"fill\":\"red\", \"figure\":\"RoundedRectangle\", \"id\":\"\", \"answer\":\"\", \"defaultRef\":\"\", \"exceptionRef\":\"\", \"content\":\"謝謝您的接聽，再見！\", \"key\":-7, \"loc\":\"360 420\"}\n" +
                " ],\n" +
                "  \"linkDataArray\": [ \n" +
                "{\"from\":-1, \"to\":-3, \"points\":[359.99999999999994,40.36610819739616,359.99999999999994,50.36610819739616,359.99999999999994,78.0783696504559,360,78.0783696504559,360,105.79063110351562,360,115.79063110351562]},\n" +
                "{\"from\":-3, \"to\":-2, \"points\":[313.6800231933594,150,303.6800231933594,150,243.67000579833984,150,243.67000579833984,150,183.6599884033203,150,173.6599884033203,150], \"text\":\"是\"},\n" +
                "{\"from\":-3, \"to\":-4, \"points\":[406.3199768066406,150,416.3199768066406,150,481.32999420166016,150,481.32999420166016,150,546.3400115966797,150,556.3400115966797,150], \"text\":\"否\"},\n" +
                "{\"points\":[150,167.6046844482422,150,177.6046844482422,150,340,238.17000579833984,340,326.3400115966797,340,336.3400115966797,340], \"from\":-2, \"to\":-6},\n" +
                "{\"from\":-3, \"to\":-5, \"points\":[360,184.20936889648436,360,194.20936889648436,360,208.3023422241211,360,208.3023422241211,360,222.39531555175782,360,232.39531555175782]},\n" +
                "{\"points\":[580,167.6046844482422,580,177.6046844482422,580,340,486.82999420166016,340,393.6599884033203,340,383.6599884033203,340], \"from\":-4, \"to\":-6},\n" +
                "{\"from\":-6, \"to\":-7, \"points\":[360,357.60468444824215,360,367.60468444824215,360,378.619288125423,360,378.619288125423,360,389.63389180260384,360,399.63389180260384]}\n" +
                " ]}";
        //构建文件路径
        String filePath = "";
        JSONObject jsonObjectStr = JSONObject.fromObject(callFlowInfo);
        //格式化数据
        //1 节点数据
        JSONArray jsonNodeArray = jsonObjectStr.getJSONArray("nodeDataArray");
        //2 节点连接数据
        JSONArray jsonNodeLinkArray = jsonObjectStr.getJSONArray("linkDataArray");
        String processId = "";
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
        for(int i=0;i<jsonNodeArray.size();i++){
            //节点obj
            JSONObject nodeObject = jsonNodeArray.getJSONObject(i);
            //获取当前节点key值
            String nodeKey = nodeObject.getString("key");
            //遍历获取所有node 创建节点
            Element node = new Element("node");
            //获取当前节点id
            node.setAttribute("id",nodeObject.getString("key"));
            //获取当前节点start属性：判断是否为第一个节点
            if(nodeObject.containsKey("start") && nodeObject.getString("start")!=null && !"".equals(nodeObject.getString("start"))){
                node.setAttribute("start","1");
            }else{
                node.setAttribute("start","");
            }
            //TODO 循环 节点连接数据jsonNodeLinkArray 获取该节点的sourceRef targetRef defaultRef 以及answer
            StringBuilder targetRef = new StringBuilder();
            //查询该节点的targetRef
            for(int j=0;j<jsonNodeLinkArray.size();j++){
                JSONObject nodeLinkObject = jsonNodeLinkArray.getJSONObject(j);
                //查询该节点的sourceRef
                if(nodeKey.equals(nodeLinkObject.getString("to"))){
                    //获取当前节点sourceRef
                    node.setAttribute("sourceRef",nodeLinkObject.getString("from"));
                }
                if(nodeKey.equals(nodeLinkObject.getString("from"))){
                    if(targetRef.length()>0){
                        targetRef.append(",");
                    }
                    targetRef.append(nodeLinkObject.getString("to"));
                    if(nodeLinkObject.containsKey("text") && "".equals(nodeLinkObject.getString("text"))){
                        node.setAttribute("defaultRef",nodeLinkObject.getString("key"));
                    }else{
                        node.setAttribute("defaultRef",nodeLinkObject.getString("to"));
                    }
                }
                //查询该节点的answer
                if(nodeKey.equals(nodeLinkObject.getString("to"))){
                    //获取当前节点sourceRef
                    if(nodeLinkObject.containsKey("text") && nodeLinkObject.getString("text")!=null){
                        node.setAttribute("answer",nodeLinkObject.getString("text"));
                    }
                }
            }
            if(node.getAttribute("sourceRef")==null){
                node.setAttribute("sourceRef","");
            }
            //获取当前节点sourceRef
            node.setAttribute("targetRef",targetRef.toString());

            if(node.getAttribute("answer")==null){
                node.setAttribute("answer","");
            }
            //获取当前节点exceptionRef
            if(nodeObject.containsKey("exceptionRef")){
                node.setAttribute("exceptionRef",nodeObject.getString("exceptionRef"));
            }else{
                node.setAttribute("exceptionRef","");
            }
            //获取当前节点defaultRef
            if(node.getAttribute("defaultRef")==null){
                node.setAttribute("defaultRef","");
            }
            //把当前节点添加到父节点中
            proccess.addContent(node);
            Element node_context = new Element("content");
            //获取当前节点context内容并添加到子节点中
            if(nodeObject.containsKey("content")){
                node_context.setText(nodeObject.getString("content"));
            }
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
        filePath = "D:\\xml\\"+title+".xml";
        out.output(document, new FileOutputStream(filePath));//或者FileWriter

        return filePath;
    }

    public static void main(String[] args) throws IOException {
        createXml("","");
    }
}
