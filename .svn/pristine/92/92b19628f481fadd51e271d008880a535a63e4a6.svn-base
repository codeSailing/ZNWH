package com.quickdone.znwh.controller;

import com.quickdone.znwh.entity.CallContent;
import com.quickdone.znwh.entity.CallContentSubject;
import com.quickdone.znwh.pojo.LayuiRequest;
import com.quickdone.znwh.pojo.LayuiResponseMap;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.CallContentService;
import com.quickdone.znwh.service.CallContentSubjectService;
import com.quickdone.znwh.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 内容管理相关接口
 *
 * @Author: zhuLong
 * @Date: 2018/7/10 14:54
 */
@Controller
@RequestMapping(value = "/callContent")
public class CallContentController {
    private Logger logger = LoggerFactory.getLogger(CallContentController.class);

    @Resource
    private CallContentService callContentService;
    @Resource
    private CallContentSubjectService callContentSubjectService;
    @Value("${content.audio.path}")
    private String audioPath;

    /**
     * @Description: 内容管理页面
     * @Auther: zhuLong
     * @Date: 2018/7/12 16:49
     * @Param: []
     * @return: org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping(value = "/page.do", method = RequestMethod.GET)
    public ModelAndView page() {
        ModelAndView model = new ModelAndView("/callContent/callContent");
        return model;
    }

    /**
     * @Description: 分页查询内容数据
     * @Auther: zhuLong
     * @Date: 2018/7/11 16:12
     * @Param: [request]
     * @return: com.quickdone.znwh.pojo.LayuiResponseMap
     */
    @RequestMapping(value = "/data.do", method = RequestMethod.GET)
    @ResponseBody
    public LayuiResponseMap contentData(HttpServletRequest request) {
        LayuiRequest layuiRequest = LayuiRequest.fromHttpRequest(request);
        PaginationMapLayui pagination = PaginationMapLayui.formJQueryLayuiTableRequest(layuiRequest);
        callContentService.findData(layuiRequest.getSearchParams(), pagination);
        LayuiResponseMap dtResp = LayuiResponseMap.fromPagination(pagination);
        return dtResp;
    }

    /**
     * @Description: 新增and修改内容
     * @Auther: zhuLong
     * @Date: 2018/7/11 9:07
     * @Param: [callContent]
     * @return: com.quickdone.znwh.pojo.ResponseData
     */
    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addContent(CallContent callContent) {
        logger.info("--------------------- 请求 /callContent/save.do ---------------------");
        ResponseData responseData;
        if (StringUtils.isNullOrEmpry(callContent.getTitle())) {
            responseData = ResponseData.getErrorResponse("内容标题不能为空");
        } else if (callContent.getContentSubjectId() == null) {
            responseData = ResponseData.getErrorResponse("内容主题不能为空");
        } else {
            callContent.setUpdateTime(new Date());
            callContentService.save(callContent);
            responseData = ResponseData.getSuccessResponse("保存成功");
        }
        return responseData;
    }

    /**
     * @Description: 删除内容
     * @Auther: zhuLong
     * @Date: 2018/7/11 9:48
     * @Param: [contentIds]
     * @return: com.quickdone.znwh.pojo.ResponseData
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteContent(String contentIds) {
        logger.info("--------------------- 请求 /callContent/delete.do --------------------- contentIds=" + contentIds);
        ResponseData responseData;
        if (!StringUtils.isNullOrEmpry(contentIds)) {
            String[] ids = contentIds.split(",");
            List<CallContent> contentList = new ArrayList<CallContent>();
            for (String id : ids) {
                CallContent callContent = (CallContent) callContentService.findById(Long.valueOf(id));
                if (callContent != null) {
                    contentList.add(callContent);
                }
            }
            if(!contentList.isEmpty()){
                callContentService.delete(contentList);
                responseData = ResponseData.getSuccessResponse("删除成功");
            }else{
                responseData = ResponseData.getErrorResponse("暂无可删除的内容");
            }
        } else {
            responseData = ResponseData.getErrorResponse("请选择需要删除的内容");
        }
        return responseData;
    }

    /**
     * @Description: 修改主题
     * @Auther: zhuLong
     * @Date: 2018/7/13 11:54
     * @Param: [contentIds, subjectId]
     * @return: com.quickdone.znwh.pojo.ResponseData
     */
    @RequestMapping(value = "/updateSubject.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateSubject(String contentIds, Long subjectId) {
        ResponseData responseData;
        if (!StringUtils.isNullOrEmpry(contentIds)) {
            CallContentSubject callContentSubject = (CallContentSubject) callContentSubjectService.findById(subjectId);
            if (callContentSubject != null) {
                List<CallContent> callContentList = new ArrayList<CallContent>();
                String[] ids = contentIds.split(",");
                for (String contentId : ids) {
                    CallContent callContent = (CallContent) callContentService.findById(Long.valueOf(contentId));
                    callContent.setContentSubjectId(subjectId);
                    callContent.setContentSubjectName(callContentSubject.getName());
                    callContent.setUpdateTime(new Date());
                    callContentList.add(callContent);
                }
                callContentService.save(callContentList);
                responseData = ResponseData.getSuccessResponse("修改成功");
            } else {
                responseData = ResponseData.getErrorResponse("无该主题信息");
            }
        } else {
            responseData = ResponseData.getErrorResponse("请选择需要修改主题的内容");
        }
        return responseData;
    }

    /**
     * @Description: 保存内容音频
     * @Auther: zhuLong
     * @Date: 2018/8/9 10:27
     * @Param: [file]
     * @return: com.quickdone.znwh.pojo.ResponseData
     */
    @RequestMapping(value = "/saveAudio.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveAudio(MultipartFile file) {
        ResponseData responseData;
        try {
            //如果目录不存在，自动创建文件夹
            File dir = new File(audioPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //文件后缀名
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //上传文件名
            String fileName = UUID.randomUUID() + suffix;
            //服务器端保存的文件对象
            File serverFile = new File(audioPath + fileName);
            //将上传的文件写入到服务器端的文件内
            file.transferTo(serverFile);
            Map<String, String> map = new HashMap<String, String>();
            map.put("fileUrl", audioPath + fileName);
            responseData = ResponseData.getSuccessResponse(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.getErrorResponse("上传失败");
        }
        return responseData;
    }

    /**
     * @Description: 读取内容音频
     * @Auther: zhuLong
     * @Date: 2018/8/10 8:53
     * @Param: [path, response]
     * @return: void
     */
    @RequestMapping(value = "/showAudio.do")
    public void showImg(@RequestParam String path, HttpServletResponse response) throws Exception {
        File file = new File(path);
        if (file.exists()) {
            FileInputStream fileIs = new FileInputStream(path);
            int i = fileIs.available(); //得到文件大小
            byte data[] = new byte[i];
            fileIs.read(data); //读数据
            //没有缓存
            response.setHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "must-revalidate");
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);
            response.setHeader("Content-type", "audio/mpeg");
            OutputStream outStream = response.getOutputStream(); //得到向客户端输出二进制数据的对象
            outStream.write(data); //输出数据
            outStream.flush();
            outStream.close();
            fileIs.close();
        }
    }

}
