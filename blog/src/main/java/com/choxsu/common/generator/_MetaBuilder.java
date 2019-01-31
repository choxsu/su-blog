package com.choxsu.common.generator;


import com.jfinal.plugin.activerecord.generator.MetaBuilder;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author chox su
 * @date 2018/01/15 11:20
 */
public class _MetaBuilder extends MetaBuilder {

    String[] noFilterTables = new String[]{"blog"};
    String[] filterTables = new String[]{};
    //new String[]{"gift_free_merchant", "gift_free_store", "gift_free_merchant_record", "orders", "order_opt_time", "gift_users", "exp_operation_rules", "sm_merchant_store_shop_payaccount_adjustlog"};

    public _MetaBuilder(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 返回 true 时将跳过当前 tableName 的处理
     *
     * @param tableName
     * @return
     */
    @Override
    protected boolean isSkipTable(String tableName) {
//        if (tableName.startsWith("sm_")){
//            return false;
//        }

//        if (tableName.startsWith("zgxl_free_sm_coupon_")) {
//            return false;
//        }

        if (Arrays.asList(noFilterTables).contains(tableName)) {
            return false;
        }

        if (Arrays.asList(filterTables).contains(tableName)){
            return true;
        }

        return true;
    }

}
