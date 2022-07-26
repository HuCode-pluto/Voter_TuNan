package com.pluto.tnvote.controller;


import com.pluto.tnvote.service.UserRoleService;
import org.springframework.beans.factory.annotation.Value;
import com.pluto.tnvote.mapper.UserMapper;
import com.pluto.tnvote.pojo.*;
import com.pluto.tnvote.service.TbQuestionService;
import com.pluto.tnvote.service.TbSurveyService;
import com.pluto.tnvote.utils.MapControl;
import com.pluto.tnvote.utils.SessionUtils;
import com.pluto.tnvote.utils.SystemInit;
import lombok.Data;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hctn
 * @since 2021-04-19
 */
@Controller
@RequestMapping("/survey")
public class TbSurveyController {
    @Autowired
    private TbSurveyService tbSurveyService;
    @Autowired
    private TbQuestionService tbQuestionService;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/tocreate")
    public String v_create(){
        return "pages/survey/add";
    }

    @PostMapping("/create")
    @ResponseBody
    public Map<String,Object> create(@RequestBody TbSurvey tbSurvey, HttpServletRequest request){
        User currUser = SessionUtils.getUser(request);
        tbSurvey.setCreator(currUser.getId());
        tbSurvey.setState(TbSurvey.state_create);
        tbSurvey.setAnon(tbSurvey.getAnon()!=null?0:1);
        int result = tbSurveyService.create(tbSurvey);
        if(result<=0){
            //失败的情况下
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(String ids){
        int result = tbSurveyService.deleteBatch(ids);
        if(result<=0){
            //失败的情况下
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody TbSurvey tbSurvey){
        tbSurvey.setAnon(tbSurvey.getAnon()!=null?0:1);
        int result = tbSurveyService.update(tbSurvey);
        if(result<=0){
            //失败的情况下
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/list")
    public String list(HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        System.err.println(user);
        //req.setAttribute("user", user);
        int userid = user.getId();
        int list =  userRoleService.query(userid);
        if(1 == list){
             return "pages/survey/partlist";
        }else if (2 == list){
             return "pages/survey/viewlist";
        }else{
            return "pages/survey/list";
        }

    }

    @PostMapping("/query")
    @ResponseBody
    public Map<String,Object> query(@RequestBody ModelMap modelMap){
		/*
		 * TbSurvey tbSurvey = new TbSurvey(); tbSurvey.setTitle(title);
		 * tbSurvey.setState(state);
		 */
    	
    	List<TbSurvey> list = tbSurveyService.query(modelMap);
        //创建者信息写入survey对象.
    	SystemInit init = new SystemInit();
    	Map<Integer, User> map = init.init(userMapper);
        for (TbSurvey entity : list) {
            entity.setUser(map.get(entity.getCreator()));
        }
        TbSurvey tbSurvey = new TbSurvey();
        if(modelMap.get("title")!=null) {
        	
        	tbSurvey.setTitle(modelMap.get("title").toString());
        }
        if(modelMap.get("state")!=null) {
        	
        	tbSurvey.setState(modelMap.get("state").toString());
        }
        Integer count = tbSurveyService.count(tbSurvey);
        return MapControl.getInstance().page(list,count).getMap();
    }


    @GetMapping("/detail")
    public String detail(Integer id,ModelMap modelMap){
        TbSurvey tbSurvey = tbSurveyService.detail(id);
        modelMap.addAttribute("tbSurvey",tbSurvey);
        return "pages/survey/update";
    }

    @GetMapping("/question")
    public String tbQuestion(Integer id,ModelMap modelMap){
        TbSurvey tbSurvey = tbSurveyService.detail(id);
        modelMap.addAttribute("tbSurvey",tbSurvey);
        return "pages/survey/question";
    }

    @GetMapping("/preview/{id}")
    public String preview(@PathVariable("id") Integer id,ModelMap modelMap){
        TbSurvey tbSurvey = tbSurveyService.detail(id);
        TbQuestion tbQuestion = new TbQuestion();
        tbQuestion.setSurveyId(tbSurvey.getId());
        //查询一个问卷中的所有问题及选项
        List<TbQuestion> tbQuestions = tbQuestionService.query(tbQuestion);
        //将问题设置为survey的属性
        tbSurvey.setTbQuestions(tbQuestions);
        modelMap.addAttribute("survey",tbSurvey);
        return "pages/survey/preview";
    }

/*    @GetMapping("/file/{id}")
    public String file(@PathVariable("id") Integer id,ModelMap modelMap){
        //TbSurvey tbSurvey = tbSurveyService.detail(id);
        TbSurvey tbSurvey = new TbSurvey();
        tbSurvey.setId(id);
        List<TbSurvey> list =  tbSurveyService.queryAll(tbSurvey);
        tbSurvey = list.get(0);
        modelMap.addAttribute("survey",tbSurvey);
        return "pages/survey/preview";
    }*/

    @Value("${file.address}")
    private String address;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(Integer id, @RequestParam("file") MultipartFile multipartFile, HttpServletRequest request){
        //上传的位置
        String path = address;
        //判断该路径是否存在
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        //上传文件项
        String filename = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String saveName = uuid + "_" + filename.substring(filename.lastIndexOf(File.separator) + 1);
        try {
            multipartFile.transferTo(new File(path, saveName));
            TbSurvey tbSurvey = new TbSurvey();
            tbSurvey.setId(id);
            tbSurvey.setBgimg(saveName);
            tbSurveyService.update(tbSurvey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:preview/"+id;
    }

    @PostMapping("/publish")
    @ResponseBody
    public Map<String,Object> publish(Integer id,HttpServletRequest request){

        TbSurvey param = tbSurveyService.detail(id);
        if(!TbSurvey.state_exec.equals(param.getState())){
            return MapControl.getInstance().error("操作失败，当前问卷未在执行中！").getMap();
        }
        String uuid = "/dy/"+UUID.randomUUID().toString();
        TbSurvey tbSurvey = new TbSurvey();
        tbSurvey.setId(id);
        //http://localhost:8080/tbSurvey/ieieas-asdf-asdf-3-asd-f-asdf
        String url = "http://"+request.getServerName()+ ":" + request.getServerPort() + request.getContextPath()+uuid;
        tbSurvey.setUrl(url);
        tbSurveyService.update(tbSurvey);
        return MapControl.getInstance().success().getMap();
    }
    @Autowired
    com.pluto.tnvote.utils.JwtUtil jwtUtil ;


    @PostMapping("/submit")
    @ResponseBody
    public Map<String,Object> submit(@RequestBody List<Map<String,Object>> list,HttpServletRequest request){
        Integer count = 0;

        List<TbAnswerOpt> optList = new ArrayList();
        List<TbAnswerTxt> txtList = new ArrayList();
        String token = (String) request.getSession().getAttribute("token");
        Map<String, Object>  map =  jwtUtil.parseJwtToken(token);
//        String uuid =  UUID.randomUUID().toString();
        String uuid = (String) map.get("name");
        for (Map<String, Object> stringObjectMap : list){

            String getid = (String)stringObjectMap.get("surveyId");
            TbSurvey surveys = new TbSurvey();
            surveys.setId(Integer.parseInt(getid));
            List<TbSurvey> list2 =  tbSurveyService.queryAll(surveys) ;
            int bounds = 0;
            for(TbSurvey getTbSurveys : list2 ){
                if(String.valueOf(getTbSurveys.getId()).equals(getid)){
                    bounds =  getTbSurveys.getBounds();
                    break;
                }
             }

            TbAnswerOpt tbAnswerOps = new TbAnswerOpt();
            tbAnswerOps.setSurveyId(object2Integer(getid));
            boolean isok = false;
            String loginusername =  (String) map.get("name");
            List<TbAnswerOpt> lid = tbSurveyService.queryTbAnswerOpt(tbAnswerOps);
            for(TbAnswerOpt everyTbAnswerOpt : lid){
               String names =  everyTbAnswerOpt.getVoter();
               if(names.startsWith("匿名")){
                   if(names.contains(loginusername) && bounds==1){     //这里有问题
                       isok = true;
                   }
               }else{
                   if(loginusername.equals(names) && bounds==1 ){     //这里有问题
                       isok = true;
                       break;
                   }
               }
            }

            if(lid.size() > 0 && isok){
               return MapControl.getInstance().error("已经提交过了，无需再次提交").getMap();
            }


            if(object2String(stringObjectMap.get("type")).equals("1") || object2String(stringObjectMap.get("type")).equals("2")){
                List<Object> opts = (List<Object>) stringObjectMap.get("opts");
                for (Object opt : opts) {
                    TbAnswerOpt tbAnswerOpt = new TbAnswerOpt();
                    tbAnswerOpt.setQuestionId(object2Integer(stringObjectMap.get("questionId")));
                    tbAnswerOpt.setSurveyId(object2Integer(stringObjectMap.get("surveyId")));
                    tbAnswerOpt.setType(object2String(stringObjectMap.get("type")));
                    tbAnswerOpt.setOptId(object2Integer(opt));
                    tbAnswerOpt.setCreateTime(new Date());
                    String value=uuid;
                   String surveyId = (String)stringObjectMap.get("surveyId");
                   TbSurvey survey = new TbSurvey();
                    survey.setId(Integer.parseInt(surveyId));
                   List<TbSurvey> li =  tbSurveyService.queryAll(survey) ;
                   TbSurvey survey2 = null ;
                   String anon = "1";
                   for(TbSurvey getTbSurvey : li ){
                      if(String.valueOf(getTbSurvey.getId()).equals(surveyId)){
                          survey2 = getTbSurvey;
                          anon = String.valueOf(survey2.getAnon());
                      }
                   }
                    if("0".equals(anon)){
                        value = "匿名"+value;
                    }
                    tbAnswerOpt.setVoter(value);
                    optList.add(tbAnswerOpt);
                }
            }
            if(object2String(stringObjectMap.get("type")).equals("3") || object2String(stringObjectMap.get("type")).equals("4")){
                TbAnswerTxt tbAnswerTxt = new TbAnswerTxt();
                tbAnswerTxt.setQuestionId(object2Integer(stringObjectMap.get("questionId")));
                tbAnswerTxt.setSurveyId(object2Integer(stringObjectMap.get("surveyId")));
                tbAnswerTxt.setResult(object2String(stringObjectMap.get("result")));
                tbAnswerTxt.setCreateTime(new Date());
                String value=uuid;
                String surveyId = (String)stringObjectMap.get("surveyId");
                TbSurvey survey = new TbSurvey();
                survey.setId(Integer.parseInt(surveyId));
                List<TbSurvey> li =  tbSurveyService.queryAll(survey) ;
                TbSurvey survey2 = null ;
                String anon = "1";
                for(TbSurvey getTbSurvey : li ){
                    if(String.valueOf(getTbSurvey.getId()).equals(surveyId)){
                        survey2 = getTbSurvey;
                        anon = String.valueOf(survey2.getAnon());
                    }
                }
                if("0".equals(anon)){
                    value = "匿名"+value;
                }
                tbAnswerTxt.setVoter(value);
                txtList.add(tbAnswerTxt);
            }
        }
        tbSurveyService.submit(optList,txtList);
//        count++;
//        Map<String, Object> ids = list.get(0);
//        Integer id =  Integer.parseInt(ids.get("surveyId").toString());
//        TbSurvey tbSurvey = (TbSurvey) tbSurveyService.selectByState(id,TbSurvey.state_exec);
////        System.out.println(tbSurvey);
////          todo 获取peolple有问题
//        Integer people = tbSurvey.getPeople();
//
//        if (count>=people ){
////            1 进到这里说明投票人数已经够了
////            2 调用关闭投票的方法
//            tbSurveyService.endState(id,tbSurvey.state_exec);
//        }
        return MapControl.getInstance().success().getMap();
    }


    @GetMapping("/query_detail/{id}")
    public String query_detail(@PathVariable("id") Integer id,ModelMap modelMap){
        TbSurvey tbSurvey = tbSurveyService.detail(id);
        TbQuestion tbQuestion = new TbQuestion();
        tbQuestion.setSurveyId(tbSurvey.getId());
        //查询一个问卷中的所有问题及选项
        List<TbQuestion> tbQuestions = tbQuestionService.query(tbQuestion);
        //将问题设置为survey的属性
        tbSurvey.setTbQuestions(tbQuestions);


        //总投票人数
        TbAnswerOpt tbAnswerOpt = new TbAnswerOpt();
        tbAnswerOpt.setSurveyId(id);
        List<TbAnswerOpt> tbAnswerOpts = tbSurveyService.queryTbAnswerOpt(tbAnswerOpt);
        Set<String> set = new HashSet<String>();
        for (TbAnswerOpt opt : tbAnswerOpts) {
            set.add(opt.getVoter());
        }

        for (TbQuestion tbQuestion1 : tbQuestions) {
            for (TbQuestionOpt tbQuestionOpt : tbQuestion1.getOptions()) {
                int num = 0;
                for (TbAnswerOpt opt : tbAnswerOpts) {
                    if(tbQuestionOpt.getId() == opt.getOptId().intValue()){
                        num++;
                    }
                }
                modelMap.addAttribute("optvoter",tbSurveyService.optvoter(tbQuestionOpt.getQuestionId()));
                modelMap.addAttribute("anonS",tbSurvey.getAnon());
                tbQuestionOpt.setNum(num);
            }
        }

        modelMap.addAttribute("survey",tbSurvey);
        modelMap.addAttribute("total",set.size());
        return "pages/survey/query_detail";
    }

    public String object2String(Object object){
        if(object !=null){
            return object+"";
        }else{
            return null;
        }
    }
    public Integer object2Integer(Object object){
        if(object !=null){
            return Integer.parseInt(object+"");
        }else{
            return null;
        }
    }

}
