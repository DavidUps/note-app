package com.example.domain.di

import com.example.domain.repository.NotesRepository
import com.example.domain.repository.NotesRepositoryImp
import com.example.domain.usescases.GetNotes
import com.example.domain.usescases.GetNotesImp
import com.example.domain.usescases.ModifyNotes
import com.example.domain.usescases.ModifyNotesImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    @Singleton
    abstract fun provideNotesUseCase(impl: GetNotesImp): GetNotes

    @Binds
    @Singleton
    abstract fun provideModifyNotesUseCase(impl: ModifyNotesImp): ModifyNotes

    @Binds
    @Singleton
    abstract fun provideNotesRepository(impl: NotesRepositoryImp): NotesRepository
}
