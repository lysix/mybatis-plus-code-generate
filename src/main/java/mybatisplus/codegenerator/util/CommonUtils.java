package mybatisplus.codegenerator.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import mybatisplus.codegenerator.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyong
 * @since 2019-04-17 12:04
 */
public class CommonUtils {

    /**
     * 数据连接信息
     * @param dbType 数据库类型
     * @param dbUrl 连接地址
     * @param username 用户名
     * @param password 密码
     * @param driver 驱动
     * @return DataSourceConfig
     */
    private static DataSourceConfig dataSourceConfig(DbType dbType, String dbUrl, String username, String password, String driver) {
        return new DataSourceConfig()
                .setDbType(dbType)
                .setUrl(dbUrl)
                .setUsername(username)
                .setPassword(password)
                .setDriverName(driver)
                ;
    }

    // 配置
    private static GlobalConfig globalConfig() {
        return new GlobalConfig()
                .setActiveRecord(false)
                .setAuthor(Config.AUTHOR)
                .setOutputDir(Config.outputDir)
                .setFileOverride(true)
                .setActiveRecord(true)// 不需要ActiveRecord特性的请改为false
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(false)// XML columList
                .setKotlin(false) //是否生成 kotlin 代码
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setEntityName(Config.FILE_NAME_MODEL)
                .setMapperName(Config.FILE_NAME_DAO)
                .setXmlName(Config.FILE_NAME_XML)
                .setServiceName(Config.FILE_NAME_SERVICE)
                .setServiceImplName(Config.FILE_NAME_SERVICE_IMPL)
                .setControllerName(Config.FILE_NAME_CONTROLLER)
                .setDateType(DateType.ONLY_DATE) //只使用 java.util.date 代替
                .setIdType(IdType.ID_WORKER)
//                .setSwagger2(true) // model swagger2
                //.setOpen(true) // 是否打开输出目录
                ;
//                if (!serviceNameStartWithI)
//                    config.setServiceName("%sService");
    }


    private static StrategyConfig strategyConfig(String [] tablePrefixs, String [] tableNames) {
        return new StrategyConfig()
                .setSuperServiceClass("BaseService")
                .setCapitalMode(true) // 全局大写命名 ORACLE 注意
                //.setDbColumnUnderline(true)
                .setTablePrefix(tablePrefixs)// 此处可以修改为您的表前缀(数组)
                .setNaming(NamingStrategy.underline_to_camel) // 表名生成策略
                .setInclude(tableNames)//修改替换成你需要的表名，多个表名传数组
                //.setExclude(new String[]{"test"}) // 排除生成的表
                .setEntityLombokModel(true) // lombok实体
                .setEntityBuilderModel(false) // 【实体】是否为构建者模型（默认 false）
                .setEntityColumnConstant(false) // 【实体】是否生成字段常量（默认 false）// 可通过常量名获取数据库字段名 // 3.x支持lambda表达式
                //.setLogicDeleteFieldName("is_delete") // 逻辑删除属性名称
                //.setEntityTableFieldAnnotationEnable
                //.entityTableFieldAnnotationEnable(true)
                ;
    }

    // 包信息配置
    private static PackageConfig packageConfig(String packageName) {
        return new PackageConfig()
                .setParent(packageName)
                .setController(Config.PACKAGE_NAME_CONTROLLER)
                .setEntity(Config.PACKAGE_NAME_MODEL)
                .setMapper(Config.PACKAGE_NAME_DAO)
                .setXml(Config.PACKAGE_NAME_XML)
                .setService(Config.PACKAGE_NAME_SERVICE)
                .setServiceImpl(Config.PACKAGE_NAME_SERVICE_IMPL)
                ;
    }

