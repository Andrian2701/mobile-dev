package com.example.lab_4.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_4.data.CommentEntity
import com.example.lab_4.data.repository.CommentRepository
import com.example.lab_4.data.repository.LabRepository
import kotlinx.coroutines.launch

class LabDetailsViewModel(
    private val labs: LabRepository,
    private val comments: CommentRepository
) : ViewModel() {

    fun labFlow(labId: Int) = labs.getById(labId)
    fun commentsFlow(labId: Int) = comments.getByLab(labId)

    fun updateStatus(labId: Int, status: String) {
        viewModelScope.launch {
            labs.updateStatus(labId, status)
        }
    }

    fun addComment(labId: Int, text: String) {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) return

        viewModelScope.launch {
            comments.add(CommentEntity(labId = labId, text = trimmed))
        }
    }
}
