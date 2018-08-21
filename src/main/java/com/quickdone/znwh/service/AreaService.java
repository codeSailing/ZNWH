package com.quickdone.znwh.service;

import com.quickdone.znwh.entity.Area;

import java.util.List;

/**
 * @Auther: Hot-jun
 * @Date: 2018/7/12
 * @Description:
 */
public interface AreaService<Area,Long> {

    /**
     * 根据code查询下级地区
     * @auther: Hou-jun
     * @date: 2018/7/12
     */
    public List<Area> findAreaInfo(String parentAreaCode);
}
