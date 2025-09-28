package com.example.lab_2

import java.time.Instant
import java.util.Date

object TodoService {

    private val todos = mutableListOf<Todo>()

    fun getAllTodos() : List<Todo> {
        return todos
    }

    fun addTodo(title : String) {
        todos.add(Todo(System.currentTimeMillis().toString(),title, Date.from(Instant.now())))
    }
}