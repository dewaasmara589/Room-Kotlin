package com.example.roomlocaldatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.roomlocaldatabase.ForeignKeys.ReportCard
import com.example.roomlocaldatabase.ForeignKeys.UserWithReport

@Dao  // Data Access Object
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>
//    Lebih baik menggunakan LiveData untuk Room Database
//    abstract fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM report_card")
    fun getAllReportCard(): List<ReportCard>

    // int array sama dengan int[] dalam Java
    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    // Join with ForeignKey
    @Query("SELECT * FROM User INNER JOIN REPORT_CARD ON User.uid=REPORT_CARD.user_id")
    fun getAllDataUserJoinReportCard(): List<UserWithReport>

    // variable number of arguments (vararg) dapat memberikan data menjadi array
    @Insert
    fun insertAll(vararg  user: User)

    @Insert
    fun insertAllReportCard(vararg  reportCard: ReportCard)

    @Delete
    fun delete(user: User)
}