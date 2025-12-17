package com.example.lab_4.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LabDao {

    @Query("SELECT * FROM labs WHERE subjectId = :subjectId ORDER BY id ASC")
    fun getBySubject(subjectId: Int): Flow<List<LabEntity>>

    @Query("SELECT * FROM labs WHERE id = :labId LIMIT 1")
    fun getById(labId: Int): Flow<LabEntity?>

    @Query("UPDATE labs SET status = :status WHERE id = :labId")
    suspend fun updateStatus(labId: Int, status: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(labs: List<LabEntity>): List<Long>
}
