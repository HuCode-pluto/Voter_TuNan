package com.pluto.tnvote.pojo;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.pluto.tnvote.utils.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

@TableName("tb_answer_txt")
public class TbAnswerTxt extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer surveyId;

    private Integer questionId;

    private String result;

    private Date createTime;

    private String voter;


}
