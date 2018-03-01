package com.choxsu.elastic.controller;

import com.choxsu.elastic.service.IStoreService;
import com.choxsu.elastic.service.impl.StoreService;
import com.choxsu.elastic.service.impl.StoreService2;
import com.choxsu.elastic.util.RetKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author chox su
 * @date 2017/12/21 11:22
 */
@RestController
@RequestMapping(value = "/elastic", produces = "application/json;charset=UTF-8")
public class ElasticController {

    @Resource(name = "storeService")
    private IStoreService storeService;

    @Autowired
    @Qualifier("storeService2")
    private IStoreService storeService2;

    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(String name, Integer age, String sex, String introduce, Date birthday) {


        return null;
    }

    @ApiOperation(value = "修改更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(String id, String name, int age, String sex, String introduce, Date birthday) {
        if (StringUtils.isEmpty(id)) {
            return RetKit.fail().set("msg", "id不能为空");
        }


        return null;
    }

    @ApiOperation(value = "通过id查询")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public Object getFind(String id) {

        if (StringUtils.isEmpty(id)) {
            return RetKit.fail().set("msg", "id不能为空");
        }


        return null;

    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object delete(String id) {
        return null;
    }

    @ApiOperation(value = "模糊查询")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Object query(String keyword, Integer page, Integer size) {

        return null;
    }

    @ApiOperation(value = "activePlugin查询")
    @RequestMapping(value = "/activePlugin", method = RequestMethod.GET)
    public Object activePlugin() {
        String save = storeService.save();
        System.out.println("id:" + save);

        Object o = storeService.find(save);

        return Ret.ok().set("data", o).set("msg", "查询成功");
    }

    @ApiOperation(value = "storeList查询")
    @GetMapping(value = "/storeList")
    public Object storeList(Integer page, Integer size, String keywords) {
        page = page == null ? 1 : page;
        size = size == null ? 100 : size;
        Page mapPage = storeService.queryStoreList(keywords, page, size);
        return RetKit.ok().set("data", mapPage).set("msg", "查询成功！");
    }

    @ApiOperation(value = "storeList查询2")
    @GetMapping(value = "/storeList2")
    public Object storeList2(Integer page, Integer size, String keywords) {
        page = page == null ? 1 : page;
        size = size == null ? 100 : size;
        Page mapPage = storeService2.queryStoreList(keywords, page, size);
        return RetKit.ok().set("data", mapPage).set("msg", "查询成功！");
    }


}
