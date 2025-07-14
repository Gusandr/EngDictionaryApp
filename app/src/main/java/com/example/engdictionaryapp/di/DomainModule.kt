package com.example.engdictionaryapp.di

import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainerImpl
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
    abstract fun bindLearnWordTrainer(learnWordTrainerImpl: LearnWordTrainerImpl): LearnWordTrainer
}