package com.example.lab_4.data.repository

import com.example.lab_4.data.LabDao
import com.example.lab_4.data.LabEntity
import kotlinx.coroutines.flow.Flow

class LabRepository(
    private val labDao: LabDao
) {
    fun getBySubject(subjectId: Int): Flow<List<LabEntity>> = labDao.getBySubject(subjectId)
    fun getById(labId: Int): Flow<LabEntity?> = labDao.getById(labId)
    suspend fun updateStatus(labId: Int, status: String) = labDao.updateStatus(labId, status)
    suspend fun insertAll(labs: List<LabEntity>) = labDao.insertAll(labs)
}
