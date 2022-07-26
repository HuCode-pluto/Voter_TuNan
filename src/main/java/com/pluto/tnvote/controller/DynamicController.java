package com.pluto.tnvote.controller;

import com.pluto.tnvote.pojo.TbQuestion;
import com.pluto.tnvote.pojo.TbSurvey;
import com.pluto.tnvote.service.TbQuestionService;
import com.pluto.tnvote.service.TbSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DynamicController {

    @Autowired
    private TbSurveyService tbSurveyService;

    @Autowired
    private TbQuestionService tbQuestionService;



    @GetMapping("/survey/dy/{uuid}")
    public String preview(@PathVariable("uuid") String uuid, ModelMap modelMap){
        TbSurvey param = new TbSurvey();
        param.setState(TbSurvey.state_exec);
        List<TbSurvey> list = tbSurveyService.queryAll(param);
       if(list == null ||list.size() == 0){
           modelMap.addAttribute("msg","你要访问的问卷已过期或不存在");
           return "redirect:/index1";
       }
        TbSurvey entity = null;
        for (TbSurvey tbSurvey : list) {
           // if(tbSurvey.getUrl() !=null && tbSurvey.getUrl().contains(uuid)){
            if(tbSurvey.getUrl() !=null && uuid.equals(tbSurvey.getId().toString())) {
                entity = tbSurvey;
            }
        }
        if(entity == null){
            modelMap.addAttribute("msg","你要访问的问卷已过期或不存在");
            return "redirect:/index1";
        }else{
            TbQuestion tbQuestion = new TbQuestion();
            tbQuestion.setSurveyId(entity.getId());
            //查询一个问卷中的所有问题及选项
            List<TbQuestion> tbQuestions = tbQuestionService.query(tbQuestion);
            //将问题设置为survey的属性
            entity.setTbQuestions(tbQuestions);
            modelMap.addAttribute("survey",entity);
            return "pages/survey/exec";
        }
    }


    @GetMapping("/survey/querypassword/{uuid}")
    @ResponseBody
    public String querypassword(@PathVariable("uuid") String uuid, ModelMap modelMap){
        TbSurvey param = new TbSurvey();
        param.setId( Integer.parseInt(uuid));
        List<TbSurvey> list = tbSurveyService.queryAll(param);
        TbSurvey tbSurvey = null;
        for(TbSurvey l:list){
           if(uuid.equals(String.valueOf(l.getId()))) {
               tbSurvey = l;
               break;
           }
        }
        if(tbSurvey == null){
            return "";
        }
        String password = tbSurvey.getPassword();
        if(password == null || password.length() == 0){
            return "";
        }
        return password;
    }


    @GetMapping("/survey/dy/success")
    public String success(){
        return "pages/survey/success";
    }

}
