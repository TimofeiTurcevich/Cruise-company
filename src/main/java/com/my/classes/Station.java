package com.my.classes;

import java.io.Serializable;

public class Station implements Serializable {
    private long id;
    private String country;
    private String city;

    public Station(){}
    public Station(long id, String country, String city){
        this.id = id;
        this.country =country;
        this.city = city;
    }

    public long getId() {
        return id;
    }
    public String getCity() {
        return city;
    }
    public String getCountry() {
        return country;
    }

    public static class Builder{
        private long id;
        private String country;
        private String city;
        private String totalCases;
        private String totalRecovered;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }
        public Builder setCity(String city) {
            this.city = city;
            return this;
        }
        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }
        public Builder setTotalCases(String totalCases){
            this.totalCases = totalCases;
            return this;
        }
        public Builder setTotalRecovered(String totalRecovered) {
            this.totalRecovered = totalRecovered;
            return this;
        }

        public Station build(){
            return new Station(id,country,city);
        }
    }



    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(obj==this){
            return true;
        }
        Station station = (Station) obj;
        if(this.getId()==station.getId()){
            return true;
        }
        return false;
    }
}
