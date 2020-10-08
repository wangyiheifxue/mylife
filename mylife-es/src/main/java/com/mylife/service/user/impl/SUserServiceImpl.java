package com.mylife.service.user.impl;

import com.google.common.collect.Lists;
import com.mylife.bean.user.SUser;
import com.mylife.bean.user.SUserQO;
import com.mylife.repository.user.UserRepository;
import com.mylife.service.user.SUserService;
import com.mylife.util.ElasticsearchUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SUserServiceImpl implements SUserService {

    @Autowired
    private ElasticsearchRestTemplate esRestTemplate;
    @Autowired
    private UserRepository userRepository;

    @Override
    public long save(SUser user) {
        return userRepository.save(user).getId();
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<SUser> list(SUserQO qo) {
        List<SUser> sUserList = Lists.newArrayList();
        try {
            SearchHits<SUser> searchHits = esRestTemplate.search(this.getSearchQuery(qo),SUser.class);
            if(searchHits.getTotalHits() > 0){
                for (SearchHit<SUser> sc:searchHits.getSearchHits()) {
                    SUser sUser = sc.getContent();

                    //-----高亮处理
                    Map<String, List<String>> highlightFieldMap = sc.getHighlightFields();
                    if(!highlightFieldMap.isEmpty()){
                        for (Map.Entry<String,List<String>> entry:highlightFieldMap.entrySet()) {
                            switch (entry.getKey()){
                                case "nickname":
                                    sUser.setNickname(entry.getValue().toString());
                                    break;
                                case "realName":
                                    sUser.setRealName(entry.getValue().toString());
                                    break;
                                case "mobilePhone":
                                    sUser.setMobilePhone(entry.getValue().toString());
                                    break;
                                case "idNo":
                                    sUser.setIdNo(entry.getValue().toString());
                                    break;
                            }
                        }
                    }

                    sUserList.add(sUser);
                }
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        return sUserList;
    }

    @Override
    public long count(SUserQO qo) {
        qo.setCountData(true);
        return esRestTemplate.count(this.getSearchQuery(qo),SUser.class);
    }

    /**
     * 获取NativeSearchQuery
     * @param qo
     * @return
     */
    private Query getSearchQuery(SUserQO qo) {
        QueryBuilder boolQueryBuilder = buildQueryBuilder(qo);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder
//                .withIndicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN)
                .withQuery(boolQueryBuilder)
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC));
        //查询
        if(!qo.isCountData()){
            //分页查询
            if(qo.getPage() != null && qo.getPageSize() != null){
                Pageable pageable = PageRequest.of(qo.getPage(), qo.getPageSize());
                nativeSearchQueryBuilder.withPageable(pageable);
            }

            //高亮
            nativeSearchQueryBuilder.withHighlightFields(ElasticsearchUtil.buildHighlightField());
        }

        return nativeSearchQueryBuilder.build();
    }

    /**
     * 构建QueryBuilder
     * @param qo
     * @return
     */
    private QueryBuilder buildQueryBuilder(SUserQO qo){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if(StringUtils.isNotBlank(qo.getKeywords())){
            BoolQueryBuilder subBoolQuery = QueryBuilders.boolQuery();
            subBoolQuery
                    .should(QueryBuilders.wildcardQuery("nickname","*" + qo.getKeywords() + "*"))
                    .should(QueryBuilders.wildcardQuery("realName","*" + qo.getKeywords() + "*"))
                    .should(QueryBuilders.wildcardQuery("mobilePhone",qo.getKeywords() + "*"))
                    .should(QueryBuilders.wildcardQuery("idNo","*" + qo.getKeywords() + "*"));

            boolQueryBuilder.should(subBoolQuery);
        }

        if(qo.getEnable() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("enable",qo.getEnable()));
        }

        return boolQueryBuilder;
    }

}
