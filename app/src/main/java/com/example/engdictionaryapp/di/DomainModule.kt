package com.example.engdictionaryapp.di

import android.content.Context
import com.example.engdictionaryapp.di.DataModule.provideWords
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideLearnWordTrainer(@ApplicationContext context: Context): LearnWordTrainer {
        return LearnWordTrainerImpl(provideWords(context))
    }

}