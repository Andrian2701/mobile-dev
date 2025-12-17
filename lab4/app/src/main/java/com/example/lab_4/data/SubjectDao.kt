package com.example.lab_4.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Query("SELECT * FROM subjects ORDER BY id ASC")
    fun getAll(): Flow<List<SubjectEntity>>

    @Query("SELECT * FROM subjects WHERE id = :id LIMIT 1")
    fun getById(id: Int): Flow<SubjectEntity?>

    @Query("SELECT COUNT(*) FROM subjects")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subject: SubjectEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(subjects: List<SubjectEntity>): List<Long>
}
