package com.quickdone.znwh.controller;

import com.mysql.jdbc.StringUtils;
import com.quickdone.znwh.entity.Area;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: Hot-jun
 * @Date: 2018/7/12
 * @Description:
 */
@Controller
@RequestMapping(value = "/area")
public class AreaController {

    @Resource
    private AreaService areaService;

    /**
     * 根据地区code查看地区下级分类信息
     * @auther: Hou-jun
     * @date: 2018/7/12
     */
    @RequestMapping(value = "/findAreaInfo.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData findAreaInfo(String areaCode) {
        ResponseData responseData = null;
        if (StringUtils.isNullOrEmpty(areaCode)) {
            List<Area> areas = areaService.findAreaInfo("100");
            responseData = ResponseData.getSuccessResponse(areas);
        } else {
            List<Area> areas = areaService.findAreaInfo(areaCode);
            responseData = ResponseData.getSuccessResponse(areas);
        }
        return responseData;
    }
}
