package com.example.todoapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TodoDataDao {
    @Insert
    fun insert(todoData: TodoData)

    @Update
    fun updata(todoData: TodoData)

    @Query("select * from todo_data")
    fun getAll(): List<TodoData>

    @Query("select * from todo_data WHERE id = :id")
    fun getDataById(id: Int): TodoData

    @Query("delete from todo_data WHERE id = :id")
    fun deleteDataById(id: Int)
}