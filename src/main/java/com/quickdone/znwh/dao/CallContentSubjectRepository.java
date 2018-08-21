package com.quickdone.znwh.dao;

import com.quickdone.znwh.entity.CallContentSubject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 内容主题管理
 *
 * @Author: zhuLong
 * @Date: 2018/7/10 15:17
 */
@Repository
public interface CallContentSubjectRepository extends BaseRepository<CallContentSubject, Long> {
    /**
     * @Description: 根据主题名称和父节点id查询
     * @Auther: zhuLong
     * @Date: 2018/7/10 17:20
     * @Param: [name, isDelete]
     * @return: com.quickdone.znwh.entity.CallContentSubject
     */
    CallContentSubject findByNameAndParentId(String name, Long pid);

    /**
     * @Author: zhum
     * @Date: 2018/7/10 15:48
     * @Description: 根据主题id查询
     */
    CallContentSubject findById(Long id);

    /**
     * @Description: 根据父节点id查询
     * @Auther: zhuLong
     * @Date: 2018/7/11 14:25
     * @Param: [parentId]
     * @return: java.util.List<com.quickdone.znwh.entity.CallContentSubject>
     */
    List<CallContentSubject> findByParentId(Long parentId);
}
