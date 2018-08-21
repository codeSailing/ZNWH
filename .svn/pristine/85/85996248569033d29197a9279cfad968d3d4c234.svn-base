package com.quickdone.znwh.pojo;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * layui table插件 获取前台传递过来的参数
 * Created by psf on 2017/11/18.
 */
public class LayuiRequest implements Serializable {
    private static final long serialVersionUID = -2145428354884936926L;

    private static final String SEARCH_PARAMS_PREFIX = "key[";
    private static final String SEARCH_PARAMS_SUFFIX = "]";

    private int page;
    private int limit;

    private Map<String, Object> searchParams = new HashMap<String, Object>();

    public Map<String, Object> getSearchParams() {
        return searchParams;
    }

    public static LayuiRequest fromHttpRequest(HttpServletRequest request) {
        LayuiRequest layuiRequest = new LayuiRequest();
        if (request.getParameter("page") != null && request.getParameter("limit") != null) {
            layuiRequest.page = Integer.parseInt(request.getParameter("page")) - 1;
            layuiRequest.limit = Integer.parseInt(request.getParameter("limit"));
        }
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String searchParam = names.nextElement();
            boolean isSearchParam = (searchParam.indexOf(SEARCH_PARAMS_PREFIX) > -1);
            if (isSearchParam) {
                int prefixIndex = SEARCH_PARAMS_PREFIX.length();
                int suffixIndex = searchParam.indexOf(SEARCH_PARAMS_SUFFIX);

                String paramName = searchParam.substring(prefixIndex, suffixIndex);
                String paramValue = request.getParameter(searchParam);
                if (!"".equals(paramValue)) {
                    layuiRequest.searchParams.put(paramName, paramValue);
                }
            }
        }
        return layuiRequest;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
