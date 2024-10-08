package com.example.todoapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="todo_data")
data class TodoData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "gone")
    var gone: Boolean = false,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
)


