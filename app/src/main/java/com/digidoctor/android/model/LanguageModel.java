package com.digidoctor.android.model;

public class LanguageModel {

    String language;
    String localeCode;
    boolean isSelected;


    public LanguageModel(String language, String localeCode, boolean isSelected) {
        this.language = language;
        this.localeCode = localeCode;
        this.isSelected = isSelected;
    }


    public LanguageModel() {
    }

    public String getLanguage() {
        return language;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
