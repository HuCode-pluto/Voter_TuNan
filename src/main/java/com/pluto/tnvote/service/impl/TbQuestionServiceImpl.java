package com.pluto.tnvote.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.pluto.tnvote.mapper.TbQuestionOptMapper;
import com.pluto.tnvote.pojo.TbQuestion;
import com.pluto.tnvote.mapper.TbQuestionMapper;
import com.pluto.tnvote.pojo.TbQuestionOpt;
import com.pluto.tnvote.service.TbQuestionService;
import com.pluto.tnvote.utils.BeanMapUtils;
import com.pluto.tnvote.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hctn
 * @since 2021-04-19
 */
@Service
@Transactional
public class TbQuestionServiceImpl  implements TbQuestionService {

    @Autowired
    private TbQuestionMapper tbQuestionMapper;
    @Autowired
    private TbQuestionOptMapper tbQuestionOptMapper;

    @Override
    public Integer create(TbQuestion pi) {
        int flag = 0;
        if(pi.getId() != null){
            flag = this.update(pi);
            tbQuestionOptMapper.deleteQ(MapParameter.getInstance().add("questionId",pi.getId()).getMap());
        }else{
            flag = tbQuestionMapper.create(pi);
        }
        if(flag>0){
            List<TbQuestionOpt> options = pi.getOptions();
            int i = 0;
            if(options != null && options.size()>0){
                for (TbQuestionOpt option : options) {
                    option.setSurveyId(pi.getSurveyId());
                    option.setQuestionId(pi.getId());
                    option.setOrderby(++i);
                    tbQuestionOptMapper.create(option);
                }
            }
        }
        return pi.getId();
    }

    @Override
    public Integer deleteBatch(String ids) {
        int flag = 0;
        List<String> list = Splitter.on(",").splitToList(ids);
        for (String s : list) {
            tbQuestionMapper.deleteQ(MapParameter.getInstance().addId(Integer.parseInt(s)).getMap());
            tbQuestionOptMapper.deleteQ(MapParameter.getInstance().add("questionId",Integer.parseInt(s)).getMap());
            flag++;
        }
        return flag;

    }

    @Override
    public Integer delete(Integer id) {
        return tbQuestionMapper.deleteQ(MapParameter.getInstance().addId(id).getMap());
    }

    @Override
    public Integer update(TbQuestion tbQuestion) {
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMapForUpdate(tbQuestion)).addId(tbQuestion.getId()).getMap();
        return tbQuestionMapper.updateQ(map);
    }

    @Override
    public List<TbQuestion> query(TbQuestion tbQuestion) {
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(tbQuestion)).getMap();
        List<TbQuestion> tbQuestionList = tbQuestionMapper.query(map);
        List<TbQuestionOpt> optList = tbQuestionOptMapper.query(MapParameter.getInstance().add("surveyId", tbQuestion.getSurveyId()).getMap());
        for (TbQuestion question1 : tbQuestionList) {
            List<TbQuestionOpt> options = new ArrayList<TbQuestionOpt>();
            for (TbQuestionOpt tbQuestionOpt : optList) {
                if(question1.getId() == tbQuestionOpt.getQuestionId().intValue()){
                    options.add(tbQuestionOpt);
                }
            }
            question1.setOptions(options);
        }
        return tbQuestionList;
    }

    @Override
    public TbQuestion detail(Integer id) {
        return tbQuestionMapper.detail(MapParameter.getInstance().addId(id).getMap());
    }

    @Override
    public Integer count(TbQuestion tbQuestion) {
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(tbQuestion)).getMap();
        return tbQuestionMapper.count(map);
    }
}
