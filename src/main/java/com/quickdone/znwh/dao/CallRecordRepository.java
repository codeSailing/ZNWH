package com.quickdone.znwh.dao;

import com.quickdone.znwh.entity.CallRecord;
import com.quickdone.znwh.entity.CallTask;

/**
 * @Author: ly
 * @Date: 2018/7/13
 * @Description: 外呼记录
 */
public interface CallRecordRepository extends BaseRepository<CallRecord,Long>{
    CallRecord findByCallTaskid(long callTaskid);
}
