package com.my.classes;

import java.io.Serializable;
import java.sql.Timestamp;

public class VoyageStation implements Serializable {
    private Station station;
    private int position;
    private Timestamp arrivalDate;
    private Timestamp departureDate;

    public VoyageStation(){}
    public VoyageStation(Station station){
        this.station = station;
    }
    public VoyageStation(Station station, int position, Timestamp arrivalDate, Timestamp departureDate){
        this.station = station;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.position = position;
    }

    public Station getStation() {
        return station;
    }
    public int getPosition() {
        return position;
    }
    public Timestamp getArrivalDate() {
        return arrivalDate;
    }
    public Timestamp getDepartureDate() {
        return departureDate;
    }

    public static class Builder{
        private Station station;
        private int position;
        private Timestamp arrivalDate;
        private Timestamp departureDate;

        public Builder setPosition(int position) {
            this.position = position;
            return this;
        }
        public Builder setArrivalDate(Timestamp arrivalDate) {
            this.arrivalDate = arrivalDate;
            return this;
        }
        public Builder setDepartureDate(Timestamp departureDate) {
            this.departureDate = departureDate;
            return this;
        }
        public Builder setStation(Station station) {
            this.station = station;
            return this;
        }

        public VoyageStation build(){
            return new VoyageStation(station,position,arrivalDate,departureDate);
        }
    }

    public void setPosition(int position){
        this.position = position;
    }
    public void setDepartureDate(Timestamp departureDate) {
        this.departureDate = departureDate;
    }
    public void setArrivalDate(Timestamp arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String toString() {
        return station.getCountry() + ", " + station.getCity()+"\n";
    }

    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(obj==this){
            return true;
        }
        VoyageStation voyageStation = (VoyageStation) obj;
        if(voyageStation.getStation().equals(this.getStation())){
            return true;
        }
        return false;
    }
}
