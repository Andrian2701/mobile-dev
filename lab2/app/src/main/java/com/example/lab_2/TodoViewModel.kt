package com.example.lab_2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {
    private var _todos = MutableLiveData<List<Todo>>()
    val todos : LiveData<List<Todo>> = _todos

    init {
        getAllTodos()
    }

    fun getAllTodos(){
        _todos.value = TodoService.getAllTodos().reversed()
    }

    fun addTodo(title : String){
        TodoService.addTodo(title)
        getAllTodos()
    }
}