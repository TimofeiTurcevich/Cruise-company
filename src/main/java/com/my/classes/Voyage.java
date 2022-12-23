package com.my.classes;

import java.io.Serializable;
import java.util.List;

public class Voyage implements Comparable<Voyage>, Serializable {
    private long id;
    private Ship ship;
    private int luxPrice;
    private int standardPrice;
    private int boughtLux;
    private int boughtStandard;
    private boolean sale;
    private int saleLuxPrice;
    private int saleStandardPrice;
    private List<VoyageStation> voyageStations;
    private boolean available;

    public Voyage(){}
    public Voyage(long id, Ship ship, int luxPrice, int standardPrice, int boughtLux, int boughtStandard, boolean sale, int saleLuxPrice, int saleStandardPrice, List<VoyageStation> voyageStations, boolean available){
        this.id = id;
        this.ship = ship;
        this.boughtLux = boughtLux;
        this.boughtStandard = boughtStandard;
        this.luxPrice =luxPrice;
        this.standardPrice = standardPrice;
        this.sale =sale;
        this.saleLuxPrice= saleLuxPrice;
        this.saleStandardPrice = saleStandardPrice;
        this.voyageStations =voyageStations;
        this.available = available;
    }

    public long getId() {
        return id;
    }
    public int getBoughtLux() {
        return boughtLux;
    }
    public int getBoughtStandard() {
        return boughtStandard;
    }
    public int getLuxPrice() {
        return luxPrice;
    }
    public int getSaleLuxPrice() {
        return saleLuxPrice;
    }
    public int getSaleStandardPrice() {
        return saleStandardPrice;
    }
    public boolean isSale() {
        return sale;
    }
    public int getStandardPrice() {
        return standardPrice;
    }
    public List<VoyageStation> getVoyageStations() {
        return voyageStations;
    }
    public Ship getShip() {
        return ship;
    }
    public boolean isAvailable() {
        return available;
    }

    public long getVoyageDuration(){
        return (this.getVoyageStations().get(this.getVoyageStations().size() - 1).getArrivalDate().getTime() - this.getVoyageStations().get(0).getDepartureDate().getTime()) / (1000 * 60 * 60 * 24);
    }

    public static class Builder{
        private long id;
        private Ship ship;
        private int luxPrice;
        private int standardPrice;
        private int boughtLux;
        private int boughtStandard;
        private boolean sale;
        private int saleLuxPrice;
        private int saleStandardPrice;
        private boolean available;
        private List<VoyageStation> voyageStations;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }
        public Builder setBoughtLux(int boughtLux) {
            this.boughtLux = boughtLux;
            return this;
        }
        public Builder setBoughtStandard(int boughtStandard) {
            this.boughtStandard = boughtStandard;
            return this;
        }
        public Builder setLuxPrice(int luxPrice) {
            this.luxPrice = luxPrice;
            return this;
        }
        public Builder setSale(boolean sale) {
            this.sale = sale;
            return this;
        }
        public Builder setSaleLuxPrice(int saleLuxPrice) {
            this.saleLuxPrice = saleLuxPrice;
            return this;
        }
        public Builder setSaleStandardPrice(int saleStandardPrice) {
            this.saleStandardPrice = saleStandardPrice;
            return this;
        }
        public Builder setShip(Ship ship) {
            this.ship = ship;
            return this;
        }
        public Builder setStandardPrice(int standardPrice) {
            this.standardPrice = standardPrice;
            return this;
        }
        public Builder setVoyageStations(List<VoyageStation> voyageStations) {
            this.voyageStations = voyageStations;
            return this;
        }
        public Builder setAvailable(boolean available) {
            this.available = available;
            return this;
        }

        public Voyage build(){
            return new Voyage(id,ship,luxPrice,standardPrice,boughtLux,boughtStandard,sale,saleLuxPrice,saleStandardPrice,voyageStations, available);
        }
    }

    public void setId(long id){
        this.id = id;
    }
    public void setBoughtLux(int boughtLux){
        this.boughtLux = boughtLux;
    }
    public void setBoughtStandard(int boughtStandard){
        this.boughtStandard = boughtStandard;
    }
    public void setStandardPrice(int standardPrice){
        this.standardPrice = standardPrice;
    }
    public void setLuxPrice(int luxPrice) {
        this.luxPrice = luxPrice;
    }
    public void setShip(Ship ship) {
        this.ship = ship;
    }
    public void setSale(boolean sale) {
        this.sale = sale;
    }
    public void setVoyageStations(List<VoyageStation> voyageStations) {
        this.voyageStations = voyageStations;
    }
    public void setSaleStandardPrice(int saleStandardPrice) {
        this.saleStandardPrice = saleStandardPrice;
    }
    public void setSaleLuxPrice(int saleLuxPrice) {
        this.saleLuxPrice = saleLuxPrice;
    }

    public int compareTo(Voyage o) {
        if(this.getLuxPrice()==o.getLuxPrice()){
            return 0;
        }
        return this.getLuxPrice()>o.getLuxPrice()?1:-1;
    }
}
