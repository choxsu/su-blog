package com.choxsu._admin.enhancer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author choxsu
 * @date 2018/12/26
 */
@Getter
@Setter
public class EnhancerShowVo {

    /**
     * 缓存名称
     */
    private String cacheName;
    /**
     * keys
     */
    private List keys;


}
