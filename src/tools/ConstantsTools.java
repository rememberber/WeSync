package tools;

import UI.ConstantsUI;

import java.io.File;

/**
 * 工具层相关的常量
 * 
 * @author Bob
 *
 */
public class ConstantsTools {

	/**
	 * 配置文件
	 */
	// 配置文件 路径
	public final static String PATH_CONFIG = ConstantsUI.CURRENT_DIR + File.separator + "config" + File.separator
			+ "config.xml";

	/**
	 * properties路径
	 */
	public final static String PATH_PROPERTY = ConstantsUI.CURRENT_DIR + File.separator + "config" + File.separator
			+ "zh-cn.properties";
	// 配置文件dom实例
	public final static ConfigManager CONFIGER = ConfigManager.getConfigManager();
	// xpath
	public final static String XPATH_LAST_SYNC_TIME = "//dataSync/status/lastSyncTime";
	public final static String XPATH_LAST_KEEP_TIME = "//dataSync/status/lastKeepTime";
	public final static String XPATH_SUCCESS_TIME = "//dataSync/status/successTime";
	public final static String XPATH_FAIL_TIME = "//dataSync/status/failTime";

	public final static String XPATH_TYPE_FROM = "//dataSync/database/from/type";
	public final static String XPATH_HOST_FROM = "//dataSync/database/from/host";
	public final static String XPATH_NAME_FROM = "//dataSync/database/from/name";
	public final static String XPATH_USER_FROM = "//dataSync/database/from/user";
	public final static String XPATH_PASSWORD_FROM = "//dataSync/database/from/password";

	public final static String XPATH_TYPE_TO = "//dataSync/database/to/type";
	public final static String XPATH_HOST_TO = "//dataSync/database/to/host";
	public final static String XPATH_NAME_TO = "//dataSync/database/to/name";
	public final static String XPATH_USER_TO = "//dataSync/database/to/user";
	public final static String XPATH_PASSWORD_TO = "//dataSync/database/to/password";

	public final static String XPATH_SCHEDULE = "//dataSync/schedule/radio";
	public final static String XPATH_SCHEDULE_FIX_TIME = "//dataSync/schedule/fixtime";

	public final static String XPATH_AUTO_BAK = "//dataSync/setting/autoBak";
	public final static String XPATH_DEBUG_MODE = "//dataSync/setting/debugMode";
	public final static String XPATH_STRICT_MODE = "//dataSync/setting/strictMode";
	public final static String XPATH_MYSQL_PATH = "//dataSync/setting/mysqlPath";
	public final static String XPATH_PRODUCT_NAME = "//dataSync/setting/productname";

	public final static String XPATH_POSITION_CODE = "//dataSync/increase/POSITION_CODE";

	// 日志文件 路径
	public final static String PATH_LOG = ConstantsUI.CURRENT_DIR + File.separator + "log" + File.separator + "log.log";
}
