package com.quickdone.znwh.dao;

import com.quickdone.znwh.entity.OutBoundTaskInfo;
import com.quickdone.znwh.entity.OutBoundTaskRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ly
 * @Date: 2018/8/23
 * @Description: 外呼内容记录
 */
@Repository
public interface OutBoundTaskInfoRepository extends BaseRepository<OutBoundTaskInfo,Long> {
    /**
     * @Author: ly
     * @Date: 2018/8/23
     * @Description: 根据id查询
     */
    public OutBoundTaskInfo findById(Long id);

    /**
     * @Author: ly
     * @Date: 2018/8/24
     * @Description: 根据
     */
    List<OutBoundTaskInfo>  findByOutBoundTaskRecord_id  (Long recordId);
}
