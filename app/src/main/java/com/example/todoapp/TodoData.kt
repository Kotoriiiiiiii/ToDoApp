package com.example.todoapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="todo_data")
data class TodoData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "gone")
    val gone: Boolean = false,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean = false
)
