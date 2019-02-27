package com.luoboduner.wesync.ui.panel;

import com.luoboduner.wesync.App;
import com.luoboduner.wesync.ui.UiConsts;
import com.luoboduner.wesync.ui.component.MyIconButton;
import com.luoboduner.wesync.logic.ConstantsLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.luoboduner.wesync.tools.ConstantsTools;
import com.luoboduner.wesync.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 高级选项面板
 *
 * @author Bob
 */
public class SettingPanelOption extends JPanel {

    private static final long serialVersionUID = 1L;

    private static MyIconButton buttonSave;

    private static MyIconButton buttionTableFiled;

    private static MyIconButton buttionClearLogs;

    private static JCheckBox checkBoxAutoBak;

    private static JCheckBox checkBoxDebug;

    private static JCheckBox checkBoxStrict;

    private static JTextField textField;

    private static final Logger logger = LoggerFactory.getLogger(SettingPanelOption.class);

    /**
     * 构造
     */
    public SettingPanelOption() {
        initialize();
        addComponent();
        setCurrentOption();
        addListener();
    }

    /**
     * 初始化
     */
    private void initialize() {
        this.setBackground(UiConsts.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    /**
     * 添加组件
     */
    private void addComponent() {

        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.add(getDownPanel(), BorderLayout.SOUTH);

    }

    /**
     * 中部面板
     *
     * @return
     */
    private JPanel getCenterPanel() {
        // 中间面板
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(1, 1));

        // 设置Grid
        JPanel panelGridOption = new JPanel();
        panelGridOption.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridOption.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));

