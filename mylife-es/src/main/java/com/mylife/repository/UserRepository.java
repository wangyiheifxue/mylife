package com.mylife.repository;

import com.mylife.bean.user.SUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<SUser,Long> {
}
