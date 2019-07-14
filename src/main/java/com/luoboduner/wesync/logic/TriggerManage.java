package com.luoboduner.wesync.logic;

import com.luoboduner.wesync.tools.ConstantsTools;
import com.luoboduner.wesync.tools.DbUtilMySQL;
import com.luoboduner.wesync.tools.DbUtilSQLServer;
import com.luoboduner.wesync.tools.Utils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bob
 */
public class TriggerManage {
    public static DbUtilSQLServer SQLServer;
    public static DbUtilMySQL MySQL;

    private static final Pattern THIS_PATTERN = Pattern.compile("this\\.[.\\w]+");
    private static final Pattern BEFORE_PATTERN = Pattern.compile("before\\.[.\\w]+");
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("\\$([^$]+)\\$");
    private static final Pattern BRACE_PATTERN = Pattern.compile("\\{([^{}]+)\\}");
    private static final Pattern AT_SYMBOL_PATTERN = Pattern.compile("\\@([^@]+)\\@");
    private static final Pattern BRACKET_PATTERN = Pattern.compile("\\(([^()]+)\\)");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");
    private static final Pattern HASH_MARK_PATTERN = Pattern.compile("\\#([^#]+)\\#");

    public static String getSqlUpdate(String snapName, Map<String, String> primKeyAndValueMap, String[] headerNow,
                                      String[] recordsLineBefore, String[] recordsLineNow) throws SQLException {

        StringBuffer sql = new StringBuffer();

        // 当前record,Key:列名Value:列值
        Map<String, String> recordNowMap = new HashMap<>();
        for (int i = 0; i < headerNow.length; i++) {
            recordNowMap.put(headerNow[i], recordsLineNow[i]);
        }

        // 原record,Key:列名Value:列值
        Map<String, String> recordBeforeMap = new HashMap<>();
        for (int i = 0; i < headerNow.length; i++) {
            recordBeforeMap.put(headerNow[i], recordsLineBefore[i]);
        }

        // 触发的目标表
        String[] targetTables = ExecuteThread.triggerMap.get(snapName);
        for (String targetTable : targetTables) {
            ArrayList<String> list = ExecuteThread.tableFieldMap.get(targetTable + ".UPDATE");
            for (String string : list) {
                sql.append(repairSqlString(string, recordBeforeMap, recordNowMap) + " ");
            }
        }

        return sql.toString();
    }

    /**
     * @param primKeyAndValueMap
     * @param headerNow
     * @param recordsLineNow
     * @return
     * @throws SQLException
     */
    public static String getSqlInsert(String snapName, Map<String, String> primKeyAndValueMap, String[] headerNow,
                                      String[] recordsLineNow) throws SQLException {
        StringBuffer sql = new StringBuffer();

        // 当前record,Key:列名Value:列值
        Map<String, String> recordNowMap = new HashMap<>();
        for (int i = 0; i < headerNow.length; i++) {
            recordNowMap.put(headerNow[i], recordsLineNow[i]);
        }

        // 触发的目标表
        String[] targetTables = ExecuteThread.triggerMap.get(snapName);
        for (String targetTable : targetTables) {
            ArrayList<String> list = null;
            list = ExecuteThread.tableFieldMap.get(targetTable + ".INSERT");
            for (String string : list) {
                sql.append(repairSqlString(string, null, recordNowMap) + " ");
            }
        }

        return sql.toString();
    }

    public static String getSqlDelete(String snapName, Map<String, String> primKeyAndValueMap, String[] headerNow,
                                      String[] recordsLineBefore) throws SQLException {
        StringBuffer sql = new StringBuffer();
        // 原record,Key:列名Value:列值
        Map<String, String> recordBeforeMap = new HashMap<>();
        for (int i = 0; i < headerNow.length; i++) {
            recordBeforeMap.put(headerNow[i], recordsLineBefore[i]);
        }
        // 触发的目标表
        String[] targetTables = ExecuteThread.triggerMap.get(snapName);
        for (String targetTable : targetTables) {
            ArrayList<String> list = ExecuteThread.tableFieldMap.get(targetTable + ".DELETE");
            for (String string : list) {
                sql.append(repairSqlString(string, recordBeforeMap, null) + " ");
            }
        }

        return sql.toString();
    }

