package com.luoboduner.wesync.test;

/**
 * @author liweiqing
 * @date 2022/3/17 14:28
 * @description
 */
public class TranslationContent {

    private String content;
    private ContentEnum contentEnum;
    private int indexStart;
    private int indexEnd;

    public TranslationContent(String content, ContentEnum contentEnum, int indexStart, int indexEnd) {
        this.content = content;
        this.contentEnum = contentEnum;
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
    }
    public TranslationContent(ContentEnum contentEnum, int indexStart, int indexEnd) {
        this.contentEnum = contentEnum;
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
    }

    public TranslationContent() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentEnum getContentEnum() {
        return contentEnum;
    }

    public void setContentEnum(ContentEnum contentEnum) {
        this.contentEnum = contentEnum;
    }

    public int getIndexStart() {
        return indexStart;
    }

    public void setIndexStart(int indexStart) {
        this.indexStart = indexStart;
    }

    public int getIndexEnd() {
        return indexEnd;
    }

    public void setIndexEnd(int indexEnd) {
        this.indexEnd = indexEnd;
    }

    @Override
    public String toString() {
        return "TranslationContent{" +
                "content='" + content + '\'' +
                ", contentEnum=" + contentEnum.getName() +
                ", indexStart=" + indexStart +
                ", indexEnd=" + indexEnd +
                '}';
    }
}
