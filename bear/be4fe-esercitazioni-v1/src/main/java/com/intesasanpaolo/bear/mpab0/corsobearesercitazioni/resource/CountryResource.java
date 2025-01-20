package com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.resource;


public class CountryResource {
    private long chiave;
    private String name;
    private String language;
    private String continent;

    public CountryResource() {
    }

    public CountryResource(long chiave, String name, String language, String continent) {
        this.chiave = chiave;
        this.name = name;
        this.language = language;
        this.continent = continent;
    }

    public long getChiave() {
        return chiave;
    }

    public void setChiave(long chiave) {
        this.chiave = chiave;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}