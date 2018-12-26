package com.choxsu._admin.sensitive_word;

import com.choxsu.common.base.BaseService;
import com.choxsu.common.entity.SensitiveWords;
import com.choxsu.kit.PinyinKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author choxsu
 * @date 2018/12/26
 */
public class SensitiveWordService extends BaseService<SensitiveWords> {

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
                sensitiveWords.setWordPinyin(PinyinKit.hanyuToPinyin(sensitiveWords.getWord()));
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
            sensitiveWords.setWordPinyin(PinyinKit.hanyuToPinyin(sensitiveWords.getWord()));
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

}
