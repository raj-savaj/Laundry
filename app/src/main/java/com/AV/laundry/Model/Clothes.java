package com.AV.laundry.Model;

/**
 * Created by joshi on 2/15/2019.
 */

public class Clothes {
    int id,price,status;
    String Name,Clothes;
    String date;
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

    public String getClothes() {
        return Clothes;
    }

    public void setClothes(String clothes) {
        Clothes = clothes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
