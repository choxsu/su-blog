package com.syc.api.controller.blog;

import com.syc.api.controller.common.BaseApiController;
import com.syc.model.result.Result;
import com.syc.service.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;

@CrossOrigin
@RestController
@RequestMapping(value = "/index/api")
public class ApiIndexController extends BaseApiController {


    @Autowired
    @Qualifier("indexService")
    private IndexService indexService;

    /**
     * @api {get} /index/api/v1/list #1、首页列表数据带分页
     * @apiName 首页列表
     * @apiGroup 首页相关接口
     * @apiDescription 首页列表数据带分页,返回了所有的相关博客列表数据
     *
     * @apiParam {token} token 登录token信息
     *
     * @apiSuccess {int} code   提示代码 -1->权限不足 0->失败 1->成功
     * @apiSuccess {String} msg 提示信息
     * @apiSuccess {Object} data
     *
     */
    @RequestMapping(value = "/v1/list", method = RequestMethod.GET)
    public Result index(String token){

        return Result.ok().setData(token);
    }
}
