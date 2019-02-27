package com.luoboduner.wesync.tools;


import com.luoboduner.wesync.ui.panel.StatusPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志封装类，控制系统信息（界面和log文件）的记录
 *
 * @author Bob
 */
public class StatusLog {
    private static final Logger logger = LoggerFactory.getLogger(StatusLog.class);

    /**
     * 设置状态面板状态信息
     *
     * @param status
     */
    public static void setStatus(String status) {
        StatusPanel.labelStatus.setText(status);
    }

    /**
     * 设置状态面板状态的详细信息+写入日志文件（Debug模式下）
     *
     * @param statusDetail
     * @param level
     */
    public static void setStatusDetail(String statusDetail, Enum<LogLevel> level) {
        StatusPanel.labelStatusDetail.setText(PropertyUtil.getProperty("ds.tool.detail") + statusDetail);
        if ("true".equals(ConstantsTools.CONFIGER.getDebugMode())) {

            if ("INFO".equals(level.toString())) {
                logger.info(statusDetail);
            } else if ("DEBUG".equals(level.toString())) {
                logger.debug(statusDetail);
            }

        }
        if ("WARN".equals(level.toString())) {
            logger.warn(statusDetail);
        } else if ("ERROR".equals(level.toString())) {
            logger.error(statusDetail);
        } else if ("FATAL".equals(level.toString())) {
            logger.error(statusDetail);
        }
    }

    /**
     * 设置状态面板中上一次同步时间，并写入conf
     *
     * @param lastTime
     */
    public static void setLastTime(String lastTime) {
        StatusPanel.labelLastTime.setText(PropertyUtil.getProperty("ds.ui.status.lastSync") + lastTime);
        try {
            ConstantsTools.CONFIGER.setLastSyncTime(lastTime);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 设置状态面板中的持续时间，并写入conf
     *
     * @param keepTime
     */
    public static void setKeepTime(String keepTime) {
        StatusPanel.labelKeepTime.setText(PropertyUtil.getProperty("ds.ui.status.keepTime") + keepTime
                + PropertyUtil.getProperty("ds.ui.status.second"));
        try {
            ConstantsTools.CONFIGER.setLastKeepTime(keepTime);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 设置状态面板中下一次同步时间
     *
     * @param nextTime
     */
    public static void setNextTime(String nextTime) {
        StatusPanel.labelNextTime.setText(PropertyUtil.getProperty("ds.ui.schedule.nextTime") + nextTime);
    }

    /**
     * 设置状态面板中的成功总次数，并写入conf
     *
     * @param success
     */
    public static void setSuccess(String success) {
        StatusPanel.labelSuccess.setText(PropertyUtil.getProperty("ds.ui.status.successTimes") + success);
        try {
            ConstantsTools.CONFIGER.setSuccessTime(success);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 设置状态面板中的失败总次数，并写入conf
     *
     * @param fail
     */
    public static void setFail(String fail) {
        StatusPanel.labelFail.setText(PropertyUtil.getProperty("ds.ui.status.failTimes") + fail);
        try {
            ConstantsTools.CONFIGER.setFailTime(fail);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }
}