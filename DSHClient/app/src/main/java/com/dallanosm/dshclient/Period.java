package com.dallanosm.dshclient;

public class Period {

    Long initDate;

    Long finishDate;

    boolean isSunmer;

    public Period(Long initDate, Long finishDate, boolean isSunmer) {
        this.initDate = initDate;
        this.finishDate = finishDate;
        this.isSunmer = isSunmer;
    }

    public Long getInitDate() {
        return initDate;
    }

    public void setInitDate(Long initDate) {
        this.initDate = initDate;
    }

    public Long getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Long finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isSunmer() {
        return isSunmer;
    }

    public void setSunmer(boolean sunmer) {
        isSunmer = sunmer;
    }
}

