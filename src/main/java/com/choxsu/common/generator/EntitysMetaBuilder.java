package com.choxsu.common.generator;


import com.choxsu.common.go.ColumnMeta;
import com.choxsu.common.go.MetaBuilder;
import com.choxsu.common.go.TableMeta;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;

/**
 * @author chox su
 * @date 2018/01/15 11:20
 */
public class EntitysMetaBuilder extends MetaBuilder {

    String[] noFilterTables = new String[]{"blog"};

    public EntitysMetaBuilder(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected boolean isSkipTable(String tableName) {
        if (Arrays.asList(noFilterTables).contains(tableName)) {
            return false;
        }
        return true;
    }


    @Override
    protected void buildColumnMetas(TableMeta tableMeta) throws SQLException {
        String sql = dialect.forTableBuilderDoBuild(tableMeta.name);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();

        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            ColumnMeta cm = new ColumnMeta();
            cm.name = rsmd.getColumnName(i);

            String typeStr = null;
            if (dialect.isKeepByteAndShort()) {
                int type = rsmd.getColumnType(i);
                if (type == Types.TINYINT) {
                    typeStr = "java.lang.Byte";
                } else if (type == Types.SMALLINT) {
                    typeStr = "java.lang.Short";
                }
            }

            if (typeStr == null) {
                String colClassName = rsmd.getColumnClassName(i);
                typeStr = typeMapping.getType(colClassName);
            }

            if (typeStr == null) {
                int type = rsmd.getColumnType(i);
                if (type == Types.BINARY || type == Types.VARBINARY || type == Types.LONGVARBINARY || type == Types.BLOB) {
                    typeStr = "byte[]";
                } else if (type == Types.CLOB || type == Types.NCLOB) {
                    typeStr = "java.lang.String";
                }
                // 支持 oracle 的 TIMESTAMP、DATE 字段类型，其中 Types.DATE 值并不会出现
                // 保留对 Types.DATE 的判断，一是为了逻辑上的正确性、完备性，二是其它类型的数据库可能用得着
                else if (type == Types.TIMESTAMP || type == Types.DATE) {
                    typeStr = "java.util.Date";
                } else {
                    typeStr = "java.lang.String";
                }
            }
            cm.javaType = typeStr;

            // 构造字段对应的属性名 attrName
            cm.attrName = buildAttrName(cm.name);

            tableMeta.columnMetas.add(cm);
        }

        rs.close();
        stm.close();
    }
}
