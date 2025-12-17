package com.example.lab_4.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "comments",
    foreignKeys = [
        ForeignKey(
            entity = LabEntity::class,
            parentColumns = ["id"],
            childColumns = ["labId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index("labId")]
)
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val labId: Int,
    val text: String,
    val createdAt: Long = System.currentTimeMillis()
)