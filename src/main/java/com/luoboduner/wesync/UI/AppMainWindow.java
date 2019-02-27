package com.luoboduner.wesync.UI;

import com.luoboduner.wesync.UI.panel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.luoboduner.wesync.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 程序入口，主窗口Frame
 *
 * @author Bob
 */
public class AppMainWindow {
    private static final Logger logger = LoggerFactory.getLogger(AppMainWindow.class);

    private JFrame frame;

    public static JPanel mainPanelCenter;

    public static StatusPanel statusPanel;
    public static DatabasePanel databasePanel;
    public static SchedulePanel schedulePanel;
    public static BackupPanel backupPanel;
    public static SettingPanel settingPanel;
    // 新建备份dialog
    public static JDialog dialog;

    /**
     * 程序入口main
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AppMainWindow window = new AppMainWindow();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 构造，创建APP
     */
    public AppMainWindow() {
        initialize();
        StatusPanel.buttonStartSchedule.doClick();
    }

    /**
     * 初始化frame内容
     */
    private void initialize() {
        logger.info("==================AppInitStart");
        // 设置系统默认样式
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 初始化主窗口
        frame = new JFrame();
        frame.setBounds(ConstantsUI.MAIN_WINDOW_X, ConstantsUI.MAIN_WINDOW_Y, ConstantsUI.MAIN_WINDOW_WIDTH,
                ConstantsUI.MAIN_WINDOW_HEIGHT);
        frame.setTitle(ConstantsUI.APP_NAME);
        frame.setIconImage(ConstantsUI.IMAGE_ICON);
        frame.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        JPanel mainPanel = new JPanel(true);
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());

        ToolBarPanel toolbar = new ToolBarPanel();
        statusPanel = new StatusPanel();
        databasePanel = new DatabasePanel();
        schedulePanel = new SchedulePanel();
        backupPanel = new BackupPanel();
        settingPanel = new SettingPanel();

        mainPanel.add(toolbar, BorderLayout.WEST);

        mainPanelCenter = new JPanel(true);
        mainPanelCenter.setLayout(new BorderLayout());
        mainPanelCenter.add(statusPanel, BorderLayout.CENTER);

        mainPanel.add(mainPanelCenter, BorderLayout.CENTER);

        // 添加数据库备份对话框
        addDialog();

        frame.add(mainPanel);

        frame.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (!StatusPanel.buttonStartSchedule.isEnabled()) {
                    JOptionPane.showMessageDialog(AppMainWindow.statusPanel,
                            PropertyUtil.getProperty("ds.ui.mainwindow.exitconfirm"), "Sorry~", JOptionPane.WARNING_MESSAGE);
                } else {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }
        });
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        logger.info("==================AppInitEnd");
    }

    /**
     * 数据库备份对话框
     */
    private void addDialog() {
        // 数据库备份对话框
        dialog = new JDialog(frame, PropertyUtil.getProperty("ds.ui.mainwindow.dialog.newBackUp"), true);
        dialog.setBounds(460, 220, 400, 250);
        JPanel panelDialog = new JPanel(new BorderLayout());
        panelDialog.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        JPanel panelDialogCenter = new JPanel(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 10));
        JPanel panelDialogDown = new JPanel(new GridLayout(1, 2));
        JPanel grid1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        JPanel grid2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));

        JLabel labelType = new JLabel(PropertyUtil.getProperty("ds.ui.mainwindow.dialog.type"));
        JLabel labelComment = new JLabel(PropertyUtil.getProperty("ds.ui.mainwindow.dialog.remarks"));
        JLabel labelProgress = new JLabel(PropertyUtil.getProperty("ds.ui.mainwindow.dialog.progress"));
        JComboBox<String> comboxType = new JComboBox<>(new String[]{PropertyUtil.getProperty("ds.ui.mainwindow.dialog.comboxType0"),
                PropertyUtil.getProperty("ds.ui.mainwindow.dialog.comboxType1"), PropertyUtil.getProperty("ds.ui.mainwindow.dialog.comboxType2"),
                PropertyUtil.getProperty("ds.ui.mainwindow.dialog.comboxType3")});
        JTextField textFieldComment = new JTextField();
        JProgressBar progressbar = new JProgressBar();

        labelType.setFont(ConstantsUI.FONT_NORMAL);
        labelComment.setFont(ConstantsUI.FONT_NORMAL);
        labelProgress.setFont(ConstantsUI.FONT_NORMAL);
        comboxType.setFont(ConstantsUI.FONT_NORMAL);
        textFieldComment.setFont(ConstantsUI.FONT_NORMAL);

        Dimension preferredSize = new Dimension(250, 30);
        comboxType.setPreferredSize(preferredSize);
        textFieldComment.setPreferredSize(preferredSize);
        progressbar.setPreferredSize(preferredSize);

        panelDialogCenter.add(labelType);
        panelDialogCenter.add(comboxType);
        panelDialogCenter.add(labelComment);
        panelDialogCenter.add(textFieldComment);
        panelDialogCenter.add(labelProgress);
        panelDialogCenter.add(progressbar);

        JButton buttonSure = new JButton(PropertyUtil.getProperty("ds.ui.sure"));
        JButton buttonCancel = new JButton(PropertyUtil.getProperty("ds.ui.cancel"));
        buttonSure.setFont(ConstantsUI.FONT_NORMAL);
        buttonCancel.setFont(ConstantsUI.FONT_NORMAL);

        grid1.add(buttonSure);
        grid2.add(buttonCancel);
        panelDialogDown.add(grid1);
        panelDialogDown.add(grid2);

        panelDialog.add(panelDialogCenter, BorderLayout.CENTER);
        panelDialog.add(panelDialogDown, BorderLayout.SOUTH);

        dialog.add(panelDialog);

        buttonCancel.addActionListener(e -> dialog.setVisible(false));
    }

}