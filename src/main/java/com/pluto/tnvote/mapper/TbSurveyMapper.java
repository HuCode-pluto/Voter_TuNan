package com.pluto.tnvote.mapper;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.pluto.tnvote.pojo.TbQuestion;
import com.pluto.tnvote.pojo.TbSurvey;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hctn
 * @since 2021-04-19
 */
//@TableName("tb_survey")
@Mapper
@Repository
public interface TbSurveyMapper extends BaseMapper<TbSurvey> {

    public int create(TbSurvey pi);
    public int deleteS(Map<String, Object> paramMap);
    public int updateS(Map<String, Object> paramMap);
    public List<TbSurvey> query(ModelMap modelMap);
    public TbSurvey detail(Map<String, Object> paramMap);
    public int count(Map<String, Object> paramMap);
    public List<Integer> queryByState(String state);
    public List<Integer> queryByStateForExec(String state);
    public List<String> selectByState(Integer id,String state);
    public List<String> optvoter(Integer optId);

}
