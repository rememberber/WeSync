package com.luoboduner.wesync.logic;

import com.luoboduner.wesync.ui.panel.StatusPanel;
import com.opencsv.CSVWriter;
import com.luoboduner.wesync.logic.bean.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.luoboduner.wesync.tools.*;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 执行器线程
 *
 * @author Bob
 */
public class ExecuteThread extends Thread implements ExecuteThreadInterface {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteThread.class);
    /**
     * 表-字段配置文件内容Map,key:目标表名,value:对应关系内容List
     */
    public static LinkedHashMap<String, ArrayList<String>> tableFieldMap;
    /**
     * TriggerMap,key:快照名,value:触发表
     */
    public static LinkedHashMap<String, String[]> triggerMap;
    /**
     * 来源表Map,key:快照名,value:Table
     */
    public static LinkedHashMap<String, Table> originalTablesMap;
    /**
     * 目标表Map,key:快照名,value:Table
     */
    public static LinkedHashMap<String, Table> targetTablesMap;

    /**
     * 初始化变量
     */
    @Override
    public void init() {
        tableFieldMap = new LinkedHashMap<>();
        triggerMap = new LinkedHashMap<>();
        originalTablesMap = new LinkedHashMap<>();
        targetTablesMap = new LinkedHashMap<>();
    }

    /**
     * 测试连接
     */
    @Override
    public boolean testLink() {
        StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.testLinking"), LogLevel.INFO);
        StatusPanel.progressCurrent.setMaximum(2);
        StatusPanel.progressCurrent.setValue(0);
        boolean isLinkedPass = true;
        DbUtilSQLServer dbSQLServer = null;
        DbUtilMySQL dbMySQL = null;
        try {
            dbSQLServer = DbUtilSQLServer.getInstance();
            Connection connSQLServer = dbSQLServer.testConnection();
            StatusPanel.progressCurrent.setValue(1);
            dbMySQL = DbUtilMySQL.getInstance();
            Connection connMySQL = dbMySQL.testConnection();
            StatusPanel.progressCurrent.setValue(2);
            if (connSQLServer == null) {
                isLinkedPass = false;
                StatusLog.setStatusDetail("SQLServer " + PropertyUtil.getProperty("ds.logic.testLinkFail"), LogLevel.ERROR);
            }

            if (connMySQL == null) {
                isLinkedPass = false;
                StatusLog.setStatusDetail("connMySQL " + PropertyUtil.getProperty("ds.logic.testLinkFail"), LogLevel.ERROR);
            }

            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.testLinkFinish"), LogLevel.INFO);

        } catch (Exception e) {

            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.testLinkFail") + e.toString(), LogLevel.ERROR);
            isLinkedPass = false;
        } finally {
            if (dbSQLServer != null) {
                try {
                    dbSQLServer.close();
                } catch (SQLException e) {
                    StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.testLinkFail") + e.toString(), LogLevel.ERROR);
                    e.printStackTrace();
                }
            }
            if (dbMySQL != null) {
                try {
                    dbMySQL.close();
                } catch (SQLException e) {
                    StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.testLinkFail") + e.toString(), LogLevel.ERROR);
                    e.printStackTrace();
                }
            }

        }
        return isLinkedPass;
    }

    /**
     * 解析配置文件
     */
    @Override
    public boolean analyseConfigFile() {
        StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.startAnalyse"), LogLevel.INFO);

        boolean isAnalyseSuccess = true;

        // 解析表-字段对应关系文件
        File tableFieldDir = null;
        // 如果快照文件夹不存在，则解析初始化表字段关系配置
        File snapsDir = new File(ConstantsLogic.SNAPS_DIR);
        if (!snapsDir.exists()) {
            tableFieldDir = new File(ConstantsLogic.TABLE_FIELD_INIT_DIR);
        } else {
            tableFieldDir = new File(ConstantsLogic.TABLE_FIELD_DIR);
        }

        File[] tableFieldFiles = tableFieldDir.listFiles();
        ArrayList<String> list;

        StatusPanel.progressCurrent.setMaximum(tableFieldFiles.length);
        int progressValue = 0;
        StatusPanel.progressCurrent.setValue(progressValue);

        for (File file : tableFieldFiles) {

            String fileName = file.getName();
            if (!fileName.endsWith(".sql")) {
                progressValue++;
                StatusPanel.progressCurrent.setValue(progressValue);
            } else {
                try {
                    // 读取解析表-字段配置sql文件
                    list = FileUtils.getSqlFileContentList(file);

                    String key = fileName.substring(0, fileName.lastIndexOf("."));

                    // 将表-字段配置文件内容存放到Map,key:表名,value:对应关系内容List
                    tableFieldMap.put(key, list);

                } catch (IOException e) {
                    isAnalyseSuccess = false;
                    StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.AnalyseFail") + e.toString(), LogLevel.ERROR);
                    e.printStackTrace();
                }
                progressValue++;
                StatusPanel.progressCurrent.setValue(progressValue);
            }
        }

        // 解析Trigger文件
        File triggerFile = new File(ConstantsLogic.TRIGGER_FILE);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(triggerFile));
            String lineTxt = null;
            while ((lineTxt = reader.readLine()) != null) {
                lineTxt = lineTxt.trim();
                if ("".equals(lineTxt) || lineTxt.startsWith("//")) {
                    // 跳过注释和空行
                } else {
                    if (lineTxt.contains("//")) {
                        // 去掉注释
                        lineTxt = lineTxt.substring(0, lineTxt.indexOf("//")).trim();
                    }
                    // 用]=<分隔比用=分隔准确，因为其他条件里也可能会有=
                    String[] arr = lineTxt.split("\\]=\\<");
                    // 补上被split掉的"]"
                    arr[0] = arr[0] + "]";
                    // 取来源表名和目标表名
                    String snapName = arr[0].substring(0, arr[0].indexOf(":"));
                    String[] tarTableNames = new String[arr.length - 1];

                    // 获取来源表map<快照名,(表名,主键,字段,其他条件或保留)>
                    Table tableOri = new Table();
                    // 匹配表名
                    Pattern p = Pattern.compile(ConstantsLogic.REGEX_TRIGGER_TABLE);
                    Matcher matcher = p.matcher(arr[0]);
                    while (matcher.find()) {
                        tableOri.setTableName(matcher.group(1));
                    }
                    // 匹配表主键
                    p = Pattern.compile(ConstantsLogic.REGEX_TRIGGER_PRIM_KEY);
                    matcher = p.matcher(arr[0]);
                    while (matcher.find()) {
                        tableOri.setPrimKey(matcher.group(1));
                    }
                    // 匹配表字段
                    p = Pattern.compile(ConstantsLogic.REGEX_TRIGGER_FIELDS);
                    matcher = p.matcher(arr[0]);
                    while (matcher.find()) {
                        tableOri.setFields(matcher.group(1));
                    }
                    // 匹配其他条件或保留
                    p = Pattern.compile(ConstantsLogic.REGEX_TRIGGER_OTHER);
                    matcher = p.matcher(arr[0]);
                    while (matcher.find()) {
                        tableOri.setOther(matcher.group(1));
                    }
                    originalTablesMap.put(snapName, tableOri);

                    for (int i = 1; i < arr.length; i++) {
                        // 补上被split掉的"<"
                        arr[i] = "<" + arr[i];
                        // 获取目标表map<快照名,(表名,主键,字段,其他条件或保留)>
                        Table tableTar = new Table();
                        // 匹配表名
                        p = Pattern.compile(ConstantsLogic.REGEX_TRIGGER_TABLE);
                        matcher = p.matcher(arr[i]);
                        while (matcher.find()) {
                            String temp = matcher.group(1);
                            tableTar.setTableName(temp);
                            tarTableNames[i - 1] = temp;
                        }

                        // 匹配表主键
                        p = Pattern.compile(ConstantsLogic.REGEX_TRIGGER_PRIM_KEY);
                        matcher = p.matcher(arr[i]);
                        while (matcher.find()) {
                            tableTar.setPrimKey(matcher.group(1));
                        }
                        // 匹配表字段
                        p = Pattern.compile(ConstantsLogic.REGEX_TRIGGER_FIELDS);
                        matcher = p.matcher(arr[i]);
                        while (matcher.find()) {
                            tableTar.setFields(matcher.group(1));
                        }
                        // 匹配其他条件或保留
                        p = Pattern.compile(ConstantsLogic.REGEX_TRIGGER_OTHER);
                        matcher = p.matcher(arr[i]);
                        while (matcher.find()) {
                            tableTar.setOther(matcher.group(1));
                        }
                        targetTablesMap.put(snapName, tableTar);
                    }
                    triggerMap.put(snapName, tarTableNames);
                }

            }

        } catch (IOException e) {
            isAnalyseSuccess = false;
            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.AnalyseTriggerFail") + e.toString(), LogLevel.ERROR);
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    isAnalyseSuccess = false;
                    StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.AnalyseTriggerFail") + e.toString(), LogLevel.ERROR);
                    e.printStackTrace();
                }
            }
        }

        if (isAnalyseSuccess) {
            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.finishAnalyse"), LogLevel.INFO);
        }

        return isAnalyseSuccess;

    }

    /**
     * 备份
     */
    @Override
    public void backUp() {
        StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.startBackUp"), LogLevel.INFO);

        StatusPanel.progressCurrent.setMaximum(100);
        String user = "";
        String password = "";
        try {
            DESPlus des = new DESPlus();
            user = des.decrypt(ConstantsTools.CONFIGER.getUserTo());
            password = des.decrypt(ConstantsTools.CONFIGER.getPasswordTo());
        } catch (Exception e) {
            logger.error(PropertyUtil.getProperty("ds.ui.database.to.err.decode") + e.toString());
            e.printStackTrace();
        }
        BackupManage.exportDatabase(ConstantsTools.CONFIGER.getHostTo(), user, password,
                ConstantsTools.CONFIGER.getNameTo());

        StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.finishBackUp"), LogLevel.INFO);
    }

    /**
     * 新建快照
     */
    @Override
    public boolean newSnap() {
        StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.startNewSnap"), LogLevel.INFO);
        boolean isSuccess = true;

        isSuccess = SnapManage.createSnap(originalTablesMap);
        if (isSuccess) {
            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.finishNewSnap"), LogLevel.INFO);
        }

        return isSuccess;
    }

    /**
     * 对比快照，并生成SQL
     */
    @Override
    public boolean diffSnap() {
        StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.startDiffSnap"), LogLevel.INFO);
        boolean isSuccess = true;

        isSuccess = SnapManage.diffSnap(originalTablesMap);

        if (isSuccess) {
            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.finishDiffSnap"), LogLevel.INFO);
        } else {
            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.diffSnapFail"), LogLevel.ERROR);
        }

        return isSuccess;
    }

    /**
     * 执行SQL语句
     *
     * @throws Exception
     */
    @Override
    public boolean executeSQL() {
        StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.startRunSql"), LogLevel.INFO);

        boolean isSuccess = true;
        ArrayList<String> sqlList = SnapManage.sqlList;
        // sql日志目录
        File logSqlFileDir = new File(ConstantsLogic.LOG_SQL_DIR);
        if (!logSqlFileDir.exists()) {
            logSqlFileDir.mkdirs();
        }
        // sql日志文件
        File logSqlFile = new File(ConstantsLogic.LOG_SQL);
        CSVWriter csvWriter = null;
        // 总sql数
        int totalSqls = 0;
        if (sqlList.size() != 0) {
            if ("false".equals(ConstantsTools.CONFIGER.getDebugMode())) {
                String sqlForLog = "";
                try {
                    if (!logSqlFile.exists()) {
                        logSqlFile.createNewFile();
                    }

                    // 整理触发器中生成的所有sql
                    StringBuffer sqlBuff = new StringBuffer();
                    for (String string : sqlList) {
                        String tempArr[] = string.split(";");
                        for (String tempSql : tempArr) {
                            if (!"".equals(tempSql.trim())) {
                                sqlBuff.append(tempSql).append(";");
                            }
                        }
                    }

                    String[] sqls = sqlBuff.toString().split(";");

                    StatusPanel.progressCurrent.setMaximum(sqls.length);

                    // 总sql数
                    totalSqls = 0;
                    // 受影响的结果行数
                    int affectedRecords = 0;
                    int progressValue = 0;
                    for (String string : sqls) {
                        if (!"".equals(string.trim())) {
                            totalSqls++;
                            sqlForLog = string;
                            int result = DbUtilMySQL.getInstance().executeUpdate(string + ";");
                            affectedRecords += result;

                            logger.warn("===" + string + ";===" + result);
                        }
                        progressValue++;
                        StatusPanel.progressCurrent.setValue(progressValue);
                    }

                    // 第二个参数:true表示在文件结尾追加
                    Writer writer = new FileWriter(logSqlFile, true);
                    csvWriter = new CSVWriter(writer, ',');
                    // log_sql第一列:系统时间,第二列:本次所有要执行的sql,第三列:执行结果受影响的总行数,第四列:是否成功
                    String[] logLine = new String[4];
                    // log_sql第一列，系统时间
                    logLine[0] = Utils.getCurrentTime();
                    // log_sql第二列，本次所有要执行的sql
                    logLine[1] = sqlBuff.toString();
                    // log_sql第三列，执行结果受影响的总行数
                    logLine[2] = String.valueOf(affectedRecords);

                    if ("true".equals(ConstantsTools.CONFIGER.getStrictMode())) {
                        if (affectedRecords == totalSqls) {
                            DbUtilMySQL.getInstance().getConnection().commit();
                            // log_sql第四列，执行结果成功
                            logLine[3] = "Success";
                        } else {
                            DbUtilMySQL.getInstance().getConnection().rollback();
                            // log_sql第四列，执行结果失败
                            logLine[3] = "Fail";
                            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.runSqlFail"), LogLevel.ERROR);
                            return false;
                        }
                    } else {
                        DbUtilMySQL.getInstance().getConnection().commit();
                        // log_sql第四列，执行结果成功
                        logLine[3] = "Success";
                    }
                    if (!"".equals(logLine[1].trim())) {
                        csvWriter.writeNext(logLine);
                        csvWriter.flush();
                    }

                    StatusLog.setLastTime(Utils.getCurrentTime());
                    StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.runSqlFinish"), LogLevel.INFO);
                    StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.syncFinish"), LogLevel.INFO);
                    return isSuccess;
                } catch (Exception e) {
                    try {
                        DbUtilMySQL.getInstance().getConnection().rollback();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    isSuccess = false;
                    StatusLog.setStatusDetail("!!!" + sqlForLog + PropertyUtil.getProperty("ds.logic.currentSqlFail") + e.toString(), LogLevel.ERROR);

                } finally {
                    if (csvWriter != null) {
                        try {
                            csvWriter.close();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    try {
                        DbUtilMySQL.getInstance().close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                StatusPanel.progressCurrent.setMaximum(sqlList.size());
                int progressValue = 0;
                StatusPanel.progressCurrent.setValue(progressValue);
                for (String string : sqlList) {
                    logger.debug("sqls:" + string);
                    progressValue++;
                    StatusPanel.progressCurrent.setValue(progressValue);
                }

            }
        } else {
            StatusPanel.progressCurrent.setMaximum(1);
            StatusPanel.progressCurrent.setValue(1);
            StatusLog.setLastTime(Utils.getCurrentTime());
            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.runSqlFinish") + totalSqls, LogLevel.INFO);
            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.syncFinish"), LogLevel.INFO);
        }

        return isSuccess;

    }

    @Override
    public void run() {
        StatusPanel.isRunning = true;
        this.setName("ExecuteThread");
        long enterTime = System.currentTimeMillis();
        StatusPanel.progressTotal.setMaximum(6);
        // 初始化变量
        init();
        // 测试连接
        boolean isLinked = testLink();
        if (isLinked) {
            StatusPanel.progressTotal.setValue(1);
            // 分析配置文件
            boolean isAnalyseSuccess = analyseConfigFile();
            if (isAnalyseSuccess) {
                StatusPanel.progressTotal.setValue(2);
                // 备份
                if ("true".equals(ConstantsTools.CONFIGER.getAutoBak())) {
                    backUp();
                }
                StatusPanel.progressTotal.setValue(3);
                // 建立新快照
                boolean isSnapSuccess = newSnap();
                if (isSnapSuccess) {
                    StatusPanel.progressTotal.setValue(4);
                    // 对比快照,并根据对比结果生成SQL
                    boolean isDiffSuccess = diffSnap();
                    if (isDiffSuccess) {
                        StatusPanel.progressTotal.setValue(5);
                        // 执行SQL
                        boolean isExecuteSuccess = executeSQL();
                        if (isExecuteSuccess) {
                            StatusPanel.progressTotal.setValue(6);

                            // 恢复按钮状态
                            if (!StatusPanel.buttonStartSchedule.isEnabled()) {
                                StatusLog.setStatus(PropertyUtil.getProperty("ds.logic.runScheduleing"));
                            } else {
                                StatusLog.setStatus(PropertyUtil.getProperty("ds.logic.manuSyncFinish"));
                            }
                            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.currentManuSyncFinish"), LogLevel.INFO);
                            // 设置持续时间
                            long leaveTime = System.currentTimeMillis();
                            float minutes = (float) (leaveTime - enterTime) / 1000;
                            StatusLog.setKeepTime(String.valueOf(minutes));
                            // 设置成功次数+1
                            String success = String
                                    .valueOf((Long.parseLong(ConstantsTools.CONFIGER.getSuccessTime()) + 1));
                            StatusLog.setSuccess(success);
                        } else {
                            // 恢复快照备份
                            SnapManage.recoverSnapBak();

                            String fail = String.valueOf((Long.parseLong(ConstantsTools.CONFIGER.getFailTime()) + 1));
                            StatusLog.setFail(fail);
                        }

                    } else {
                        // 恢复快照备份
                        SnapManage.recoverSnapBak();

                        String fail = String.valueOf((Long.parseLong(ConstantsTools.CONFIGER.getFailTime()) + 1));
                        StatusLog.setFail(fail);
                    }

                } else {
                    // 恢复快照备份
                    SnapManage.recoverSnapBak();

                    String fail = String.valueOf((Long.parseLong(ConstantsTools.CONFIGER.getFailTime()) + 1));
                    StatusLog.setFail(fail);
                }

            } else {
                String fail = String.valueOf((Long.parseLong(ConstantsTools.CONFIGER.getFailTime()) + 1));
                StatusLog.setFail(fail);
            }

        } else {
            String fail = String.valueOf((Long.parseLong(ConstantsTools.CONFIGER.getFailTime()) + 1));
            StatusLog.setFail(fail);
        }

        StatusPanel.buttonStartNow.setEnabled(true);
        StatusPanel.buttonStartSchedule.setEnabled(true);
        StatusPanel.isRunning = false;
    }
}
