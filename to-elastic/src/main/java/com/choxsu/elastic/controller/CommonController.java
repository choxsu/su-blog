package com.choxsu.elastic.controller;

import com.choxsu.elastic.service.CommonService;
import com.choxsu.elastic.util.RetKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author chox su
 * @date 2017/12/23 16:53
 */
@RestController
@RequestMapping(value = "/common", produces = "application/json;charset=UTF-8")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @ApiOperation(value = "分页查询公共方法")
    @RequestMapping(value = "/commonMethod", method = RequestMethod.POST)
    public Object commonMethod(@RequestBody LinkedHashMap<String, Object> params) {
        params.keySet().forEach(key -> System.out.println("key:" + key+"；value:" + params.get(key)));
        Map resultMap = new LinkedHashMap();
        try {
            Method methodCount = CommonService.class.getMethod(params.get("method") + "Count", new Class[]{LinkedHashMap.class});
            Method methodList = CommonService.class.getMethod(params.get("method") + "List", new Class[]{LinkedHashMap.class});

            Map mapCount = (Map)methodCount.invoke(commonService, params);
            List<Record> listMap = (List<Record>) methodList.invoke(commonService, params);

            resultMap.put("total", mapCount.get("total"));
            resultMap.put("list", listMap);
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail().set("msg", e.getMessage());
        }

        return RetKit.ok().set("data", resultMap).set("msg", "查询成功！");
    }

}
