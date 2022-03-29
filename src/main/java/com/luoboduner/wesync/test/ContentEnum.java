package com.luoboduner.wesync.test;

/**
 * @author liweiqing
 * @date 2022/3/17 14:20
 * @description
 */
public enum ContentEnum {
    KEYWORD("易混淆关键词", 1), PLAIN_TEXT("普通文本", 2);
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private ContentEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (ContentEnum c : ContentEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}