package com.pluto.tnvote.mapper;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.pluto.tnvote.pojo.TbQuestionOpt;
import org.apache.ibatis.annotations.Mapper;

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
@TableName("tb_question_opt")
public interface TbQuestionOptMapper extends BaseMapper<TbQuestionOpt> {
    public int create(TbQuestionOpt pi);
    public int deleteQ(Map<String, Object> paramMap);
    public int updateQ(Map<String, Object> paramMap);
    public List<TbQuestionOpt> query(Map<String, Object> paramMap);
    public TbQuestionOpt detail(Map<String, Object> paramMap);
    public int count(Map<String, Object> paramMap);

}
