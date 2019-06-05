package com.choxsu._front.article;

import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Account;
import com.choxsu.common.entity.Blog;
import com.choxsu.common.pageview.AddClickInterceptor;
import com.choxsu.common.safe.JsoupFilter;
import com.choxsu.common.safe.RestTime;
import com.choxsu.utils.kit.SensitiveWordsKit;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.paragetter.Para;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;

/**
 * @author choxsu
 */
@Before(ArticleSEO.class)
public class ArticleController extends BaseController {

    @Inject
    ArticleService articleService;

    @Inject
    ArticleReplyService articleReplySrv;

    public void index() {
        renderError(404);
    }

    @Before(AddClickInterceptor.class)
    public void detail() {
        Blog article = articleService.findBlog(getParaToInt());
        if (article != null) {
            setAttr("blog", article);
            setAttr("replyPage", articleReplySrv.getReplyPage(article.getId(), getInt("p", 1)));
            render("detail.html");
        } else {
            renderError(404);
        }
    }

    /**
     * 回复
     */
    public void saveReply(Integer articleId) {
        if (notLogin()) {
            renderJson(Ret.fail("msg", "登录后才可以评论").set("toLogin", true));
            return;
        }
        String restTimeMsg = RestTime.checkRestTime(getLoginAccount());
        if (restTimeMsg != null) {
            renderJson(Ret.fail("msg", restTimeMsg));
            return;
        }
        String replyContent = get("replyContent");
        if (StrKit.isBlank(replyContent)) {
            renderJson(Ret.fail("msg", "回复内容不能为空"));
            return;
        }
        // 防止xss攻击
        replyContent = JsoupFilter.getSimpleHtml(replyContent);
        if (SensitiveWordsKit.checkSensitiveWord(replyContent) != null) {
            renderJson(Ret.fail("msg", "回复内容不能包含敏感词"));
            return;
        }
        Ret ret = articleService.saveReply(articleId, getLoginAccountId(), replyContent);
        if (ret.isFail()){
            renderJson(ret);
            return;
        }
        // 注入 nickName 与 avatar 便于 renderToString 生成 replyItem html 片段
        Account loginAccount = getLoginAccount();
        ret.set("loginAccount", loginAccount);
        // 用模板引擎生成 HTML 片段 replyItem
        String replyItem = renderToString("_reply_item.html", ret);
        ret.set("replyItem", replyItem);
        renderJson(ret);
    }

    /**
     * 删除回复
     */
    public void deleteReply(Integer id) {
        if (isLogin()) {
            int accountId = getLoginAccountId();
            int replyId = id;
            articleService.deleteReplyById(accountId, replyId);
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail("msg", "未登录用户不会显示删除链接，请勿手工制造请求"));
        }
    }
}
