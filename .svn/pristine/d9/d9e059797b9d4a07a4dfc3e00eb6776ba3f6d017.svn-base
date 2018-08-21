package com.quickdone.znwh.dao;

import com.quickdone.znwh.entity.Area;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Auther: Hot-jun
 * @Date: 2018/7/12
 * @Description:
 */
public interface AreaRepository extends BaseRepository<Area,Long> {

    @Query(value = "select a from Area a where length(a.areaCode) = :length and a.areaCode like :parentAreaCode%")
    List<Area> findByParentAreaId(@Param("parentAreaCode") String parentAreaCode, @Param("length") int length);
}
