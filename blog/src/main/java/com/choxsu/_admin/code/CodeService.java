package com.choxsu._admin.code;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class CodeService {

    public Page<Record> getTables(int p, int size) throws Exception{
        Connection connection = DbKit.getConfig().getDataSource().getConnection();
        String database = connection.getCatalog();
        String select = "SELECT " +
                "\tTABLE_SCHEMA AS databaseName,\n" +
                "\tTABLE_NAME AS tableName,\n" +
                "\t`ENGINE` AS engineName,\n" +
                "\tTABLE_ROWS AS rows,\n" +
                "\tCREATE_TIME AS cTime,\n" +
                "\tUPDATE_TIME AS uTime,\n" +
                "\tTABLE_COMMENT AS remark ";
        String from = "FROM information_schema.TABLES WHERE TABLE_SCHEMA= ?";
        return Db.paginate(p, size, select, from, database);
    }

}
