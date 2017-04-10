package com.hans.sses.member.model;

import com.uangel.platform.model.AbstractModel;

import java.util.Date;
import javax.persistence.*;

/**
 * Created by wypark on 2017. 3. 29..
 */
@Entity
@Table(name = "TBL_EQUIPMENT_INFO")
public class Equipment {
    @Id
    private String macaddress;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @Column(name = "MAKE_DATE")
    private String make_date;

    @Column(name = "ETC")
    private String etc;

    @Column(name = "WATT")
    private int watt;

    private String hardwareinfo;

    @Column(name = "REG_DATE", updatable=false)
    private Date reg_date;

    @Column(name = "MOD_DATE")
    private Date mod_date;

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

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

    public int getWatt() {
        return watt;
    }

    public void setWatt(int watt) {
        this.watt = watt;
    }

    public String getHardwareinfo() {
        return hardwareinfo;
    }

    public void setHardwareinfo(String hardwareinfo) {
        this.hardwareinfo = hardwareinfo;
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