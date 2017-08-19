package UI.panel;

import UI.AppMainWindow;
import UI.ConstantsUI;
import UI.MyIconButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.ConstantsTools;
import tools.PropertyUtil;
import tools.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 计划任务面板
 *
 * @author Bob
 */
public class SchedulePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static MyIconButton buttonSave;

    private static JRadioButton radio5Min, radio15Min, radio30Min, radioPerHour, radioPerDay, radioPerWeek,
            radioEveryDay;

    private static JTextField fixedTime;

    private static final Logger logger = LoggerFactory.getLogger(SchedulePanel.class);

    /**
     * 构造
     */
    public SchedulePanel() {
        initialize();
        addComponent();
        addListener();
        setCurrentSchedule();
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

        this.add(getUpPanel(), BorderLayout.NORTH);
        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.add(getDownPanel(), BorderLayout.SOUTH);
    }

    /**
     * 上部面板
     *
     * @return
     */
    private JPanel getUpPanel() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 5));

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.schedule.title"));
        labelTitle.setFont(ConstantsUI.FONT_TITLE);
        labelTitle.setForeground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        panelUp.add(labelTitle);

        return panelUp;
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
        panelCenter.setLayout(new GridLayout(1, 1));

        // 计划时间Grid
        JPanel panelGridSchedule = new JPanel();
        panelGridSchedule.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridSchedule.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        // 初始化组件
        radio5Min = new JRadioButton(PropertyUtil.getProperty("ds.ui.schedule.radio1"));
        radio15Min = new JRadioButton(PropertyUtil.getProperty("ds.ui.schedule.radio2"));
        radio30Min = new JRadioButton(PropertyUtil.getProperty("ds.ui.schedule.radio3"));
        radioPerHour = new JRadioButton(PropertyUtil.getProperty("ds.ui.schedule.radio4"));
        radioPerDay = new JRadioButton(PropertyUtil.getProperty("ds.ui.schedule.radio5"));
        radioPerWeek = new JRadioButton(PropertyUtil.getProperty("ds.ui.schedule.radio6"));
        radioEveryDay = new JRadioButton(PropertyUtil.getProperty("ds.ui.schedule.radio7"));
        fixedTime = new JTextField();

        // 字体
        radio5Min.setFont(ConstantsUI.FONT_RADIO);
        radio15Min.setFont(ConstantsUI.FONT_RADIO);
        radio30Min.setFont(ConstantsUI.FONT_RADIO);
        radioPerHour.setFont(ConstantsUI.FONT_RADIO);
        radioPerDay.setFont(ConstantsUI.FONT_RADIO);
        radioPerWeek.setFont(ConstantsUI.FONT_RADIO);
        radioEveryDay.setFont(ConstantsUI.FONT_RADIO);
        fixedTime.setFont(ConstantsUI.FONT_RADIO);

        // 颜色
        radio5Min.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        radio15Min.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        radio30Min.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        radioPerHour.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        radioPerDay.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        radioPerWeek.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        radioEveryDay.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        fixedTime.setBackground(ConstantsUI.MAIN_BACK_COLOR);

        // 大小
        radio5Min.setMinimumSize(ConstantsUI.RADIO_SIZE);
        radio15Min.setPreferredSize(ConstantsUI.RADIO_SIZE);
        radio30Min.setMinimumSize(ConstantsUI.RADIO_SIZE);
        radioPerHour.setPreferredSize(ConstantsUI.RADIO_SIZE);
        radioPerDay.setMinimumSize(ConstantsUI.RADIO_SIZE);
        radioPerWeek.setPreferredSize(ConstantsUI.RADIO_SIZE);
        radioEveryDay.setMinimumSize(ConstantsUI.RADIO_SIZE);
        Dimension preferredSize = new Dimension(130, 26);
        fixedTime.setPreferredSize(preferredSize);

        // 组合元素
        panelGridSchedule.add(radio5Min);
        panelGridSchedule.add(radio15Min);
        panelGridSchedule.add(radio30Min);
        panelGridSchedule.add(radioPerHour);
        panelGridSchedule.add(radioPerDay);
        panelGridSchedule.add(radioPerWeek);
        panelGridSchedule.add(radioEveryDay);
        panelGridSchedule.add(fixedTime);

        panelCenter.add(panelGridSchedule);

        return panelCenter;
    }

    /**
     * 下部面板
     *
     * @return
     */
    private JPanel getDownPanel() {
        JPanel panelDown = new JPanel();
        panelDown.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelDown.setLayout(new FlowLayout(FlowLayout.RIGHT, ConstantsUI.MAIN_H_GAP, 15));

        buttonSave = new MyIconButton(ConstantsUI.ICON_SAVE, ConstantsUI.ICON_SAVE_ENABLE,
                ConstantsUI.ICON_SAVE_DISABLE, "");
        panelDown.add(buttonSave);

        return panelDown;
    }

    /**
     * 设置当前的radio状态
     */
    public void setCurrentSchedule() {

        String[] indexs = ConstantsTools.CONFIGER.getSchedule().split(",");
        radio5Min.setSelected(Boolean.parseBoolean(indexs[0]));
        radio15Min.setSelected(Boolean.parseBoolean(indexs[1]));
        radio30Min.setSelected(Boolean.parseBoolean(indexs[2]));
        radioPerHour.setSelected(Boolean.parseBoolean(indexs[3]));
        radioPerDay.setSelected(Boolean.parseBoolean(indexs[4]));
        radioPerWeek.setSelected(Boolean.parseBoolean(indexs[5]));
        radioEveryDay.setSelected(Boolean.parseBoolean(indexs[6]));
        fixedTime.setText(ConstantsTools.CONFIGER.getScheduleFixTime());
    }

    /**
     * 为各组件添加事件监听
     */
    public void addListener() {

        radio5Min.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                radio5Min.setSelected(true);
                radio15Min.setSelected(false);
                radio30Min.setSelected(false);
                radioPerHour.setSelected(false);
                radioPerDay.setSelected(false);
                radioPerWeek.setSelected(false);
                radioEveryDay.setSelected(false);

                AppMainWindow.schedulePanel.updateUI();

            }
        });

        radio15Min.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                radio5Min.setSelected(false);
                radio15Min.setSelected(true);
                radio30Min.setSelected(false);
                radioPerHour.setSelected(false);
                radioPerDay.setSelected(false);
                radioPerWeek.setSelected(false);
                radioEveryDay.setSelected(false);

                AppMainWindow.schedulePanel.updateUI();

            }
        });

        radio30Min.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                radio5Min.setSelected(false);
                radio15Min.setSelected(false);
                radio30Min.setSelected(true);
                radioPerHour.setSelected(false);
                radioPerDay.setSelected(false);
                radioPerWeek.setSelected(false);
                radioEveryDay.setSelected(false);

                AppMainWindow.schedulePanel.updateUI();

            }
        });

        radioPerHour.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                radio5Min.setSelected(false);
                radio15Min.setSelected(false);
                radio30Min.setSelected(false);
                radioPerHour.setSelected(true);
                radioPerDay.setSelected(false);
                radioPerWeek.setSelected(false);
                radioEveryDay.setSelected(false);

                AppMainWindow.schedulePanel.updateUI();

            }
        });

        radioPerDay.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                radio5Min.setSelected(false);
                radio15Min.setSelected(false);
                radio30Min.setSelected(false);
                radioPerHour.setSelected(false);
                radioPerDay.setSelected(true);
                radioPerWeek.setSelected(false);
                radioEveryDay.setSelected(false);

                AppMainWindow.schedulePanel.updateUI();

            }
        });

        radioPerWeek.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                radio5Min.setSelected(false);
                radio15Min.setSelected(false);
                radio30Min.setSelected(false);
                radioPerHour.setSelected(false);
                radioPerDay.setSelected(false);
                radioPerWeek.setSelected(true);
                radioEveryDay.setSelected(false);

                AppMainWindow.schedulePanel.updateUI();

            }
        });

        radioEveryDay.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                radio5Min.setSelected(false);
                radio15Min.setSelected(false);
                radio30Min.setSelected(false);
                radioPerHour.setSelected(false);
                radioPerDay.setSelected(false);
                radioPerWeek.setSelected(false);
                radioEveryDay.setSelected(true);

                AppMainWindow.schedulePanel.updateUI();

            }
        });

        buttonSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                StringBuffer index = new StringBuffer();
                // radio5Min, radioPerHour, radioPerDay, radioPerWeek;
                index.append(radio5Min.isSelected()).append(",");
                index.append(radio15Min.isSelected()).append(",");
                index.append(radio30Min.isSelected()).append(",");
                index.append(radioPerHour.isSelected()).append(",");
                index.append(radioPerDay.isSelected()).append(",");
                index.append(radioPerWeek.isSelected()).append(",");
                index.append(radioEveryDay.isSelected());

                try {
                    ConstantsTools.CONFIGER.setSchedule(index.toString());
                    ConstantsTools.CONFIGER.setScheduleFixTime(fixedTime.getText());
                    JOptionPane.showMessageDialog(AppMainWindow.schedulePanel,
                            PropertyUtil.getProperty("ds.ui.schedule.saveTips"), PropertyUtil.getProperty("ds.ui.tips"), JOptionPane.PLAIN_MESSAGE);
                    if (!StatusPanel.buttonStartSchedule.isEnabled()) {// 设置显示下一次执行时间
                        StatusPanel.labelNextTime.setText(PropertyUtil.getProperty("ds.ui.schedule.nextTime") + Utils.getNextSyncTime());
                    }

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(AppMainWindow.schedulePanel, PropertyUtil.getProperty("ds.ui.save.fail") + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
                            JOptionPane.ERROR_MESSAGE);
                    logger.error("Write to xml file error" + e1.toString());
                }

            }
        });
    }
}
