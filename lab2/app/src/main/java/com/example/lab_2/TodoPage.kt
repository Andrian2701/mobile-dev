package com.example.lab_2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TodoInput(
    inputVal: String,
    onInputChange: (String) -> Unit,
    onTodoAdd: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, top = 35.dp, end = 4.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            singleLine = true,
            value = inputVal,
            onValueChange = onInputChange,
        )
        Button(onClick = onTodoAdd, enabled = inputVal.isNotBlank()) {
            Text("Add")
        }
    }
}

@Composable
fun TodoItem(todo: Todo) {
    Row(modifier = Modifier
        .fillMaxHeight()
        .padding(4.dp)) {
        Column {
            Text(text = SimpleDateFormat("HH:mm:aa", Locale.ENGLISH).format(todo.createdAt), fontSize = 12.sp, color = Color.Gray)
            Text(text = todo.title, fontSize = 14.sp)
        }
    }
}

@Composable
fun TodoPage(viewModel: TodoViewModel) {
    val todos by viewModel.todos.observeAsState()

    var inputVal by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        TodoInput(
            inputVal = inputVal,
            onInputChange = { inputVal = it },
            onTodoAdd = {
                viewModel.addTodo(inputVal)
                inputVal = ""
            }
        )

        todos?.let {
            LazyColumn {
                itemsIndexed(it) { _, todo ->
                    TodoItem(todo = todo)
                }
            }
        } ?: Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            text = "Add todos"
        )
    }
}