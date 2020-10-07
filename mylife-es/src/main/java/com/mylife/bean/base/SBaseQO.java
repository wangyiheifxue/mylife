package com.mylife.bean.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SBaseQO {
    /**
     * 搜索关键词
     */
    protected String keywords;
    /**
     * 分页查询-页码数
     */
    protected Integer page;
    /**
     * 分页查询-每页条数
     */
    protected Integer pageSize;
    /**
     * 是否是计数查询，默认false
     */
    protected boolean countData = false;
}
