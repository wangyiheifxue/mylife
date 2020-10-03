package com.mylife.service.user.impl;

import com.mylife.bean.user.SUser;
import com.mylife.bean.user.SUserQO;
import com.mylife.repository.user.UserRepository;
import com.mylife.service.user.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SUserServiceImpl implements SUserService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
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
        return null;
    }

    @Override
    public int count(SUserQO qo) {
        return 0;
    }
}
