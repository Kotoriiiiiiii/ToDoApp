package com.example.todoapp

import androidx.lifecycle.LiveData
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

    @Query("SELECT * FROM todo_data WHERE gone = 1")
    fun getGoneTodoData(): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_data WHERE gone = 0")
    fun getNotGoneTodoData(): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_data WHERE favorite = 1")
    fun starData(): LiveData<List<TodoData>>

}