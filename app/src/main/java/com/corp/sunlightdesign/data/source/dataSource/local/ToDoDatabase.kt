package com.corp.sunlightdesign.data.source.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.corp.sunlightdesign.data.Task
import com.corp.sunlightdesign.data.source.dataSource.local.auth.AuthDao


@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun taskDao(): AuthDao
}

