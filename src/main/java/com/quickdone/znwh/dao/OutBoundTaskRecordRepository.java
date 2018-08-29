package com.quickdone.znwh.dao;

import com.quickdone.znwh.entity.OutBoundTaskRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ly
 * @Date: 2018/8/23
 * @Description: 任务客户外呼记录
 */
@Repository
public interface OutBoundTaskRecordRepository extends BaseRepository<OutBoundTaskRecord,Long> {

    /**
     * @Author: ly
     * @Date: 2018/8/23
     * @Description: 根据id查询
     */
    OutBoundTaskRecord findById(Long id);

    /**
     * @Author: ly
     * @Date: 2018/8/23
     * @Description: 根据SessionId查询
     */
    OutBoundTaskRecord findBySessionId(String id);

}
