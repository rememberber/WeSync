package com.luoboduner.wesync.ui.panel;

import com.luoboduner.wesync.App;
import com.luoboduner.wesync.ui.UiConsts;
import com.luoboduner.wesync.ui.component.MyIconButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.luoboduner.wesync.tools.ConstantsTools;
import com.luoboduner.wesync.tools.DESPlus;
import com.luoboduner.wesync.tools.DbUtilSQLServer;
import com.luoboduner.wesync.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

/**
 * 来源数据库面板
 *
 * @author Bob
 */
public class DatabasePanelFrom extends JPanel {

    private static final long serialVersionUID = 1L;

    private static MyIconButton buttonTestLink;
    private static MyIconButton buttonSave;
    private static JTextField textFieldDatabaseHost;
    private static JTextField textFieldDatabaseName;
    private static JTextField textFieldDatabaseUser;
    private static JPasswordField passwordFieldDatabasePassword;

    private static final Logger logger = LoggerFactory.getLogger(DatabasePanelFrom.class);

    /**
     * 构造
     */
    public DatabasePanelFrom() {
        initialize();
        addComponent();
        setContent();
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
        panelCenter.setLayout(new GridLayout(2, 1));

        // 设置Grid
        JPanel panelGridSetting = new JPanel();
        panelGridSetting.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridSetting.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));

        // 初始化组件
        JLabel labelDatabaseType = new JLabel(PropertyUtil.getProperty("ds.ui.database.type"));
        JLabel labelDatabaseHost = new JLabel(PropertyUtil.getProperty("ds.ui.database.host"));
        JLabel labelDatabaseName = new JLabel(PropertyUtil.getProperty("ds.ui.database.name"));
        JLabel labelDatabaseUser = new JLabel(PropertyUtil.getProperty("ds.ui.database.user"));
        JLabel labelDatabasePassword = new JLabel(PropertyUtil.getProperty("ds.ui.database.password"));
        JComboBox<String> comboxDatabaseType = new JComboBox<String>();
        comboxDatabaseType.addItem("SQL Server");
        comboxDatabaseType.setEditable(false);
        textFieldDatabaseHost = new JTextField();
        textFieldDatabaseName = new JTextField();
        textFieldDatabaseUser = new JTextField();
        passwordFieldDatabasePassword = new JPasswordField();

        // 字体
        labelDatabaseType.setFont(UiConsts.FONT_NORMAL);
        labelDatabaseHost.setFont(UiConsts.FONT_NORMAL);
        labelDatabaseName.setFont(UiConsts.FONT_NORMAL);
        labelDatabaseUser.setFont(UiConsts.FONT_NORMAL);
        labelDatabasePassword.setFont(UiConsts.FONT_NORMAL);
        comboxDatabaseType.setFont(UiConsts.FONT_NORMAL);
        textFieldDatabaseHost.setFont(UiConsts.FONT_NORMAL);
        textFieldDatabaseName.setFont(UiConsts.FONT_NORMAL);
        textFieldDatabaseUser.setFont(UiConsts.FONT_NORMAL);
        passwordFieldDatabasePassword.setFont(UiConsts.FONT_NORMAL);

        // 大小
        labelDatabaseType.setPreferredSize(UiConsts.LABLE_SIZE_ITEM);
        labelDatabaseHost.setPreferredSize(UiConsts.LABLE_SIZE_ITEM);
        labelDatabaseName.setPreferredSize(UiConsts.LABLE_SIZE_ITEM);
        labelDatabaseUser.setPreferredSize(UiConsts.LABLE_SIZE_ITEM);
        labelDatabasePassword.setPreferredSize(UiConsts.LABLE_SIZE_ITEM);
        comboxDatabaseType.setPreferredSize(UiConsts.TEXT_FIELD_SIZE_ITEM);
        textFieldDatabaseHost.setPreferredSize(UiConsts.TEXT_FIELD_SIZE_ITEM);
        textFieldDatabaseName.setPreferredSize(UiConsts.TEXT_FIELD_SIZE_ITEM);
        textFieldDatabaseUser.setPreferredSize(UiConsts.TEXT_FIELD_SIZE_ITEM);
        passwordFieldDatabasePassword.setPreferredSize(UiConsts.TEXT_FIELD_SIZE_ITEM);

        // 组合元素
        panelGridSetting.add(labelDatabaseType);
        panelGridSetting.add(comboxDatabaseType);
        panelGridSetting.add(labelDatabaseHost);
        panelGridSetting.add(textFieldDatabaseHost);
        panelGridSetting.add(labelDatabaseName);
        panelGridSetting.add(textFieldDatabaseName);
        panelGridSetting.add(labelDatabaseUser);
        panelGridSetting.add(textFieldDatabaseUser);
        panelGridSetting.add(labelDatabasePassword);
        panelGridSetting.add(passwordFieldDatabasePassword);

        panelCenter.add(panelGridSetting);
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

        buttonTestLink = new MyIconButton(UiConsts.ICON_TEST_LINK, UiConsts.ICON_TEST_LINK_ENABLE,
                UiConsts.ICON_TEST_LINK_DISABLE, "");
        buttonSave = new MyIconButton(UiConsts.ICON_SAVE, UiConsts.ICON_SAVE_ENABLE,
                UiConsts.ICON_SAVE_DISABLE, "");
        panelDown.add(buttonTestLink);
        panelDown.add(buttonSave);

        return panelDown;
    }

    /**
     * 设置文本区内容
     */
    public static void setContent() {
        textFieldDatabaseHost.setText(ConstantsTools.CONFIGER.getHostFrom());
        textFieldDatabaseName.setText(ConstantsTools.CONFIGER.getNameFrom());

        String password = "";
        String user = "";
        try {
            DESPlus des = new DESPlus();
            password = des.decrypt(ConstantsTools.CONFIGER.getPasswordFrom());
            user = des.decrypt(ConstantsTools.CONFIGER.getUserFrom());
        } catch (Exception e) {
            logger.error(PropertyUtil.getProperty("ds.ui.database.from.err.decode") + e.toString());
            e.printStackTrace();
        }
        textFieldDatabaseUser.setText(user);
        passwordFieldDatabasePassword.setText(password);

    }

    /**
     * 为相关组件添加事件监听
     */
    private void addListener() {
        buttonSave.addActionListener(e -> {

            try {
                ConstantsTools.CONFIGER.setHostFrom(textFieldDatabaseHost.getText());
                ConstantsTools.CONFIGER.setNameFrom(textFieldDatabaseName.getText());

                String password = "";
                String user = "";
                try {
                    DESPlus des = new DESPlus();
                    user = des.encrypt(textFieldDatabaseUser.getText());
                    password = des.encrypt(new String(passwordFieldDatabasePassword.getPassword()));
                } catch (Exception e1) {
                    logger.error(PropertyUtil.getProperty("ds.ui.database.from.err.encode") + e1.toString());
                    e1.printStackTrace();
                }
                ConstantsTools.CONFIGER.setUserFrom(user);
                ConstantsTools.CONFIGER.setPasswordFrom(password);

                JOptionPane.showMessageDialog(App.databasePanel, PropertyUtil.getProperty("ds.ui.save.success"), PropertyUtil.getProperty("ds.ui.tips"),
                        JOptionPane.PLAIN_MESSAGE);

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(App.databasePanel, PropertyUtil.getProperty("ds.ui.save.fail") + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
                        JOptionPane.ERROR_MESSAGE);
                logger.error("Write to xml file error" + e1.toString());
            }

        });

        buttonTestLink.addActionListener(e -> {

            try {
                DbUtilSQLServer dbSQLServer = DbUtilSQLServer.getInstance();
                String dburl = textFieldDatabaseHost.getText();
                String dbname = textFieldDatabaseName.getText();
                String dbuser = textFieldDatabaseUser.getText();
                String dbpassword = new String(passwordFieldDatabasePassword.getPassword());
                Connection conn = dbSQLServer.testConnection(dburl, dbname, dbuser, dbpassword);
                if (conn == null) {
                    JOptionPane.showMessageDialog(App.databasePanel, PropertyUtil.getProperty("ds.ui.database.err.link.fail"), PropertyUtil.getProperty("ds.ui.tips"),
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(App.databasePanel, PropertyUtil.getProperty("ds.ui.database.err.link.success"), PropertyUtil.getProperty("ds.ui.tips"),
                            JOptionPane.PLAIN_MESSAGE);
                }

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(App.databasePanel, PropertyUtil.getProperty("ds.ui.database.err.link.fail") + "\n" + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
                        JOptionPane.ERROR_MESSAGE);
            }

        });
    }

}