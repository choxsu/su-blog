package com.choxsu.common.base;

import com.choxsu.kit.ClassKits;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author choxsu
 */
public abstract class BaseService<M extends Model<M>> {

    public M DAO;

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
    }

    public abstract String getTableName();

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
        String sql = "select * from " + getTableName();
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
        return m == null ? false : m.delete();
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

    public void join(Page<? extends Model> page, String joinOnField) {
        join(page.getList(), joinOnField);
    }

    public void join(Page<? extends Model> page, String joinOnField, String[] attrs) {
        join(page.getList(), joinOnField, attrs);
    }


    public void join(List<? extends Model> models, String joinOnField) {
        if (models != null && !models.isEmpty()) {
            for (Model m : models) {
                join(m, joinOnField);
            }
        }
    }


    public void join(List<? extends Model> models, String joinOnField, String[] attrs) {
        if (models != null && !models.isEmpty()) {
            for (Model m : models) {
                join(m, joinOnField, attrs);
            }
        }
    }


    public void join(Page<? extends Model> page, String joinOnField, String joinName) {
        join(page.getList(), joinOnField, joinName);
    }


    public void join(List<? extends Model> models, String joinOnField, String joinName) {
        if (models != null && !models.isEmpty()) {
            for (Model m : models) {
                join(m, joinOnField, joinName);
            }
        }
    }

    public void join(Page<? extends Model> page, String joinOnField, String joinName, String[] attrs) {
        join(page.getList(), joinOnField, joinName, attrs);
    }


    public void join(List<? extends Model> models, String joinOnField, String joinName, String[] attrs) {
        if (models != null && !models.isEmpty()) {
            for (Model m : models) {
                join(m, joinOnField, joinName, attrs);
            }
        }
    }

    /**
     * 添加关联数据到某个model中去，避免关联查询，提高性能。
     *
     * @param model       要添加到的model
     * @param joinOnField model对于的关联字段
     */
    public void join(Model model, String joinOnField) {
        if (model == null)
            return;
        String id = model.getStr(joinOnField);
        if (id == null) {
            return;
        }
        Model m = findById(id);
        if (m != null) {
            model.put(StrKit.firstCharToLowerCase(m.getClass().getSimpleName()), m);
        }
    }

    /**
     * 添加关联数据到某个model中去，避免关联查询，提高性能。
     *
     * @param model
     * @param joinOnField
     * @param attrs
     */
    public void join(Model model, String joinOnField, String[] attrs) {
       /* if (model == null)
            return;
        String id = model.getStr(joinOnField);
        if (id == null) {
            return;
        }
        M m = findById(id);
        if (m != null) {
            m = m.copy();
            m.keep(attrs);
            model.put(StrKit.firstCharToLowerCase(m.getClass().getSimpleName()), m);
        }*/
    }


    /**
     * 添加关联数据到某个model中去，避免关联查询，提高性能。
     *
     * @param model
     * @param joinOnField
     * @param joinName
     */
    public void join(Model model, String joinOnField, String joinName) {
        if (model == null)
            return;
        String id = model.getStr(joinOnField);
        if (id == null) {
            return;
        }
        Model m = findById(id);
        if (m != null) {
            model.put(joinName, m);
        }
    }


    /**
     * 添加关联数据到某个model中去，避免关联查询，提高性能。
     *
     * @param model
     * @param joinOnField
     * @param joinName
     * @param attrs
     */
    public void join(Model model, String joinOnField, String joinName, String[] attrs) {
       /* if (model == null)
            return;
        String id = model.getStr(joinOnField);
        if (id == null) {
            return;
        }
        M m = findById(id);
        if (m != null) {
            m = m.copy();
            m.keep(attrs);
            model.put(joinName, m);
        }*/

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
        return DAO.paginate(page, size, "select * ", "from " + getTableName());
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
        sql.append("from ").append(getTableName());

        sql.append(" order by ").append(sortColumn).append("\t");
        sql.append(sortEnum.toString().toLowerCase());
        if (StrKit.isBlank(column)){
            column = "*";
        }
        return DAO.paginate(page, size, "select " + column, sql.toString());
    }

}
