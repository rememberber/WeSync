package com.luoboduner.wesync.logic;

import com.luoboduner.wesync.tools.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 备份管理类
 *
 * @author Bob
 */
public class BackupManage {

    /**
     * 创建生成mysql备份bat
     *
     * @param hostIP
     * @param userName
     * @param password
     * @param databaseName
     * @return
     */
    public static boolean createMysqlBackBat(String hostIP, String userName, String password, String databaseName) {
        boolean isSuccess = true;

        // 写bat文件
        File bat = new File(ConstantsLogic.BAT_DIR_MYSQL);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(bat));

            bw.write("@echo off & setlocal ENABLEEXTENSIONS");
            bw.newLine();
            bw.write("set BACKUP_PATH=" + ConstantsLogic.PATH_MYSQL_BAK);
            bw.newLine();
            bw.write("set DATABASES=" + databaseName);
            bw.newLine();
            bw.write("set HOST=" + hostIP);
            bw.newLine();
            bw.write("set USERNAME=" + userName);
            bw.newLine();
            bw.write("set PASSWORD=" + password);
            bw.newLine();
            bw.write("set MYSQL=" + ConstantsTools.CONFIGER.getMysqlPath());
            bw.newLine();
            bw.write("set path_bin_mysql=%MYSQL%\\bin\\");
            bw.newLine();

            StringBuffer batContentPart2 = new StringBuffer();
            batContentPart2.append("set YEAR=%date:~0,4%").append("\r\n");
            batContentPart2.append("set MONTH=%date:~5,2%").append("\r\n");
            batContentPart2.append("set DAY=%date:~8,2%").append("\r\n");
            batContentPart2.append("set HOUR=%time:~0,2%").append("\r\n");
            batContentPart2.append("set MINUTE=%time:~3,2%").append("\r\n");
            batContentPart2.append("set SECOND=%time:~6,2%").append("\r\n");
            batContentPart2.append("set DIR=%BACKUP_PATH%").append("\r\n");
            batContentPart2.append("set ADDON=\"%YEAR%%MONTH%%DAY%_%HOUR%%MINUTE%%SECOND%\"").append("\r\n");

            batContentPart2.append("if not exist %DIR% (").append("\r\n");
            batContentPart2.append("mkdir %DIR%").append("\r\n");
            batContentPart2.append(")").append("\r\n");
            batContentPart2.append("if not exist %DIR% (").append("\r\n");
            batContentPart2.append("echo Backup path: %DIR% not exists, create dir failed.").append("\r\n");
            batContentPart2.append("goto exit").append("\r\n");
            batContentPart2.append(")").append("\r\n");
            batContentPart2.append("cd /d %DIR%").append("\r\n");

            batContentPart2.append("echo Start dump databases...").append("\r\n");
            batContentPart2.append("for %%D in (%DATABASES%) do (").append("\r\n");
            batContentPart2.append("echo Dumping database %%D ...").append("\r\n");
            batContentPart2
                    .append("%path_bin_mysql%mysqldump -h%HOST% -u%USERNAME% -p%PASSWORD% --skip-lock-tables %%D > %%D_%ADDON%.sql")
                    .append("\r\n");
            batContentPart2.append(")").append("\r\n");
            batContentPart2.append("echo Done").append("\r\n");
            batContentPart2.append(":exit");
            bw.write(batContentPart2.toString());
            bw.flush();
        } catch (IOException e) {
            isSuccess = false;
            e.printStackTrace();
            return false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return isSuccess;
    }

    /**
     * MySQL数据库导出
     *
     * @param hostIP
     * @param userName
     * @param password
     * @param databaseName
     * @return
     */
    public static boolean exportDatabase(String hostIP, String userName, String password, String databaseName) {
        StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.backuping"), LogLevel.INFO);
        boolean isSuccess = true;

        // 创建整库备份bat
        // isSuccess = createMysqlBackBat(hostIP, userName, password,
        // databaseName);
        //
        // Process ps = null;
        // try {
        // ps = Runtime.getRuntime().exec(ConstantsLogic.BAT_DIR_MYSQL);
        // // 取得命令结果的输出流
        // InputStream fis = ps.getInputStream();
        // // 用一个读输出流类去读
        // InputStreamReader isr = new InputStreamReader(fis);
        // // 用缓冲器读行
        // BufferedReader br = new BufferedReader(isr);
        // String line = null;
        // // 直到读完为止
        // while ((line = br.readLine()) != null) {
        // StatusLog.setStatusDetail("正在备份MySQL数据库，请耐心等待:" + line,
        // LogLevel.INFO);
        // }
        // ps.waitFor();
        // } catch (IOException | InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // 进行表备份
        try {
            DbUtilMySQL mySql = DbUtilMySQL.getInstance();
            ArrayList<String> list = new ArrayList<String>();
            // 读取解析表备份sql文件
            File file = new File(ConstantsLogic.MYSQL_TABLE_BACKUP_SQL_FILE);
            list = FileUtils.getSqlFileContentList(file);
            for (String string : list) {
                StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.backupTable") + string, LogLevel.INFO);
                mySql.executeUpdate(string);
            }
            mySql.getConnection().commit();
            isSuccess = true;
        } catch (IOException | SQLException e1) {
            isSuccess = false;
            e1.printStackTrace();
            StatusLog.setStatusDetail(e1.toString(), LogLevel.ERROR);
        }

        return isSuccess;
    }

}
