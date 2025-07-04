package com.example.engdictionaryapp.di

import android.content.Context
import com.example.engdictionaryapp.R
import com.example.engdictionaryapp.trainer.data.DictionaryJSON
import com.example.engdictionaryapp.trainer.data.WordsProvider
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.InputStream
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Provides
    @Singleton
    fun provideLearnWordTrainer(@ApplicationContext context: Context): LearnWordTrainer {
        return LearnWordTrainerImpl(provideWords(context))
    }

    @Provides
    @Singleton
    fun provideWords(@ApplicationContext context: Context): WordsProvider {
        return DictionaryJSON(context.resources.openRawResource(R.raw.words))
    }
}