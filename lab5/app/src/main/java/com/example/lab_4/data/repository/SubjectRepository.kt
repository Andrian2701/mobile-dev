package com.example.lab_4.data.repository

import com.example.lab_4.data.SubjectDao
import com.example.lab_4.data.SubjectEntity
import kotlinx.coroutines.flow.Flow

class SubjectRepository(
    private val subjectDao: SubjectDao
) {
    fun getAll(): Flow<List<SubjectEntity>> = subjectDao.getAll()
    fun getById(id: Int): Flow<SubjectEntity?> = subjectDao.getById(id)
    suspend fun count(): Int = subjectDao.count()
    suspend fun insert(subject: SubjectEntity): Long = subjectDao.insert(subject)
}
