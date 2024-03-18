package com.luoboduner.wesync.vivo.bean;

import java.io.File;
import java.util.List;

/**
 * @author liweiqing
 * @date 2023/6/19 22:48
 * @description
 */

public class VivoEveryDayReportForder {

    //日报 目录文件夹
    public File everyDayReportForder;

    //文件开始日期
    public String startDate;

    //项目编号，由文件夹名决定， 文件夹名上的项目编号 由项目经理提供时 编辑好。 该编号 对应公司系统订单号。
    public String projectLSP;

    //客户
    public String projectName;

    //目标语言,行数据
//    public Map<String, List<VivoReportRowData>> rowsData;

    public List<VivoReportRowData> getRowDataList() {
        return rowDataList;
    }

    public void setRowDataList(List<VivoReportRowData> rowDataList) {
        this.rowDataList = rowDataList;
    }

    public List<VivoReportRowData> rowDataList;

    public File getEveryDayReportForder() {
        return everyDayReportForder;
    }

    public void setEveryDayReportForder(File everyDayReportForder) {
        this.everyDayReportForder = everyDayReportForder;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getProjectLSP() {
        return projectLSP;
    }

    public void setProjectLSP(String projectLSP) {
        this.projectLSP = projectLSP;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
