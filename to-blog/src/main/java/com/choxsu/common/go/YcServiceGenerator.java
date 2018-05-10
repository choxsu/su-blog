package com.choxsu.common.go;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author chox su
 * @date 2018/01/20 10:36
 */
public class YcServiceGenerator {

    protected DataSource dataSource;

    protected String template = "/jf/yc_service_template.jf";
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
        Engine engine = Engine.create("forController");
        engine.setSourceFactory(new ClassPathSourceFactory());
        engine.addSharedMethod(new StrKit());

        for (TableMeta tableMeta : tableMetas) {
            genModelContent(tableMeta);
        }
        writeToFile(tableMetas);

    }

    protected void genModelContent(TableMeta tableMeta) {
        Kv data = Kv.create();
        data.set("servicePackageName", "com.choxsu.service");
        data.set("tableMeta", tableMeta);

        String ret = Engine.use("forController").getTemplate(template).renderToString(data);
        tableMeta.modelContent = ret;
    }

    protected void writeToFile(List<TableMeta> tableMetas) {
        try {
            for (TableMeta tableMeta : tableMetas) {
                writeToFile(tableMeta);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 若 model 文件存在，则不生成，以免覆盖用户手写的代码
     */
    protected void writeToFile(TableMeta tableMeta) throws IOException {
        File dir = new File(serviceGeneratorOutputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String target = serviceGeneratorOutputDir + File.separator + tableMeta.modelName + "Service.java";

        File file = new File(target);
        if (file.exists()) {
            return ;	// 若 Model 存在，不覆盖
        }

        FileWriter fw = new FileWriter(file);
        try {
            fw.write(tableMeta.modelContent);
        }
        finally {
            fw.close();
        }
    }


}
