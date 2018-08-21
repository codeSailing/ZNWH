package com.quickdone.znwh.service.impl;

import com.quickdone.znwh.dao.*;
import com.quickdone.znwh.entity.*;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.*;
import com.quickdone.znwh.utils.DateSearchUtils;
import com.quickdone.znwh.utils.StringUtils;
import com.quickdone.znwh.utils.ToClass;
import com.quickdone.znwh.vo.CustomerCallFlowVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: ly
 * @Date: 2018/7/13
 * @Description: 外呼记录
 */
@Service
@Transactional
public class CustomerCallFlowServiceImpl implements CustomerCallFlowService<CustomerCallFlow,Long> {
    @Resource
    private CustomerCallFlowRepository CustomerCallFlowRepository;
    @Resource
    private CallTaskRepository callTaskRepository;
    @Resource
    private CustomerRepository customerRepository;
    @Resource
    private CallCategoryRepository callCategoryRepository;
    @Resource
    private CallContentSubjectRepository callContentSubjectRepository;

    @Override
    public CustomerCallFlow addCustomerCallFlow(CustomerCallFlow CustomerCallFlow) {
        CustomerCallFlow.setExecutetime(new Date());
        return CustomerCallFlowRepository.save(CustomerCallFlow);
    }

    @Override
    public ResponseData delCustomerCallFlow(String CustomerCallFlowIds) {
        String[] ids = CustomerCallFlowIds.split(",");
        for (String CustomerCallFlowId : ids) {
            CustomerCallFlowRepository.delete(Long.valueOf(CustomerCallFlowId));
        }
        return ResponseData.getSuccessResponse("删除成功！");
    }

