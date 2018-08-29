package com.quickdone.znwh.taskhandle;

import com.quickdone.znwh.common.Constants;
import com.quickdone.znwh.controller.LoginController;
import com.quickdone.znwh.controller.api.IVRController;
import com.quickdone.znwh.entity.CallFlow;
import com.quickdone.znwh.entity.CallTask;
import com.quickdone.znwh.enums.IvrEnum;
import com.quickdone.znwh.pojo.ProcessNode;
import com.quickdone.znwh.service.CallFlowService;
import com.quickdone.znwh.service.CallTaskService;
import com.quickdone.znwh.utils.StringUtils;
import com.quickdone.znwh.utils.Xml2JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.aspectj.weaver.ast.Call;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: psf
 * @Date: 2018/7/10 15:03
 * @Description:
 */

@Component
public class TaskSchedule {
    private static Logger logger = LoggerFactory.getLogger(TaskSchedule.class);

    //    @Value("${flow.file.path}")
//    private String flowpath;
    @Resource
    private IVRController ivr;
    @Resource
    private CallTaskService callTaskService;
    @Resource
    private CallFlowService callFlowService;

    private static List<ProcessNode> nodeList = new ArrayList<ProcessNode>();//流程节点

    private static final String END_START = "-1"; //默认的结束节点标识

    private static Map<Long,List<ProcessNode>> nodeMap = new HashMap<Long,List<ProcessNode>>();//流程map

    /**
     * 任务执行通用类
     * 执行步骤：
     * 1、触发外呼任务
     * 2、调用IVR接口，将一个或多个客户信息（电话号码）提交给IVR（开发阶段跳转到一个测试页面）
     * 3、IVR拨通电话后回调外呼系统（第一次拨通电话的回调需要带一个标识参数）
     * 4、外呼系统接收到请求后开始解析本次任务的流程配置文件，将第一个节点的内容发送给IVR
     * 5、IVR系统将客户的应答状况反馈给外呼系统（测试页面）
     * 6、外呼系统调用语义模块，得到标准答案，外呼系统根据标准答案决定下一步的流程
     */
    public Map<String, Object> executeTask(String currentNode, Map<String, Object> params, int callFLowId) throws IOException {
        String answer = (String) params.get("content");
        logger.info("本次流程节点的id是：" + currentNode + "  客户语音的语义结果是：" + answer + "   任务id是:" + callFLowId);
        String type = (String) params.get("type");
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNullOrEmpry(type)) {
            type = Constants.IntelligentCallStatus.CHAT;
        }
        //获取任务信息，再获取其对应的流程信息
        CallTask callTask = (CallTask) callTaskService.findById(new Long((long) callFLowId));
        Long callFlowId = callTask.getCallFlowId();
        CallFlow callFlow = (CallFlow) callFlowService.findById(callFlowId);
        if (callFlow == null || callTask == null) {
            return result;
        }
        switch (type) {
            case Constants.IntelligentCallStatus.NORMAL:
                logger.info("進入正常流程=============");
                break;
            case Constants.IntelligentCallStatus.CHAT:
                logger.info("進入閑聊流程=============节点id" + currentNode);
                ProcessNode processNode = getNodeById(currentNode,callFlowId);
                answer = answer + "," + processNode.getContent();
                //拿到闲聊的内容
                // currentNode = String.valueOf(Integer.parseInt(currentNode) - 1);
                result.put("nodesize", nodeList.size());
                result.put("content", answer);
                result.put("currentnode", String.valueOf(Integer.parseInt(currentNode)));
                return result;


            case Constants.IntelligentCallStatus.NOTHEARING:
                logger.info("進入沒聽清流程=============节点id" + currentNode);
                currentNode = String.valueOf(Integer.parseInt(currentNode));
                ProcessNode processNode2 = getNodeById(currentNode,callFlowId);
                //返回上一个节点的流程
                result.put("nodesize", nodeList.size());
                result.put("content", processNode2.getContent());
                //result.put("currentnode", String.valueOf(Integer.valueOf(currentNode) - 1));
                result.put("currentnode", String.valueOf(Integer.valueOf(currentNode)));
                return result;
            case Constants.IntelligentCallStatus.USERBUSY:
                //直接跳转到结束节点，将staus 3 传递到前台
                logger.info("進入挂斷流程=============");
                //根据 start=-1 来找到最后一个节点
                ProcessNode node = getNodeByStart(END_START,callFlowId);
                if (node != null) {
                    result.put("nodesize", nodeList.size());
                    result.put("content", node.getContent());
                    result.put("currentnode", String.valueOf(Integer.valueOf(node.getId())));
                    result.put("status", IvrEnum.HANGUP.getValue());
                }
//                ProcessNode processNode1 = getNodeById(currentNode);
//                //String exception = processNode1.getExceptionRef();
//                String defaultRef = processNode1.getDefaultRef();
//
//                result.put("nodesize", nodeList.size());
//                result.put("content", getNodeById(defaultRef).getContent());
//                //result.put("currentnode", String.valueOf(Integer.valueOf(getNodeById(exception).getId()) - 1));
//                result.put("currentnode", String.valueOf(Integer.valueOf(getNodeById(defaultRef).getId())));
                return result;
            case Constants.IntelligentCallStatus.NORESULT:
                logger.info("進入无语义结果流程=========");
                break;
            default:
                logger.info("進入正常流程=============");
                String path = callFlow.getResourcePath();
                logger.info("path:" + path);
                nodeList = new ArrayList<ProcessNode>();
                loadNodeList(path,callFlowId);
                /**
                 * 如果当前节点大于等于1  则需要根据nodeid和answer 匹配下一个节点并且输出节点内容
                 * 如果当前节点小于1 则直接输出第一个节点的内容
                 */

                ProcessNode pnode = getNodeByIdAndAnswer(currentNode, answer,callFlowId);
                String start = pnode.getContent();
                if (IvrEnum.HANGUP.getValue().equals(pnode.getStart())) {
                    result.put("status", IvrEnum.HANGUP.getValue());
                }
                result.put("nodesize", nodeList.size());
                result.put("content", start);
                logger.info("下一节点内容====" + start + " ===== 节点id是:" + pnode.getId());
                return result;

        }

