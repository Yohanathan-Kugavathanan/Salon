package com.example.saloon;

public class Model {
    String id,dat,tim,numbr;

    public Model() {
    }

    public Model(String id, String dat, String tim, String numbr) {
        this.id = id;
        this.dat = dat;
        this.tim = tim;
        this.numbr = numbr;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getTim() {
        return tim;
    }

    public void setTime(String tim) {
        this.tim = tim;
    }

    public String getNumbr() {
        return numbr;
    }

    public void setNumbr(String numbr) {
        this.numbr = numbr;
    }
}
