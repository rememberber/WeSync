package com.luoboduner.wesync.ui;

import com.luoboduner.wesync.App;
import com.luoboduner.wesync.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;

/**
 * UI相关的常量
 *
 * @author Bob
 */
public class UiConsts {

    /**
     * 软件名称,版本
     */
    public final static String APP_NAME = "WeSync";
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
            .getImage(App.class.getResource("/icon/WeSync.png"));

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

    // 字体
    /**
     * 标题字体
     */
    public final static Font FONT_TITLE = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 27);
    /**
     * 普通字体
     */
    public final static Font FONT_NORMAL = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 13);
    /**
     * radio字体
     */
    public final static Font FONT_RADIO = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 15);

    /**
     * 主图标
     */
    public final static ImageIcon ICON_DATA_SYNC = new ImageIcon(
            App.class.getResource("/icon/WeSync.png"));
    // 工具栏图标
    /**
     * 状态 默认
     */
    public final static ImageIcon ICON_STATUS = new ImageIcon(
            App.class.getResource("/icon/status.png"));
    /**
     * 状态 激活
     */
    public final static ImageIcon ICON_STATUS_ENABLE = new ImageIcon(
            App.class.getResource("/icon/statusEnable.png"));
    /**
     * 数据库 默认
     */
    public final static ImageIcon ICON_DATABASE = new ImageIcon(
            App.class.getResource("/icon/database.png"));
    /**
     * 数据库 激活
     */
    public final static ImageIcon ICON_DATABASE_ENABLE = new ImageIcon(
            App.class.getResource("/icon/databaseEnable.png"));
    /**
     * 执行计划 默认
     */
    public final static ImageIcon ICON_SCHEDULE = new ImageIcon(
            App.class.getResource("/icon/schedule.png"));
    /**
     * 执行计划 激活
     */
    public final static ImageIcon ICON_SCHEDULE_ENABLE = new ImageIcon(
            App.class.getResource("/icon/scheduleEnable.png"));
    /**
     * 设置 默认
     */
    public final static ImageIcon ICON_SETTING = new ImageIcon(
            App.class.getResource("/icon/setting.png"));
    /**
     * 设置 激活
     */
    public final static ImageIcon ICON_SETTING_ENABLE = new ImageIcon(
            App.class.getResource("/icon/settingEnable.png"));
    /**
     * 备份 默认
     */
    public final static ImageIcon ICON_BACKUP = new ImageIcon(
            App.class.getResource("/icon/backup.png"));
    /**
     * 备份 激活
     */
    public final static ImageIcon ICON_BACKUP_ENABLE = new ImageIcon(
            App.class.getResource("/icon/backupEnable.png"));

    // 按钮 图标
    /**
     * 按计划执行 默认
     */
    public final static ImageIcon ICON_START_SCHEDULE = new ImageIcon(
            App.class.getResource("/icon/startSchedule.png"));
    /**
     * 按计划执行 激活
     */
    public final static ImageIcon ICON_START_SCHEDULE_ENABLE = new ImageIcon(
            App.class.getResource("/icon/startScheduleEnable.png"));
    /**
     * 按计划执行 失效
     */
    public final static ImageIcon ICON_START_SCHEDULE_DISABLE = new ImageIcon(
            App.class.getResource("/icon/startScheduleDisable.png"));
    /**
     * 停止 默认
     */
    public final static ImageIcon ICON_STOP = new ImageIcon(
            App.class.getResource("/icon/stop.png"));
    /**
     * 停止 激活
     */
    public final static ImageIcon ICON_STOP_ENABLE = new ImageIcon(
            App.class.getResource("/icon/stopEnable.png"));
    /**
     * 停止 失效
     */
    public final static ImageIcon ICON_STOP_DISABLE = new ImageIcon(
            App.class.getResource("/icon/stopDisable.png"));
    /**
     * 立即同步 默认
     */
    public final static ImageIcon ICON_SYNC_NOW = new ImageIcon(
            App.class.getResource("/icon/syncNow.png"));
    /**
     * 立即同步 激活
     */
    public final static ImageIcon ICON_SYNC_NOW_ENABLE = new ImageIcon(
            App.class.getResource("/icon/syncNowEnable.png"));
    /**
     * 立即同步 失效
     */
    public final static ImageIcon ICON_SYNC_NOW_DISABLE = new ImageIcon(
            App.class.getResource("/icon/syncNowDisable.png"));
    /**
     * 测试连接 默认
     */
    public final static ImageIcon ICON_TEST_LINK = new ImageIcon(
            App.class.getResource("/icon/testLinkButton.png"));
    /**
     * 测试连接 激活
     */
    public final static ImageIcon ICON_TEST_LINK_ENABLE = new ImageIcon(
            App.class.getResource("/icon/testLinkButtonEnable.png"));
    /**
     * 测试连接 失效
     */
    public final static ImageIcon ICON_TEST_LINK_DISABLE = new ImageIcon(
            App.class.getResource("/icon/testLinkButtonDisable.png"));
    /**
     * 保存 默认
     */
    public final static ImageIcon ICON_SAVE = new ImageIcon(
            App.class.getResource("/icon/saveButton.png"));
    /**
     * 保存 激活
     */
    public final static ImageIcon ICON_SAVE_ENABLE = new ImageIcon(
            App.class.getResource("/icon/saveButtonEnable.png"));
    /**
     * 保存 失效
     */
    public final static ImageIcon ICON_SAVE_DISABLE = new ImageIcon(
            App.class.getResource("/icon/saveButtonDisable.png"));
    /**
     * 新建备份 默认
     */
    public final static ImageIcon ICON_NEW_BAK = new ImageIcon(
            App.class.getResource("/icon/newBak.png"));
    /**
     * 新建备份 激活
     */
    public final static ImageIcon ICON_NEW_BAK_ENABLE = new ImageIcon(
            App.class.getResource("/icon/newBakEnable.png"));
    /**
     * 新建备份 失效
     */
    public final static ImageIcon ICON_NEW_BAK_DISABLE = new ImageIcon(
            App.class.getResource("/icon/newBakDisable.png"));
    /**
     * 删除备份 默认
     */
    public final static ImageIcon ICON_DEL_BAK = new ImageIcon(
            App.class.getResource("/icon/deleteBak.png"));
    /**
     * 删除备份 激活
     */
    public final static ImageIcon ICON_DEL_BAK_ENABLE = new ImageIcon(
            App.class.getResource("/icon/deleteBakEnable.png"));
    /**
     * 删除备份 失效
     */
    public final static ImageIcon ICON_DEL_BAK_DISABLE = new ImageIcon(
            App.class.getResource("/icon/deleteBakDisable.png"));
    /**
     * 还原备份 默认
     */
    public final static ImageIcon ICON_RECOVER_BAK = new ImageIcon(
            App.class.getResource("/icon/recoverBak.png"));
    /**
     * 还原备份 激活
     */
    public final static ImageIcon ICON_RECOVER_BAK_ENABLE = new ImageIcon(
            App.class.getResource("/icon/recoverBakEnable.png"));
    /**
     * 还原备份 失效
     */
    public final static ImageIcon ICON_RECOVER_BAK_DISABLE = new ImageIcon(
            App.class.getResource("/icon/recoverBakDisable.png"));

    /**
     * 清空所有备份 默认
     */
    public final static ImageIcon ICON_CLEAR_ALL_BAKS = new ImageIcon(
            App.class.getResource("/icon/clearAllBaks.png"));
    /**
     * 清空所有备份 激活
     */
    public final static ImageIcon ICON_CLEAR_ALL_BAKS_ENABLE = new ImageIcon(
            App.class.getResource("/icon/clearAllBaksEnable.png"));
    /**
     * 清空所有备份 失效
     */
    public final static ImageIcon ICON_CLEAR_ALL_BAKS_DISABLE = new ImageIcon(
            App.class.getResource("/icon/clearAllBaksDisable.png"));

    /**
     * 表字段对应关系 默认
     */
    public final static ImageIcon ICON_TABLE_FIELD = new ImageIcon(
            App.class.getResource("/icon/tableFiled.png"));
    /**
     * 表字段对应关系 激活
     */
    public final static ImageIcon ICON_TABLE_FIELD_ENABLE = new ImageIcon(
            App.class.getResource("/icon/tableFiledEnable.png"));
    /**
     * 表字段对应关系 失效
     */
    public final static ImageIcon ICON_TABLE_FIELD_DISABLE = new ImageIcon(
            App.class.getResource("/icon/tableFiledDisable.png"));
    /**
     * 清空Log 默认
     */
    public final static ImageIcon ICON_CLEAR_LOG = new ImageIcon(
            App.class.getResource("/icon/clearLog.png"));
    /**
     * 清空Log 激活
     */
    public final static ImageIcon ICON_CLEAR_LOG_ENABLE = new ImageIcon(
            App.class.getResource("/icon/clearLogEnable.png"));
    /**
     * 清空Log 失效
     */
    public final static ImageIcon ICON_CLEAR_LOG_DISABLE = new ImageIcon(
            App.class.getResource("/icon/clearLogDisable.png"));

    // 样式布局相关
    /**
     * 主面板水平间隔
     */
    public final static int MAIN_H_GAP = 25;
    /**
     * 主面板Label 大小
     */
    public final static Dimension LABLE_SIZE = new Dimension(1300, 30);
    /**
     * Item Label 大小
     */
    public final static Dimension LABLE_SIZE_ITEM = new Dimension(78, 30);
    /**
     * Item text field 大小
     */
    public final static Dimension TEXT_FIELD_SIZE_ITEM = new Dimension(400, 24);
    /**
     * radio 大小
     */
    public final static Dimension RADIO_SIZE = new Dimension(1300, 60);
    /**
     * 高级选项面板Item 大小
     */
    public final static Dimension PANEL_ITEM_SIZE = new Dimension(1300, 40);

}