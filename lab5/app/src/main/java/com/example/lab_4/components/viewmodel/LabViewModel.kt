package com.example.lab_4.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lab_4.data.repository.LabRepository
import com.example.lab_4.data.repository.SubjectRepository

class LabsViewModel(
    private val subjects: SubjectRepository,
    private val labs: LabRepository
) : ViewModel() {

    fun subjectFlow(subjectId: Int) = subjects.getById(subjectId)
    fun labsFlow(subjectId: Int) = labs.getBySubject(subjectId)
}
