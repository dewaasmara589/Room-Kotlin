package com.example.roomlocaldatabase.ForeignKeys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.roomlocaldatabase.User

@Entity(
    tableName = "report_card",
    foreignKeys = [ForeignKey(
        entity = User::class,
        childColumns = ["user_id"],
        parentColumns = ["uid"]
    )]
)
data class ReportCard (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "user_id") val userId: Int?,
    @ColumnInfo(name = "role") val role: String?,
){

}