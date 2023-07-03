package com.sss.rentalapp.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RentalDao {

    @Insert
    void insert(Rental rental);


    @Update
    void update(Rental rental);


    @Query("delete from Rental where id=:id")
    void delete(int id);

    @Query("select * from Rental")
    List<Rental> getRental();


}
