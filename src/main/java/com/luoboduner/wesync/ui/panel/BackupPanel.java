package com.luoboduner.wesync.ui.panel;

import com.luoboduner.wesync.App;
import com.luoboduner.wesync.ui.UiConsts;
import com.luoboduner.wesync.ui.component.MyIconButton;
import com.luoboduner.wesync.logic.ConstantsLogic;
import com.luoboduner.wesync.tools.FileUtils;
import com.luoboduner.wesync.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 备份面板
 *
 * @author Bob
 */
public class BackupPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public static JTable tableFrom;

    private static MyIconButton buttonNewBakFrom;

    private static Object[][] tableDatas;

    /**
     * 构造
     */
    public BackupPanel() {
        initialize();
        initTableData();
        addComponent();
        addListener();
    }

    /**
     * 初始化面板
     */
    private void initialize() {
        this.setBackground(UiConsts.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    /**
     * 为面板添加组件
     */
    private void addComponent() {

        this.add(getUpPanel(), BorderLayout.NORTH);
        this.add(getCenterPanel(), BorderLayout.CENTER);
    }

    /**
     * 面板上部
     *
     * @return
     */
    private JPanel getUpPanel() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 5));

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.backup.title"));
        labelTitle.setFont(UiConsts.FONT_TITLE);
        labelTitle.setForeground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelUp.add(labelTitle);

        return panelUp;
    }

    /**
     * 面板中部
     *
     * @return
     */
    private JPanel getCenterPanel() {
        // 中间面板
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(1, 1));

        panelCenter.add(getPanelGridBakFrom());

        return panelCenter;
    }

    /**
     * 数据库来源Grid面板
     *
     * @return
     */
    private JPanel getPanelGridBakFrom() {
        // 来源备份Grid
        JPanel panelGridBakFrom = new JPanel();
        panelGridBakFrom.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridBakFrom.setLayout(new BorderLayout());

        JPanel panelFromControl = new JPanel();
        panelFromControl.setLayout(new GridLayout(1, 2));
        JPanel panelFromTable = new JPanel();
        panelFromTable.setLayout(new BorderLayout());

        // 初始化控制组件
        JPanel panelFromControlLeft = new JPanel();
        panelFromControlLeft.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 5));
        panelFromControlLeft.setBackground(UiConsts.MAIN_BACK_COLOR);
        JPanel panelFromControlRight = new JPanel();
        panelFromControlRight.setLayout(new FlowLayout(FlowLayout.RIGHT, UiConsts.MAIN_H_GAP, 5));
        panelFromControlRight.setBackground(UiConsts.MAIN_BACK_COLOR);

        JLabel labelFrom = new JLabel(PropertyUtil.getProperty("ds.ui.database.label.to"));
        labelFrom.setFont(new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 18));
        labelFrom.setForeground(Color.gray);
        panelFromControlLeft.add(labelFrom);

        buttonNewBakFrom = new MyIconButton(UiConsts.ICON_NEW_BAK, UiConsts.ICON_NEW_BAK_ENABLE,
                UiConsts.ICON_NEW_BAK_DISABLE, "");
        MyIconButton buttonRecvBakFrom = new MyIconButton(UiConsts.ICON_RECOVER_BAK,
                UiConsts.ICON_RECOVER_BAK_ENABLE, UiConsts.ICON_RECOVER_BAK_DISABLE, "");
        MyIconButton buttonDelBakFrom = new MyIconButton(UiConsts.ICON_DEL_BAK, UiConsts.ICON_DEL_BAK_ENABLE,
                UiConsts.ICON_DEL_BAK_DISABLE, "");
        panelFromControlRight.add(buttonNewBakFrom);
        panelFromControlRight.add(buttonRecvBakFrom);
        panelFromControlRight.add(buttonDelBakFrom);

        panelFromControl.add(panelFromControlLeft);
        panelFromControl.add(panelFromControlRight);

        panelGridBakFrom.add(panelFromControl, BorderLayout.NORTH);

        // 初始化表格组件

        tableFrom = new JTable(tableDatas, new String[]{PropertyUtil.getProperty("ds.ui.backup.table.head0"), PropertyUtil.getProperty("ds.ui.backup.table.head1"), PropertyUtil.getProperty("ds.ui.backup.table.head2")});
        tableFrom.setFont(UiConsts.FONT_NORMAL);
        tableFrom.getTableHeader().setFont(UiConsts.FONT_NORMAL);
        tableFrom.getTableHeader().setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        tableFrom.setRowHeight(31);
        tableFrom.setGridColor(UiConsts.TABLE_LINE_COLOR);
        tableFrom.setSelectionBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        // 设置列宽
        tableFrom.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableFrom.getColumnModel().getColumn(0).setMaxWidth(50);
        tableFrom.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableFrom.getColumnModel().getColumn(2).setMaxWidth(150);

        JScrollPane panelScroll = new JScrollPane(tableFrom);
        panelScroll.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridBakFrom.add(panelScroll, BorderLayout.CENTER);

        return panelGridBakFrom;
    }

    public static void initTableData() {
        File bakupFilesDir = new File(ConstantsLogic.PATH_MYSQL_BAK);
        if (!bakupFilesDir.exists()) {
            bakupFilesDir.mkdirs();
        }
        File[] bakupFiles = bakupFilesDir.listFiles();
        tableDatas = new Object[bakupFiles.length][3];
        for (int i = 0; i < bakupFiles.length; i++) {
            tableDatas[i] = new Object[]{i + 1, bakupFiles[i].getName(),
                    FileUtils.formetfilesize(bakupFiles[i].length())};
        }
    }

    private void addListener() {
        buttonNewBakFrom.addActionListener(e -> App.dbBackUpCreateDialog.setVisible(true));
    }

}