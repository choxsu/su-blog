/**
 * 请勿将俱乐部专享资源复制给其他人，保护知识产权即是保护我们所在的行业，进而保护我们自己的利益
 * 即便是公司的同事，也请尊重 JFinal 作者的努力与付出，不要复制给同事
 * <p>
 * 如果你尚未加入俱乐部，请立即删除该项目，或者现在加入俱乐部：http://jfinal.com/club
 * <p>
 * 俱乐部将提供 jfinal-club 项目文档与设计资源、专用 QQ 群，以及作者在俱乐部定期的分享与答疑，
 * 价值远比仅仅拥有 jfinal club 项目源代码要大得多
 * <p>
 * JFinal 俱乐部是五年以来首次寻求外部资源的尝试，以便于有资源创建更加
 * 高品质的产品与服务，为大家带来更大的价值，所以请大家多多支持，不要将
 * 首次的尝试扼杀在了摇篮之中
 */

package com.choxsu.common.generator;

import com.choxsu.common.StartConfig;
import com.choxsu.common.go.MyGenerator;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

/**
 * Model、BaseModel、_MappingKit 生成器
 *
 * @author Administrator
 */
public class _Generator {

    /**
     * 部分功能使用 Db + Record 模式实现，无需生成 model 的 table 在此配置
     * // 暂不实现该功能
     */
    private static String[] excludedTable = {
            "",
    };

    /**
     * 重用 JFinalClubConfig 中的数据源配置，避免冗余配置
     */
    public static DataSource getDataSource() {
//        String url = "jdbc:mysql://192.168.3.45:3306/xilian168?characterEncoding=utf8&useSSL=false";
//        String username = "root";
//        String pwd = "xl168";
//        DruidPlugin druidPlugin = new DruidPlugin(url, username, pwd);
        DruidPlugin druidPlugin = StartConfig.getDruidPlugin();
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

    public static void main(String[] args) {
        // base model 所使用的包名
        String baseModelPackageName = "com.choxsu.common.entity.base";
        // base model 文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath()
                + "/src/main/java/com/choxsu/common/entity/base";

        System.out.println("输出路径：" + baseModelOutputDir);

        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "com.choxsu.common.entity";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";

        String controllerGeneratorOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/choxsu/controller";

        // 创建生成器
        MyGenerator gen = new MyGenerator(getDataSource(),
                baseModelPackageName,
                baseModelOutputDir,
                modelPackageName,
                modelOutputDir,
                controllerGeneratorOutputDir);

        // 设置数据库方言
        gen.setDialect(new MysqlDialect());

        //设置Mapping生成的文文件名
        gen.setMappingKitClassName("MappingKit");

        /**
         * 设置 BaseModel 是否生成链式 setter 方法
         */
        gen.setGenerateChainSetter(false);

        //设置自定义表生成

        gen.setMetaBuilder(new _MetaBuilder(getDataSource()));
        // 添加不需要生成的表名
        for (String table : excludedTable) {
            gen.addExcludedTable(table);
        }
        // 设置是否在 Model 中生成 dao 对象
        gen.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
        gen.setGenerateDataDictionary(false);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        // gernerator.setRemovedTableNamePrefixes("t_")
        // 生成
        gen.generate();
    }
}
