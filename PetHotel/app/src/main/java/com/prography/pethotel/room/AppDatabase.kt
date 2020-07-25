package com.prography.pethotel.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prography.pethotel.api.main.response.HotelReviewData
import com.prography.pethotel.room.auth.AccountPropertiesDao
import com.prography.pethotel.room.entities.*
import com.prography.pethotel.room.main.MainDao

@Database(entities = [User::class, Pet::class, Hotel::class,
    HotelLike::class, Price::class, HotelReviewData::class, Reservation::class],
    version = 9,
    exportSchema = false)
@TypeConverters(Converters::class)
public abstract class AppDatabase : RoomDatabase() {

    abstract val accountPropertiesDao : AccountPropertiesDao
    abstract val mainDao : MainDao

    companion object{
        private const val DB_NAME = "pet_db"

        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}