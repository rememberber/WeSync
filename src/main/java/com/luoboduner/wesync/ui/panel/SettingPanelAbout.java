package com.luoboduner.wesync.ui.panel;

import com.luoboduner.wesync.ui.UiConsts;
import com.luoboduner.wesync.ui.component.MyIconButton;
import com.luoboduner.wesync.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 关于面板
 *
 * @author Bob
 */
public class SettingPanelAbout extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * 构造
     */
    public SettingPanelAbout() {
        initialize();
        addComponent();
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
        panelCenter.setLayout(new GridLayout(3, 1));

        // 图标、版本Grid
        JPanel panelGridIcon = new JPanel();
        panelGridIcon.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridIcon.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));

        // 初始化组件
        MyIconButton icon = new MyIconButton(UiConsts.ICON_DATA_SYNC, UiConsts.ICON_DATA_SYNC,
                UiConsts.ICON_DATA_SYNC, "");
        JLabel labelName = new JLabel(UiConsts.APP_NAME);
        JLabel labelVersion = new JLabel(UiConsts.APP_VERSION);

        // 字体
        labelName.setFont(UiConsts.FONT_NORMAL);
        labelVersion.setFont(UiConsts.FONT_NORMAL);

        // 大小
        Dimension size = new Dimension(200, 30);
        labelName.setPreferredSize(size);
        labelVersion.setPreferredSize(size);

        // 组合元素
        panelGridIcon.add(icon);
        panelGridIcon.add(labelName);
        panelGridIcon.add(labelVersion);

        // 建议帮助 Grid
        JPanel panelGridHelp = new JPanel();
        panelGridHelp.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridHelp.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));

        // 初始化组件
        JLabel labelAdvice = new JLabel(PropertyUtil.getProperty("ds.ui.app.advice"));
        JLabel labelHelp = new JLabel(PropertyUtil.getProperty("ds.ui.app.help"));

        // 字体
        labelAdvice.setFont(UiConsts.FONT_NORMAL);
        labelHelp.setFont(UiConsts.FONT_NORMAL);

        // 大小
        labelAdvice.setPreferredSize(UiConsts.LABLE_SIZE);
        labelHelp.setPreferredSize(UiConsts.LABLE_SIZE);

        // 组合元素
        panelGridHelp.add(labelAdvice);
        panelGridHelp.add(labelHelp);

        panelCenter.add(panelGridIcon);
        // panelCenter.add(panelGridHelp);
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
        panelDown.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 15));

        JLabel labelInfo = new JLabel(PropertyUtil.getProperty("ds.ui.app.info"));
        labelInfo.setFont(UiConsts.FONT_NORMAL);
        labelInfo.setForeground(Color.gray);

        panelDown.add(labelInfo);

        return panelDown;
    }

}
