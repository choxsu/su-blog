package com.choxsu._admin.sensitive_word;

import com.choxsu.common.base.BaseService;
import com.choxsu.common.entity.Account;
import com.choxsu.common.entity.SensitiveWords;
import com.choxsu.common.interceptor.AuthCacheClearInterceptor;
import com.choxsu.kit.PinyinKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * @author choxsu
 * @date 2018/12/26
 */
public class SensitiveWordAdminService extends BaseService<SensitiveWords> {

    @Override
    public String getTableName() {
        return SensitiveWords.tableName;
    }

    public Page<SensitiveWords> list(Integer p, Integer size) {
        return paginate(p, size);
    }

    public Ret saveOrUpdate(SensitiveWords sensitiveWords) {

        if (sensitiveWords == null) {
            return Ret.fail().set("msg", "请输入必要的参数");
        }
        if (sensitiveWords.getId() != null && sensitiveWords.getId() > 0) {//更新
            //word 文字是否已经存在
            if (isExists(sensitiveWords.getWord(), sensitiveWords.getId())) {
                return Ret.fail().set("msg", "敏感文字已经存在");
            } else {
                sensitiveWords.setWordPinyin(PinyinKit.hanyuToPinyin(sensitiveWords.getWord(), true, true));
            }
            boolean update = sensitiveWords.update();
            if (update) {
                return Ret.ok().set("msg", "更新成功");
            } else {
                return Ret.fail().set("msg", "修改失败，请查证！");
            }
        } else {//保存
            if (isExists(sensitiveWords.getWord())) {
                return Ret.fail().set("msg", "敏感文字已经存在");
            }
            sensitiveWords.setWordPinyin(PinyinKit.hanyuToPinyin(sensitiveWords.getWord(), true, true));
            sensitiveWords.setStatus(1);
            return sensitiveWords.save() ? Ret.ok().set("msg", "保存成功") : Ret.fail().set("msg", "保存失败");
        }
    }

    /**
     * 新增判断
     *
     * @param word 文字
     * @return
     */
    private boolean isExists(String word) {
        if (word == null) {
            return false;
        }
        Long r = Db.queryLong("select count(0) from sensitive_words where word = ?", word.trim());
        return r > 0;
    }

    /**
     * 更新判断
     *
     * @param word 文字
     * @return
     */
    private boolean isExists(String word, int id) {
        if (word == null) {
            return false;
        }
        Long r = Db.queryLong("select count(0) from sensitive_words where word = ? and id != ?", word.trim(), id);
        return r > 0;
    }

    /**
     * 敏感字转换拼音
     *
     * @param loginAccount 登录用户
     * @return
     */
    public Ret exchange(Account loginAccount) {

        boolean admin = AuthCacheClearInterceptor.isAdmin(loginAccount);
        if (!admin) {
            return Ret.fail().set("msg", "只有管理员才有操作权限");
        }
        int i = 0, j = 0;
        List<SensitiveWords> sensitiveWords = DAO.find("select * from " + getTableName() + " where word_pinyin = '' ");
        if (sensitiveWords.size() > 0) {
            for (SensitiveWords sensitiveWord : sensitiveWords) {
                sensitiveWord.setWordPinyin(PinyinKit.hanyuToPinyin(sensitiveWord.getWord(), true, true));
                boolean update = sensitiveWord.update();
                if (update) {
                    i++;
                } else {
                    j++;
                }
            }
        }
        String msg = "转换成功条数：" + i + ",转换失败条数：" + j;
        return Ret.ok().set("msg", msg);
    }
}
