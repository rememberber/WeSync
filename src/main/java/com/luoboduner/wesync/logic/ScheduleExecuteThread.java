package com.luoboduner.wesync.logic;

import com.luoboduner.wesync.ui.panel.StatusPanel;
import com.luoboduner.wesync.tools.*;

/**
 * 定时任务执行器，继承于执行器线程类
 *
 * @author lwq
 */
public class ScheduleExecuteThread extends ExecuteThread {

    @Override
    public void run() {
        if (!StatusPanel.isRunning) {
            StatusPanel.isRunning = true;
            this.setName("ScheduleExecuteThread");
            StatusPanel.buttonStartNow.setEnabled(false);
            long enterTime = System.currentTimeMillis();
//            StatusPanel.progressTotal.setMaximum(6);
            // 初始化变量
            init();
            // 测试连接
//            boolean isLinked = testLink();
//            if (isLinked) {
//                // 设置显示下一次执行时间
////            StatusPanel.labelNextTime.setText(PropertyUtil.getProperty("ds.ui.schedule.nextTime") + Utils.getNextSyncTime());
//                StatusPanel.isRunning = false;
//            }
        }
    }
}
