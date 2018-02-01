package com.choxsu.common.go;

import com.jfinal.kit.StrKit;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author chox su
 * @date 2018/01/20 10:36
 */
public class YcServiceGenerator {

    protected DataSource dataSource;
    /**
     * service输出目录
     */
    protected String serviceGeneratorOutputDir;

    public YcServiceGenerator(DataSource dataSource, String serviceGeneratorOutputDir) {
        this.dataSource = dataSource;
        this.serviceGeneratorOutputDir = serviceGeneratorOutputDir;
    }

    public void setServiceGeneratorOutputDir(String serviceGeneratorOutputDir) {
        if (StrKit.notBlank(serviceGeneratorOutputDir)){
            this.serviceGeneratorOutputDir = serviceGeneratorOutputDir;
        }
    }

    /**
     * service生成器
     * @param tableMetas
     */
    public void generate(List<TableMeta> tableMetas) {


    }
}
