package com.uipath.seamlessboard.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "restaurants",
    indices = [Index(value = ["restaurant_id"]),
        Index(value = ["name"], unique = true)]
)
data class Restaurant(@ColumnInfo(name = "name") val name: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "restaurant_id")
    var id: Long = 0
}