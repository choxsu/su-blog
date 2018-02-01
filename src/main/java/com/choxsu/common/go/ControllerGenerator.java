package com.choxsu.common.go;

import com.jfinal.kit.StrKit;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author chox su
 * @date 2018/01/20 10:36
 */
public class ControllerGenerator {

    protected DataSource dataSource;
    /**
     * controller输出目录
     */
    protected String controllerGeneratorOutputDir;

    public ControllerGenerator(DataSource dataSource, String controllerGeneratorOutputDir) {
        this.dataSource = dataSource;
        this.controllerGeneratorOutputDir = controllerGeneratorOutputDir;
    }

    public void setControllerGeneratorOutputDir(String controllerGeneratorOutputDir) {
        if (StrKit.notBlank(controllerGeneratorOutputDir)){
            this.controllerGeneratorOutputDir = controllerGeneratorOutputDir;
        }
    }

    /**
     * 生成Controller
     * @param tableMetas
     */
    public static void generate(List<TableMeta> tableMetas){

    }

}
