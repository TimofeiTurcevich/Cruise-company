package com.my.classes;

import java.io.Serializable;

public class Staff implements Serializable {
    private long id;
    private String name;
    private String position;

    public Staff(){}
    public Staff(long id, String name, String position){
        this.id = id;
        this.name = name;
        this.position =  position;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPosition() {
        return position;
    }

    public static class Builder{
        private long id;
        private String name;
        private String position;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setPosition(String position) {
            this.position = position;
            return this;
        }

        public Staff build(){
            return new Staff(id,name,position);
        }
    }

}
