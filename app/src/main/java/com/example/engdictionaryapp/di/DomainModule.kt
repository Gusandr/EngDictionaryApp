package com.example.engdictionaryapp.di

import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindLearnWordTrainer(learnWordTrainerImpl: LearnWordTrainerImpl): LearnWordTrainer
}