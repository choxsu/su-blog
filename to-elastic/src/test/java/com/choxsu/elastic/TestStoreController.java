package com.choxsu.elastic;

import com.choxsu.elastic.service.IStoreService;
import com.jfinal.plugin.activerecord.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author chox su
 * @date 2018/03/01 12:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestStoreController {

    @Resource(name = "storeService")
    private IStoreService storeService;

    @Test
    public void query() {
        Page<Map<String, Object>> mapPage = storeService.queryStoreList("", 1, 10);
        System.out.println("total:" + mapPage.getTotalRow());
    }
}
