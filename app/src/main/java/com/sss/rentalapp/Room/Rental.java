package com.sss.rentalapp.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Rental {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String rentalservicename;
    private String rentalserviceprice;

    public Rental(int id, String rentalservicename, String rentalserviceprice) {
        this.id = id;
        this.rentalservicename = rentalservicename;
        this.rentalserviceprice = rentalserviceprice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRentalservicename() {
        return rentalservicename;
    }

    public void setRentalservicename(String rentalservicename) {
        this.rentalservicename = rentalservicename;
    }

    public String getRentalserviceprice() {
        return rentalserviceprice;
    }

    public void setRentalserviceprice(String rentalserviceprice) {
        this.rentalserviceprice = rentalserviceprice;
    }
}
