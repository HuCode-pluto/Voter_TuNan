package com.pluto.tnvote.pojo;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.pluto.tnvote.utils.Entity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author hctn
 * @since 2021-04-19
 */
@Data
@TableName("user")
public class User extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String sex;

    private String account;

    private String password;

    private String userStatus;

    private String name;

    private Integer roleId;

    private String email;

    private Date createtime;

    private String privateSalt;

    private String phone;

    private String remark;

    @TableField(exist = false)
    private List<Role> roleList;


}
