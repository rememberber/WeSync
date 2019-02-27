package com.luoboduner.wesync.ui.panel;

import com.luoboduner.wesync.App;
import com.luoboduner.wesync.ui.UiConsts;
import com.luoboduner.wesync.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 数据库设置面板
 *
 * @author Bob
 */
public class DatabasePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public static JPanel panelFrom;
    public static JPanel panelTo;
    public static JPanel databaseSettingPanel;
    private static JPanel databaseSettingPanelFrom;
    private static JPanel databaseSettingPanelTo;

    /**
     * 构造
     */
    public DatabasePanel() {
        initialize();
        addComponent();
        addListener();
    }

    /**
     * 初始化面板
     */
    private void initialize() {
        this.setBackground(UiConsts.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
        databaseSettingPanelFrom = new DatabasePanelFrom();
        databaseSettingPanelTo = new DatabasePanelTo();
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

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.database.title"));
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
        panelCenter.setLayout(new BorderLayout());

        // 数据库列表Panel
        JPanel panelList = new JPanel();
        Dimension preferredSize = new Dimension(245, UiConsts.MAIN_WINDOW_HEIGHT);
        panelList.setPreferredSize(preferredSize);
        panelList.setBackground(new Color(62, 62, 62));
        panelList.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        panelFrom = new JPanel();
        panelFrom.setBackground(new Color(69, 186, 121));
        panelFrom.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
        Dimension preferredSizeListItem = new Dimension(245, 48);
        panelFrom.setPreferredSize(preferredSizeListItem);
        panelTo = new JPanel();
        panelTo.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelTo.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
        panelTo.setPreferredSize(preferredSizeListItem);

        JLabel labelFrom = new JLabel(PropertyUtil.getProperty("ds.ui.database.label.from"));
        JLabel labelTo = new JLabel(PropertyUtil.getProperty("ds.ui.database.label.to"));
        Font fontListItem = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 15);
        labelFrom.setFont(fontListItem);
        labelTo.setFont(fontListItem);
        labelFrom.setForeground(Color.white);
        labelTo.setForeground(Color.white);
        panelFrom.add(labelFrom);
        panelTo.add(labelTo);

        panelList.add(panelFrom);
        panelList.add(panelTo);

        // 数据库设置Panel

        databaseSettingPanel = new JPanel();
        databaseSettingPanel.setBackground(UiConsts.MAIN_BACK_COLOR);
        databaseSettingPanel.setLayout(new BorderLayout());
        databaseSettingPanel.add(databaseSettingPanelFrom);

        panelCenter.add(panelList, BorderLayout.WEST);
        panelCenter.add(databaseSettingPanel, BorderLayout.CENTER);

        return panelCenter;
    }

    /**
     * 添加相关组件的事件监听
     */
    private void addListener() {
        panelFrom.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                panelFrom.setBackground(new Color(69, 186, 121));
                panelTo.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);

                DatabasePanel.databaseSettingPanel.removeAll();
                DatabasePanelFrom.setContent();
                DatabasePanel.databaseSettingPanel.add(databaseSettingPanelFrom);
                App.databasePanel.updateUI();

            }
        });

        panelTo.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                panelTo.setBackground(new Color(69, 186, 121));
                panelFrom.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);

                DatabasePanel.databaseSettingPanel.removeAll();
                DatabasePanelTo.setContent();
                DatabasePanel.databaseSettingPanel.add(databaseSettingPanelTo);
                App.databasePanel.updateUI();

            }
        });

    }
}
