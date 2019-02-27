package com.luoboduner.wesync.ui.dialog;

import com.luoboduner.wesync.tools.PropertyUtil;
import com.luoboduner.wesync.ui.UiConsts;

import javax.swing.*;
import java.awt.*;

/**
 * <pre>
 * 新建备份dialog
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">Zhou Bo</a>
 * @since 2019/2/27.
 */
public class DbBackUpCreateDialog extends JDialog {
    public DbBackUpCreateDialog(JFrame frame, String property, boolean b) {
        super(frame, property, b);
    }

    public void init() {
        this.setBounds(460, 220, 400, 250);
        JPanel panelDialog = new JPanel(new BorderLayout());
        panelDialog.setBackground(UiConsts.MAIN_BACK_COLOR);
        JPanel panelDialogCenter = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 10));
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

        labelType.setFont(UiConsts.FONT_NORMAL);
        labelComment.setFont(UiConsts.FONT_NORMAL);
        labelProgress.setFont(UiConsts.FONT_NORMAL);
        comboxType.setFont(UiConsts.FONT_NORMAL);
        textFieldComment.setFont(UiConsts.FONT_NORMAL);

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
        buttonSure.setFont(UiConsts.FONT_NORMAL);
        buttonCancel.setFont(UiConsts.FONT_NORMAL);

        grid1.add(buttonSure);
        grid2.add(buttonCancel);
        panelDialogDown.add(grid1);
        panelDialogDown.add(grid2);

        panelDialog.add(panelDialogCenter, BorderLayout.CENTER);
        panelDialog.add(panelDialogDown, BorderLayout.SOUTH);

        this.add(panelDialog);

        buttonCancel.addActionListener(e -> this.setVisible(false));
    }
}
