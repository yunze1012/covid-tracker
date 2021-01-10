package com.personal.covidtracker.model;

public class Statistics {

    private String country;
    private String province;
    private int cases;
    private int dailyDelta;

    public void setDailyDelta(int dailyDelta) {
        this.dailyDelta = dailyDelta;
    }

    public int getDailyDelta() {
        return dailyDelta;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

}
