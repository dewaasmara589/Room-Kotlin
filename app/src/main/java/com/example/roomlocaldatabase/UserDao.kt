package com.example.roomlocaldatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao  // Data Access Object
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    // int array sama dengan int[] dalam Java
    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    // variable number of arguments (vararg) dapat memberikan data menjadi array
    @Insert
    fun insertAll(vararg  user: User)

    @Delete
    fun delete(user: User)
}