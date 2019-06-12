package com.choxsu._admin.reply;

import com.choxsu._admin.permission.Remark;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.BlogReply;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Page;


public class ReplyController extends BaseController {

    @Inject
    ReplyService replyService;

    @Remark("评论列表首页")
    public void index(){

        Page<BlogReply> blogReplyPage = replyService.findAllReply(getInt("p", 1));
        set("pageResult", blogReplyPage);
        render("index.html");
    }


}
