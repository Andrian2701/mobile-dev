package com.example.lab_4.di

import com.example.lab_4.data.AppDatabase
import com.example.lab_4.data.AppSeeder
import com.example.lab_4.data.repository.CommentRepository
import com.example.lab_4.data.repository.LabRepository
import com.example.lab_4.data.repository.SubjectRepository
import com.example.lab_4.ui.viewmodel.LabDetailsViewModel
import com.example.lab_4.ui.viewmodel.LabsViewModel
import com.example.lab_4.ui.viewmodel.SubjectsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { AppDatabase.getInstance(get()) }

    single { get<AppDatabase>().subjectDao() }
    single { get<AppDatabase>().labDao() }
    single { get<AppDatabase>().commentDao() }

    single { SubjectRepository(get()) }
    single { LabRepository(get()) }
    single { CommentRepository(get()) }

    single { AppSeeder(get(), get()) }

    viewModel { SubjectsViewModel(get()) }
    viewModel { LabsViewModel(get(), get()) }
    viewModel { LabDetailsViewModel(get(), get()) }
}
