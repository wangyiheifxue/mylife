package com.mylife.filter;

import com.mylife.constant.Const;
import com.mylife.constant.DateTimeConst;
import com.mylife.entity.user.TUser;
import com.mylife.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * @descirption : 请求参数自定义处理过滤器
 * @author : wyh
 * @date : 2020/9/2 11:53
 */
public class RequestParamFilter implements Filter {

    private static Map<String,String> customFilterMap = new HashMap<>(); //自定义过滤map
    private static final Integer PAGE_SIZE = 10; //分页查询-每页条数 默认值
    private static final String endTimeReg = "^end.*Time$"; //结束**时间正则

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        customFilterMap.put("<","&lt;");
//        customFilterMap.put(">","&gt;");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ChangeRequestParamWrapper changeRequestParamWrapper = new ChangeRequestParamWrapper((HttpServletRequest)servletRequest);
        filterChain.doFilter(changeRequestParamWrapper, servletResponse);
    }

    @Override
    public void destroy() {
    }

    /**
     * 继承HttpServletRequestWrapper，创建装饰类，以达到修改HttpServletRequest参数的目的
     */
    private class ChangeRequestParamWrapper extends HttpServletRequestWrapper {
        private Map<String, String[]> parameterMap = new HashMap<>();//所有参数的Map集合

        public ChangeRequestParamWrapper(HttpServletRequest request) {
            super(request);
            this.parameterMap.putAll(request.getParameterMap());
            this.autoSetCustomParameters(request);
        }

        /**
         * 获取所有参数名
         * @return
         */
        @Override
        public Enumeration<String> getParameterNames() {
            Vector<String> vector = new Vector<>(parameterMap.keySet());
            return vector.elements();
        }

        /**
         * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值
         * @param name
         * @return
         */
        @Override
        public String getParameter(String name) {
            String[] results = parameterMap.get(name);
            if (results == null || results.length <= 0){
                return null;
            }
            else {
                return checkParam(results[0]);
            }
        }

        /**
         * 获取指定参数名的所有值的数组
         * @param name
         * @return
         */
        @Override
        public String[] getParameterValues(String name) {
            String[] results = parameterMap.get(name);
            if (results == null || results.length <= 0){
                return null;
            }
            else {
                int length = results.length;
                for (int i = 0; i < length; i++) {
                    results[i] = checkParam(results[i]);
                }
                return results;
            }
        }

        /**
         * 过滤参数值（替换自定义字符）
         * @param value
         * @return
         */
        private String checkParam(String value){
            for(Map.Entry<String, String> entry:customFilterMap.entrySet()){
                value = value.replace(entry.getKey(),entry.getValue());
            }
            return value;
        }

        /**
         * 自动设置自定义参数
         * @param request
         */
        public void autoSetCustomParameters(HttpServletRequest request){
            //-----公共参数 userId
            TUser user = (TUser) request.getSession().getAttribute(Const.SESSION_USER);
            addParameter("userId",user.getId());

            //-----自动设置 mysql分页查询 开始行数（startRow）
            if(StringUtils.isNotBlank(getParameter("page"))){
                String pageSizeStr = getParameter("pageSize");
                int pageSize;
                if(StringUtils.isBlank(pageSizeStr)){
                    addParameter("pageSize",PAGE_SIZE);
                    pageSize = PAGE_SIZE;
                }else{
                    pageSize = Integer.valueOf(pageSizeStr);
                }

                addParameter("startRow",(Integer.valueOf(getParameter("page")) -1) * pageSize);
            }

            //-----end.Time型的参数，自动补上时分秒 23:59:59
            for (String key:this.parameterMap.keySet()) {
                if (Pattern.matches(endTimeReg,key)){
                    String value = getParameter(key);
                    addParameter(key,value + " 23:59:59");
                    if(DateTimeConst.PATTERN_YMD.equals(DateTimeUtil.getDateTimeFormat(value))){
                        addParameter(key,value + " 23:59:59");
                    }
                }
            }
        }

        /**
         * 添加自定义参数s
         * @param extraParams
         */
        public void addParameters(Map<String, Object> extraParams) {
            for (Map.Entry<String, Object> entry : extraParams.entrySet()) {
                addParameter(entry.getKey(), entry.getValue());
            }
        }

        /**
         * 添加自定义参数
         * @param name
         * @param value
         */
        public void addParameter(String name, Object value) {
            if (value != null) {
                if (value instanceof String[]) {
                    parameterMap.put(name, (String[]) value);
                } else if (value instanceof String) {
                    parameterMap.put(name, new String[]{(String) value});
                } else {
                    parameterMap.put(name, new String[]{String.valueOf(value)});
                }
            }
        }

    }
}
