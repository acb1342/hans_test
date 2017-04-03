package com.mobilepark.doit5.member.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Created by wypark on 2017. 3. 29..
 */
@Entity
@Table(name = "TBL_EQUIPMENT_INFO")
public class Equipment {
    @Id
    @Column(name = "EQUIP_SEQ")
    private int equip_seq;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @Column(name = "MAKE_DATE")
    private String make_date;

    @Column(name = "ETC")
    private String etc;

    @Column(name = "ELECT_POWER")
    private int elect_power;

    @Column(name = "REG_DATE", updatable=false)
    private Date reg_date;

    @Column(name = "MOD_DATE")
    private Date mod_date;

    public int getEquip_seq() { return equip_seq; }

    public void setEquip_seq(int equip_seq) { this.equip_seq = equip_seq; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMake_date() {
        return make_date;
    }

    public void setMake_date(String make_date) {
        this.make_date = make_date;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public int getElect_power() {
        return elect_power;
    }

    public void setElect_power(int elect_power) {
        this.elect_power = elect_power;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public Date getMod_date() {
        return mod_date;
    }

    public void setMod_date(Date mod_date) {
        this.mod_date = mod_date;
    }
}