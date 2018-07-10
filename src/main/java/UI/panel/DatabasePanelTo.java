package UI.panel;

import UI.AppMainWindow;
import UI.ConstantsUI;
import UI.MyIconButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.ConstantsTools;
import tools.DESPlus;
import tools.DbUtilMySQL;
import tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

/**
 * 目标数据库面板
 *
 * @author Bob
 */
public class DatabasePanelTo extends JPanel {

    private static final long serialVersionUID = 1L;

    private static MyIconButton buttonTestLink;
    private static MyIconButton buttonSave;
    private static JTextField textFieldDatabaseHost;
    private static JTextField textFieldDatabaseName;
    private static JTextField textFieldDatabaseUser;
    private static JPasswordField passwordFieldDatabasePassword;

    private static final Logger logger = LoggerFactory.getLogger(DatabasePanelTo.class);

    /**
     * 构造
     */
    public DatabasePanelTo() {
        initialize();
        addComponent();
        setContent();
        addListener();
    }

    /**
     * 初始化
     */
    private void initialize() {
        this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
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
        panelCenter.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(2, 1));

        // 设置Grid
        JPanel panelGridSetting = new JPanel();
        panelGridSetting.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridSetting.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        // 初始化组件
        JLabel labelDatabaseType = new JLabel(PropertyUtil.getProperty("ds.ui.database.type"));
        JLabel labelDatabaseHost = new JLabel(PropertyUtil.getProperty("ds.ui.database.host"));
        JLabel labelDatabaseName = new JLabel(PropertyUtil.getProperty("ds.ui.database.name"));
        JLabel labelDatabaseUser = new JLabel(PropertyUtil.getProperty("ds.ui.database.user"));
        JLabel labelDatabasePassword = new JLabel(PropertyUtil.getProperty("ds.ui.database.password"));
        JComboBox<String> comboxDatabaseType = new JComboBox<String>();
        comboxDatabaseType.addItem("MySQL");
        comboxDatabaseType.setEditable(false);
        textFieldDatabaseHost = new JTextField();
        textFieldDatabaseName = new JTextField();
        textFieldDatabaseUser = new JTextField();
        passwordFieldDatabasePassword = new JPasswordField();

        // 字体
        labelDatabaseType.setFont(ConstantsUI.FONT_NORMAL);
        labelDatabaseHost.setFont(ConstantsUI.FONT_NORMAL);
        labelDatabaseName.setFont(ConstantsUI.FONT_NORMAL);
        labelDatabaseUser.setFont(ConstantsUI.FONT_NORMAL);
        labelDatabasePassword.setFont(ConstantsUI.FONT_NORMAL);
        comboxDatabaseType.setFont(ConstantsUI.FONT_NORMAL);
        textFieldDatabaseHost.setFont(ConstantsUI.FONT_NORMAL);
        textFieldDatabaseName.setFont(ConstantsUI.FONT_NORMAL);
        textFieldDatabaseUser.setFont(ConstantsUI.FONT_NORMAL);
        passwordFieldDatabasePassword.setFont(ConstantsUI.FONT_NORMAL);

        // 大小
        labelDatabaseType.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        labelDatabaseHost.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        labelDatabaseName.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        labelDatabaseUser.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        labelDatabasePassword.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        comboxDatabaseType.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        textFieldDatabaseHost.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        textFieldDatabaseName.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        textFieldDatabaseUser.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        passwordFieldDatabasePassword.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);

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
        panelDown.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelDown.setLayout(new FlowLayout(FlowLayout.RIGHT, ConstantsUI.MAIN_H_GAP, 15));

        buttonTestLink = new MyIconButton(ConstantsUI.ICON_TEST_LINK, ConstantsUI.ICON_TEST_LINK_ENABLE,
                ConstantsUI.ICON_TEST_LINK_DISABLE, "");
        buttonSave = new MyIconButton(ConstantsUI.ICON_SAVE, ConstantsUI.ICON_SAVE_ENABLE,
                ConstantsUI.ICON_SAVE_DISABLE, "");
        panelDown.add(buttonTestLink);
        panelDown.add(buttonSave);

        return panelDown;
    }

    /**
     * 设置文本区内容
     */
    public static void setContent() {
        textFieldDatabaseHost.setText(ConstantsTools.CONFIGER.getHostTo());
        textFieldDatabaseName.setText(ConstantsTools.CONFIGER.getNameTo());

        String user = "";
        String password = "";
        try {
            DESPlus des = new DESPlus();
            user = des.decrypt(ConstantsTools.CONFIGER.getUserTo());
            password = des.decrypt(ConstantsTools.CONFIGER.getPasswordTo());
        } catch (Exception e) {
            logger.error(PropertyUtil.getProperty("ds.ui.database.to.err.decode") + e.toString());
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
                ConstantsTools.CONFIGER.setHostTo(textFieldDatabaseHost.getText());
                ConstantsTools.CONFIGER.setNameTo(textFieldDatabaseName.getText());

                String password = "";
                String user = "";
                try {
                    DESPlus des = new DESPlus();
                    user = des.encrypt(textFieldDatabaseUser.getText());
                    password = des.encrypt(new String(passwordFieldDatabasePassword.getPassword()));
                } catch (Exception e1) {
                    logger.error(PropertyUtil.getProperty("ds.ui.database.to.err.encode") + e1.toString());
                    e1.printStackTrace();
                }
                ConstantsTools.CONFIGER.setUserTo(user);
                ConstantsTools.CONFIGER.setPasswordTo(password);

                JOptionPane.showMessageDialog(AppMainWindow.databasePanel, PropertyUtil.getProperty("ds.ui.save.success"), PropertyUtil.getProperty("ds.ui.tips"),
                        JOptionPane.PLAIN_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(AppMainWindow.databasePanel, PropertyUtil.getProperty("ds.ui.save.fail") + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
                        JOptionPane.ERROR_MESSAGE);
                logger.error("Write to xml file error" + e1.toString());
            }

        });

        buttonTestLink.addActionListener(e -> {

            try {
                DbUtilMySQL dbMySQL = DbUtilMySQL.getInstance();
                String DBUrl = textFieldDatabaseHost.getText();
                String DBName = textFieldDatabaseName.getText();
                String DBUser = textFieldDatabaseUser.getText();
                String DBPassword = new String(passwordFieldDatabasePassword.getPassword());
                Connection conn = dbMySQL.testConnection(DBUrl, DBName, DBUser, DBPassword);
                if (conn == null) {
                    JOptionPane.showMessageDialog(AppMainWindow.databasePanel, PropertyUtil.getProperty("ds.ui.database.err.link.fail"), PropertyUtil.getProperty("ds.ui.tips"),
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(AppMainWindow.databasePanel, PropertyUtil.getProperty("ds.ui.database.err.link.success"), PropertyUtil.getProperty("ds.ui.tips"),
                            JOptionPane.PLAIN_MESSAGE);
                }

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(AppMainWindow.databasePanel, PropertyUtil.getProperty("ds.ui.database.err.link.fail") + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
                        JOptionPane.ERROR_MESSAGE);
            }

        });

    }
}
