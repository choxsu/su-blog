package test.choxsu;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import test.choxsu.model.TaxPayExcel;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test1 {
    //当最后处理上传
    private static Boolean isRollback = null;

    public static void main(String[] args) {
        DruidPlugin dp = WebConfig.createDruidPlugin();
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        arp.setDialect(new SqlServerDialect());//添加SqlServer方言
        arp.addMapping("TaxPayExcel", "id", TaxPayExcel.class);
        dp.start();
        arp.start();

        //总共运行多少线程
        int poolCount = 20;
        //监控主线程，用来等待
        CountDownLatch mainMonitor = new CountDownLatch(1);
        //监控全部子线程，批量提交
        CountDownLatch childMonitor = new CountDownLatch(poolCount);

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < poolCount; i++) {
            executorService.execute(() -> {
                List<TaxPayExcel> records = new ArrayList<>();
                for (int j = 0; j < 10; j++) {
                    TaxPayExcel t = new TaxPayExcel();
                    t.setId(UUID.randomUUID().toString());
                    records.add(t);
                }
                boolean tx = Db.tx(Connection.TRANSACTION_REPEATABLE_READ, () -> {
                    try {
                        int[] nums = Db.batchSave(records, records.size());
                    } catch (Throwable e) {
                        System.out.println(e.getMessage());
                        isRollback = false;
                    } finally {
                        //等待其他的子线程执行完成，一起执行主线程的判断
                        childMonitor.countDown();
                    }
                    try {
                        //等待主线程的判断逻辑执行完，执行下面的是否回滚逻辑
                        System.out.println(Thread.currentThread().getName() + ":等待主线程释放");
                        mainMonitor.await();
                        System.out.println(Thread.currentThread().getName() + ":已释放");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return isRollback;
                });
            });
        }
        try {
            //监测全部子线程的执行完毕
            childMonitor.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //根据返回结果来确定是否回滚
        if (isRollback == null) {
            isRollback = true;
        }
        System.out.println("主线程释放:结果" + isRollback);
        //全部子线程开始执行
        mainMonitor.countDown();
        //执行完关闭线程池
        executorService.shutdown();

    }
}
