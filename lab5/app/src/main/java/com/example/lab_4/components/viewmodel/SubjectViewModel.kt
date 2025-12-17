package com.example.lab_4.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lab_4.data.repository.SubjectRepository

class SubjectsViewModel(
    private val subjects: SubjectRepository
) : ViewModel() {
    val subjectsFlow = subjects.getAll()
}
