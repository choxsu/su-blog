package com.choxsu.common.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;
/**
 * @author choxsu, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSensitiveWords<M extends BaseSensitiveWords<M>> extends Model<M> implements IBean {

    /**
     * 该字段暂无注释
     */
    private java.lang.String id = "id";
    /**
     * 该字段暂无注释
     */
    private java.lang.String word = "word";
    /**
     * 该字段暂无注释
     */
    private java.lang.String status = "status";
    /**
     * 该字段暂无注释
     */
    private java.lang.String wordPinyin = "word_pinyin";


	public void setId(java.lang.Integer id) {
		set(this.id, id);
	}
	

	public java.lang.Integer getId() {
		return getInt(id);
	}


	public void setWord(java.lang.String word) {
		set(this.word, word);
	}
	

	public java.lang.String getWord() {
		return getStr(word);
	}


	public void setStatus(java.lang.Integer status) {
		set(this.status, status);
	}
	

	public java.lang.Integer getStatus() {
		return getInt(status);
	}


	public void setWordPinyin(java.lang.String wordPinyin) {
		set(this.wordPinyin, wordPinyin);
	}
	

	public java.lang.String getWordPinyin() {
		return getStr(wordPinyin);
	}

}
