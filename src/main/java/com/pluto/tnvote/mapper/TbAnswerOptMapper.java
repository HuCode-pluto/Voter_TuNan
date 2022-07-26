package com.pluto.tnvote.mapper;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.pluto.tnvote.pojo.TbAnswerOpt;
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
@TableName("tb_answer_opt")
public interface TbAnswerOptMapper extends BaseMapper<TbAnswerOpt> {
    public int createBy(TbAnswerOpt pi);
    public int deleteB(Map<String, Object> paramMap);
    public int updateBy(Map<String, Object> paramMap);
    public List<TbAnswerOpt> query(Map<String, Object> paramMap);
    public TbAnswerOpt detail(Map<String, Object> paramMap);
    public int count(Map<String, Object> paramMap);
}
