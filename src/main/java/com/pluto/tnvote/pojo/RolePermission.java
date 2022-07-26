package com.pluto.tnvote.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.pluto.tnvote.utils.Entity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hctn
 * @since 2021-04-19
 */
@Data

@TableName("role_permission")
public class RolePermission extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer roleId;

    private Integer permissionId;


}
