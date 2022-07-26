package com.pluto.tnvote.mapper;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.pluto.tnvote.pojo.User;
import com.pluto.tnvote.pojo.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hctn
 * @since 2021-04-19
 */
@Mapper
@TableName("user_role")
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Insert("insert into user_role(id,user_id,role_id)values(#{userId}, #{userId}, #{roleId})")
    public int create(UserRole userRole);

    @Select("Select role_id from user_role where id = #{UserId}")
    public Integer query(int  UserId);

}
