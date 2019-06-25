package com.choxsu.utils.kit;

import com.jfinal.log.Log;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类实例创建者创建者
 *
 * @author michael
 * @date 17/3/21
 */
public class ClassKits {

    public static Log log = Log.getLog(ClassKits.class);
    private static final Map<Class, Object> singletons = new ConcurrentHashMap<>();


    /**
     * 获取单例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T singleton(Class<T> clazz) {
        Object object = singletons.get(clazz);
        if (object == null) {
            synchronized (clazz) {
                object = singletons.get(clazz);
                if (object == null) {
                    object = newInstance(clazz);
                    if (object != null) {
                        singletons.put(clazz, object);
                    } else {
                        Log.getLog(clazz).error("cannot new newInstance!!!!");
                    }

                }
            }
        }

        return (T) object;
    }

    /**
     * 创建新的实例
     *
     * @param <T>
     * @param clazz
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        return newInstance(clazz, false);
    }


    public static <T> T newInstance(Class<T> clazz, boolean createdByGuice) {
        if (createdByGuice) {
            //TODO
            return null;
        } else {
            try {
                Constructor constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                return (T) constructor.newInstance();
            } catch (Exception e) {
                log.error("can not newInstance class:" + clazz + "\n" + e.toString(), e);
            }

            return null;
        }
    }

    /**
     * 创建新的实例
     *
     * @param <T>
     * @param clazzName
     * @return
     */
    public static <T> T newInstance(String clazzName) {
        try {
            Class<T> clazz = (Class<T>) Class.forName(clazzName, false, Thread.currentThread().getContextClassLoader());
            return newInstance(clazz);
        } catch (Exception e) {
            log.error("can not newInstance class:" + clazzName + "\n" + e.toString(), e);
        }

        return null;
    }


    public static Class<?> getUsefulClass(Class<?> clazz) {
        //ControllerTest$ServiceTest$$EnhancerByGuice$$40471411#hello
        //com.demo.blog.Blog$$EnhancerByCGLIB$$69a17158
        return clazz.getName().indexOf("$$EnhancerBy") == -1 ? clazz : clazz.getSuperclass();
    }


}
