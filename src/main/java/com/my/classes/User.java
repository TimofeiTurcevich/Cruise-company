package com.my.classes;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
    private long id;
    private String lastName;
    private String firstName;
    private String email;
    private Date birthDate;
    private String role;
    private String phone;
    private String lang;
    private String code;


    public User(){}
    public User(long id, String lastName, String firstName, String email,Date birthDate, String role, String phone,String lang, String code){
        this.id = id;
        this.code = code;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.birthDate = birthDate;
        this.role = role;
        this.phone = phone;
        this.lang = lang;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    public long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getRole() {
        return role;
    }
    public String getPhone(){
        return phone;
    }
    public String getLang() {
        return lang;
    }
    public String getCode(){return code;}

    public static class Builder{
        private long id;
        private String lastName;
        private String firstName;
        private String email;
        private Date birthDate;
        private String role;
        private String phone;
        private String lang;
        private String code;

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }
        public Builder setBirthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder setId(long id) {
            this.id = id;
            return this;
        }
        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder setLang(String lang){
            this.lang = lang;
            return this;
        }
        public Builder setCode(String code){
            this.code = code;
            return this;
        }
        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public User build(){
            return new User(id,lastName,firstName,email,birthDate,role,phone,lang,code);
        }
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
