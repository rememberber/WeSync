package com.luoboduner.wesync.vivo.bean;

import java.io.File;

/**
 * @author liweiqing
 * @date 2023/6/19 23:14
 * @description
 */
public class VivoReportRowData {

    //所属分析报告的 文件名
    public String reportFileName;
    public File file;
    public String targetLanguage;
    /**
     * 分类名称 ，默认为 OS字串
     */
    public String category;
    public String projectName;
    public String sdlxliffFileName;
    public String taskType;
    public String requester;
    /**
     * 目标语言
     */
    public String target;
    /**
     * 匹配率-新翻   85%以下都是算新字 对应到 report 里   （75% - 84%、50% - 74%、New/AT）
     *
     */
    public Integer matching_New;

    /**
     * 匹配率 85%-94%
     */
    public Integer matching_50_74;
    public Integer matching_75_84;
    public Integer matching_85_94;
    public Integer matching_95_99;
    public Integer matching_100;
    /**
     * 对应到 report里  Context Match
     */
    public Integer matching_100locked;
    /**
     * 对应到 report里  Repetitions、Cross-file Repetitions
     */
    public Integer matching_Repetitions;
    public Integer matching_CrossFileRepetitions;
    public Double unitPrice;
    public String lspProject;
    public String lsp;
    public String startDate;
    public String translator;
    /**
     * 暂时没用
     */
    public String DeliveryDate;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getMatching_New() {
        return matching_New;
    }

    public void setMatching_New(Integer matching_New) {
        this.matching_New = matching_New;
    }

    public Integer getMatching_85_94() {
        return matching_85_94;
    }

    public void setMatching_85_94(Integer matching_85_94) {
        this.matching_85_94 = matching_85_94;
    }

    public Integer getMatching_95_99() {
        return matching_95_99;
    }

    public void setMatching_95_99(Integer matching_95_99) {
        this.matching_95_99 = matching_95_99;
    }

    public Integer getMatching_100() {
        return matching_100;
    }

    public void setMatching_100(Integer matching_100) {
        this.matching_100 = matching_100;
    }

    public Integer getMatching_Repetitions() {
        return matching_Repetitions;
    }

    public void setMatching_Repetitions(Integer matching_Repetitions) {
        this.matching_Repetitions = matching_Repetitions;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getLspProject() {
        return lspProject;
    }

    public void setLspProject(String lspProject) {
        this.lspProject = lspProject;
    }

    public String getLsp() {
        return lsp;
    }

    public void setLsp(String lsp) {
        this.lsp = lsp;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public String getSdlxliffFileName() {
        return sdlxliffFileName;
    }

    public void setSdlxliffFileName(String sdlxliffFileName) {
        this.sdlxliffFileName = sdlxliffFileName;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public Integer getMatching_100locked() {
        return matching_100locked;
    }

    public void setMatching_100locked(Integer matching_100locked) {
        this.matching_100locked = matching_100locked;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public Integer getMatching_50_74() {
        return matching_50_74;
    }

    public void setMatching_50_74(Integer matching_50_74) {
        this.matching_50_74 = matching_50_74;
    }

    public Integer getMatching_75_84() {
        return matching_75_84;
    }

    public void setMatching_75_84(Integer matching_75_84) {
        this.matching_75_84 = matching_75_84;
    }

    public Integer getMatching_CrossFileRepetitions() {
        return matching_CrossFileRepetitions;
    }

    public void setMatching_CrossFileRepetitions(Integer matching_CrossFileRepetitions) {
        this.matching_CrossFileRepetitions = matching_CrossFileRepetitions;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
