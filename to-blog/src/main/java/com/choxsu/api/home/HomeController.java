package com.choxsu.api.home;

import com.choxsu.common.BaseController;
import com.choxsu.common.es.CommonUtil;
import com.choxsu.common.es.EsPlugin;
import com.choxsu.common.kit.PgBeanKit;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Page;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author chox su
 * @date 2018/03/02 10:21
 */
@Slf4j
public class HomeController extends BaseController {

    //    private CommonUtil commonUtil = CommonUtil.newInstance();
    private TransportClient client = EsPlugin.getClient();

    private HomeService homeService = Enhancer.enhance(HomeService.class);

    public void index() {
        long begin = System.currentTimeMillis();
        System.out.println("===========================处理业务开始：" + begin + "=================================");
        int page = getParaToInt("page", 1);
        int size = getParaToInt("size", 15);
        Page<Map<String, Object>> pageResult = null;
        //no do thing
        pageResult = PgBeanKit.getPage(page, size);
        long end = System.currentTimeMillis();
        System.out.println("===========================处理业务结束：" + end + "===========================");
        System.out.println("===========================用时：" + (end - begin) + "ms ===========================");
        renderJson(respSuccess("查询成功", pageResult));
    }

//    /**
//     * 单例模式 暂时不用
//     */
//    public void singleMode() {
//        long begin = System.currentTimeMillis();
//        System.out.println("===========================处理业务开始：" + begin + "=================================");
//        int page = getParaToInt("page", 1);
//        int size = getParaToInt("size", 15);
//        Page<Map<String, Object>> pageResult = null;
//        try {
//            TransportClient client = commonUtil.getClient();
//            pageResult = PgBeanKit.getPage(page, size);
//        } catch (UnknownHostException e) {
//            log.error(e.getMessage(), e);
//            renderJson(respFail(e.getMessage()));
//            return;
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("===========================处理业务结束：" + end + "===========================");
//        System.out.println("===========================用时：" + (end - begin) + "ms ===========================");
//        renderJson(respSuccess("查询成功", pageResult));
//    }

    /**
     * 插件模式
     */
    public void pluginMode() {
        long begin = System.currentTimeMillis();
        System.out.println("===========================处理业务开始：" + begin + "=================================");
        int page = getParaToInt("page", 1);
        int size = getParaToInt("size", 15);
        String keyword = getPara("keyword");
        Page<Map<String, Object>> storePage = homeService.getStorePage(keyword, page, size);
        long end = System.currentTimeMillis();
        System.out.println("===========================处理业务结束：" + end + "===========================");
        System.out.println("===========================用时：" + (end - begin) + "ms ===========================");
        renderJson(respSuccess("查询成功", storePage));
    }

}
