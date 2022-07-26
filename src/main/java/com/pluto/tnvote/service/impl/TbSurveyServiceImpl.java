package com.pluto.tnvote.service.impl;
import com.google.common.base.Splitter;
import com.pluto.tnvote.mapper.TbAnswerOptMapper;
import com.pluto.tnvote.mapper.TbAnswerTxtMapper;
import com.pluto.tnvote.pojo.TbAnswerOpt;
import com.pluto.tnvote.pojo.TbAnswerTxt;
import com.pluto.tnvote.pojo.TbSurvey;
import com.pluto.tnvote.mapper.TbSurveyMapper;
import com.pluto.tnvote.service.TbSurveyService;
import com.pluto.tnvote.utils.BeanMapUtils;
import com.pluto.tnvote.utils.MapParameter;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.HashMap;
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
public class TbSurveyServiceImpl implements TbSurveyService {

    /**
     *
     */
	@Autowired
    private TbSurveyMapper tbSurveyMapper;

	@Autowired
    private TbAnswerTxtMapper tbAnswerTxtMapper;

	@Autowired
    private TbAnswerOptMapper tbAnswerOptMapper;

    @Override
    public int create(TbSurvey pi) {
        return tbSurveyMapper.create(pi);
    }

    @Override
    public int deleteBatch(String ids) {
        int flag = 0;
        List<String> list = Splitter.on(",").splitToList(ids);
        for (String s : list) {
            tbSurveyMapper.deleteS(MapParameter.getInstance().addId(Integer.parseInt(s)).getMap());
            flag++;
        }

        return flag;
    }

    @Override
    public int delete(Integer id) {
        return tbSurveyMapper.deleteS(MapParameter.getInstance().addId(id).getMap());
    }

    @Override
    public List<TbSurvey> query(ModelMap modelMap) {
		/* PageHelper.startPage(tbSurvey.getPage(),tbSurvey.getLimit()); */
    	int page = Integer.valueOf(modelMap.get("page").toString())-1;
    	modelMap.put("page", page);
        return tbSurveyMapper.query(modelMap);
    }

    @Override
    public List<TbSurvey> queryAll(TbSurvey tbSurvey) {
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(tbSurvey)).getMap();
        System.out.println();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("state",tbSurvey.getState());
        return tbSurveyMapper.query(modelMap);
    }

    @Override
    public int update(TbSurvey tbSurvey) {
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMapForUpdate(tbSurvey)).addId(tbSurvey.getId()).getMap();
        return tbSurveyMapper.updateS(map);
    }

    @Override
    public int count(TbSurvey tbSurvey) {
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(tbSurvey)).getMap();
        return tbSurveyMapper.count(map);
    }

    @Override
    public TbSurvey detail(Integer id) {
        return tbSurveyMapper.detail(MapParameter.getInstance().addId(id).getMap());
    }

    @Override
    public List<TbAnswerOpt> queryTbAnswerOpt(TbAnswerOpt tbAnswerOpt) {
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(tbAnswerOpt)).getMap();
        return tbAnswerOptMapper.query(map);
    }

    @Override
    public void updateState() {
        List<Integer> list = tbSurveyMapper.queryByState(TbSurvey.state_create);
        for (Integer id : list) {
            tbSurveyMapper.updateS(MapParameter.getInstance().add("updateState",TbSurvey.state_exec).add("id",id).getMap());
        }
        List<Integer> list2 = tbSurveyMapper.queryByStateForExec(TbSurvey.state_exec);
        for (Integer id : list2) {
            tbSurveyMapper.updateS(MapParameter.getInstance().add("updateState",TbSurvey.state_over).add("id",id).getMap());
        }

    }

    @Override
    public Integer submit(List<TbAnswerOpt> opts, List<TbAnswerTxt> txts) {
        int flag = 0;
        for (TbAnswerOpt opt : opts) {
            flag = tbAnswerOptMapper.createBy(opt);
        }
        for (TbAnswerTxt txt : txts) {
            flag = tbAnswerTxtMapper.create(txt);
        }
        return flag;
    }

    @Override
    public List<String> optvoter(Integer optId) {
        return tbSurveyMapper.optvoter(optId);
    }

    @Override
    public List<String> selectByState(Integer id,String state) {

        return tbSurveyMapper.selectByState(id,state);
    }

    @Override
    public void endState(Integer id,String state) {
        tbSurveyMapper.updateS(MapParameter.getInstance().add("updateState",TbSurvey.state_over).add("id",id).getMap());
    }
}
