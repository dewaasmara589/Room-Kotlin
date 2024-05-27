package com.example.roomlocaldatabase

import androidx.room.Database
import androidx.room.RoomDatabase

// :: creates a member reference or a class reference.
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}