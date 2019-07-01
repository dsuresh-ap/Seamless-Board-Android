package com.uipath.seamlessboard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uipath.seamlessboard.database.dao.RestaurantDao
import com.uipath.seamlessboard.database.entity.Restaurant
import com.uipath.seamlessboard.database.entity.Review

@Database(entities = [Restaurant::class, Review::class], version = 1, exportSchema = false)
abstract class BoardDatabase : RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao

}