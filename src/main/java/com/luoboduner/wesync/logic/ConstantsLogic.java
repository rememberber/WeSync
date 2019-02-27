package com.luoboduner.wesync.logic;

import java.io.File;

import com.luoboduner.wesync.ui.UiConsts;

/**
 * Logic层的常量类
 *
 * @author Bob
 */
public class ConstantsLogic {

    // 各配置文件路径
    /**
     * 表-字段配置文件 路径
     */
    public final static String TABLE_FIELD_DIR = UiConsts.CURRENT_DIR + File.separator + "TableField";
    /**
     * 表-字段配置文件 路径
     */
    public final static String TABLE_FIELD_INIT_DIR = UiConsts.CURRENT_DIR + File.separator + "TableField"
            + File.separator + "init";
    /**
     * Trigger配置文件
     */
    public final static String TRIGGER_FILE = UiConsts.CURRENT_DIR + File.separator + "Trigger" + File.separator
            + "Trigger.txt";
    /**
     * 表快照文件 路径
     */
    public final static String SNAPS_DIR = UiConsts.CURRENT_DIR + File.separator + "snaps";
    /**
     * 表快照文件备份 路径
     */
    public final static String SNAPS_BAK_DIR = UiConsts.CURRENT_DIR + File.separator + "snaps_bak";
    /**
     * sql日志文件路径
     */
    public final static String LOG_SQL_DIR = UiConsts.CURRENT_DIR + File.separator + "log_SQL";
    /**
     * sql日志文件
     */
    public final static String LOG_SQL = UiConsts.CURRENT_DIR + File.separator + "log_SQL" + File.separator
            + "log_sql.csv";

    /**
     * mysql整库备份bat文件路径
     */
    public final static String BAT_DIR_MYSQL = UiConsts.CURRENT_DIR + File.separator + "DB_Backup" + File.separator
            + "mysql_backup.bat";

    /**
     * mysql表备份sql文件路径
     */
    public final static String MYSQL_TABLE_BACKUP_SQL_FILE = UiConsts.CURRENT_DIR + File.separator + "DB_Backup"
            + File.separator + "mysql_table_backup.sql";

    /**
     * mysql备份sql文件路径
     */
    public final static String PATH_MYSQL_BAK = UiConsts.CURRENT_DIR + File.separator + "DB_Backup" + File.separator
            + "Target";

    // 正则表达式
    /**
     * trigger配置文件，解析表名的正则
     */
    public final static String REGEX_TRIGGER_TABLE = "<([^<>]+)>";
    /**
     * trigger配置文件，解析表主键的正则
     */
    public final static String REGEX_TRIGGER_PRIM_KEY = "\\(([^()]+)\\)";
    /**
     * trigger配置文件，解析表字段的正则
     */
    public final static String REGEX_TRIGGER_FIELDS = "\\{([^{}]+)\\}";
    /**
     * trigger配置文件，解析“其他条件或保留”的正则
     */
    public final static String REGEX_TRIGGER_OTHER = "\\[([^\\[\\]]+)\\]";

}