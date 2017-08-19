package UI;

import tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * UI相关的常量
 *
 * @author Bob
 */
public class ConstantsUI {

    /**
     * 软件名称,版本
     */
    public final static String APP_NAME = "Data Sync";
    public final static String APP_VERSION = "v_1.60_160511";

    /**
     * 主窗口大小
     */
    public final static int MAIN_WINDOW_X = 240;
    public final static int MAIN_WINDOW_Y = 100;
    public final static int MAIN_WINDOW_WIDTH = 885;
    public final static int MAIN_WINDOW_HEIGHT = 636;

    /**
     * 系统当前路径
     */
    public final static String CURRENT_DIR = System.getProperty("user.dir");

    /**
     * 主窗口图标
     */
    public final static Image IMAGE_ICON = Toolkit.getDefaultToolkit()
            .getImage(CURRENT_DIR + File.separator + "icon" + File.separator + "DataSync.png");

    /**
     * 主窗口背景色
     */
    public final static Color MAIN_BACK_COLOR = Color.WHITE;

    /**
     * 工具栏背景色
     */
    public final static Color TOOL_BAR_BACK_COLOR = new Color(37, 174, 96);
    /**
     * 表格线条背景色
     */
    public final static Color TABLE_LINE_COLOR = new Color(229, 229, 229);

    /**
     * 字体
     */
    // 标题字体
    public final static Font FONT_TITLE = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 27);
    // 普通字体
    public final static Font FONT_NORMAL = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 13);
    // radio字体
    public final static Font FONT_RADIO = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 15);

    /**
     * 主图标
     */
    public final static ImageIcon ICON_DATA_SYNC = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "DataSync.png");
    /**
     * 工具栏图标
     */
    // 状态 默认
    public final static ImageIcon ICON_STATUS = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "status.png");
    // 状态 激活
    public final static ImageIcon ICON_STATUS_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "statusEnable.png");
    // 数据库 默认
    public final static ImageIcon ICON_DATABASE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "database.png");
    // 数据库 激活
    public final static ImageIcon ICON_DATABASE_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "databaseEnable.png");
    // 执行计划 默认
    public final static ImageIcon ICON_SCHEDULE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "schedule.png");
    // 执行计划 激活
    public final static ImageIcon ICON_SCHEDULE_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "scheduleEnable.png");
    // 设置 默认
    public final static ImageIcon ICON_SETTING = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "setting.png");
    // 设置 激活
    public final static ImageIcon ICON_SETTING_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "settingEnable.png");
    // 备份 默认
    public final static ImageIcon ICON_BACKUP = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "backup.png");
    // 备份 激活
    public final static ImageIcon ICON_BACKUP_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "backupEnable.png");

    /**
     * 按钮 图标
     */
    // 按计划执行 默认
    public final static ImageIcon ICON_START_SCHEDULE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "startSchedule.png");
    // 按计划执行 激活
    public final static ImageIcon ICON_START_SCHEDULE_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "startScheduleEnable.png");
    // 按计划执行 失效
    public final static ImageIcon ICON_START_SCHEDULE_DISABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "startScheduleDisable.png");
    // 停止 默认
    public final static ImageIcon ICON_STOP = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "stop.png");
    // 停止 激活
    public final static ImageIcon ICON_STOP_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "stopEnable.png");
    // 停止 失效
    public final static ImageIcon ICON_STOP_DISABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "stopDisable.png");
    // 立即同步 默认
    public final static ImageIcon ICON_SYNC_NOW = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "syncNow.png");
    // 立即同步 激活
    public final static ImageIcon ICON_SYNC_NOW_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "syncNowEnable.png");
    // 立即同步 失效
    public final static ImageIcon ICON_SYNC_NOW_DISABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "syncNowDisable.png");
    // 测试连接 默认
    public final static ImageIcon ICON_TEST_LINK = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "testLinkButton.png");
    // 测试连接 激活
    public final static ImageIcon ICON_TEST_LINK_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "testLinkButtonEnable.png");
    // 测试连接 失效
    public final static ImageIcon ICON_TEST_LINK_DISABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "testLinkButtonDisable.png");
    // 保存 默认
    public final static ImageIcon ICON_SAVE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "saveButton.png");
    // 保存 激活
    public final static ImageIcon ICON_SAVE_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "saveButtonEnable.png");
    // 保存 失效
    public final static ImageIcon ICON_SAVE_DISABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "saveButtonDisable.png");
    // 新建备份 默认
    public final static ImageIcon ICON_NEW_BAK = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "newBak.png");
    // 新建备份 激活
    public final static ImageIcon ICON_NEW_BAK_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "newBakEnable.png");
    // 新建备份 失效
    public final static ImageIcon ICON_NEW_BAK_DISABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "newBakDisable.png");
    // 删除备份 默认
    public final static ImageIcon ICON_DEL_BAK = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "deleteBak.png");
    // 删除备份 激活
    public final static ImageIcon ICON_DEL_BAK_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "deleteBakEnable.png");
    // 删除备份 失效
    public final static ImageIcon ICON_DEL_BAK_DISABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "deleteBakDisable.png");
    // 还原备份 默认
    public final static ImageIcon ICON_RECOVER_BAK = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "recoverBak.png");
    // 还原备份 激活
    public final static ImageIcon ICON_RECOVER_BAK_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "recoverBakEnable.png");
    // 还原备份 失效
    public final static ImageIcon ICON_RECOVER_BAK_DISABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "recoverBakDisable.png");

    // 清空所有备份 默认
    public final static ImageIcon ICON_CLEAR_ALL_BAKS = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "clearAllBaks.png");
    // 清空所有备份 激活
    public final static ImageIcon ICON_CLEAR_ALL_BAKS_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "clearAllBaksEnable.png");
    // 清空所有备份 失效
    public final static ImageIcon ICON_CLEAR_ALL_BAKS_DISABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "clearAllBaksDisable.png");

    // 表字段对应关系 默认
    public final static ImageIcon ICON_TABLE_FIELD = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "tableFiled.png");
    // 表字段对应关系 激活
    public final static ImageIcon ICON_TABLE_FIELD_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "tableFiledEnable.png");
    // 表字段对应关系 失效
    public final static ImageIcon ICON_TABLE_FIELD_DISABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "tableFiledDisable.png");
    // 清空Log 默认
    public final static ImageIcon ICON_CLEAR_LOG = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "clearLog.png");
    // 清空Log 激活
    public final static ImageIcon ICON_CLEAR_LOG_ENABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "clearLogEnable.png");
    // 清空Log 失效
    public final static ImageIcon ICON_CLEAR_LOG_DISABLE = new ImageIcon(
            CURRENT_DIR + File.separator + "icon" + File.separator + "clearLogDisable.png");

    /**
     * 样式布局相关
     */
    // 主面板水平间隔
    public final static int MAIN_H_GAP = 25;
    // 主面板Label 大小
    public final static Dimension LABLE_SIZE = new Dimension(1300, 30);
    // Item Label 大小
    public final static Dimension LABLE_SIZE_ITEM = new Dimension(78, 30);
    // Item text field 大小
    public final static Dimension TEXT_FIELD_SIZE_ITEM = new Dimension(400, 24);
    // radio 大小
    public final static Dimension RADIO_SIZE = new Dimension(1300, 60);
    // 高级选项面板Item 大小
    public final static Dimension PANEL_ITEM_SIZE = new Dimension(1300, 40);

}
