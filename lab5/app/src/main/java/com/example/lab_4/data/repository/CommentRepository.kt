package com.example.lab_4.data.repository

import com.example.lab_4.data.CommentDao
import com.example.lab_4.data.CommentEntity
import kotlinx.coroutines.flow.Flow

class CommentRepository(
    private val commentDao: CommentDao
) {
    fun getByLab(labId: Int): Flow<List<CommentEntity>> = commentDao.getByLab(labId)
    suspend fun add(comment: CommentEntity): Long = commentDao.insert(comment)
}
