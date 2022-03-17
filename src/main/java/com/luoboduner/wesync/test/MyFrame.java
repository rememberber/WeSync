package com.luoboduner.wesync.test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {

    public MyFrame() {
        super("test");
        JPanel root = new JPanel();

        this.setContentPane(root);
        JCheckBox jCheckBox = new JCheckBox("用户协议");
        jCheckBox.setSelected(false);

        JButton jButton = new JButton("下一步");
        jButton.setEnabled(false);

        String test="test";
        test.contains("");
        root.add(jCheckBox);
        root.add(jButton);
        jCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jCheckBox.isSelected()) {
                    jButton.setEnabled(true);

                }else {
                    jButton.setEnabled(false);
                }
            }
        });
    }
}
