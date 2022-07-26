package com.pluto.tnvote.controller;

import com.pluto.tnvote.pojo.TbQuestion;
import com.pluto.tnvote.service.TbQuestionService;
import com.pluto.tnvote.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private TbQuestionService tbQuestionService;


    @PostMapping("/create")
    @ResponseBody
    public Map<String,Object> create(@RequestBody TbQuestion question){
        int result = tbQuestionService.create(question);
        if(result<=0){
            //失败的情况下
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().add("id",result).getMap();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(String ids){
        int result = tbQuestionService.deleteBatch(ids);
        if(result<=0){
            //失败的情况下
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody TbQuestion tbQuestion){
        int result = tbQuestionService.update(tbQuestion);
        if(result<=0){
            //失败的情况下
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    @GetMapping("/list")
    public String list(){
       return  "question/list";
    }

    @PostMapping("/query")
    @ResponseBody
    public Map<String,Object> query(@RequestBody TbQuestion tbQuestion, ModelMap modelMap){
        List<TbQuestion> list = tbQuestionService.query(tbQuestion);
        Integer count = tbQuestionService.count(tbQuestion);
        return MapControl.getInstance().page(list,count).getMap();
    }


    @GetMapping("/detail")
    public String detail(Integer id,ModelMap modelMap){
        TbQuestion tbQuestion = tbQuestionService.detail(id);
        modelMap.addAttribute("question",tbQuestion);
        return "question/update";
    }

    @GetMapping("/question")
    public String question(Integer id,ModelMap modelMap){
        TbQuestion tbQuestion = tbQuestionService.detail(id);
        modelMap.addAttribute("question",tbQuestion);
        return "question/question";
    }


}
