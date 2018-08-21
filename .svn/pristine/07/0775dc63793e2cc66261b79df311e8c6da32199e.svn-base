package com.quickdone.znwh.controller;

import com.mysql.jdbc.StringUtils;
import com.quickdone.znwh.entity.CustomerBetweenGroup;
import com.quickdone.znwh.entity.CustomerGroup;
import com.quickdone.znwh.pojo.LayuiRequest;
import com.quickdone.znwh.pojo.LayuiResponseMap;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.CustomerBetweenGroupService;
import com.quickdone.znwh.service.CustomerGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: Hot-jun
 * @Date: 2018/7/10
 * @Description:
 */
@Controller
@RequestMapping(value = "/customerGroup")
public class CustomerGroupController {
    private static Logger logger = LoggerFactory.getLogger(CustomerGroupController.class);
    @Resource
    private CustomerGroupService customerGroupService;
    @Resource
    private CustomerBetweenGroupService customerBetweenGroupService;

    /**
     * 调至客户组管理页面
     *
     * @return
     */
    @RequestMapping(value = "/showCustomerGroup.do", method = RequestMethod.GET)
    public ModelAndView showCustomerGroup() {
        ModelAndView modelAndView = new ModelAndView("customerGroup/customerGroup");
        return modelAndView;
    }

    /**
     * 展示所有分组
     *
     * @auther: Hou-jun
     * @date: 2018/7/13
     */
    @RequestMapping(value = "/showTree.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData showTree() {
        List<CustomerGroup> customerGroupList = customerGroupService.findAll();
        if (!customerGroupList.isEmpty()) {
            return ResponseData.getSuccessResponse(customerGroupList);
        } else {
            return ResponseData.getErrorResponse("暂无分组");
        }
    }

    /**
     * 分页查询客户组
     *
     * @auther: Hou-jun
     * @date: 2018/7/11
     */
    @RequestMapping(value = "/pageCustomerGroup.do", method = RequestMethod.GET)
    @ResponseBody
    public LayuiResponseMap pageCustomerGroup(HttpServletRequest request) {
        LayuiRequest layuiRequest = LayuiRequest.fromHttpRequest(request);
        PaginationMapLayui pagination = PaginationMapLayui.formJQueryLayuiTableRequest(layuiRequest);
        customerGroupService.pageCustomerGroup(layuiRequest.getSearchParams(), pagination);
        LayuiResponseMap dtResp = LayuiResponseMap.fromPagination(pagination);
        return dtResp;
    }

    /**
     * 添加用户组
     *
     * @auther: Hou-jun
     * @date: 2018/7/11
     */
    @ResponseBody
    @RequestMapping(value = "/addCustomerGroup.do", method = RequestMethod.POST)
    public ResponseData addCustomerGroup(CustomerGroup customerGroup) {
        logger.debug("==============添加用户组=================");
        CustomerGroup customerGroupSuccess = (CustomerGroup) customerGroupService.addCustomerGroup(customerGroup);
        return ResponseData.getSuccessResponse(customerGroupSuccess);
    }

    /**
     * 用户组详情
     *
     * @auther: Hou-jun
     * @date: 2018/7/11
     */
    @ResponseBody
    @RequestMapping(value = "/detailCustomerGroup.do", method = RequestMethod.GET)
    public ResponseData detailCustomerGroup(Long id) {
        CustomerGroup customerGroup = (CustomerGroup) customerGroupService.findById(id);
        return ResponseData.getSuccessResponse(customerGroup);
    }

    /**
     * 修改用户组
     *
     * @auther: Hou-jun
     * @date: 2018/7/11
     */
    @ResponseBody
    @RequestMapping(value = "/updateCustomerGroup.do", method = RequestMethod.POST)
    public ResponseData updateCustomerGroup(CustomerGroup customerGroup) {
        logger.debug("==============修改用户组=================");
        CustomerGroup customerGroupUpdateSuccess = (CustomerGroup) customerGroupService.updCustomerGroup(customerGroup);
        return ResponseData.getSuccessResponse(customerGroupUpdateSuccess);
    }

    /**
     * 删除用户组
     *
     * @auther: Hou-jun
     * @date: 2018/7/11
     */
    @ResponseBody
    @RequestMapping(value = "/delCustomerGroup.do", method = RequestMethod.POST)
    public ResponseData delCustomerGroup(String customerGroupId) {
        ResponseData responseData=null;
        logger.debug("==============删除用户组=================");
        if (!StringUtils.isNullOrEmpty(customerGroupId)) {
            String[] ids = customerGroupId.split(",");
            for (String contentSubjectId : ids) {
                CustomerGroup customerGroup = (CustomerGroup) customerGroupService.findById(Long.valueOf(contentSubjectId));
                List<CustomerGroup> customerGroupList = customerGroupService.findByGroupId(customerGroup.getId());
                List<CustomerBetweenGroup> customerBetweenGroupList = customerBetweenGroupService.findByCustomerGroupId(customerGroup.getId());
                if (customerGroupList.size() > 0) {
                    responseData = ResponseData.getErrorResponse("请先删除该组下子组,才能删除此组!!!");
                } else if (customerBetweenGroupList.size() > 0) {
                    responseData = ResponseData.getErrorResponse("请先删除组下的客户,才能删除此用户组!!!");
                } else {
                    responseData = customerGroupService.delCustomerGroup(customerGroup);
                }
            }
            return responseData;
        } else {
            return ResponseData.getErrorResponse("请选择需要删除客户组");
        }
    }

    /**
     * 单独给用户进行分组
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/dataGrouping.do", method = RequestMethod.POST)
    public ResponseData dataGrouping(String customerId, String cusGroupId) {
        return customerBetweenGroupService.dataGrouping(customerId, cusGroupId);
    }
}
