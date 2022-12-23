package com.my.classes;

import java.io.Serializable;

public class BoughtTickets implements Serializable {
    private long id;
    private Voyage voyage;
    private long userId;
    private String status;
    private int totalPrice;
    private int ticketsCount;
    private String type;

    public BoughtTickets(){}
    public BoughtTickets(Voyage voyage,String status, long userId, String type){
        this.voyage = voyage;
        this.status = status;
        this.userId =userId;
        this.type = type;
    }

    public BoughtTickets(long id, Voyage voyage,String status, long userId, String type){
        this.voyage = voyage;
        this.status = status;
        this.userId =userId;
        this.type = type;
        this.id = id;
    }

    public BoughtTickets(long id, Voyage voyage,String status, long userId, String type, int totalPrice, int ticketsCount){
        this.voyage = voyage;
        this.status = status;
        this.userId =userId;
        this.type = type;
        this.id = id;
        this.totalPrice = totalPrice;
        this.ticketsCount = ticketsCount;
    }

    public long getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }
    public Voyage getVoyage() {
        return voyage;
    }
    public long getUserId() {
        return userId;
    }
    public int getTicketsCount() {
        return ticketsCount;
    }
    public int getTotalPrice() {
        return totalPrice;
    }
    public String getType(){
        return type;
    }

    public static class Builder{
        private long id;
        private Voyage voyage;
        private long userId;
        private String status;
        private int totalPrice;
        private int ticketsCount;
        private String type;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }
        public Builder setType(String type) {
            this.type = type;
            return this;
        }
        public Builder setTicketsCount(int ticketsCount) {
            this.ticketsCount = ticketsCount;
            return this;
        }
        public Builder setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }
        public Builder setUserId(long userId) {
            this.userId = userId;
            return this;
        }
        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }
        public Builder setVoyage(Voyage voyage) {
            this.voyage = voyage;
            return this;
        }

        public BoughtTickets build(){
            return new BoughtTickets(id,voyage,status,userId,type,totalPrice,ticketsCount);
        }
    }


}
