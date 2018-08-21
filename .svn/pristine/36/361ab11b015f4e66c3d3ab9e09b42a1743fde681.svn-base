package com.quickdone.znwh.service.impl;

import com.quickdone.znwh.dao.AreaRepository;
import com.quickdone.znwh.entity.Area;
import com.quickdone.znwh.service.AreaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: Hot-jun
 * @Date: 2018/7/12
 * @Description:
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService<Area,Long> {
    @Resource
    private AreaRepository areaRepository;

    /**
     * 根据地区code查看地区下级分类信息
     *
     * @param parentAreaCode
     * @return List<Area>
     */
    @Override
    public List<Area> findAreaInfo(String parentAreaCode) {
        int length = parentAreaCode.length();
        if (length == 1) {
            length = 3;
        } else if (length == 3) {
            length = 6;
        } else if (length == 6) {
            length = 10;
        } else if (length == 10) {
            length = 15;
        } else if (length == 15) {
            length = 20;
        }
        List<Area> areas = areaRepository.findByParentAreaId(parentAreaCode, length);
        return areas;
    }
}
