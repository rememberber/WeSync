package com.luoboduner.wesync.ui.panel;

import com.luoboduner.wesync.ui.UiConsts;
import com.luoboduner.wesync.ui.component.MyIconButton;
import com.luoboduner.wesync.logic.ExecuteThread;
import com.luoboduner.wesync.logic.ScheduleExecuteThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.luoboduner.wesync.tools.ConstantsTools;
import com.luoboduner.wesync.tools.PropertyUtil;
import com.luoboduner.wesync.tools.StatusLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 状态面板
 *
 * @author Bob
 */
public class StatusPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(StatusPanel.class);

    public static MyIconButton buttonStartSchedule;
    public static MyIconButton buttonStop;
    public static MyIconButton buttonStartNow;

    public static JProgressBar progressTotal;
    public static JProgressBar progressCurrent;

    public static JLabel labelStatus;
    public static JLabel labelStatusDetail;
    public static JLabel labelFrom;
    public static JLabel labelTo;
    public static JLabel labelLastTime;
    public static JLabel labelKeepTime;
    public static JLabel labelNextTime;
    public static JLabel labelSuccess;
    public static JLabel labelFail;
    private static JLabel labelLog;

    private static ScheduledExecutorService service;

    public static boolean isRunning = false;

    /**
     * 构造
     */
    public StatusPanel() {
        super(true);
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
        panelUp.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 5));

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.status.title"));
        labelTitle.setFont(UiConsts.FONT_TITLE);
        labelTitle.setForeground(UiConsts.TOOL_BAR_BACK_COLOR);
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
        panelCenter.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(4, 1));

        // 状态Grid
        JPanel panelGridStatus = new JPanel();
        panelGridStatus.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridStatus.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));

        labelStatus = new JLabel(PropertyUtil.getProperty("ds.ui.status.ready"));
        labelStatusDetail = new JLabel(PropertyUtil.getProperty("ds.ui.status.detail"));
        labelStatus.setFont(new Font(PropertyUtil.getProperty("ds.ui.font.family"), 1, 15));
        labelStatusDetail.setFont(UiConsts.FONT_NORMAL);

        labelStatus.setPreferredSize(UiConsts.LABLE_SIZE);
        labelStatusDetail.setPreferredSize(UiConsts.LABLE_SIZE);

        panelGridStatus.add(labelStatus);
        panelGridStatus.add(labelStatusDetail);

        // 来源/目标 Grid
        JPanel panelGridFromTo = new JPanel();
        panelGridFromTo.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridFromTo.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));

        labelFrom = new JLabel();
        labelTo = new JLabel();
        labelFrom.setFont(UiConsts.FONT_NORMAL);
        labelTo.setFont(UiConsts.FONT_NORMAL);
        labelFrom.setPreferredSize(UiConsts.LABLE_SIZE);
        labelTo.setPreferredSize(UiConsts.LABLE_SIZE);

        panelGridFromTo.add(labelFrom);
        panelGridFromTo.add(labelTo);

        // 详情Grid
        JPanel panelGridDetail = new JPanel();
        panelGridDetail.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridDetail.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));

        labelLastTime = new JLabel();
        labelKeepTime = new JLabel();
        labelNextTime = new JLabel();
        labelNextTime.setText(PropertyUtil.getProperty("ds.ui.schedule.nextTime"));
        labelSuccess = new JLabel();
        labelFail = new JLabel();
        labelLog = new JLabel(PropertyUtil.getProperty("ds.ui.status.logDetail"));

        labelLastTime.setFont(UiConsts.FONT_NORMAL);
        labelKeepTime.setFont(UiConsts.FONT_NORMAL);
        labelNextTime.setFont(UiConsts.FONT_NORMAL);
        labelSuccess.setFont(UiConsts.FONT_NORMAL);
        labelFail.setFont(UiConsts.FONT_NORMAL);
        labelLog.setFont(UiConsts.FONT_NORMAL);
        labelLastTime.setPreferredSize(new Dimension(240, 30));
        labelKeepTime.setPreferredSize(new Dimension(300, 30));
        labelNextTime.setPreferredSize(UiConsts.LABLE_SIZE);
        labelSuccess.setPreferredSize(new Dimension(240, 30));
        labelFail.setPreferredSize(new Dimension(236, 30));
        labelLog.setPreferredSize(UiConsts.LABLE_SIZE);
        labelLog.setForeground(UiConsts.TOOL_BAR_BACK_COLOR);

        panelGridDetail.add(labelLastTime);
        panelGridDetail.add(labelKeepTime);
        panelGridDetail.add(labelNextTime);
        panelGridDetail.add(labelSuccess);
        panelGridDetail.add(labelFail);
        panelGridDetail.add(labelLog);

        // 进度Grid
        JPanel panelGridProgress = new JPanel();
        panelGridProgress.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridProgress.setLayout(new GridLayout(2, 1, UiConsts.MAIN_H_GAP, 0));
        JPanel panelCurrentProgress = new JPanel();
        panelCurrentProgress.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelCurrentProgress.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 20));
        JPanel panelTotalProgress = new JPanel();
        panelTotalProgress.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelTotalProgress.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));

        JLabel labelCurrent = new JLabel(PropertyUtil.getProperty("ds.ui.status.progress.current"));
        JLabel labelTotal = new JLabel(PropertyUtil.getProperty("ds.ui.status.progress.total"));
        labelCurrent.setFont(UiConsts.FONT_NORMAL);
        labelTotal.setFont(UiConsts.FONT_NORMAL);
        progressCurrent = new JProgressBar();
        progressTotal = new JProgressBar();

        Dimension preferredSizeLabel = new Dimension(80, 30);
        labelCurrent.setPreferredSize(preferredSizeLabel);
        labelTotal.setPreferredSize(preferredSizeLabel);
        Dimension preferredSizeProgressbar = new Dimension(640, 20);
        progressCurrent.setPreferredSize(preferredSizeProgressbar);
        progressTotal.setPreferredSize(preferredSizeProgressbar);

        panelCurrentProgress.add(labelCurrent);
        panelCurrentProgress.add(progressCurrent);
        panelTotalProgress.add(labelTotal);
        panelTotalProgress.add(progressTotal);

        panelGridProgress.add(panelCurrentProgress);
        panelGridProgress.add(panelTotalProgress);

        panelCenter.add(panelGridStatus);
        panelCenter.add(panelGridFromTo);
        panelCenter.add(panelGridDetail);
        panelCenter.add(panelGridProgress);

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
        panelDown.setLayout(new GridLayout(1, 2));
        JPanel panelGrid1 = new JPanel();
        panelGrid1.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGrid1.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 15));
        JPanel panelGrid2 = new JPanel();
        panelGrid2.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGrid2.setLayout(new FlowLayout(FlowLayout.RIGHT, UiConsts.MAIN_H_GAP, 15));

        buttonStartSchedule = new MyIconButton(UiConsts.ICON_START_SCHEDULE, UiConsts.ICON_START_SCHEDULE_ENABLE,
                UiConsts.ICON_START_SCHEDULE_DISABLE, "");
        buttonStop = new MyIconButton(UiConsts.ICON_STOP, UiConsts.ICON_STOP_ENABLE,
                UiConsts.ICON_STOP_DISABLE, "");
        buttonStop.setEnabled(false);
        buttonStartNow = new MyIconButton(UiConsts.ICON_SYNC_NOW, UiConsts.ICON_SYNC_NOW_ENABLE,
                UiConsts.ICON_SYNC_NOW_DISABLE, "");
        panelGrid1.add(buttonStartSchedule);
        panelGrid1.add(buttonStop);
        panelGrid2.add(buttonStartNow);

        panelDown.add(panelGrid1);
        panelDown.add(panelGrid2);
        return panelDown;
    }

    /**
     * 设置状态面板组件内容
     */
    public static void setContent() {

        labelFrom.setText(
                PropertyUtil.getProperty("ds.ui.status.from") + ConstantsTools.CONFIGER.getHostFrom() + "/" + ConstantsTools.CONFIGER.getNameFrom());
        labelTo.setText(PropertyUtil.getProperty("ds.ui.status.to") + ConstantsTools.CONFIGER.getHostTo() + "/" + ConstantsTools.CONFIGER.getNameTo());
        labelLastTime.setText(PropertyUtil.getProperty("ds.ui.status.lastSync") + ConstantsTools.CONFIGER.getLastSyncTime());
        labelKeepTime.setText(PropertyUtil.getProperty("ds.ui.status.keepTime") + ConstantsTools.CONFIGER.getLastKeepTime() + PropertyUtil.getProperty("ds.ui.status.second"));

        labelSuccess.setText(PropertyUtil.getProperty("ds.ui.status.successTimes") + ConstantsTools.CONFIGER.getSuccessTime());
        labelFail.setText(PropertyUtil.getProperty("ds.ui.status.failTimes") + ConstantsTools.CONFIGER.getFailTime());

    }

    /**
     * 为各组件添加事件监听
     */
    private void addListener() {
        buttonStartNow.addActionListener(e -> {
            if (isRunning == false) {
                buttonStartNow.setEnabled(false);
                buttonStartSchedule.setEnabled(false);
                StatusPanel.setContent();
                StatusPanel.progressTotal.setValue(0);
                StatusPanel.progressCurrent.setValue(0);
                labelStatus.setText(PropertyUtil.getProperty("ds.ui.status.manu"));
                ExecuteThread syncThread = new ExecuteThread();
                syncThread.start();
            }
        });
        buttonStartSchedule.addActionListener(e -> {

            buttonStartSchedule.setEnabled(false);
            buttonStop.setEnabled(true);
            StatusPanel.setContent();

            StatusPanel.progressTotal.setValue(0);
            StatusPanel.progressCurrent.setValue(0);
            labelStatus.setText(PropertyUtil.getProperty("ds.ui.status.scheduledRunning"));
            ScheduleExecuteThread syncThread = new ScheduleExecuteThread();
            service = Executors.newSingleThreadScheduledExecutor();
            // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
            String scheduleConf = ConstantsTools.CONFIGER.getSchedule();
            if ("true,false,false,false,false,false,false".equals(scheduleConf)) {
                service.scheduleAtFixedRate(syncThread, 0, 5, TimeUnit.MINUTES);

            } else if ("false,true,false,false,false,false,false".equals(scheduleConf)) {
                service.scheduleAtFixedRate(syncThread, 0, 15, TimeUnit.MINUTES);

            } else if ("false,false,true,false,false,false,false".equals(scheduleConf)) {
                service.scheduleAtFixedRate(syncThread, 0, 30, TimeUnit.MINUTES);

            } else if ("false,false,false,true,false,false,false".equals(scheduleConf)) {
                service.scheduleAtFixedRate(syncThread, 0, 1, TimeUnit.HOURS);

            } else if ("false,false,false,false,true,false,false".equals(scheduleConf)) {
                service.scheduleAtFixedRate(syncThread, 0, 1, TimeUnit.DAYS);

            } else if ("false,false,false,false,false,true,false".equals(scheduleConf)) {
                service.scheduleAtFixedRate(syncThread, 0, 7, TimeUnit.DAYS);

            } else if ("false,false,false,false,false,false,true".equals(scheduleConf)) {
                long oneDay = 24 * 60 * 60 * 1000;
                long initDelay = getTimeMillis(ConstantsTools.CONFIGER.getScheduleFixTime().trim())
                        - System.currentTimeMillis();
                initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
                service.scheduleAtFixedRate(syncThread, initDelay, oneDay, TimeUnit.MILLISECONDS);
            }

        });
        buttonStop.addActionListener(e -> {
            buttonStartSchedule.setEnabled(true);
            StatusPanel.buttonStartNow.setEnabled(true);
            service.shutdown();
            StatusLog.setNextTime("");
            labelStatus.setText(PropertyUtil.getProperty("ds.ui.status.ready"));
            buttonStop.setEnabled(false);
        });
        labelLog.addMouseListener(new MouseListener() {

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
                labelLog.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().open(new File(UiConsts.CURRENT_DIR + File.separator + "log"));
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    logger.error(e1.toString());
                }
            }
        });
    }

    /**
     * 获取指定时间对应的毫秒数
     *
     * @param time "HH:mm:ss"
     * @return
     */
    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
