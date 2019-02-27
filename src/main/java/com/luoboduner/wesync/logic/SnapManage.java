package com.luoboduner.wesync.logic;

import com.luoboduner.wesync.ui.panel.StatusPanel;
import com.opencsv.CSVWriter;
import com.luoboduner.wesync.logic.bean.Table;
import com.luoboduner.wesync.logic.init.Init4pxp2p;
import com.luoboduner.wesync.tools.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * 快照管理类
 *
 * @author Bob
 */
public class SnapManage {

    public static ArrayList<String> sqlList;

    /**
     * 创建快照
     *
     * @param tableMap：来源表Map
     * @return
     */
    public static boolean createSnap(Map<String, Table> tableMap) {
        boolean isSuccess = true;

        // 如果快照文件夹不存在，则进行初始化
        File snapsDir = new File(ConstantsLogic.SNAPS_DIR);
        File snapsBakDir = new File(ConstantsLogic.SNAPS_BAK_DIR);
        if (!snapsDir.exists()) {
            boolean isInitSuccess = Init4pxp2p.init();
            if (!isInitSuccess) {
                return false;
            }
        }
        if (!snapsBakDir.exists()) {
            snapsBakDir.mkdirs();
        }
        // 备份当前快照文件夹，以便后续过程失败时恢复
        try {
            // 清空快照备份文件夹
            FileUtils.clearDirectiory(ConstantsLogic.SNAPS_BAK_DIR);
            // 拷贝到快照备份文件夹
            FileUtils.copyDirectiory(ConstantsLogic.SNAPS_DIR, ConstantsLogic.SNAPS_BAK_DIR);
        } catch (IOException e1) {
            isSuccess = false;

            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.currentSnapDirBackFail") + e1.toString(), LogLevel.ERROR);
            e1.printStackTrace();
            return isSuccess;
        }
        StatusPanel.progressCurrent.setMaximum(tableMap.keySet().size());
        int progressValue = 0;
        StatusPanel.progressCurrent.setValue(progressValue);

        // 遍历来源表Map，建立快照
        for (String snapName : tableMap.keySet()) {
            if (!"".equals(snapName.trim())) {

                // 上一次快照
                File snapBefore = new File(ConstantsLogic.SNAPS_DIR + File.separator + snapName + "_before.csv");
                // 当前快照
                File snapNow = new File(ConstantsLogic.SNAPS_DIR + File.separator + snapName + ".csv");

                // 判断上一次的快照是否存在，如果不存在，则新建一个空的
                if (!snapBefore.exists()) {
                    try {
                        snapBefore.createNewFile();
                    } catch (IOException e) {
                        StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.ifLastSnapExistFail") + e.toString(), LogLevel.ERROR);
                        e.printStackTrace();
                    }
                } else {
                    // 删除原快照，将当前快照变成原快照
                    snapBefore.delete();
                    snapNow.renameTo(new File(ConstantsLogic.SNAPS_DIR + File.separator + snapName + "_before.csv"));
                }
                // 建立当前快照，以便与原快照对比
                // 新快照文件
                CSVWriter csvWriter = null;
                DbUtilSQLServer sqlServer = null;
                try {
                    snapNow.createNewFile();
                    Writer writer = new FileWriter(snapNow);
                    csvWriter = new CSVWriter(writer, ',');

                    // 获取SQLServer连接实例
                    sqlServer = DbUtilSQLServer.getInstance();

                    String querySql = "SELECT " + tableMap.get(snapName).getFields() + " FROM "
                            + tableMap.get(snapName).getTableName();
                    if (tableMap.get(snapName).getOther() != null) {
                        querySql += (" " + tableMap.get(snapName).getOther());
                    }
                    // 表查询
                    ResultSet rs = sqlServer.executeQuery(querySql);
                    // 获取列信息
                    ResultSetMetaData m;
                    m = rs.getMetaData();

                    List<String> list = new ArrayList<String>();

                    int columns = m.getColumnCount();
                    // 表的表头
                    for (int i = 1; i <= columns; i++) {
                        list.add(m.getColumnName(i));
                    }

                    int size = list.size();
                    String[] arr = list.toArray(new String[size]);
                    csvWriter.writeNext(arr);
                    // 表的内容
                    while (rs.next()) {
                        list.clear();
                        for (int i = 1; i <= columns; i++) {
                            list.add(rs.getString(i));
                        }

                        size = list.size();
                        String[] arr2 = list.toArray(new String[size]);
                        csvWriter.writeNext(arr2);
                    }

                } catch (IOException | SQLException e) {
                    StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.newSnapFileFail") + e.toString(), LogLevel.ERROR);
                    e.printStackTrace();
                } finally {
                    if (csvWriter != null) {
                        try {
                            csvWriter.close();
                        } catch (IOException e) {
                            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.newSnapFileFail") + e.toString(), LogLevel.ERROR);
                            e.printStackTrace();
                        }
                    }
                    if (sqlServer != null) {
                        try {
                            sqlServer.close();
                        } catch (SQLException e) {
                            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.newSnapFileFail") + e.toString(), LogLevel.ERROR);
                            e.printStackTrace();
                        }
                    }

                }

            }
            progressValue++;
            StatusPanel.progressCurrent.setValue(progressValue);
        }

        return isSuccess;
    }

