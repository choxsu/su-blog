package com.choxsu.elastic.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.choxsu.elastic.entity.Person;
import com.choxsu.elastic.service.PersonService;
import com.choxsu.elastic.service.StoreService;
import com.choxsu.elastic.util.RetKit;
import com.jfinal.aop.Enhancer;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author chox su
 * @date 2017/12/21 11:22
 */
@RestController
@RequestMapping(value = "/elastic", produces = "application/json;charset=UTF-8")
public class ElasticController {

    @Autowired
    private PersonService personService;

    @Autowired
    private StoreService storeService;

    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(String name, Integer age, String sex, String introduce, Date birthday) {

        Person person = new Person(name, age, sex, birthday, introduce);

        String s = personService.savePerson(person);

        if (StringUtils.hasText(s)) {
            return RetKit.ok().set("data", s).set("msg", "保存成功！");
        }

        return RetKit.fail().set("data", "").set("msg", "保存失败！");
    }

    @ApiOperation(value = "修改更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(String id, String name, int age, String sex, String introduce, Date birthday) {
        if (StringUtils.isEmpty(id)) {
            return RetKit.fail().set("msg", "id不能为空");
        }

        Person person = new Person(name, age, sex, birthday, introduce);

        person.setId(id);

        String s = personService.updatePerson(person);
        if (StringUtils.hasText(s)) {
            return RetKit.ok().set("data", s).set("msg", "修改成功！");
        }
        return RetKit.fail().set("data", "").set("msg", "修改失败！");
    }

    @ApiOperation(value = "通过id查询")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public Object getFind(String id) {

        if (StringUtils.isEmpty(id)) {
            return RetKit.fail().set("msg", "id不能为空");
        }

        Object person = personService.findPerson(id);

        //Person resultPerson = JSONObject.parseObject(JSON.toJSONString(person), Person.class);
        return RetKit.ok().set("data", person).set("msg", "查询成功！");

    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object delete(String id) {
        if (StringUtils.isEmpty(id)) {
            return RetKit.fail().set("msg", "id不能为空");
        }
        String s = personService.delPerson(id);
        return RetKit.ok().set("data", s).set("msg", "删除成功！");
    }

    @ApiOperation(value = "模糊查询")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Object query(String keyword, Integer page, Integer size) {

        page = page == null ? 1 : page;
        size = size == null ? 20 : size;

        Object obj = personService.queryPerson(keyword, page, size);

        List<Person> list = JSONObject.parseArray(JSON.toJSONString(obj), Person.class);
        return RetKit.ok().set("data", list).set("msg", "查询成功！");
    }

    @ApiOperation(value = "activePlugin查询")
    @RequestMapping(value = "/activePlugin", method = RequestMethod.GET)
    public Object activePlugin(){
        String save = storeService.save();
        System.out.println("id:"+save);

        Object o = storeService.find(save);

        return Ret.ok().set("data",o).set("msg", "查询成功");
    }


}
