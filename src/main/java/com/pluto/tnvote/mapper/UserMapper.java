package com.pluto.tnvote.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.pluto.tnvote.pojo.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hctn
 * @since 2021-04-19
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    public int create(User pi);



    public int deleteU(Map<String, Object> paramMap);

    public int updateU(Map<String, Object> paramMap);

    public List<User> query(Map<String, Object> paramMap);

    public User detail(Map<String, Object> paramMap);

    public int count(Map<String, Object> paramMap);

    @Select("select distinct r.code" +
            "from role r,user u,user_role ur" +
            "where r.id = ur.role_id and u.id = ur.user_id and u.account = #{account}")
    Set<String> findRolesByUserName(String account);

    @Select("select distinct r.code" +
            "from role r,user u,user_role ur,role_permission rp,permission p"+
            "where r.id = ur.role_id and u.id = ur.user_id and rp.role_id = r.id and rp.permissom_id = p.id"+
            "and u.account = #{account}")
    Set<String> findPermissionsByUserName(String account);
}
