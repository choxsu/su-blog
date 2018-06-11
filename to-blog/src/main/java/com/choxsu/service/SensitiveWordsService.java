package com.choxsu.service;

import org.springframework.stereotype.Service;
import com.choxsu.common.entity.*;

/**
 * @author chox su
 * 表: sensitive_words()
 */
@Service
public class SensitiveWordsService {

    private static final SensitiveWords sensitiveWordsDao = new SensitiveWords().dao();

    /**
     * 保存
     */
    public boolean save(SensitiveWords sensitiveWords){
        return sensitiveWords.save();
    }
    /**
     * 更新
     */
    public boolean update(SensitiveWords sensitiveWords){
        return sensitiveWords.update();
    }

     /**
      * 通过主键查找
      */
    public SensitiveWords findOne(Integer id){
        return sensitiveWordsDao.findById(id);
    }

     /**
      * 通过主键删除
      */
    private boolean deleteById(Integer id){
        return sensitiveWordsDao.deleteById(id);
    }


}