        // 初始化组件
        JPanel panelItem1 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem2 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem3 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem4 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem5 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem6 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem7 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));

        panelItem1.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem2.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem3.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem4.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem5.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem6.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem7.setBackground(UiConsts.MAIN_BACK_COLOR);

        panelItem1.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem2.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem3.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem4.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem5.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem6.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem7.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);

        // 各Item
        buttionTableFiled = new MyIconButton(UiConsts.ICON_TABLE_FIELD, UiConsts.ICON_TABLE_FIELD_ENABLE,
                UiConsts.ICON_TABLE_FIELD_DISABLE, "");
        panelItem1.add(buttionTableFiled);

        buttionClearLogs = new MyIconButton(UiConsts.ICON_CLEAR_LOG, UiConsts.ICON_CLEAR_LOG_ENABLE,
                UiConsts.ICON_CLEAR_LOG_DISABLE, "");
        panelItem2.add(buttionClearLogs);

        MyIconButton buttionClearBaks = new MyIconButton(UiConsts.ICON_CLEAR_ALL_BAKS, UiConsts.ICON_CLEAR_ALL_BAKS_ENABLE,
                UiConsts.ICON_CLEAR_ALL_BAKS_DISABLE, "");
        panelItem3.add(buttionClearBaks);

        checkBoxAutoBak = new JCheckBox(PropertyUtil.getProperty("ds.ui.setting.autoBackUp"));
        checkBoxAutoBak.setBackground(UiConsts.MAIN_BACK_COLOR);
        checkBoxAutoBak.setFont(UiConsts.FONT_RADIO);
        panelItem4.add(checkBoxAutoBak);

        JLabel label = new JLabel(PropertyUtil.getProperty("ds.ui.setting.mysqlPath"));
        textField = new JTextField();
        label.setBackground(UiConsts.MAIN_BACK_COLOR);
        label.setFont(UiConsts.FONT_RADIO);
        textField.setFont(UiConsts.FONT_RADIO);
        Dimension dm = new Dimension(334, 26);
        textField.setPreferredSize(dm);
        panelItem5.add(label);
        panelItem5.add(textField);

        checkBoxStrict = new JCheckBox(PropertyUtil.getProperty("ds.ui.setting.strict"));
        checkBoxStrict.setBackground(UiConsts.MAIN_BACK_COLOR);
        checkBoxStrict.setFont(UiConsts.FONT_RADIO);
        panelItem6.add(checkBoxStrict);

        checkBoxDebug = new JCheckBox(PropertyUtil.getProperty("ds.ui.setting.debugMode"));
        checkBoxDebug.setBackground(UiConsts.MAIN_BACK_COLOR);
        checkBoxDebug.setFont(UiConsts.FONT_RADIO);
        panelItem7.add(checkBoxDebug);

        // 组合元素
        panelGridOption.add(panelItem1);
        panelGridOption.add(panelItem2);
        panelGridOption.add(panelItem3);
        panelGridOption.add(panelItem4);
        panelGridOption.add(panelItem5);
        panelGridOption.add(panelItem6);
        panelGridOption.add(panelItem7);

        panelCenter.add(panelGridOption);
        return panelCenter;
    }

    /**
     * 底部面板
     *
     * @return
     */
    private JPanel getDownPanel() {
        JPanel panelDown = new JPanel();
        panelDown.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelDown.setLayout(new FlowLayout(FlowLayout.RIGHT, UiConsts.MAIN_H_GAP, 15));

        buttonSave = new MyIconButton(UiConsts.ICON_SAVE, UiConsts.ICON_SAVE_ENABLE,
                UiConsts.ICON_SAVE_DISABLE, "");
        panelDown.add(buttonSave);

        return panelDown;
    }

    /**
     * 设置当前combox选项状态
     */
    public static void setCurrentOption() {
        checkBoxAutoBak.setSelected(Boolean.parseBoolean(ConstantsTools.CONFIGER.getAutoBak()));
        checkBoxDebug.setSelected(Boolean.parseBoolean(ConstantsTools.CONFIGER.getDebugMode()));
        checkBoxStrict.setSelected(Boolean.parseBoolean(ConstantsTools.CONFIGER.getStrictMode()));
        textField.setText(ConstantsTools.CONFIGER.getMysqlPath());
    }

    /**
     * 为相关组件添加事件监听
     */
    private void addListener() {
        buttonSave.addActionListener(e -> {

            try {
                ConstantsTools.CONFIGER.setAutoBak(String.valueOf(checkBoxAutoBak.isSelected()));
                ConstantsTools.CONFIGER.setDebugMode(String.valueOf(checkBoxDebug.isSelected()));
                ConstantsTools.CONFIGER.setStrictMode(String.valueOf(checkBoxStrict.isSelected()));
                ConstantsTools.CONFIGER.setMysqlPath(textField.getText());
                JOptionPane.showMessageDialog(App.settingPanel, PropertyUtil.getProperty("ds.ui.save.success"),
                        PropertyUtil.getProperty("ds.ui.tips"), JOptionPane.PLAIN_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(App.settingPanel, PropertyUtil.getProperty("ds.ui.save.fail") + e1.getMessage(),
                        PropertyUtil.getProperty("ds.ui.tips"),
                        JOptionPane.ERROR_MESSAGE);
                logger.error("Write to xml file error" + e1.toString());
            }

        });

        buttionTableFiled.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File(ConstantsLogic.TABLE_FIELD_DIR));
            } catch (IOException e1) {
                logger.error("open table_field file fail:" + e1.toString());
                e1.printStackTrace();
            }

        });

        buttionClearLogs.addActionListener(e -> {

            int answer = JOptionPane.showConfirmDialog(App.settingPanel,
                    PropertyUtil.getProperty("ds.ui.setting.clean.makeSure"),
                    PropertyUtil.getProperty("ds.ui.tips"), 2);

            if (answer == 0) {
                FileOutputStream testfile = null;
                try {
                    testfile = new FileOutputStream(ConstantsTools.PATH_LOG);
                    testfile.write(new String("").getBytes());
                    testfile.flush();
                    JOptionPane.showMessageDialog(App.settingPanel,
                            PropertyUtil.getProperty("ds.ui.setting.clean.success"),
                            PropertyUtil.getProperty("ds.ui.tips"),
                            JOptionPane.PLAIN_MESSAGE);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(App.settingPanel,
                            PropertyUtil.getProperty("ds.ui.setting.clean.fail") + e1.getMessage(),
                            PropertyUtil.getProperty("ds.ui.tips"),
                            JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } finally {
                    if (testfile != null) {
                        try {
                            testfile.close();
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(App.settingPanel,
                                    PropertyUtil.getProperty("ds.ui.setting.clean.fail") + e1.getMessage(),
                                    PropertyUtil.getProperty("ds.ui.tips"), JOptionPane.ERROR_MESSAGE);
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
