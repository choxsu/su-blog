package com.choxsu._admin.reply;

import com.choxsu.common.base.BaseService;
import com.choxsu.common.entity.BlogReply;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;

public class ReplyService extends BaseService<BlogReply> {


    public Page<BlogReply> findAllReply(Integer p) {
        Kv kv = Kv.create();

        return DAO.template("reply.findAllReply", kv).paginate(p, 10);
    }
}
