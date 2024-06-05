package com.example.roomlocaldatabase.ForeignKeys

import androidx.room.ColumnInfo

class UserWithReport {
    var first_name: String = ""
    var last_name: String = ""
    var uid: Int = 0
    var user_id: Int = 0
    var role: String = ""

    constructor(first_name: String, last_name: String, uid: Int, user_id: Int, role: String) {
        this.first_name = first_name
        this.last_name = last_name
        this.uid = uid
        this.user_id = user_id
        this.role = role
    }

    override fun toString(): String {
        return "UserWithReport(first_name='$first_name', last_name='$last_name', uid=$uid, user_id=$user_id, role='$role')"
    }


}