    /**
     *
     * @param packageConfig
     * @return
     */
    private static InjectionConfig injectionConfig(final PackageConfig packageConfig,String tableName) {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("lowerObjectName",underlineToCamel(tableName));
                map.put("upperObjectName",underlineToPascal(tableName));
                map.put("packageName",packageConfig.getParent());
                this.setMap(map);
            }
        };

        List<FileOutConfig> fileOutConfigList = new ArrayList<FileOutConfig>();
        fileOutConfigList.add(new FileOutConfig("template/dto.kt.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                System.out.println(Config.projectPath + "/src/main/java/"+packageConfig.getParent().replace(".","/") + "/dto/" +underlineToCamel(tableName) + "Dto" + StringPool.DOT_JAVA);
                return Config.projectPath + "/src/main/java/"+packageConfig.getParent().replace(".","/") + "/dto/" +underlineToPascal(tableName)+ "Dto" + StringPool.DOT_JAVA;
            }
        });
        fileOutConfigList.add(new FileOutConfig("template/BusinessService.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return Config.projectPath + "/src/main/java/"+packageConfig.getParent().replace(".","/") + "/businessService/" +underlineToPascal(tableName)+ "BusinessService" + StringPool.DOT_JAVA;
            }
        });
        fileOutConfigList.add(new FileOutConfig("template/BusinessServiceImpl.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return Config.projectPath + "/src/main/java/"+packageConfig.getParent().replace(".","/") + "/businessService/impl/" +underlineToPascal(tableName)+ "BusinessServiceImpl" + StringPool.DOT_JAVA;
            }
        });
        fileOutConfigList.add(new FileOutConfig("template/queryParam.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return Config.projectPath + "/src/main/java/"+packageConfig.getParent().replace(".","/") + "/queryParam/" +underlineToPascal(tableName)+ "QueryParam" + StringPool.DOT_JAVA;
            }
        });
        fileOutConfigList.add(new FileOutConfig("template/BussService.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return Config.projectPath + "/src/main/java/"+packageConfig.getParent().replace(".","/") + "/innerService/businessService/" +underlineToPascal(tableName)+ "BussService" + StringPool.DOT_JAVA;
            }
        });
        fileOutConfigList.add(new FileOutConfig("template/BussServiceImpl.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                System.out.println(Config.projectPath + "/src/main/java/"+packageConfig.getParent().replace(".","/") + "/innerService/businessService/impl/" +underlineToPascal(tableName)+ "BusssServiceImpl" + StringPool.DOT_JAVA);
                return Config.projectPath + "/src/main/java/"+packageConfig.getParent().replace(".","/") + "/innerService/businessService/impl/" +underlineToPascal(tableName)+ "BussServiceImpl" + StringPool.DOT_JAVA;
            }
        });

        injectionConfig.setFileOutConfigList(fileOutConfigList);
        return injectionConfig;
    }



    /**
     * 执行器
     * @param dbType
     * @param dbUrl
     * @param username
     * @param password
     * @param driver
     * @param tablePrefixs
     * @param tableNames
     * @param packageName
     */
    public static void execute(DbType dbType, String dbUrl, String username, String password, String driver,
                               String [] tablePrefixs, String [] tableNames, String packageName) {
        GlobalConfig globalConfig = globalConfig();
        DataSourceConfig dataSourceConfig = dataSourceConfig(dbType, dbUrl, username, password, driver);
        StrategyConfig strategyConfig = strategyConfig(tablePrefixs, tableNames);
        PackageConfig packageConfig = packageConfig(packageName);
        InjectionConfig injectionConfig = injectionConfig(packageConfig,tableNames[0]);

        //生成自定义Service,enity,mapper,serviceImpl层
        TemplateConfig tc = new TemplateConfig();
        tc.setEntity("template/entity.java.vm");
        tc.setController("template/controller.java.vm");
        tc.setMapper("template/mapper.java.vm");
        tc.setService("template/service.java.vm");
        tc.setServiceImpl("template/serviceImpl.java.vm");
        tc.setXml("template/mapper.xml.vm");
        new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig(packageName))
                .setTemplate(tc)
                .setCfg(injectionConfig)
                .execute();
    }

    public static String underlineToCamel(String underline){
        if (org.apache.commons.lang3.StringUtils.isNotBlank(underline)){
            return NamingStrategy.underlineToCamel(underline);
        }
        return null;
    }

    public static String underlineToPascal(String underline){
        if (org.apache.commons.lang3.StringUtils.isNotBlank(underline)){
            return NamingStrategy.capitalFirst(NamingStrategy.underlineToCamel(underline));
        }
        return null;
    }

}
