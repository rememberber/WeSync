package com.luoboduner.wesync.logic.bean;

/**
 * 表object类
 *
 * @author Bob
 */
public class Table {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 主键
     */
    private String primKey;
    /**
     * 字段
     */
    private String fields;

    /**
     * 其他条件/保留
     */
    private String other;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimKey() {
        return primKey;
    }

    public void setPrimKey(String primKey) {
        this.primKey = primKey;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
