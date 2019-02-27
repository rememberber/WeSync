package com.luoboduner.wesync.ui.panel;

import com.luoboduner.wesync.App;
import com.luoboduner.wesync.ui.UiConsts;
import com.luoboduner.wesync.ui.component.MyIconButton;
import com.luoboduner.wesync.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 工具栏面板
 *
 * @author Bob
 */
public class ToolBarPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static MyIconButton buttonStatus;
    private static MyIconButton buttonDatabase;
    private static MyIconButton buttonSchedule;
    private static MyIconButton buttonBackup;
    private static MyIconButton buttonSetting;

    /**
     * 构造
     */
    public ToolBarPanel() {
        initialize();
        addButtion();
        addListener();
    }

    /**
     * 初始化
     */
    private void initialize() {
        Dimension preferredSize = new Dimension(48, UiConsts.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        this.setLayout(new GridLayout(2, 1));
    }

    /**
     * 添加工具按钮
     */
    private void addButtion() {

        JPanel panelUp = new JPanel();
        panelUp.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(-2, -2, -4));
        JPanel panelDown = new JPanel();
        panelDown.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelDown.setLayout(new BorderLayout(0, 0));

        buttonStatus = new MyIconButton(UiConsts.ICON_STATUS_ENABLE, UiConsts.ICON_STATUS_ENABLE,
                UiConsts.ICON_STATUS, PropertyUtil.getProperty("ds.ui.status.title"));
        buttonDatabase = new MyIconButton(UiConsts.ICON_DATABASE, UiConsts.ICON_DATABASE_ENABLE,
                UiConsts.ICON_DATABASE, PropertyUtil.getProperty("ds.ui.database.title"));
        buttonSchedule = new MyIconButton(UiConsts.ICON_SCHEDULE, UiConsts.ICON_SCHEDULE_ENABLE,
                UiConsts.ICON_SCHEDULE, PropertyUtil.getProperty("ds.ui.schedule.title"));
        buttonBackup = new MyIconButton(UiConsts.ICON_BACKUP, UiConsts.ICON_BACKUP_ENABLE,
                UiConsts.ICON_BACKUP, PropertyUtil.getProperty("ds.ui.backup.title"));
        buttonSetting = new MyIconButton(UiConsts.ICON_SETTING, UiConsts.ICON_SETTING_ENABLE,
                UiConsts.ICON_SETTING, PropertyUtil.getProperty("ds.ui.setting.title"));

        panelUp.add(buttonStatus);
        panelUp.add(buttonDatabase);
        panelUp.add(buttonSchedule);
        panelUp.add(buttonBackup);

        panelDown.add(buttonSetting, BorderLayout.SOUTH);
        this.add(panelUp);
        this.add(panelDown);

    }

    /**
     * 为各按钮添加事件动作监听
     */
    private void addListener() {
        buttonStatus.addActionListener(e -> {

            buttonStatus.setIcon(UiConsts.ICON_STATUS_ENABLE);
            buttonDatabase.setIcon(UiConsts.ICON_DATABASE);
            buttonSchedule.setIcon(UiConsts.ICON_SCHEDULE);
            buttonBackup.setIcon(UiConsts.ICON_BACKUP);
            buttonSetting.setIcon(UiConsts.ICON_SETTING);

            App.mainPanelCenter.removeAll();
            StatusPanel.setContent();
            App.mainPanelCenter.add(App.statusPanel, BorderLayout.CENTER);

            App.mainPanelCenter.updateUI();

        });

        buttonDatabase.addActionListener(e -> {

            buttonStatus.setIcon(UiConsts.ICON_STATUS);
            buttonDatabase.setIcon(UiConsts.ICON_DATABASE_ENABLE);
            buttonSchedule.setIcon(UiConsts.ICON_SCHEDULE);
            buttonBackup.setIcon(UiConsts.ICON_BACKUP);
            buttonSetting.setIcon(UiConsts.ICON_SETTING);

            App.mainPanelCenter.removeAll();
            DatabasePanelFrom.setContent();
            DatabasePanelTo.setContent();
            App.mainPanelCenter.add(App.databasePanel, BorderLayout.CENTER);

            App.mainPanelCenter.updateUI();

        });

        buttonSchedule.addActionListener(e -> {

            buttonStatus.setIcon(UiConsts.ICON_STATUS);
            buttonDatabase.setIcon(UiConsts.ICON_DATABASE);
            buttonSchedule.setIcon(UiConsts.ICON_SCHEDULE_ENABLE);
            buttonBackup.setIcon(UiConsts.ICON_BACKUP);
            buttonSetting.setIcon(UiConsts.ICON_SETTING);

            App.mainPanelCenter.removeAll();
            App.schedulePanel.setCurrentSchedule();
            App.mainPanelCenter.add(App.schedulePanel, BorderLayout.CENTER);

            App.mainPanelCenter.updateUI();

        });

        buttonBackup.addActionListener(e -> {

            buttonStatus.setIcon(UiConsts.ICON_STATUS);
            buttonDatabase.setIcon(UiConsts.ICON_DATABASE);
            buttonSchedule.setIcon(UiConsts.ICON_SCHEDULE);
            buttonBackup.setIcon(UiConsts.ICON_BACKUP_ENABLE);
            buttonSetting.setIcon(UiConsts.ICON_SETTING);

            BackupPanel.initTableData();
            BackupPanel.tableFrom.validate();

            App.mainPanelCenter.removeAll();
            App.mainPanelCenter.add(App.backupPanel, BorderLayout.CENTER);

            App.mainPanelCenter.updateUI();

        });

        buttonSetting.addActionListener(e -> {

            buttonStatus.setIcon(UiConsts.ICON_STATUS);
            buttonDatabase.setIcon(UiConsts.ICON_DATABASE);
            buttonSchedule.setIcon(UiConsts.ICON_SCHEDULE);
            buttonBackup.setIcon(UiConsts.ICON_BACKUP);
            buttonSetting.setIcon(UiConsts.ICON_SETTING_ENABLE);

            App.mainPanelCenter.removeAll();
            SettingPanelOption.setCurrentOption();
            App.mainPanelCenter.add(App.settingPanel, BorderLayout.CENTER);

            App.mainPanelCenter.updateUI();

        });
    }
}
