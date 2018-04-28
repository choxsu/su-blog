package com.choxsu.common.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.choxsu.common.base.db.Column;
import com.choxsu.common.base.db.Columns;
import com.choxsu.common.base.dialect.IBaseModelDialect;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author choxsu
 */
public class BaseModel<M extends BaseModel<M>> extends Model<M> {


    /**
     * 更新或者保存
     * 有主键就更新，没有就保存
     *
     * @return
     */
    public boolean saveOrUpdate() {
        if (null == get(getPrimaryKey())) {
            return this.save();
        }
        return this.update();
    }

    /**
     * 复制一个新的model
     * 主要是用在 从缓存取出数据的时候，如果直接修改，在ehcache会抛异常
     * 如果要对model进行修改，可以先copy一份新的，然后再修改
     *
     * @return
     */
    public M copy() {
        M m = null;
        try {
            m = (M) _getUsefulClass().newInstance();
            m.put(_getAttrs());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return m;
    }


    private transient Table table;

    @JSONField(serialize = false)
    protected String getTableName() {
        if (table == null) {
            table = _getTable();
            if (table == null) {
                throw new ActiveRecordException("表不存在");
            }
        }
        return table.getName();
    }

    private transient String primaryKey;


    @JSONField(serialize = false)
    protected String getPrimaryKey() {
        if (primaryKey != null) {
            return primaryKey;
        }
        String[] primaryKeys = _getTable().getPrimaryKey();
        if (null != primaryKeys && primaryKeys.length == 1) {
            primaryKey = primaryKeys[0];
        }

        assert primaryKey != null;

        return primaryKey;
    }

    private IBaseModelDialect getDialect() {
        return (IBaseModelDialect) _getConfig().getDialect();
    }


    /**
     * 根据列名和值，查找1条数据
     *
     * @param column
     * @param value
     * @return
     */
    public M findFirstByColumn(String column, Object value) {
        String sql = getDialect().forFindByColumns(getTableName(), "*", Columns.create(column, value).getList(), null, 1);
        return findFirst(sql, value);
    }

    /**
     * 根据 列和值 查询1条数据
     *
     * @param column
     * @return
     */
    public M findFirstByColumn(Column column) {
        String sql = getDialect().forFindByColumns(getTableName(), "*", Columns.create(column).getList(), null, 1);
        return findFirst(sql, column.getValue());
    }

    /**
     * 根据 多列和值，查询1条数据
     *
     * @param columns
     * @return
     */
    public M findFirstByColumns(Columns columns) {
        String sql = getDialect().forFindByColumns(getTableName(), "*", columns.getList(), null, 1);
        LinkedList<Object> params = new LinkedList<Object>();
        List<Column> list = columns.getList();
        if (list != null && !list.isEmpty()) {
            for (Column column : columns.getList()) {
                params.add(column.getValue());
            }
        }
        return findFirst(sql, params.toArray());
    }


    /**
     * 查找全部数据
     *
     * @return
     */
    public List<M> findAll() {
        String sql = getDialect().forFindByColumns(getTableName(), "*", null, null, null);
        return find(sql);
    }

    /**
     * 根据列名和值 查询一个列表
     *
     * @param column
     * @param value
     * @param count  最多查询多少条数据
     * @return
     */
    public List<M> findListByColumn(String column, Object value, Integer count) {
        List<Column> columns = new ArrayList<>();
        columns.add(Column.create(column, value));
        return findListByColumns(columns, count);
    }

    /**
     * 根据 列信息 查找数据列表
     *
     * @param column
     * @param count
     * @return
     */
    public List<M> findListByColumn(Column column, Integer count) {
        return findListByColumns(Columns.create(column).getList(), count);
    }


    public List<M> findListByColumn(String column, Object value) {
        return findListByColumn(column, value, null);
    }

    public List<M> findListByColumn(Column column) {
        return findListByColumn(column, null);
    }

    public List<M> findListByColumns(List<Column> columns) {
        return findListByColumns(columns, null, null);
    }

    public List<M> findListByColumns(List<Column> columns, String orderBy) {
        return findListByColumns(columns, orderBy, null);
    }

    public List<M> findListByColumns(List<Column> columns, Integer count) {
        return findListByColumns(columns, null, count);
    }


    public List<M> findListByColumns(Columns columns) {
        return findListByColumns(columns.getList());
    }

    public List<M> findListByColumns(Columns columns, String orderBy) {
        return findListByColumns(columns.getList(), orderBy);
    }

    public List<M> findListByColumns(Columns columns, Integer count) {
        return findListByColumns(columns.getList(), count);
    }


    public List<M> findListByColumns(Columns columns, String orderBy, Integer count) {
        return findListByColumns(columns.getList(), orderBy, count);
    }


    /**
     * 根据列信心查询列表
     *
     * @param columns
     * @param orderBy
     * @param count
     * @return
     */
    public List<M> findListByColumns(List<Column> columns, String orderBy, Integer count) {
        LinkedList<Object> params = new LinkedList<>();

        if (columns != null && !columns.isEmpty()) {
            for (Column column : columns) {
                params.add(column.getValue());
            }
        }

        String sql = getDialect().forFindByColumns(getTableName(), "*", columns, orderBy, count);
        return params.isEmpty() ? find(sql) : find(sql, params.toArray());
    }


    /**
     * 分页查询数据
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<M> paginate(int pageNumber, int pageSize, String orderBy) {
        return paginateByColumns(pageNumber, pageSize, null, orderBy);
    }


    /**
     * 根据某列信息，分页查询数据
     *
     * @param pageNumber
     * @param pageSize
     * @param column
     * @return
     */
    public Page<M> paginateByColumn(int pageNumber, int pageSize, Column column) {
        return paginateByColumns(pageNumber, pageSize, Columns.create(column).getList(), null);
    }


    /**
     * 根据某列信息，分页查询数据
     *
     * @param pageNumber
     * @param pageSize
     * @param column
     * @return
     */
    public Page<M> paginateByColumn(int pageNumber, int pageSize, Column column, String orderBy) {
        return paginateByColumns(pageNumber, pageSize, Columns.create(column).getList(), orderBy);
    }


    /**
     * 根据列信息，分页查询数据
     *
     * @param pageNumber
     * @param pageSize
     * @param columns
     * @return
     */
    public Page<M> paginateByColumns(int pageNumber, int pageSize, List<Column> columns) {
        return paginateByColumns(pageNumber, pageSize, columns, null);
    }


    /**
     * 根据列信息，分页查询数据
     *
     * @param pageNumber
     * @param pageSize
     * @param columns
     * @param orderBy
     * @return
     */
    public Page<M> paginateByColumns(int pageNumber, int pageSize, List<Column> columns, String orderBy) {
        String selectPartSql = getDialect().forPaginateSelect("*");
        String fromPartSql = getDialect().forPaginateFrom(getTableName(), columns, orderBy);

        LinkedList<Object> params = new LinkedList<Object>();

        if (columns != null && !columns.isEmpty()) {
            for (Column column : columns) {
                params.add(column.getValue());
            }
        }
        return params.isEmpty() ? paginate(pageNumber, pageSize, selectPartSql, fromPartSql)
                : paginate(pageNumber, pageSize, selectPartSql, fromPartSql, params.toArray());
    }

    // ######################## Override begin ##################################

    @Override
    public List<M> find(String sql, Object... paras) {
        debugPrintParas(paras);
        return super.find(sql, paras);
    }

    @Override
    public M findFirst(String sql, Object... paras) {
        debugPrintParas(paras);
        return super.findFirst(sql, paras);
    }

    // ######################## Override end ##################################


    private void debugPrintParas(Object... objects) {
        if (JFinal.me().getConstants().getDevMode()) {
            System.out.println("\r\n---------------Paras: " + Arrays.toString(objects) + "----------------");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof BaseModel)) {
            return false;
        }

        Object id = ((BaseModel) o).get(getPrimaryKey());
        if (id == null) {
            return false;
        }

        return id.equals(get(getPrimaryKey()));
    }

}
