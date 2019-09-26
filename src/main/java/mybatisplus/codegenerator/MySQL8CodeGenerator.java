package mybatisplus.codegenerator;

import com.baomidou.mybatisplus.annotation.DbType;
import mybatisplus.codegenerator.util.CommonUtils;

import java.io.File;

/**
 * @author liu yong
 * @since 2019-04-17 10:33
 */
public class MySQL8CodeGenerator {

    public static void main(String[] args) {
        DbType dbType = DbType.MYSQL;
        String dbUrl = "jdbc:mysql://*******";
        String username = "*****";
        String password = "*****";
        String driver = "com.mysql.cj.jdbc.Driver";
        // 表前缀，生成的实体类，不含前缀
        String [] tablePrefixs = {""};
        // 表名
        String [] tableNames = {"*****"};
        // 基础包名
        String packageName = "******";

        //删除该基础包名下的文件
        String filePath = Config.projectPath + "/src/main/java/" + packageName.replace(".","/");
        File file = new File(filePath);
        removeDir(file);
        //生成文件服务
        CommonUtils.execute(dbType, dbUrl, username, password, driver, tablePrefixs, tableNames, packageName);
    }

    private static void removeDir(File dir) {
        File[] files = dir.listFiles();
        if (files != null){
            for (File file : files) {
                if (file.isDirectory()) {
                    removeDir(file);
                } else {
                    System.out.println(file + ":" + file.delete());
                }
            }
        }

        System.out.println(dir + ":" + dir.delete());
    }

}
