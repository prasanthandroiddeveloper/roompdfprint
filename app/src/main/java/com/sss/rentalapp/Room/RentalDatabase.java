package com.sss.rentalapp.Room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Rental.class},version = 1)
public abstract class RentalDatabase extends RoomDatabase {

    public abstract RentalDao getDao();
    public static RentalDatabase INSTANCE;
    public static RentalDatabase getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context,RentalDatabase.class,"RENTALDATABASE").
                    allowMainThreadQueries().
                    fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
