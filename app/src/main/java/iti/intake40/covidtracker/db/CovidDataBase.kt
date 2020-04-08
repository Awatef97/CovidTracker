package iti.intake40.covidtracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import iti.intake40.covidtracker.db.model.CovidModel

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(CovidModel::class), version = 1, exportSchema = false)
 abstract class CovidDataBase : RoomDatabase() {

    abstract fun covidDao(): CovidDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CovidDataBase? = null

        fun getDatabase(context: Context): CovidDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CovidDataBase::class.java,
                    "countries_stat"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}