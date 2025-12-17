package com.example.lab_4.data

import com.example.lab_4.data.repository.LabRepository
import com.example.lab_4.data.repository.SubjectRepository

class AppSeeder(
    private val subjects: SubjectRepository,
    private val labs: LabRepository
) {
    suspend fun seedIfNeeded() {
        if (subjects.count() > 0) return

        val economicsId = subjects.insert(SubjectEntity(name = "Economics")).toInt()
        val isdId = subjects.insert(SubjectEntity(name = "Information systems design")).toInt()

        labs.insertAll(
            listOf(
                LabEntity(
                    subjectId = economicsId,
                    title = "Midterm examination work",
                    status = LabStatus.IN_PROGRESS.name
                )
            )
        )

        val isdLabs = (1..7).map { i ->
            LabEntity(
                subjectId = isdId,
                title = "Lab $i",
                status = LabStatus.DONE.name
            )
        }
        labs.insertAll(isdLabs)
    }
}
