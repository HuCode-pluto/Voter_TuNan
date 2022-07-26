package com.pluto.tnvote.service;

import com.pluto.tnvote.pojo.User;
import com.pluto.tnvote.pojo.UserRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hctn
 * @since 2021-04-19
 */
public interface UserRoleService {
    public int create(UserRole userRole);
    public Integer query(int  UserId);
}
