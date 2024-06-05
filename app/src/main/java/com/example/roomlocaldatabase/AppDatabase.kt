package com.example.roomlocaldatabase

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomlocaldatabase.ForeignKeys.ReportCard

// :: creates a member reference or a class reference.
@Database(entities = [User::class, ReportCard::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    // TODO Singleton Pattern to Use Room
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "testForeignKeyDB"
//                ).build()
//                INSTANCE = instance
//                instance
//            }

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,
                        "testForeignKeyDB")
                        .allowMainThreadQueries()
                        .build()
                }
                Log.d("TAG", "=== Run INSTANCE ===")
            }
            return  INSTANCE!!
        }
    }

    abstract fun userDao(): UserDao
}