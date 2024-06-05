package com.example.roomlocaldatabase

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
// Multiple PrimaryKey
//@Entity(primaryKeys = ["firstName", "lastName"])
// Initializing table name
//@Entity(tableName = "Users")

// Tipe 1
data class User (
    // ? untuk memberikan nullexception atau dapat menerima null dalam tipe variabel yang digunakan
    @ColumnInfo (name = "first_name") var firstName: String?,
    @ColumnInfo (name = "last_name") var lastName: String?,
){
    @PrimaryKey(autoGenerate = true) var uid: Int = 0;
}

// Tipe 2
//public class User {
//    @PrimaryKey(autoGenerate = true) val uid: Int = 0
//    val firstName: String?
//    val lastName: String?
//

//    constructor(firstName: String?, lastName: String?) {
//        this.firstName = firstName
//        this.lastName = lastName
//    }
//}
