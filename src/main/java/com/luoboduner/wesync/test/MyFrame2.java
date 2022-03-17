package com.luoboduner.wesync.test;

import javax.swing.*;

public class MyFrame2 extends JFrame {

    public MyFrame2() {
        super("test");
        JPanel root = new JPanel();
        this.setContentPane(root);

        //
        JComboBox<String> colorField = new JComboBox<>();
        colorField.addItem("红");
        colorField.addItem("黄");
        colorField.addItem("绿");
        root.add(colorField);

        JButton button = new JButton("测试");
        root.add(button);
        button.addActionListener( e -> {
            int count =colorField.getItemCount();
            String value = colorField.getItemAt(0);
            System.out.println(value+","+count);

            System.out.println("选择的索引："+colorField.getSelectedIndex());
            System.out.println("选择的值："+colorField.getSelectedItem());
        });


        JComboBox<Student> colorField2 = new JComboBox<>();
        root.add(colorField2);
          int id=123123;
          String name="张三";
         boolean sex=true;
         String  phone="6688";
        colorField2.addItem( new Student(id,name,sex,phone));
    }
}
