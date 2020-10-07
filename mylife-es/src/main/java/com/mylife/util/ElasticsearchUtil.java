package com.mylife.util;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
/**
 * @descirption : es工具类
 * @author : wyh
 * @date : 2020/10/7 23:30
 */
public class ElasticsearchUtil {

    private static final String PRE_TAG = "<font color='#dd4b39'>";
    private static final String POST_TAG = "</font>";

    /**
     * 获取高亮field
     * @param fieldName
     * @return
     */
    public static HighlightBuilder.Field getHighlightField(String fieldName){
        return new HighlightBuilder.Field(fieldName).preTags(PRE_TAG).postTags(POST_TAG);
    }

    /**
     * 构建高亮field
     * @return
     */
    public static HighlightBuilder.Field buildHighlightField() {
        return new HighlightBuilder.Field("*").requireFieldMatch(false).preTags(PRE_TAG).postTags(POST_TAG);
    }
}
