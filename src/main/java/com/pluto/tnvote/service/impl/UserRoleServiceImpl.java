package com.pluto.tnvote.service.impl;

import com.pluto.tnvote.mapper.UserRoleMapper;
import com.pluto.tnvote.pojo.User;
import com.pluto.tnvote.pojo.UserRole;
import com.pluto.tnvote.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hctn
 * @since 2021-04-19
 */
@Service
@Transactional
public class UserRoleServiceImpl  implements UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public int create(UserRole userRole) {
        return userRoleMapper.create(userRole);
    }

    @Override
    public Integer query(int UserId) {
        return  userRoleMapper.query(UserId);
    }
}
