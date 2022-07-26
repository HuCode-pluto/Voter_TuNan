package com.pluto.tnvote.utils;

import com.pluto.tnvote.pojo.User;
import com.pluto.tnvote.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemInit {




  //  public Map<Integer,User> userMap = new HashMap<>(this.init(userMapper));

    public Map<Integer,User> init(UserMapper userMapper){
    	Map<Integer,User> userMap = new HashMap<>();
        List<User> list = userMapper.query(null);
        for (User admin : list) {
            userMap.put(admin.getId(),admin);
        }
        System.out.println("初始化加载数据..."+userMap);
		return userMap;
    }

}