    /**
     * 对比快照
     *
     * @param tableMap：来源表Map
     * @return
     */
    public static boolean diffSnap(Map<String, Table> tableMap) {
        StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.startDiffSnap"), LogLevel.INFO);
        StatusPanel.progressCurrent.setMaximum(tableMap.keySet().size());
        int progressValue = 0;
        StatusPanel.progressCurrent.setValue(progressValue);
        boolean isSuccess = true;
        // 初始化目标sqlList
        sqlList = new ArrayList<String>();
        // 遍历来源表Map，对比快照
        for (String snapName : tableMap.keySet()) {
            if (!"".equals(snapName.trim())) {
                // 上一次快照
                File snapBefore = new File(ConstantsLogic.SNAPS_DIR + File.separator + snapName + "_before.csv");
                // 当前快照
                File snapNow = new File(ConstantsLogic.SNAPS_DIR + File.separator + snapName + ".csv");
                // MD5比对两个文件
                String snapMD5Before = FileUtils.getFileMD5(snapBefore);
                String snapMD5Now = FileUtils.getFileMD5(snapNow);
                if (snapMD5Before == null) {
                    StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.beforeSnapMd5Fail") + snapBefore.getName(), LogLevel.ERROR);
                    return false;
                }
                if (snapMD5Now == null) {
                    StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.nowSnapMd5Fail") + snapNow.getName(), LogLevel.ERROR);
                    return false;
                }
                if (snapMD5Before.equals(snapMD5Now)) {
                    progressValue++;
                    StatusPanel.progressCurrent.setValue(progressValue);
                    continue;
                } else {
                    try {
                        isSuccess = diffCsvLineByLine(snapBefore, snapNow);
                    } catch (Exception e) {
                        StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.lineByLineDiffFail") + e.toString(), LogLevel.ERROR);
                        e.printStackTrace();
                        return false;
                    } finally {
                        if (TriggerManage.SQLServer != null) {
                            try {
                                TriggerManage.SQLServer.close();
                            } catch (SQLException e) {
                                StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.lineByLineDiffFail") + e.toString(), LogLevel.ERROR);
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            progressValue++;
            StatusPanel.progressCurrent.setValue(progressValue);
        }
        StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.finishDiffSnap"), LogLevel.INFO);
        return isSuccess;
    }

    /**
     * 恢复快照备份
     */
    public static void recoverSnapBak() {

        // 清空快照文件夹
        FileUtils.clearDirectiory(ConstantsLogic.SNAPS_DIR);
        // 拷贝快照备份到快照文件夹
        try {
            FileUtils.copyDirectiory(ConstantsLogic.SNAPS_BAK_DIR, ConstantsLogic.SNAPS_DIR);
        } catch (IOException e) {
            StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.restoreSnapBackFail") + e.toString(), LogLevel.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * 逐行对比csv文件
     *
     * @param snapBefore
     * @param snapNow
     * @throws IOException
     */
    public static boolean diffCsvLineByLine(File snapBefore, File snapNow) throws Exception {

        boolean isSuccess = true;

        ArrayList<String[]> snapBeforeList = FileUtils.getCsvFileContentList(snapBefore);
        ArrayList<String[]> snapNowList = FileUtils.getCsvFileContentList(snapNow);

        // 去掉末尾空行，如果有的话
        // if (snapBeforeList.get(snapBeforeList.size() - 1)[0] == null) {
        // snapBeforeList.remove(snapBeforeList.size() - 1);
        // }
        //
        // if (snapNowList.get(snapNowList.size() - 1)[0] == null) {
        // snapNowList.remove(snapNowList.size() - 1);
        // }

        // 获取该快照名
        String snapName = snapNow.getName().substring(0, snapNow.getName().indexOf(".csv"));

        // 获取表头
        String[] headerBefore;
        String[] headerNow = snapNowList.get(0);
        if (snapBeforeList.size() == 0) {
            // 如果原快照是空的，说明是第一次进行快照，所以将原快照加一个新快照表头即可
            headerBefore = headerNow;
        } else {
            headerBefore = snapBeforeList.get(0);
        }

        // 对比表头，看表结构是否有变
        // Arrays.sort(headerBefore);
        // Arrays.sort(headerNow);
        if (!Arrays.equals(headerBefore, headerNow)) {
            StatusLog.setStatusDetail(snapName + PropertyUtil.getProperty("ds.logic.tableChanged"), LogLevel.ERROR);
            return false;
        }

        // 获取主键
        String primKeys = ExecuteThread.originalTablesMap.get(snapName).getPrimKey();
        String[] primKeysArr = primKeys.split(",");

        // 获取主键index
        int[] prinKeyIndex = new int[primKeysArr.length];
        for (int i = 0; i < primKeysArr.length; i++) {
            prinKeyIndex[i] = Utils.getStrArrIndex(headerNow, primKeysArr[i]);
        }

        // 主键比较+主键查找+游标控制法

        // 获取原快照和新快照主键的值Set,多个主键值用逗号分隔
        LinkedHashSet<String> primKeyValuesSetBefore = new LinkedHashSet<String>();
        LinkedHashSet<String> primKeyValuesSetNow = new LinkedHashSet<String>();
        for (int i = 0; i < snapBeforeList.size(); i++) {
            String[] recordsLineBefore = snapBeforeList.get(i);
            StringBuffer keyValues = new StringBuffer();
            for (int j = 0; j < primKeysArr.length; j++) {
                keyValues.append(recordsLineBefore[prinKeyIndex[j]]);
                if (j < primKeysArr.length - 1) {
                    keyValues.append(",");
                }
            }
            primKeyValuesSetBefore.add(keyValues.toString());
        }
        for (int i = 0; i < snapNowList.size(); i++) {
            String[] recordsLineNow = snapNowList.get(i);
            StringBuffer keyValues = new StringBuffer();
            for (int j = 0; j < primKeysArr.length; j++) {
                keyValues.append(recordsLineNow[prinKeyIndex[j]]);
                if (j < primKeysArr.length - 1) {
                    keyValues.append(",");
                }
            }
            primKeyValuesSetNow.add(keyValues.toString());
        }
        if (snapBeforeList.size() > snapNowList.size()) {
            StatusPanel.progressCurrent.setMaximum(snapBeforeList.size());
        } else {
            StatusPanel.progressCurrent.setMaximum(snapNowList.size());
        }
        int progressValue = 0;
        StatusPanel.progressCurrent.setValue(progressValue);

        for (int flagBefore = 1, flagNow = 1; flagBefore < snapBeforeList.size() || flagNow < snapNowList.size(); ) {

            if (!(snapNowList.size() > flagNow)) {
                // 说明新快照到了结尾，后面没有内容了
                String[] recordsLineBefore = snapBeforeList.get(flagBefore);

                // 生成原快照该条记录主键map，key:主键名，value:主键值
                Map<String, String> primKeyAndValueMapBefore = new LinkedHashMap<>();
                for (int j = 0; j < primKeysArr.length; j++) {
                    primKeyAndValueMapBefore.put(primKeysArr[j], recordsLineBefore[prinKeyIndex[j]]);
                }

                StringBuffer primKeyValuesBefore = new StringBuffer();
                int k = 0;
                for (String key : primKeyAndValueMapBefore.keySet()) {
                    primKeyValuesBefore.append(primKeyAndValueMapBefore.get(key));
                    if (k < primKeyAndValueMapBefore.keySet().size() - 1) {
                        primKeyValuesBefore.append(",");
                    }
                    k++;
                }

                boolean isNowContainsBefore = primKeyValuesSetNow.contains(primKeyValuesBefore.toString());

                if (!isNowContainsBefore) {
                    // 生成主键map，key:主键名，value:主键值
                    Map<String, String> primKeyAndValueMap = new HashMap<>();

                    for (k = 0; k < primKeysArr.length; k++) {
                        primKeyAndValueMap.put(primKeysArr[k], recordsLineBefore[prinKeyIndex[k]]);
                    }

                    String sql = TriggerManage.getSqlDelete(snapName, primKeyAndValueMap, headerNow, recordsLineBefore);
                    sqlList.add(sql);
                    StatusLog.setStatusDetail(sql, LogLevel.DEBUG);
                }

                flagBefore++;
            } else if (!(snapBeforeList.size() > flagBefore)) {
                // 说明原快照到了结尾，后面没有内容了
                String[] recordsLineNow = snapNowList.get(flagNow);
                // 生成新快照该条记录主键map，key:主键名，value:主键值
                Map<String, String> primKeyAndValueMapNow = new LinkedHashMap<>();
                for (int j = 0; j < primKeysArr.length; j++) {
                    primKeyAndValueMapNow.put(primKeysArr[j], recordsLineNow[prinKeyIndex[j]]);
                }

                // 看新记录主键的值在原快照主键值set中是否存在
                StringBuffer primKeyValuesNow = new StringBuffer();
                int k = 0;
                for (String key : primKeyAndValueMapNow.keySet()) {
                    primKeyValuesNow.append(primKeyAndValueMapNow.get(key));
                    if (k < primKeyAndValueMapNow.keySet().size() - 1) {
                        primKeyValuesNow.append(",");
                    }
                    k++;
                }
                boolean isBeforeContainsNow = primKeyValuesSetBefore.contains(primKeyValuesNow.toString());
                if (!isBeforeContainsNow) {
                    // 生成主键map，key:主键名，value:主键值
                    Map<String, String> primKeyAndValueMap = new HashMap<>();
                    for (k = 0; k < primKeysArr.length; k++) {
                        primKeyAndValueMap.put(primKeysArr[k], recordsLineNow[prinKeyIndex[k]]);
                    }

                    String sql = TriggerManage.getSqlInsert(snapName, primKeyAndValueMap, headerNow, recordsLineNow);
                    sqlList.add(sql);
                    StatusLog.setStatusDetail(sql, LogLevel.DEBUG);
                }

                flagNow++;
            } else {

                String[] recordsLineBefore = snapBeforeList.get(flagBefore);
                String[] recordsLineNow = snapNowList.get(flagNow);

                // 生成原快照该条记录主键map，key:主键名，value:主键值
                Map<String, String> primKeyAndValueMapBefore = new LinkedHashMap<>();
                for (int j = 0; j < primKeysArr.length; j++) {
                    primKeyAndValueMapBefore.put(primKeysArr[j], recordsLineBefore[prinKeyIndex[j]]);
                }
                // 生成新快照该条记录主键map，key:主键名，value:主键值
                Map<String, String> primKeyAndValueMapNow = new LinkedHashMap<>();
                for (int j = 0; j < primKeysArr.length; j++) {
                    primKeyAndValueMapNow.put(primKeysArr[j], recordsLineNow[prinKeyIndex[j]]);
                }

                if (Arrays.equals(recordsLineNow, recordsLineBefore)) {
                    // 若完全一致，则继续下一行
                    flagBefore++;
                    flagNow++;
                    continue;
                } else {
                    // 先看原快照该条记录和新快照该条记录主键是否一致
                    boolean isPrimKeyTheSame = Utils.mapCompare4PrimKey(primKeyAndValueMapBefore,
                            primKeyAndValueMapNow);

                    if (isPrimKeyTheSame) {
                        // 若一致，说明该条记录内容有修改，需要触发Update
                        // 通过Trigger生成UpdateSQL
                        String sql = TriggerManage.getSqlUpdate(snapName, primKeyAndValueMapNow, headerNow,
                                recordsLineBefore, recordsLineNow);
                        sqlList.add(sql);
                        StatusLog.setStatusDetail(sql, LogLevel.DEBUG);
                    } else {
                        // 若不一致，看原记录主键的值在新快照主键值set中是否存在
                        StringBuffer primKeyValuesBefore = new StringBuffer();
                        int k = 0;
                        for (String key : primKeyAndValueMapBefore.keySet()) {
                            primKeyValuesBefore.append(primKeyAndValueMapBefore.get(key));
                            if (k < primKeyAndValueMapBefore.keySet().size() - 1) {
                                primKeyValuesBefore.append(",");
                            }
                            k++;
                        }

                        // 看新记录主键的值在原快照主键值set中是否存在
                        StringBuffer primKeyValuesNow = new StringBuffer();
                        k = 0;
                        for (String key : primKeyAndValueMapNow.keySet()) {
                            primKeyValuesNow.append(primKeyAndValueMapNow.get(key));
                            if (k < primKeyAndValueMapNow.keySet().size() - 1) {
                                primKeyValuesNow.append(",");
                            }
                            k++;
                        }
                        boolean isNowContainsBefore = primKeyValuesSetNow.contains(primKeyValuesBefore.toString());
                        boolean isBeforeContainsNow = primKeyValuesSetBefore.contains(primKeyValuesNow.toString());

                        if (isNowContainsBefore && !isBeforeContainsNow) {
                            // 说明新增了记录，则触发Insert
                            // 通过Trigger生成InsertSQL
                            String sql = TriggerManage.getSqlInsert(snapName, primKeyAndValueMapNow, headerNow,
                                    recordsLineNow);
                            sqlList.add(sql);
                            StatusLog.setStatusDetail(sql, LogLevel.DEBUG);

                            // 左边
                            String[] recordsLineNowTemp = snapNowList.get(
                                    Utils.getIndexInLinkedHashSet(primKeyValuesSetNow, primKeyValuesBefore.toString()));
                            if (!Arrays.equals(recordsLineNowTemp, recordsLineBefore)) {
                                // 若不完全一致，则Update
                                sql = TriggerManage.getSqlUpdate(snapName, primKeyAndValueMapBefore, headerNow,
                                        recordsLineBefore, recordsLineNowTemp);
                                sqlList.add(sql);
                                snapNowList.set(Utils.getIndexInLinkedHashSet(primKeyValuesSetNow,
                                        primKeyValuesBefore.toString()), recordsLineBefore);
                                StatusLog.setStatusDetail(sql, LogLevel.DEBUG);
                            }
                        } else if (!isNowContainsBefore && isBeforeContainsNow) {
                            // 说明原记录被删除了，则触发Delete
                            // 通过Trigger生成DeleteSQL
                            String sql = TriggerManage.getSqlDelete(snapName, primKeyAndValueMapBefore, headerNow,
                                    recordsLineBefore);
                            sqlList.add(sql);
                            StatusLog.setStatusDetail(sql, LogLevel.DEBUG);

                            // 右边
                            String[] recordsLineBeforeTemp = snapBeforeList.get(
                                    Utils.getIndexInLinkedHashSet(primKeyValuesSetBefore, primKeyValuesNow.toString()));
                            if (!Arrays.equals(recordsLineNow, recordsLineBeforeTemp)) {
                                // 若不完全一致，则Update
                                sql = TriggerManage.getSqlUpdate(snapName, primKeyAndValueMapNow, headerNow,
                                        recordsLineBeforeTemp, recordsLineNow);
                                sqlList.add(sql);
                                snapBeforeList.set(Utils.getIndexInLinkedHashSet(primKeyValuesSetBefore,
                                        primKeyValuesNow.toString()), recordsLineNow);
                                StatusLog.setStatusDetail(sql, LogLevel.DEBUG);
                            }
                        } else if (isNowContainsBefore && isBeforeContainsNow) {
                            // 说明该两条记录都有可能被更新了，还要继续拿出来比较一下

                            // 左边
                            String[] recordsLineNowTemp = snapNowList.get(
                                    Utils.getIndexInLinkedHashSet(primKeyValuesSetNow, primKeyValuesBefore.toString()));
                            if (!Arrays.equals(recordsLineNowTemp, recordsLineBefore)) {
                                // 若不完全一致，则Update
                                String sql = TriggerManage.getSqlUpdate(snapName, primKeyAndValueMapBefore, headerNow,
                                        recordsLineBefore, recordsLineNowTemp);
                                sqlList.add(sql);
                                snapNowList.set(Utils.getIndexInLinkedHashSet(primKeyValuesSetNow,
                                        primKeyValuesBefore.toString()), recordsLineBefore);
                                // snapBeforeList.remove(flagBefore);
                                StatusLog.setStatusDetail(sql, LogLevel.DEBUG);
                            }

                            // 右边
                            String[] recordsLineBeforeTemp = snapBeforeList.get(
                                    Utils.getIndexInLinkedHashSet(primKeyValuesSetBefore, primKeyValuesNow.toString()));
                            if (!Arrays.equals(recordsLineNow, recordsLineBeforeTemp)) {
                                // 若不完全一致，则Update
                                String sql = TriggerManage.getSqlUpdate(snapName, primKeyAndValueMapNow, headerNow,
                                        recordsLineBeforeTemp, recordsLineNow);
                                sqlList.add(sql);
                                snapBeforeList.set(Utils.getIndexInLinkedHashSet(primKeyValuesSetBefore,
                                        primKeyValuesNow.toString()), recordsLineNow);
                                // snapNowList.remove(flagNow);
                                StatusLog.setStatusDetail(sql, LogLevel.DEBUG);
                            }

                        } else if (!isNowContainsBefore && !isBeforeContainsNow) {
                            // 既说明原记录被删除了，则触发Delete
                            // 又说明新纪录被插入了，则触发Insert
                            // 通过Trigger生成SQL
                            String sql = TriggerManage.getSqlDelete(snapName, primKeyAndValueMapBefore, headerNow,
                                    recordsLineBefore);
                            sqlList.add(sql);
                            StatusLog.setStatusDetail(sql, LogLevel.DEBUG);

                            sql = TriggerManage.getSqlInsert(snapName, primKeyAndValueMapNow, headerNow,
                                    recordsLineNow);
                            sqlList.add(sql);
                            StatusLog.setStatusDetail(sql, LogLevel.DEBUG);
                        }
                    }
                    flagBefore++;
                    flagNow++;
                }
            }
            progressValue++;
            StatusPanel.progressCurrent.setValue(progressValue);
        }
        return isSuccess;
    }

}