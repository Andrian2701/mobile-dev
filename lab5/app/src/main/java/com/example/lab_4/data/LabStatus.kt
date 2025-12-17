package com.example.lab_4.data

enum class LabStatus(val label: String) {
    DONE("Done"),
    IN_PROGRESS("In progress"),
    BACKLOG("Backlog");

    companion object {
        fun fromDb(value: String): LabStatus =
            entries.firstOrNull { it.name == value } ?: IN_PROGRESS
    }
}