        //外呼系统读取流程配置文件，按照语义结果匹配下一节点，并将节点内容发送给IVR系统
        String processPath = callFlow.getResourcePath();
        nodeList = new ArrayList<ProcessNode>();
        loadNodeList(processPath,callFlowId);
        /**
         * 如果当前节点大于等于1  则需要根据nodeid和answer 匹配下一个节点并且输出节点内容
         * 如果当前节点小于1 则直接输出第一个节点的内容
         */

        ProcessNode node = getNodeByIdAndAnswer(currentNode, answer,callFlowId);

        if (node == null) {
            logger.error("未找到对应节点");
            String proid = getNodeById(currentNode,callFlowId).getDefaultRef();
            result.put("nodesize", nodeList.size());
            result.put("content", getNodeById(proid,callFlowId).getContent());
            result.put("currentnode", proid);
        } else {

            if (END_START.equals(node.getStart())) {
                result.put("status", IvrEnum.HANGUP.getValue());
            }
            String startSpeak = node.getContent();
            logger.info("下一节点内容是" + startSpeak);
            result.put("nodesize", nodeList.size());
            result.put("content", startSpeak);
            result.put("currentnode", node.getId());
        }

        return result;
    }

    //执行任务流程
    public Map<String, Object> executeTaskCallFlow(String currentNode, Map<String, Object> params, String callFlowId) throws IOException {
        String answer = (String) params.get("content");
        logger.info("本次流程节点的id是：" + currentNode + "  客户语音的语义结果是：" + answer + "   任务id是:" + callFlowId);
        String type = (String) params.get("type");
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNullOrEmpry(type)) {
            type = Constants.IntelligentCallStatus.CHAT;
        }
        //获取其对应的流程信息
