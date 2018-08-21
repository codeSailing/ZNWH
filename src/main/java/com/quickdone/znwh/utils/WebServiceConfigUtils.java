package com.quickdone.znwh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by Xing ye on 2017/12/7.
 */
/*@Configuration
@ConfigurationProperties(prefix = "webService")
@PropertySource("classpath:webService.properties")*/
public class WebServiceConfigUtils {
    private static Logger logger = LoggerFactory.getLogger(WebServiceConfigUtils.class);

    private static Properties properties;

    static {
         properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("webService.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        try {
            return new String(properties.getProperty(key).getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
