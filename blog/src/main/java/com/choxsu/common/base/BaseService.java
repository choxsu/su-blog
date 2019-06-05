package com.choxsu.common.base;

import com.choxsu.utils.kit.ClassKits;
import com.choxsu.utils.kit.SqlKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author choxsu
 */
public abstract class BaseService<M extends Model<M>> {

    public M DAO;
    protected String tableName;

    public BaseService() {
        Class<M> modelClass = null;
        Type t = ClassKits.getUsefulClass(getClass()).getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            modelClass = (Class<M>) p[0];
        }
        if (modelClass == null) {
            throw new RuntimeException("can not get parameterizedType in BaseService");
        }
        DAO = ClassKits.newInstance(modelClass).dao();
        Table table = TableMapping.me().getTable(DAO.getClass());
        tableName = table.getName();
    }

    public M getDao() {
        return DAO;
    }

    /**
     * 根据ID查找model
     *
     * @param id
     * @return
     */
    public M findById(Object id) {
        return DAO.findById(id);
    }


    /**
     * 查找全部数据
     *
     * @return
     */
    public List<M> findAll() {
        String sql = "select * from " + tableName;
        return DAO.find(sql);
    }

    /**
     * 根据ID 删除model
     *
     * @param id
     * @return
     */
    public boolean deleteById(Object id) {
        M m = findById(id);
        return m != null && m.delete();
    }

    /**
     * 删除
     *
     * @param model
     * @return
     */
    public boolean delete(M model) {
        return model.delete();
    }

    /**
     * 保存到数据库
     *
     * @param model
     * @return
     */
    public boolean save(M model) {
        return model.save();
    }

    /**
     * 更新
     *
     * @param model
     * @return
     */
    public boolean update(M model) {
        return model.update();
    }


    public void keep(Model model, String... attrs) {
        if (model == null) {
            return;
        }

        model.keep(attrs);
    }

    public void keep(List<? extends Model> models, String... attrs) {
        if (models != null && !models.isEmpty()) {
            for (Model m : models) {
                keep(m, attrs);
            }
        }
    }

    public Page<M> paginate(Integer page, Integer size) {
        String select = "select * ";
        return DAO.paginate(page, size, select, "from " + tableName);
    }


    /**
     * @param page     当前页
     * @param size     每页条数
     * @param column   排序的字段
     * @param sortColumn   返回的字段
     * @param sortEnum 类型
     * @return
     */
    public Page<M> paginateOrderBy(Integer page, Integer size, String column, String sortColumn, SortEnum sortEnum) {

        if (StrKit.isBlank(sortColumn)) {
            throw new RuntimeException("column can not be null");
        }
        if (sortEnum == null) {
            throw new RuntimeException("sortEnum can not be null");
        }

        StringBuffer sql = new StringBuffer();
        sql.append("from ").append(tableName);

        sql.append(" order by ").append(sortColumn).append("\t");
        sql.append(sortEnum.toString().toLowerCase());
        if (StrKit.isBlank(column)){
            column = "*";
        }
        return DAO.paginate(page, size, "select " + column, sql.toString());
    }

}
