package com.escoladeti.oldowl.stonix.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import javax.persistence.Entity;
import java.io.File;
import java.util.Base64;
import java.util.Date;

/**
 * Created by felipe on 26/08/16.
 */

@Entity
public class User extends SuperEntity {
    private String name;
    private Date birth;
    private String email;
    private Integer xp;
    private Integer xpForNextLevel;
    private Integer level;
    private Integer punctuation;
    private String password;
    private String imageProfile;
    private Boolean tutor;

    public User() {
        this.xp = 0;
        this.punctuation = 0;
        this.xpForNextLevel = 40;
        this.level = 0;
        this.tutor = false;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Integer getXpForNextLevel() {
        return xpForNextLevel;
    }

    public void setXpForNextLevel(Integer xpForNextLevel) {
        this.xpForNextLevel = xpForNextLevel;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getTutor() {
        return tutor;
    }

    public void setTutor(Boolean tutor) {
        this.tutor = tutor;
    }

    public Integer getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(Integer punctuation) {
        this.punctuation = punctuation;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updatePunctuation(Integer points){
        this.punctuation = this.punctuation + points;
    }

    public void updateLevel(){
        this.level++;
    }

    public void updateXpForNextLevel(Integer xp){
        this.xp = xp - this.xpForNextLevel;
        this.xpForNextLevel = xpForNextLevel * 2;
        this.updateLevel();
    }

    public void updateXp(Integer xp){
        this.xp = this.xp + xp;
        if(this.xp >= this.xpForNextLevel){
            this.updateXpForNextLevel(this.xp );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;
        return Objects.equal(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getDead());
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", birth=" + birth +
                ", email='" + email + '\'' +
                ", xp=" + xp +
                ", xpForNextLevel=" + xpForNextLevel +
                ", level=" + level +
                ", punctuation=" + punctuation +
                ", password='" + password + '\'' +
                '}';
    }
}