    /**
     * 解析sql中的公式
     *
     * @param string
     * @param recordNowMap
     * @return
     * @throws SQLException
     */
    private static String repairSqlString(String string, Map<String, String> recordBeforeMap,
                                          Map<String, String> recordNowMap) throws SQLException {

        SQLServer = DbUtilSQLServer.getInstance();

        if (string.contains("this.")) {
            // 第一级别公式：替换this.的内容
            // this.***：取当前快照该条记录的某字段值
            // before.***：取原快照该条记录的某字段值（仅UPDATE和DELETE时会用到）

            Matcher matcher = THIS_PATTERN.matcher(string);
            ArrayList<String> strs = new ArrayList<String>();
            while (matcher.find()) {
                strs.add(matcher.group(0));
            }
            for (String str : strs) {
                if (recordNowMap.containsKey(str.substring(str.indexOf(".") + 1))) {
                    string = string.replaceAll(str, recordNowMap.get(str.substring(str.indexOf(".") + 1)));
                }

            }
        }
        if (string.contains("before.")) {
            // 第一级别公式：替换before.的内容
            // this.***：取当前快照该条记录的某字段值
            // before.***：取原快照该条记录的某字段值（仅UPDATE和DELETE时会用到）
            Matcher matcher = BEFORE_PATTERN.matcher(string);
            ArrayList<String> strs = new ArrayList<String>();
            while (matcher.find()) {
                strs.add(matcher.group(0));
            }
            for (String str : strs) {
                if (recordBeforeMap.containsKey(str.substring(str.indexOf(".") + 1))) {
                    string = string.replaceAll(str, recordBeforeMap.get(str.substring(str.indexOf(".") + 1)));
                }

            }
        }
        if (string.contains("$")) {
            // 第二级别公式：
            // $INCREASE{POSITION_CODE}$：将大括号内的持久化变量递增（*在config文件中设置持久化变量）
            // $PINYIN{}$：将大括号内的汉字转为拼音
            // $SYS_DATE_TIME$：系统日期+时间（2016-01-19 17:57:49）
            // $SQL{}$：执行大括号内的子查询sql，返回对应的查询结果（仅限一个）
            Matcher matcher = EXPRESSION_PATTERN.matcher(string);
            ArrayList<String> strs = new ArrayList<String>();
            while (matcher.find()) {
                strs.add(matcher.group(0));
            }
            for (String str : strs) {
                if (str.startsWith("$PINYIN")) {
                    matcher = BRACE_PATTERN.matcher(str);
                    while (matcher.find()) {
                        string = string.replace(str, Utils.getPingYin(matcher.group(1).trim()));
                    }
                } else if (str.startsWith("$SQL")) {
                    matcher = BRACE_PATTERN.matcher(str);
                    while (matcher.find()) {
                        String tempSql = matcher.group(1);
                        ResultSet rs = SQLServer.executeQuery(tempSql);
                        String temp = "";
                        int rowCount = 0;
                        while (rs.next()) {
                            temp = rs.getString(1);
                            rowCount++;
                        }
                        if (rowCount != 1 || temp == null) {
                            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            temp = "";
                        }
                        string = string.replace(str, temp);
                    }
                } else if (str.startsWith("$SYS_DATE_TIME")) {
                    string = string.replace(str, Utils.getCurrentTime());
                } else if (str.startsWith("$INCREASE")) {
                    matcher = BRACE_PATTERN.matcher(str);
                    String para = "";
                    while (matcher.find()) {
                        para = matcher.group(1);
                    }
                    if ("POSITION_CODE".equals(para)) {
                        String positionCode = ConstantsTools.CONFIGER.getPositionCode();// 从config文件取出持久化的参数值

                        string = string.replace(str, String.format("%04d", Integer.parseInt(positionCode))); // 不足4位在前面补0

                        int pCode = Integer.parseInt(positionCode) + 1;// 加1之后进行持久化
                        try {
                            ConstantsTools.CONFIGER.setPositionCode(String.valueOf(pCode));
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        string = string.replace(str, "");
                    }

                }
            }
        }

        if (string.contains("@")) {
            // 第三级别公式：
            // @SUB(5,8){}@：将大括号内的字符串从第5个字符截取到第8个字符
            // @SUB(5,END){}@：将大括号内的字符串从第5个字符截取到最后
            // @SUB(0,LAST"."){}@：将大括号内的字符串从第0个字符截取到最后一个"."
            // @SUB("a",LAST"a"){}@：将大括号内的字符串从第1个"a"截取到最后一个"a"（不含左,不含右）
            Matcher matcher = AT_SYMBOL_PATTERN.matcher(string);
            ArrayList<String> strs = new ArrayList<String>();
            while (matcher.find()) {
                strs.add(matcher.group(0));
            }
            for (String str : strs) {
                if (str.startsWith("@SUB")) {
                    // 先获取要截取的index和字符串内容
                    String[] indexs = null;
                    String strContent = "";
                    matcher = BRACKET_PATTERN.matcher(str);
                    while (matcher.find()) {
                        indexs = matcher.group(1).split(",");
                    }
                    matcher = BRACE_PATTERN.matcher(str);
                    while (matcher.find()) {
                        strContent = matcher.group(1);
                    }
                    int beginIndex = 0;
                    int endIndex = 0;

                    if (NUMBER_PATTERN.matcher(indexs[0]).matches()) {
                        beginIndex = Integer.parseInt(indexs[0]);
                    } else {
                        beginIndex = strContent.indexOf(indexs[0].replaceAll("\"", "")) + 1;
                    }

                    if (NUMBER_PATTERN.matcher(indexs[1]).matches()) {
                        endIndex = Integer.parseInt(indexs[1]);
                    } else if ("END".equals(indexs[1])) {
                        endIndex = strContent.length();
                    } else if (indexs[1].startsWith("LAST")) {
                        // if (strContent.contains(indexs[1].substring(5,
                        // indexs[1].length() - 1))) {
                        endIndex = strContent.lastIndexOf(indexs[1].substring(5, indexs[1].length() - 1));
                        // } else {
                        // endIndex = strContent.length();
                        // }
                    }
                    string = string.replace(str, strContent.substring(beginIndex, endIndex));
                }
            }
        }

        if (string.contains("#")) {
            // 第四级别公式：
            // #REPLACE("a","b"){}#将大括号内容中所有的"a"替换为"b"
            // #CASE(a=b,c=d,e=f){}#：如果大括号里边的内容是a，则替换为b；如果为c，则替换为d...
            Matcher matcher = HASH_MARK_PATTERN.matcher(string);
            ArrayList<String> strs = new ArrayList<String>();
            while (matcher.find()) {
                strs.add(matcher.group(0));
            }
            for (String str : strs) {
                if (str.startsWith("#REPLACE")) {
                    // 先获取要替换的新旧字符串和内容
                    String[] indexs = null;
                    String strContent = "";
                    matcher = BRACKET_PATTERN.matcher(str);
                    while (matcher.find()) {
                        indexs = matcher.group(1).split(",");
                    }
                    matcher = BRACE_PATTERN.matcher(str);
                    while (matcher.find()) {
                        strContent = matcher.group(1);
                    }
                    String oldStr = "";
                    String newStr = "";

                    oldStr = indexs[0].replaceAll("\"", "");
                    newStr = indexs[1].replaceAll("\"", "");

                    String temp = strContent.replace(oldStr, newStr);
                    string = string.replace(str, temp);
                } else if (str.startsWith("#CASE")) {

                    String[] keyValue = null;
                    String strContent = "";
                    matcher = BRACKET_PATTERN.matcher(str);
                    while (matcher.find()) {
                        keyValue = matcher.group(1).split(",");
                    }
                    matcher = BRACE_PATTERN.matcher(str);
                    while (matcher.find()) {
                        strContent = matcher.group(1);
                    }

                    HashMap<String, String> map = new HashMap<String, String>();
                    for (String kV : keyValue) {
                        String[] temp = kV.split("=");
                        map.put(temp[0], temp[1]);
                    }

                    if (map.containsKey(strContent)) {
                        string = string.replace(str, map.get(strContent));
                    } else {
                        string = string.replace(str, "");
                    }

                } else if (str.startsWith("#MD5PE")) {
                    String strContent = "";

                    matcher = BRACE_PATTERN.matcher(str);
                    while (matcher.find()) {
                        strContent = matcher.group(1);
                    }
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    string = string.replace(str, bCryptPasswordEncoder.encode(strContent));
                }
            }
        }

        return string;
    }

}
