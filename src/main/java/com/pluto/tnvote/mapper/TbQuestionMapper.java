package com.pluto.tnvote.mapper;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.pluto.tnvote.pojo.TbQuestion;
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
@TableName("tb_question")
public interface TbQuestionMapper extends BaseMapper<TbQuestion> {
    public int create(TbQuestion pi);
    public int deleteQ(Map<String, Object> paramMap);
    public int updateQ(Map<String, Object> paramMap);
    public List<TbQuestion> query(Map<String, Object> paramMap);
    public TbQuestion detail(Map<String, Object> paramMap);
    public int count(Map<String, Object> paramMap);
}
