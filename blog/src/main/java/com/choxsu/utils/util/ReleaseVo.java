package com.choxsu.utils.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author choxsu
 * @date 2019/01/09
 */
@Getter
@Setter
public class ReleaseVo implements java.io.Serializable{

    private int num;
    private int curNum;
    private int intervalSecond;
    private int nextTime;

}
