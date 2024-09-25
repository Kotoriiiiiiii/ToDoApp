package com.example.todoapp

data class TodoData(
    val id: Long,
    val gone: Boolean,
    val title: String,
    val favorite: Boolean
)