//        CallTask callTask = (CallTask) callTaskService.findById(new Long((long) callFLowId));
//        Long callFlowId = callTask.getCallFlowId();
        CallFlow callFlow = (CallFlow) callFlowService.findById(Long.valueOf(callFlowId));
        if (callFlow == null) {
            return result;
        }
        //外呼系统读取流程配置文件，按照语义结果匹配下一节点，并将节点内容发送给IVR系统
        String processPath = callFlow.getResourcePath();
        nodeList = new ArrayList<ProcessNode>();
        loadNodeList(processPath,Long.valueOf(callFlowId));
        switch (type) {
            case Constants.IntelligentCallStatus.NORMAL:
                logger.info("进入正常流程=============");
                break;
            case Constants.IntelligentCallStatus.CHAT:
                logger.info("进入闲聊流程=============节点id" + currentNode);
                ProcessNode processNode = getNodeById(currentNode,Long.valueOf(callFlowId));
                if(processNode!=null){
                    answer = answer + "," + processNode.getContent();
                }
                //拿到闲聊的内容
                // currentNode = String.valueOf(Integer.parseInt(currentNode) - 1);
                if(nodeMap.size()>0){
                    result.put("nodesize", nodeMap.get(Long.valueOf(callFlowId)).size());
                }
                result.put("content", answer);
                result.put("currentnode", String.valueOf(Integer.parseInt(currentNode)));
                return result;
            case Constants.IntelligentCallStatus.NOTHEARING:
                logger.info("进入没听清流程=============节点id" + currentNode);
                currentNode = String.valueOf(Integer.parseInt(currentNode));
                //如果是第一次没听清，则返回第一次没听清标识，并提示ivr重复播报问候语
                if(Integer.parseInt(currentNode) == 0){
                    result.put("content", "第一次没听清ivr重复播报问候语");//TODO
                    if(nodeMap.size()>0){
                        result.put("nodesize", nodeMap.get(Long.valueOf(callFlowId)).size());
                    }
                    result.put("currentnode", String.valueOf(Integer.valueOf(currentNode)));
                }else{
                    ProcessNode processNode2 = getNodeById(currentNode,Long.valueOf(callFlowId));
                    if(processNode2!=null){
                        result.put("content", processNode2.getContent());
                    }
                    //返回上一个节点的流程
                    if(nodeMap.size()>0){
                        result.put("nodesize", nodeMap.get(Long.valueOf(callFlowId)).size());
                    }
                    //result.put("currentnode", String.valueOf(Integer.valueOf(currentNode) - 1));
                    result.put("currentnode", String.valueOf(Integer.valueOf(currentNode)));
                }
                return result;
            case Constants.IntelligentCallStatus.USERBUSY:
                //直接跳转到结束节点，将staus 3 传递到前台
                logger.info("进入挂断流程=============");
                //根据 start=-1 来找到最后一个节点
//                ProcessNode node = getNodeByStart(END_START,Long.valueOf(callFlowId));
                result.put("status", IvrEnum.HANGUP.getValue());
//                if (node != null) {
//                    if(nodeMap.size()>0){
//                        result.put("nodesize", nodeMap.get(Long.valueOf(callFlowId)).size());
//                    }
//                    result.put("content", node.getContent());
//                    result.put("currentnode", String.valueOf(Integer.valueOf(node.getId())));
//                }
//                ProcessNode processNode1 = getNodeById(currentNode);
//                //String exception = processNode1.getExceptionRef();
//                String defaultRef = processNode1.getDefaultRef();
//
//                result.put("nodesize", nodeList.size());
//                result.put("content", getNodeById(defaultRef).getContent());
//                //result.put("currentnode", String.valueOf(Integer.valueOf(getNodeById(exception).getId()) - 1));
//                result.put("currentnode", String.valueOf(Integer.valueOf(getNodeById(defaultRef).getId())));
                return result;
            case Constants.IntelligentCallStatus.NORESULT:
                logger.info("进入无语义结果流程=========");
                break;
            default:
                logger.info("进入正常流程=============");
                String path = callFlow.getResourcePath();
                logger.info("path:" + path);
                nodeList = new ArrayList<ProcessNode>();
                loadNodeList(path,Long.valueOf(callFlowId));
                /**
                 * 如果当前节点大于等于1  则需要根据nodeid和answer 匹配下一个节点并且输出节点内容
                 * 如果当前节点小于1 则直接输出第一个节点的内容
                 */
                ProcessNode pnode = getNodeByIdAndAnswer(currentNode, answer,Long.valueOf(callFlowId));
                //TODO 获取节点是否被打标签
                String start = pnode.getContent();
                if (IvrEnum.HANGUP.getValue().equals(pnode.getStart())) {
                    result.put("status", IvrEnum.HANGUP.getValue());
                }
                if(nodeMap.size()>0){
                    result.put("nodesize", nodeMap.get(Long.valueOf(callFlowId)).size());
                }
                result.put("content", start);
                logger.info("下一节点内容====" + start + " ===== 节点id是:" + pnode.getId());
                return result;
        }

        /**
         * 如果当前节点大于等于1  则需要根据nodeid和answer 匹配下一个节点并且输出节点内容
         * 如果当前节点小于1 则直接输出第一个节点的内容
         */

        ProcessNode node = getNodeByIdAndAnswer(currentNode, answer,Long.valueOf(callFlowId));
        if (node == null) {
            logger.error("未找到对应节点");
            String proid = getNodeById(currentNode,Long.valueOf(callFlowId)).getDefaultRef();
            if(nodeMap.size()>0){
                result.put("nodesize", nodeMap.get(Long.valueOf(callFlowId)).size());
            }
            result.put("content", getNodeById(proid,Long.valueOf(callFlowId)).getContent());
            result.put("currentnode", proid);
        } else {

            if (END_START.equals(node.getStart())) {
                result.put("status", IvrEnum.HANGUP.getValue());
            }
            String startSpeak = node.getContent();
            logger.info("下一节点内容是" + startSpeak);
            if(nodeMap.size()>0){
                result.put("nodesize", nodeMap.get(Long.valueOf(callFlowId)).size());
            }
            result.put("content", startSpeak);
            result.put("currentnode", node.getId());
        }
        return result;
    }

    private ProcessNode getNodeByStart(String i,Long callFlowId) {
        if(nodeMap.size()>0){
            for (ProcessNode processNode : nodeMap.get(callFlowId)) {
                if (i.equals(processNode.getStart())) {
                    return processNode;
                }
            }
        }
        return null;
    }


    private void loadNodeList(String processPath,Long callFlowId) throws IOException {
        String json = Xml2JsonUtil.xml2json(processPath);
        logger.info("流程文件解析结果:" + json);
        JSONArray myJsonArray = JSONArray.fromObject(json);
        JSONObject obj = (JSONObject) myJsonArray.get(0);
        JSONArray nodeArray = JSONArray.fromObject(obj.get("node"));
        int size = nodeArray.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                JSONObject job = nodeArray.getJSONObject(i);
                ProcessNode node = configNode(job);
                nodeList.add(node);
            }
        }
        nodeMap.put(callFlowId,nodeList);
    }

    private ProcessNode configNode(JSONObject job) {
        ProcessNode node = new ProcessNode();
        node.setId(job.getString("@id"));
        node.setSourceRef(job.getString("@sourceRef"));
        node.setAnswer(job.getString("@answer"));
        node.setTargetRef(job.getString("@targetRef"));
        node.setDefaultRef(job.getString("@defaultRef"));
        node.setExceptionRef(job.getString("@exceptionRef"));
        node.setContent(job.getString("content"));
        node.setStart(job.getString("@start"));
        node.setLabel(job.getString("@label"));
        return node;
    }

    private ProcessNode getNodeByIdAndAnswer(String id, String answer,Long callFlowId) {
        if (StringUtils.isNullOrEmpry(id)) {
            return null;
        }
        if(nodeMap.size()<0){
            return null;
        }
        if (nodeMap.get(callFlowId) == null || nodeMap.get(callFlowId).isEmpty()) {
            return null;
        }

        //if (Integer.parseInt(id) == 0) { //第一次解析节点 直接将id为1的节点的内容返回
        for (ProcessNode processNode : nodeMap.get(callFlowId)) {

            if (Integer.parseInt(id) == 0 && "1".equals(processNode.getStart())) {
                return processNode;
            } else if (id.equals(processNode.getId())) {
                String targets = processNode.getTargetRef();//找到当前节点的目标节点
                String start_end=processNode.getStart();
                if (END_START.equals(start_end)||StringUtils.isNullOrEmpry(targets)) {//说明是最后一个节点
//                    int finalnode = nodeList.size();
//                    return getNodeById(String.valueOf(finalnode));
                    return processNode;
                } else {
                    String[] target = targets.split(",");
                    if (target.length == 1) {
                        return getNodeById(target[0],callFlowId);
                    } else {
                        for (String pro_id : target) {//遍历下一个目标节点
                            ProcessNode processNode1 = getNodeById(pro_id,callFlowId);
                            if (!StringUtils.isNullOrEmpry(answer) && answer.equals(processNode1.getAnswer())) {
                                return processNode1;
                            }

                        }
                    }

                }
            }

        }
        // }

        return null;
    }


    /**
     * 根据id 获取节点的工具类
     */

    private ProcessNode getNodeById(String id,Long callFlowId) {
        if(nodeMap.size()>0){
            for (ProcessNode processNode : nodeMap.get(callFlowId)) {
                if (id.equals(processNode.getId())) {
                    return processNode;
                }
            }
        }
        return null;

    }

}
