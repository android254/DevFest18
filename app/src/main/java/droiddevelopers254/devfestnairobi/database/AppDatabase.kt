package droiddevelopers254.devfestnairobi.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

import droiddevelopers254.devfestnairobi.database.converters.Converter
import droiddevelopers254.devfestnairobi.database.dao.SessionsDao
import droiddevelopers254.devfestnairobi.database.dao.StarredSessionDao
import droiddevelopers254.devfestnairobi.database.entities.StarredSessionEntity
import droiddevelopers254.devfestnairobi.models.SessionsModel
import droiddevelopers254.devfestnairobi.models.StarredSessionModel

@Database(entities = [StarredSessionModel::class, SessionsModel::class], version = 3, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    //dao
    abstract fun sessionsDao(): SessionsDao

    abstract fun starredSessionDao(): StarredSessionDao

    companion object {
        //Singleton
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "droidconKE_db")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}
