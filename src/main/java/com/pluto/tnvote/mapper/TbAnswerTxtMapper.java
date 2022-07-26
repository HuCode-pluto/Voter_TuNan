package com.pluto.tnvote.mapper;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.pluto.tnvote.pojo.TbAnswerTxt;
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
@TableName("tb_answer_txt")
public interface TbAnswerTxtMapper extends BaseMapper<TbAnswerTxt> {
    public int create(TbAnswerTxt pi);
    public int deleteA(Map<String, Object> paramMap);
    public int updateA(Map<String, Object> paramMap);
    public List<TbAnswerTxt> query(Map<String, Object> paramMap);
    public TbAnswerTxt detail(Map<String, Object> paramMap);
    public int count(Map<String, Object> paramMap);
}
