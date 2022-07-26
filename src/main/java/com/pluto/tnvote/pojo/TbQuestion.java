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

@TableName("tb_question")
public class TbQuestion extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String remark;

    /**
     * 1radio|2checkbox|3text|4textarea
     */
    private Integer type;

    /**
     * 0?Ǳ???1???
     */
    private Integer required;

    /**
     * text;number;date
     */
    private String checkStyle;

    /**
     * 0˳??1???
     */
    private Integer orderStyle;

    /**
     * 1;2;3;4
     */
    private Integer showStyle;

    /**
     * 0??????1????
     */
    private Integer test;

    private Integer score;

    private Integer orderby;

    private Integer creator;

    private Date createTime;

    private Integer surveyId;

    @TableField(exist = false)
    private List<TbQuestionOpt> options;

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public String getCheckStyle() {
        return checkStyle;
    }

    public void setCheckStyle(String checkStyle) {
        this.checkStyle = checkStyle;
    }

    public Integer getOrderStyle() {
        return orderStyle;
    }

    public void setOrderStyle(Integer orderStyle) {
        this.orderStyle = orderStyle;
    }

    public Integer getShowStyle() {
        return showStyle;
    }

    public void setShowStyle(Integer showStyle) {
        this.showStyle = showStyle;
    }

    public Integer getTest() {
        return test;
    }

    public void setTest(Integer test) {
        this.test = test;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



    public List<TbQuestionOpt> getOptions() {
        return options;
    }

    public void setOptions(List<TbQuestionOpt> options) {
        this.options = options;
    }


}
