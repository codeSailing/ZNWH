package com.quickdone.znwh.service.impl;

import com.mysql.jdbc.StringUtils;
import com.quickdone.znwh.dao.CustomerBetweenGroupRepository;
import com.quickdone.znwh.dao.CustomerCallFlowRepository;
import com.quickdone.znwh.dao.CustomerGroupRepository;
import com.quickdone.znwh.dao.CustomerRepository;
import com.quickdone.znwh.entity.*;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.CustomerService;
import com.quickdone.znwh.utils.ToClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Hot-jun
 * @Date: 2018/7/10
 * @Description:
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService<Customer, Long> {

    @Resource
    private CustomerRepository customerRepository;
    @Resource
    private CustomerBetweenGroupRepository customerBetweenGroupRepository;
    @Resource
    private CustomerGroupRepository customerGroupRepository;
    @Resource
    private CustomerCallFlowRepository customerCallFlowRepository;

    @Override
    public void pageCustomer(final Map<String, Object> searchParams, PaginationMapLayui pagination) {
        Pageable pageable = new PageRequest(pagination.getStart(), pagination.getLength(), new Sort(Sort.Direction.DESC, "addTime"));
        Page<Customer> page = customerRepository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Path<String> name = root.get("name");
                Path<String> telephone = root.get("telephone");
                if (searchParams.containsKey("searchParam")) {
                    Predicate p1 = cb.like(name, "%" + searchParams.get("searchParam") + "%");
                    Predicate p2 = cb.like(telephone, "%" + searchParams.get("searchParam") + "%");
                    Predicate p = cb.or(p1, p2);
                    predicates.add(p);
                }
                Path<Integer> age = root.get("age");
                if (searchParams.containsKey("ageSmall")) {
                    Predicate p = cb.greaterThanOrEqualTo(age, Integer.valueOf(searchParams.get("ageSmall").toString()));
                    predicates.add(p);
                }
                if (searchParams.containsKey("ageBig")) {
                    Predicate p = cb.lessThanOrEqualTo(age, Integer.valueOf(searchParams.get("ageBig").toString()));
                    predicates.add(p);
                }
                Path<Integer> sex = root.get("sex");
                if (searchParams.containsKey("sex")) {
                    Predicate p = cb.equal(sex, searchParams.get("sex"));
                    predicates.add(p);
                }
                Path<String> areaCode = root.get("areaCode");
                if (searchParams.containsKey("areaCode")) {
                    Predicate p = cb.like(areaCode, searchParams.get("areaCode") + "%");
                    predicates.add(p);
                }
                Path<String> descri = root.get("descri");
                if (searchParams.containsKey("descri")) {
                    Predicate p = cb.equal(descri, searchParams.get("descri"));
                    predicates.add(p);
                }
                Path<Long> id = root.get("id");
                if (searchParams.containsKey("groupId")) {
                    Long groupId = Long.valueOf(searchParams.get("groupId").toString());
                    List<CustomerBetweenGroup> customerBetweenGroup = customerBetweenGroupRepository.findByCustomerGroupId(groupId);
                    if (customerBetweenGroup.size() > 0) {
                        CriteriaBuilder.In<Long> in = cb.in(id);
                        for (CustomerBetweenGroup customerBetweenGroup1 : customerBetweenGroup) {
                            in.value(customerBetweenGroup1.getCustomerId());
                        }
                        predicates.add(in);
                    } else {
                        //此处强搞
                        CriteriaBuilder.In<Long> in = cb.in(id);
                        in.value(0l);
                        predicates.add(in);
                    }
//                    Predicate p = cb.equal(id, searchParams.get("groupId"));
                }
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, pageable);
        List<Customer> contentList = page.getContent();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (Customer customer : contentList) {
            String groupName = "";
            List<CustomerBetweenGroup> customerBetweenGroupList = customerBetweenGroupRepository.findByCustomerId(customer.getId());
            if (customerBetweenGroupList.size() != 0) {
                for (CustomerBetweenGroup cbg : customerBetweenGroupList) {
                    CustomerGroup customerGroup = customerGroupRepository.findById(cbg.getCustomerGroupId());
                    if (groupName == "") {
                        //groupName = cbg.getCustomerGroup().getName()+",";
                        groupName = customerGroup.getName() + ",";
                    } else {
                        //groupName = groupName+cbg.getCustomerGroup().getName()+",";
                        groupName = groupName + customerGroup.getName() + ",";
                    }
                }
                groupName = groupName.substring(0, groupName.length() - 1);
            } else {
                groupName = "-";
            }
            customer.setGroupName(groupName);
            Map<String, Object> map = ToClass.beanToMap(customer);
            mapList.add(map);
        }
        pagination.setData(mapList);
        pagination.setRecordsTotal(page.getTotalElements());
    }

    @Override
    public ResponseData addCustomer(Customer customer) {
        Customer customerValue = customerRepository.findByTelephone(customer.getTelephone());
        //代表查询出现重复的用户
        if (customerValue != null) {
            return ResponseData.getErrorResponse("手机号已存在");
        } else {
            //代表选择了分组
            if (!StringUtils.isNullOrEmpty(customer.getCustomerGroupId())) {
                customer.setAddTime(new Date());
                Customer customerSuccess = customerRepository.save(customer);
                String[] ids = customer.getCustomerGroupId().split(",");
                for (String contentSubjectId : ids) {
                    CustomerGroup customerGroup = customerGroupRepository.findById(Long.valueOf(contentSubjectId));
                    CustomerBetweenGroup customerBetweenGroup = new CustomerBetweenGroup();
                    customerBetweenGroup.setCustomerGroupId(customerGroup.getId());
                    customerBetweenGroup.setCustomerId(customerSuccess.getId());
                    customerBetweenGroupRepository.save(customerBetweenGroup);
                }
                return ResponseData.getSuccessResponse(customerSuccess);
            } else {
                //未选择分组
                customer.setAddTime(new Date());
                Customer customerSuccess = customerRepository.save(customer);
                //添加成功后会返回该对象
                if (customerSuccess != null) {
                    return ResponseData.getSuccessResponse(customerSuccess);
                } else {//未返回代表添加失败
                    return ResponseData.getErrorResponse("添加失败");
                }
            }
        }
    }

    @Override
    public Customer findByTelephone(String telephone) {
        return customerRepository.findByTelephone(telephone);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public ResponseData updateCustomer(Customer customer) {
        Customer customerTel = customerRepository.findByTelephone(customer.getTelephone());
        if (customerTel != null) {
            if (customerTel.getId().longValue() != customer.getId().longValue()) {
                return ResponseData.getErrorResponse("该手机号已存在!");
            }
        }
        List<CustomerBetweenGroup> customerBetweenGroupList = customerBetweenGroupRepository.findByCustomerId(customer.getId());
        for (CustomerBetweenGroup customerBetweenGroupDetail : customerBetweenGroupList) {
            //先删除原有的所有的用户与用户组的关联
            customerBetweenGroupRepository.deleteByCustomerId(customerBetweenGroupDetail.getCustomerId());
        }
        if (!StringUtils.isNullOrEmpty(customer.getCustomerGroupId())) {
            //添加为现选择的分组
            String[] ids = customer.getCustomerGroupId().split(",");
            for (String contentSubjectId : ids) {
                CustomerBetweenGroup customerBetweenGroup = new CustomerBetweenGroup();
                CustomerGroup customerGroup = customerGroupRepository.findById(Long.valueOf(contentSubjectId));
                customerBetweenGroup.setCustomerId(customer.getId());
                customerBetweenGroup.setCustomerGroupId(customerGroup.getId());
                customerBetweenGroupRepository.save(customerBetweenGroup);
            }
        }
        //由于前台传入对象 所以此处硬搞
        Customer customerAddTime = customerRepository.findById(customer.getId());
        List<CustomerCallFlow> customerCallFlowList = customerCallFlowRepository.findByTelephone(customerAddTime.getTelephone());
        if (customerCallFlowList != null && customerBetweenGroupList.size() > 0) {
            for (CustomerCallFlow customerCallFlow : customerCallFlowList) {
                customerCallFlow.setTelephone(customer.getTelephone());
                //修改对应的外呼记录表
                customerCallFlowRepository.save(customerCallFlow);
            }
        }
        customer.setAddTime(customerAddTime.getAddTime());
        customerRepository.save(customer);
        return ResponseData.getSuccessResponse("修改成功");
    }

    @Override
    public ResponseData delCustomer(Customer customer) {
        List<CustomerCallFlow> customerCallFlowList = customerCallFlowRepository.findByTelephone(customer.getTelephone());
        for (CustomerCallFlow customerCallFlow : customerCallFlowList) {
            //删除对应的外呼记录
            customerCallFlowRepository.delete(customerCallFlow.getId());
        }
        customerRepository.delete(customer.getId());
        List<CustomerBetweenGroup> customerBetweenGroupList = customerBetweenGroupRepository.findByCustomerId(customer.getId());
        if (customerBetweenGroupList != null) {
            for (CustomerBetweenGroup customerBetweenGroupDetail : customerBetweenGroupList) {
                customerBetweenGroupRepository.deleteByCustomerId(customerBetweenGroupDetail.getCustomerId());
            }
        }
        //customerRepository.delete(customer);
        return ResponseData.getSuccessResponse("删除成功!");
    }

    @Override
    public ResponseData saveImportCustomer(List<Map<String, Object>> mapList) {
        if (mapList != null) {
            for (Map<String, Object> map : mapList) {
                String areaName = "";
                Customer customer = new Customer();
                customer.setAddTime(new Date());
                if (map.containsKey("name")) {
                    customer.setName(String.valueOf(map.get("name")));
                }
                if (map.containsKey("telephone")) {
                    customer.setTelephone(String.valueOf(map.get("telephone")));
                }
                if (map.containsKey("sex")) {
                    customer.setSex(Integer.parseInt(map.get("sex").toString()));
                }
                if (map.containsKey("age")) {
                    customer.setAge(Integer.parseInt(map.get("age").toString()));
                }
                if (map.containsKey("province")) {
                    areaName = String.valueOf(map.get("province"));
                }
                if (map.containsKey("city")) {
                    areaName = areaName + "-" + String.valueOf(map.get("city"));
                }
                if (map.containsKey("county")) {
                    areaName = areaName + "-" + String.valueOf(map.get("county"));
                }
                if (areaName != "") {
                    customer.setAreaName(areaName);
                    //TODO 先临时存个固定的code
                    customer.setAreaCode("100100100010001");
                }
                if (map.containsKey("descri")) {
                    customer.setDescri(map.get("descri").toString());
                }
                if (map.containsKey("importCustomerGroupId")) {
                    String importCustomerGroupId = String.valueOf(map.get("importCustomerGroupId"));
                    Customer customerSuccess = customerRepository.save(customer);
                    String[] ids = importCustomerGroupId.split(",");
                    for (String contentSubjectId : ids) {
                        CustomerGroup customerGroup = customerGroupRepository.findById(Long.valueOf(contentSubjectId));
                        CustomerBetweenGroup customerBetweenGroup = new CustomerBetweenGroup();
                        customerBetweenGroup.setCustomerGroupId(customerGroup.getId());
                        customerBetweenGroup.setCustomerId(customerSuccess.getId());
                        customerBetweenGroupRepository.save(customerBetweenGroup);
                    }
                }else {
                    customerRepository.save(customer);
                }
            }
            return ResponseData.getSuccessResponse("导入成功");
        } else {
            return ResponseData.getErrorResponse("该模板为空");
        }
    }
}
