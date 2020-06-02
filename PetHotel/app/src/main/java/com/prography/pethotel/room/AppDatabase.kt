package com.prography.pethotel.room

import androidx.room.RoomDatabase

public abstract class AppDatabase : RoomDatabase() {
    companion object{
        val DB_NAME = "pet_db"

    }

}