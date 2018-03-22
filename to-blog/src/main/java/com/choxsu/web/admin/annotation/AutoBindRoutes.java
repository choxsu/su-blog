/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.choxsu.web.admin.annotation;

import com.choxsu.util.annotation.ClassSearcher;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自动绑定（来自jfinal ext)
 * 1.如果没用加入注解，必须以Controller结尾,自动截取前半部分为key
 * 2.加入ControllerBind的 获取 key
 *
 * @author zb
 * 2014-3-20
 */
public class AutoBindRoutes extends Routes {

    protected final Log logger = Log.getLog(getClass());

    private List<Class<? extends Controller>> excludeClasses = new ArrayList<Class<? extends Controller>>();

    private List<String> includeJars = new ArrayList<String>();

    private boolean autoScan = true;

    private String suffix = "Controller";

    public AutoBindRoutes() {
    }

    public AutoBindRoutes(boolean autoScan) {
        this.autoScan = autoScan;
    }

    /**
     * 添加Jar包名 单个文件
     *
     * @param jarName 包名
     * @return 自动绑定路由
     */
    public AutoBindRoutes addJar(String jarName) {
        if (StrKit.notBlank(jarName)) {
            includeJars.add(jarName);
        }
        return this;
    }

    /**
     * 添加Jar包名 多个文件
     *
     * @param jarNames 包名 多个 使用,分割开
     * @return 自动绑定路由
     */
    public AutoBindRoutes addJars(String jarNames) {
        if (StrKit.notBlank(jarNames)) {
            addJars(jarNames.split(","));
        }
        return this;
    }

    /**
     * 添加Jar包名 多个文件
     *
     * @param jarsName 包名 数组
     * @return 自动绑定路由
     */
    public AutoBindRoutes addJars(String[] jarsName) {
        includeJars.addAll(Arrays.asList(jarsName));
        return this;
    }

    /**
     * 添加Jar包名 多个文件
     *
     * @param jarsName 包名 list
     * @return 自动绑定路由
     */
    public AutoBindRoutes addJars(List<String> jarsName) {
        includeJars.addAll(jarsName);
        return this;
    }

    /**
     * 排除不被自动绑定的类
     *
     * @param clazz 类
     * @return 自动绑定路由
     */
    public AutoBindRoutes addExcludeClass(Class<? extends Controller> clazz) {
        if (clazz != null) {
            excludeClasses.add(clazz);
        }
        return this;
    }

    /**
     * 排除不被自动绑定的类
     *
     * @param clazzes 类数组
     * @return 自动绑定路由
     */
    public AutoBindRoutes addExcludeClasses(Class<? extends Controller>[] clazzes) {
        excludeClasses.addAll(Arrays.asList(clazzes));
        return this;
    }

    /**
     * 排除不被自动绑定的类
     *
     * @param clazzes 类list
     * @return 自动绑定路由
     */
    public AutoBindRoutes addExcludeClasses(List<Class<? extends Controller>> clazzes) {
        excludeClasses.addAll(clazzes);
        return this;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void config() {
        List<Class<? extends Controller>> controllerClasses = ClassSearcher.findInClasspathAndJars(Controller.class, includeJars);
        ControllerBind controllerBind = null;
        for (Class controller : controllerClasses) {
            if (excludeClasses.contains(controller)) {
                continue;
            }
            controllerBind = (ControllerBind) controller.getAnnotation(ControllerBind.class);
            if (controllerBind == null) {
                if (!autoScan) {
                    continue;
                }
                if (!controller.getSimpleName().endsWith(suffix)) {
                    logger.debug("routes.add " + controller.getName() + " is suffix not " + suffix);
                    continue;
                }

                this.add(controllerKey(controller), controller);
                logger.debug("routes.add(" + controllerKey(controller) + ", " + controller.getName() + ")");
            } else if (StrKit.isBlank(controllerBind.viewPath())) {
                this.add(controllerBind.controllerKey(), controller);
                logger.debug("routes.add(" + controllerBind.controllerKey() + ", " + controller.getName() + ")");
            } else {
                this.add(controllerBind.controllerKey(), controller, controllerBind.viewPath());
                logger.debug("routes.add(" + controllerBind.controllerKey() + ", " + controller + "," + controllerBind.viewPath() + ")");
            }
        }
    }

    private String controllerKey(Class<Controller> clazz) {
        String controllerKey = "/" + StrKit.firstCharToLowerCase(clazz.getSimpleName());
        controllerKey = controllerKey.substring(0, controllerKey.indexOf(suffix));
        return controllerKey;
    }

    public List<Class<? extends Controller>> getExcludeClasses() {
        return excludeClasses;
    }

    public void setExcludeClasses(List<Class<? extends Controller>> excludeClasses) {
        this.excludeClasses = excludeClasses;
    }

    public List<String> getIncludeJars() {
        return includeJars;
    }

    public void setIncludeJars(List<String> includeJars) {
        this.includeJars = includeJars;
    }

    public void setAutoScan(boolean autoScan) {
        this.autoScan = autoScan;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
