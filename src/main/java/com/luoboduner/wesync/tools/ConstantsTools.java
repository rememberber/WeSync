package com.luoboduner.wesync.tools;

import com.luoboduner.wesync.ui.UiConsts;

import java.io.File;

/**
 * 工具层相关的常量
 *
 * @author Bob
 */
public class ConstantsTools {

    // 配置文件
    /**
     * 配置文件 路径
     */
    public final static String PATH_CONFIG = UiConsts.CURRENT_DIR + File.separator + "config" + File.separator
            + "config.xml";

    /**
     * properties路径
     */
    public final static String PATH_PROPERTY = UiConsts.CURRENT_DIR + File.separator + "config" + File.separator
            + "zh-cn.properties";
    /**
     * 配置文件dom实例
     */
    public final static ConfigManager CONFIGER = ConfigManager.getConfigManager();
    /**
     * xpath
     */
    public final static String XPATH_LAST_SYNC_TIME = "//weSync/status/lastSyncTime";
    public final static String XPATH_LAST_KEEP_TIME = "//weSync/status/lastKeepTime";
    public final static String XPATH_SUCCESS_TIME = "//weSync/status/successTime";
    public final static String XPATH_FAIL_TIME = "//weSync/status/failTime";

    public final static String XPATH_TYPE_FROM = "//weSync/database/from/type";
    public final static String XPATH_HOST_FROM = "//weSync/database/from/host";
    public final static String XPATH_NAME_FROM = "//weSync/database/from/name";
    public final static String XPATH_USER_FROM = "//weSync/database/from/user";
    public final static String XPATH_PASSWORD_FROM = "//weSync/database/from/password";

    public final static String XPATH_TYPE_TO = "//weSync/database/to/type";
    public final static String XPATH_HOST_TO = "//weSync/database/to/host";
    public final static String XPATH_NAME_TO = "//weSync/database/to/name";
    public final static String XPATH_USER_TO = "//weSync/database/to/user";
    public final static String XPATH_PASSWORD_TO = "//weSync/database/to/password";

    public final static String XPATH_SCHEDULE = "//weSync/schedule/radio";
    public final static String XPATH_SCHEDULE_FIX_TIME = "//weSync/schedule/fixtime";

    public final static String XPATH_AUTO_BAK = "//weSync/setting/autoBak";
    public final static String XPATH_DEBUG_MODE = "//weSync/setting/debugMode";
    public final static String XPATH_STRICT_MODE = "//weSync/setting/strictMode";
    public final static String XPATH_MYSQL_PATH = "//weSync/setting/mysqlPath";
    public final static String XPATH_PRODUCT_NAME = "//weSync/setting/productname";

    public final static String XPATH_POSITION_CODE = "//weSync/increase/POSITION_CODE";

    /**
     * 日志文件 路径
     */
    public final static String PATH_LOG = UiConsts.CURRENT_DIR + File.separator + "log" + File.separator + "log.log";
}
