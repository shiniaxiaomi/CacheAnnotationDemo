package com.lyj.demo.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    Test test;

    @RequestMapping("test")
    @ResponseBody
    public String test(int id,String name){
        User user = test.select(id, name);
        System.out.println(user);
        return "chenggong";
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(int id){
        test.delete(id);
        return "chenggong";
    }

    @RequestMapping("save")
    @ResponseBody
    public String save(int id,String name){
        User user = test.save(id, name);
        System.out.println(user);
        return "chenggong";
    }

    @RequestMapping("test2")
    @ResponseBody
    public String test2(int id,String name){
        User user = test.test2(id, name);
        System.out.println(user);
        return "chenggong";
    }
}
