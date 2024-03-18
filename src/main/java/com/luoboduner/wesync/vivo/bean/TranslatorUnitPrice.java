package com.luoboduner.wesync.vivo.bean;

/**
 * @author liweiqing
 * @date 2023/6/20 9:59
 * @description
 */
public class TranslatorUnitPrice {

    public String targetLanguageCode;

    public String translator;

    public Double unitPrice;

    public String getTargetLanguageCode() {
        return targetLanguageCode;
    }

    public void setTargetLanguageCode(String targetLanguageCode) {
        this.targetLanguageCode = targetLanguageCode;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
