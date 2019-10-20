package com.AV.laundry.Model;

/**
 * Created by joshi on 2/15/2019.
 */

public class Account {
    int id;
    String Name;
    String StartDate;
    String EndDate;
    String BasePrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getBasePrice() {
        return BasePrice;
    }

    public void setBasePrice(String basePrice) {
        BasePrice = basePrice;
    }



}
