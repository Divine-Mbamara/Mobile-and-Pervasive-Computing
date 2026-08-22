package com.example.weatherapp.model;

public class Weather {
    private String name, country, condition, icon, windMph, windKph, windDirect;
    private Integer windDegree, tempC, tempF, feelLikeC, feelLikeF;

    public Weather(String name, String country, String condition, String icon, Integer feelLikeC, Integer feelLikeF, String windMph, String windKph, String windDirect, Integer tempC, Integer tempF, Integer windDegree) {
        this.name = name;
        this.country = country;
        this.condition = condition;
        this.icon = icon;
        this.feelLikeC = feelLikeC;
        this.feelLikeF = feelLikeF;
        this.windMph = windMph;
        this.windKph = windKph;
        this.windDirect = windDirect;
        this.tempC = tempC;
        this.tempF = tempF;
        this.windDegree = windDegree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getFeelLikeC() {
        return feelLikeC;
    }

    public void setFeelLikeC(Integer feelLikeC) {
        this.feelLikeC = feelLikeC;
    }

    public Integer getFeelLikeF() {
        return feelLikeF;
    }

    public void setFeelLikeF(Integer feelLikeF) {
        this.feelLikeF = feelLikeF;
    }

    public String getWindMph() {
        return windMph;
    }

    public void setWindMph(String windMph) {
        this.windMph = windMph;
    }

    public String getWindKph() {
        return windKph;
    }

    public void setWindKph(String windKph) {
        this.windKph = windKph;
    }

    public String getWindDirect() {
        return windDirect;
    }

    public void setWindDirect(String windDirect) {
        this.windDirect = windDirect;
    }

    public Integer getTempC() {
        return tempC;
    }

    public void setTempC(Integer tempC) {
        this.tempC = tempC;
    }

    public Integer getTempF() {
        return tempF;
    }

    public void setTempF(Integer tempF) {
        this.tempF = tempF;
    }

    public Integer getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(Integer windDegree) {
        this.windDegree = windDegree;
    }
}
