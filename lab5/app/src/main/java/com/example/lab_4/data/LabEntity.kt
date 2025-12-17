package com.example.lab_4.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "labs",
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index("subjectId")]
)
data class LabEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subjectId: Int,
    val title: String,
    val status: String
)