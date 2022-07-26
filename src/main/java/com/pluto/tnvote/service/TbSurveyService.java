package com.pluto.tnvote.service;

import com.pluto.tnvote.pojo.TbAnswerOpt;
import com.pluto.tnvote.pojo.TbAnswerTxt;
import com.pluto.tnvote.pojo.TbSurvey;

import java.util.List;

import org.springframework.ui.ModelMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hctn
 * @since 2021-04-19
 */
public interface TbSurveyService  {

    int create(TbSurvey pi);

    int deleteBatch(String ids);

    int delete(Integer id);

    List<TbSurvey> query(ModelMap map);

    List<TbSurvey> queryAll(TbSurvey tbSurvey);

    int update(TbSurvey tbSurvey);

    int count(TbSurvey tbSurvey);

    TbSurvey detail(Integer id);

    List<TbAnswerOpt> queryTbAnswerOpt(TbAnswerOpt tbAnswerOpt);

    void updateState();

    Integer submit(List<TbAnswerOpt> opts,List<TbAnswerTxt> txts);

    public List<String> optvoter(Integer optId);

    public List<String> selectByState(Integer id,String state);

    void endState(Integer id,String state);
}
