package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.datasource.NotesDataSource
import com.example.data.datasource.NotesDataSourceImp
import com.example.data.local.NotesLocal
import com.example.data.local.database.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCharactersLocal(@ApplicationContext appContext: Context): NotesLocal =
        NotesLocal(
            Room.databaseBuilder(
                appContext,
                NotesDatabase::class.java,
                "notes"
            ).build()
        )

    @Provides
    @Singleton
    fun provideNotesDataSource(
        local: NotesLocal
    ): NotesDataSource {
        return NotesDataSourceImp(
            local = local
        )
    }
}
