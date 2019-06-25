package com.choxsu.admin.code;

import com.choxsu.common.entity.CodeConfig;
import com.choxsu.utils.kit.SnowFlakeKit;
import com.choxsu.utils.kit.ZipFilesKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class CodeService {

    private CodeConfig codeConfigDao = new CodeConfig().dao();
    private final String codeDir = "gen/";
    private final String path = PathKit.getWebRootPath() + "/" + codeDir;
    private final String baseDir = "zipFile/";
    private final String zipPath = PathKit.getWebRootPath() + "/" + baseDir;

    public List<Record> getAllTables() {
        String database;
        try {
            Connection connection = DbKit.getConfig().getDataSource().getConnection();
            database = connection.getCatalog();
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("\tTABLE_SCHEMA AS databaseName,\n");
        sql.append("\tTABLE_NAME AS tableName,\n");
        sql.append("\t`ENGINE` AS engineName,\n");
        sql.append("\tTABLE_ROWS AS rows,\n");
        sql.append("\tCREATE_TIME AS cTime,\n");
        sql.append("\tUPDATE_TIME AS uTime,\n");
        sql.append("\tTABLE_COMMENT AS remark \n ");
        sql.append("FROM information_schema.TABLES WHERE TABLE_SCHEMA= ?");
        List<Object> list = new LinkedList<>();
        list.add(database);
        return Db.find(sql.toString(), list.toArray());
    }

    public Page<Record> getTables(int p, int size, String tableName, String remark) {
        String database;
        try {
            Connection connection = DbKit.getConfig().getDataSource().getConnection();
            database = connection.getCatalog();
        } catch (SQLException e) {
            return new Page<>();
        }
        String select = "SELECT " +
                "\tTABLE_SCHEMA AS databaseName,\n" +
                "\tTABLE_NAME AS tableName,\n" +
                "\t`ENGINE` AS engineName,\n" +
                "\tTABLE_ROWS AS rows,\n" +
                "\tCREATE_TIME AS cTime,\n" +
                "\tUPDATE_TIME AS uTime,\n" +
                "\tTABLE_COMMENT AS remark \n ";
        StringBuilder from = new StringBuilder("FROM information_schema.TABLES WHERE TABLE_SCHEMA= ?\n");
        List<Object> list = new LinkedList<>();
        list.add(database);
        if (StrKit.notBlank(tableName)) {
            from.append("and instr(TABLE_NAME,?) > 0");
            list.add(tableName);
        }
        if (StrKit.notBlank(remark)) {
            from.append("and instr(TABLE_COMMENT,?) > 0");
            list.add(remark);
        }
        return Db.paginate(p, size, select, from.toString(), list.toArray());
    }

    Ret genValid(List<String> list, List<CodeConfig> codeConfigs) {
        if (list.size() == 0) {
            return Ret.fail("msg", "选择要生成的表");
        }
        CodeConfig codeConfig = codeConfigDao.findById(1);
        if (codeConfig == null) {
            return Ret.fail("msg", "请初始化生成配置");
        }
        codeConfigs.add(codeConfig);
        return null;
    }

    public File gen(List<String> list, CodeConfig codeConfig) {
        if (codeConfig == null) {
            throw new RuntimeException("请初始化生成代码配置");
        }
        String nextId = String.valueOf(SnowFlakeKit.nextId());
        DataSource dataSource = DbKit.getConfig().getDataSource();
        // base model 所使用的包名
        String basePackage = codeConfig.getBasePackage();
        String entityBasePackage = codeConfig.getEntityBasePackage();
        String baseModelPackageName = StrKit.isBlank(entityBasePackage) ? "com.choxsu.common.entity.base" : (basePackage + "." + entityBasePackage);
        // base model 文件保存路径
        String baseModelOutputDir = path + baseModelPackageName.replace('.', '/');
        // model 所使用的包名
        String entityPackage = codeConfig.getEntityPackage();
        String modelPackageName = StrKit.isBlank(entityPackage) ? "com.choxsu.common.entity" : (basePackage + "." + entityPackage);
        // model 文件保存路径
        String modelOutputDir = path + modelPackageName.replace('.', '/');
        // 创建生成器
        Generator gen = new Generator(dataSource, baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        // 设置数据库方言
        gen.setDialect(new MysqlDialect());
        //设置Mapping生成的文文件名
        gen.setMappingKitClassName("_MappingKit");
        // 设置 BaseModel 是否生成链式 setter 方法
        Boolean isGenerateChainSetter = codeConfig.getIsGenerateChainSetter();
        gen.setGenerateChainSetter(isGenerateChainSetter != null && isGenerateChainSetter);
        //设置自定义表生成
        gen.setMetaBuilder(new MyMetaBuilder(dataSource, list));
        // 设置是否在 Model 中生成 dao 对象
        gen.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
        //gen.setGenerateDataDictionary(false);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        Boolean isClearPrefix = codeConfig.getIsClearPrefix();
        if (isClearPrefix != null && isClearPrefix) {
            String prefix = codeConfig.getPrefix();
            gen.setRemovedTableNamePrefixes(prefix);
        }
        //生成数据库备注,默认生成数据库
        gen.setGenerateRemarks(true);
        // 生成
        gen.generate();
        // 将生成好的文件打包下载，并删除之前的文件
        String zipName = nextId + ".zip";
        ZipOutputStream zos = null;
        File zipPathDir = new File(zipPath);
        if (!zipPathDir.exists()) {
            zipPathDir.mkdirs();
        }
        File zipFile = new File(zipPath + zipName);
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            File f = new File(path);
            ZipFilesKit.compress(f, baseDir, zos);
            if (f.isDirectory()) {
                f.delete();
            }
            ZipFilesKit.deleteDir(f);
            return zipFile;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static class MyMetaBuilder extends MetaBuilder {

        private List<String> list;

        public MyMetaBuilder(DataSource dataSource) {
            super(dataSource);
        }

        public MyMetaBuilder(DataSource dataSource, List<String> list) {
            super(dataSource);
            this.list = list;
        }

        /**
         * 返回 true 时将跳过当前 tableName 的处理
         *
         * @param tableName
         * @return
         */
        @Override
        protected boolean isSkipTable(String tableName) {
            return !list.contains(tableName);
        }
    }


}
