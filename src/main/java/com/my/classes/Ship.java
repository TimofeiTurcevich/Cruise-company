package com.my.classes;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class Ship implements Serializable {
    private long id;
    private String name;
    private int standardRooms;
    private int luxRooms;
    private List<Staff> staffs;
    private File picture;

    public Ship(){}
    public Ship(long id, String name, int standardRooms, int luxRooms, List<Staff> staffs, File picture){
        this.id = id;
        this.name = name;
        this.standardRooms = standardRooms;
        this.luxRooms = luxRooms;
        this.staffs = staffs;
        this.picture = picture;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }
    public String getName() {
        return name;
    }
    public long getId() {
        return id;
    }
    public int getLuxRooms() {
        return luxRooms;
    }
    public int getStandardRooms() {
        return standardRooms;
    }
    public File getPicture() {
        return picture;
    }

    public static class Builder{
        private long id;
        private String name;
        private int standardRooms;
        private int luxRooms;
        private List<Staff> staffs;
        private File picture;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setId(long id) {
            this.id = id;
            return this;
        }
        public Builder setPicture(File picture) {
            this.picture = picture;
            return this;
        }
        public Builder setLuxRooms(int luxRooms) {
            this.luxRooms = luxRooms;
            return this;
        }
        public Builder setStandardRooms(int standardRooms) {
            this.standardRooms = standardRooms;
            return this;
        }
        public Builder setStaffs(List<Staff> staffs) {
            this.staffs = staffs;
            return this;
        }

        public Ship build(){
            return new Ship(id,name,standardRooms,luxRooms,staffs,picture);
        }
    }


}