    @Override
    public void findAll(final Map<String, Object> searchParams, PaginationMapLayui pagination) {
        Pageable pageable = new PageRequest(pagination.getStart(), pagination.getLength(), new Sort(Sort.Direction.DESC, "executetime"));//根据时间倒叙
        //取出两个集合的id方便判断
        final List<String> stringList=new ArrayList<String>();
        final List<String> listSubject=new ArrayList<String>();
        final List<String> strList=new ArrayList<String>();         //存储根据任务名称模糊查询的taskid
        //判断任务分类
        if(searchParams.containsKey("callCategory")){
            Long callCategoryId=Long.valueOf(searchParams.get("callCategory").toString());
            List<CallTask> callTasks=callTaskRepository.findByCallCategoryId(callCategoryId);
            if(null != callTasks&&callTasks.size() > 0){
                for (int i=0;i<callTasks.size();i++){
                    stringList.add(callTasks.get(i).getId().toString());
                }
            }
        }
        //判断外呼主题
        if(searchParams.containsKey("callContentSubject")){
        Long  ContentSubject=Long.valueOf(searchParams.get("callContentSubject").toString());
        List<CallTask> callTaskList=callTaskRepository.findByCallContentSubjectId(ContentSubject);
        if(null != callTaskList && callTaskList.size()>0){
            for(int i=0;i<callTaskList.size();i++){
                listSubject.add(callTaskList.get(i).getId().toString());
            }
        }
        }
        //根据任务名称模糊查询
        if(searchParams.containsKey("title")){
        String title = searchParams.get("title").toString();
        List<CallTask> list = callTaskRepository.findByNameLike("%"+title+"%");
        //获取查询数据的id
        if(null != list || list.size()>0){
            for(int i=0;i<list.size();i++){
                strList.add(list.get(i).getId().toString());
            }
        }
        }
        Page<CustomerCallFlow> page = CustomerCallFlowRepository.findAll(new Specification<CustomerCallFlow>() {
            @Override
            public Predicate toPredicate(Root<CustomerCallFlow> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if(searchParams.containsKey("result")){//执行结果
                    Predicate p2 = criteriaBuilder.equal(root.<Integer>get("result"),Integer.parseInt(searchParams.get("result").toString()));
                    predicateList.add(p2);
                }
                if(searchParams.containsKey("callCategory")){//任务分类
                    //如果stingList是null或者数量为零，代表未查询到
                    if(null == stringList || stringList.size()<=0 ){
                        Predicate p2 = criteriaBuilder.isNull(root.<Long>get("id"));
                        predicateList.add(p2);
                    }else{
                        Expression<String> exp = root.<String>get("taskid");
                        Predicate p2=exp.in(stringList);
                        predicateList.add(p2);
                    }
                }
                //根据外呼主题
                if(searchParams.containsKey("callContentSubject")){//外呼主题
                    //如果listSubject是null或者数量为零，代表未查询到
                    if(null == listSubject || listSubject.size()<=0 ){
                        Predicate p2 = criteriaBuilder.isNull(root.<Long>get("id"));
                        predicateList.add(p2);
                    }else{
                        Expression<String> exp = root.<String>get("taskid");
                        Predicate p2=exp.in(listSubject);
                        predicateList.add(p2);
                    }
                }
                //根据时间段查询
                if(searchParams.containsKey("executetime")){
                    String executeBeginDate = searchParams.get("executetime").toString().substring(0,searchParams.get("executetime").toString().length()/2).trim();
                    String executeEndDate = searchParams.get("executetime").toString().substring((searchParams.get("executetime").toString().length()/2)+1,searchParams.get("executetime").toString().length()).trim();
                    try{
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                          Date dateBegin=sdf.parse(executeBeginDate);
                          Date dateEnd=sdf.parse(executeEndDate);
                        if(null !=dateBegin && null != dateEnd){
                            Predicate p2=criteriaBuilder.between(root.<Date>get("executetime"),dateBegin,dateEnd);
                            predicateList.add(p2);
                        }
                    }catch (Exception e){e.getMessage();}
                }
                //根据任务名称查询（）
                if(searchParams.containsKey("title") ){
                    if(null == strList || strList.size()<=0 ){
                        Predicate p2 = criteriaBuilder.isNull(root.<Long>get("id"));
                        predicateList.add(p2);
                    }else{
                        Expression<String> exp = root.<String>get("taskid");
                        Predicate p3=exp.in(strList);
                        predicateList.add(p3);
                    }
                }


                //根据状态值查询
                if(searchParams.containsKey("status")){//计划类型
                    Predicate p5 = criteriaBuilder.equal(root.<Integer>get("status"),Integer.parseInt(searchParams.get("status").toString()));
                    predicateList.add(p5);
                }
                    Predicate p5 = criteriaBuilder.isNotNull(root.<String>get("telephone"));
                predicateList.add(p5);

                //任务配置详情点击根据任务id查看外呼记录 by:zhum
                if(searchParams.containsKey("searchCallTaskId") && searchParams.get("searchCallTaskId")!=null){//执行结果
                    Predicate p6 = criteriaBuilder.equal(root.<String>get("taskid"),searchParams.get("searchCallTaskId").toString());
                    predicateList.add(p6);
                }

                Predicate[] p = new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(p));
            }
        },pageable);
        List<CustomerCallFlow> list = page.getContent();
        List<CustomerCallFlowVo> customerCallFlowVoList=new ArrayList<CustomerCallFlowVo>();
        if(list.size()>0){
            //循环list值，赋值给vo
            for(int i=0;i<list.size();i++){
                CustomerCallFlowVo customerCallFlowVo=new CustomerCallFlowVo();
                customerCallFlowVo.setTaskid(list.get(i).getTaskid());
                customerCallFlowVo.setId(list.get(i).getId());
                customerCallFlowVo.setTelephone(list.get(i).getTelephone());
                customerCallFlowVo.setStatus(list.get(i).getStatus());
                customerCallFlowVo.setCallTime(list.get(i).getCallTime());
                customerCallFlowVo.setResult(list.get(i).getResult());
                if(!StringUtils.isNullOrEmpry(list.get(i).getCustomer_tab())){
                    customerCallFlowVo.setCustomer_tab(list.get(i).getCustomer_tab());
                }
                //外键查询任务分类名称客户姓名插入
                CallTask callTask=(CallTask) callTaskRepository.findOne(Long.valueOf(list.get(i).getTaskid()));
                if( null != callTask && null != callTask.getCallCategoryId()){
                    CallCategory callCategory=(CallCategory) callCategoryRepository.findById(callTask.getCallCategoryId());
                    customerCallFlowVo.setCategory(callCategory.getName());               //分类
                    customerCallFlowVo.setTaskName(callTask.getName());                                 //任务名称
                }
                if( null != callTask && null != callTask.getCallContentSubjectId()){
                    CallContentSubject callContentSubject=(CallContentSubject)callContentSubjectRepository.findById(callTask.getCallContentSubjectId());
                    customerCallFlowVo.setTheme(callContentSubject.getName());                              //主题
                }
                customerCallFlowVo.setExecutetime(list.get(i).getExecutetime());
                //根据电话查询用户获取姓名
                Customer customer=(Customer)customerRepository.findByTelephone(list.get(i).getTelephone());
                if(null != customer){
                    customerCallFlowVo.setName(customer.getName()+"("+customer.getTelephone()+")");                      //客户姓名
                }
                customerCallFlowVoList.add(customerCallFlowVo);
            }
        }
        List<Map> resuList = new ArrayList<Map>();
        for (CustomerCallFlowVo customerCallFlowVo : customerCallFlowVoList) {
                Map map = ToClass.beanToMap(customerCallFlowVo);
                resuList.add(map);
        }
        pagination.setData(resuList);
        pagination.setRecordsTotal(page.getTotalElements());
    }

    @Override
    public List<CustomerCallFlow> findBytelephoneAndtaskid(String telephone, String taskid) {
        return CustomerCallFlowRepository.findByTelephoneAndTaskid(telephone,taskid);
    }

//    @Override
//    public List<CustomerCallFlow> findBytelephone(String telephone) {
//        return CustomerCallFlowRepository.findByTelephone(telephone) ;
//    }

    @Override
    public List<CustomerCallFlow> findByTaskid(String findByTaskid) {
        return CustomerCallFlowRepository.findByTaskid(findByTaskid);
    }
}
