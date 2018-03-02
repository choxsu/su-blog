package com.choxsu.common.generator;


import com.choxsu.common.go.MetaBuilder;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author chox su
 * @date 2018/01/15 11:20
 */
public class _MetaBuilder extends MetaBuilder {

    String[] noFilterTables = new String[]{"blog", "blog_account", "blog_category", "blog_tag", "visitor", "sensitive_words"};

    public _MetaBuilder(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected boolean isSkipTable(String tableName) {
        if (Arrays.asList(noFilterTables).contains(tableName)) {
            return false;
        }
        return true;
    }

}
