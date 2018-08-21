package com.quickdone.znwh.utils;

import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * Created by zhum on 2017/12/28.
 */
public class JSONConfigUtil {
    public static JsonConfig jsonConfig(String args){
        //过滤不需要的字段
        JsonConfig config = new JsonConfig();
        config.setIgnoreDefaultExcludes(false);
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        config.setExcludes(args.split(","));
        return config;
    }
}
