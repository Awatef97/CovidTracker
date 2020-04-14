package iti.intake40.covidtracker.db.localDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import iti.intake40.covidtracker.db.model.CovidModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(CovidModel::class), version = 1, exportSchema = false)
 abstract class CovidDataBase : RoomDatabase() {

    abstract fun covidDao(): CovidDao

    private class CovidDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        INSTANCE?.let { database ->
            scope.launch {
                var covidDao = database.covidDao()
                covidDao.delete()
            }}}}

            companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CovidDataBase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CovidDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CovidDataBase::class.java,
                    "word_database"
                )
                    .addCallback(
                        CovidDatabaseCallback(
                            scope
                        )
                    )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}