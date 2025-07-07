package com.example.engdictionaryapp.di

import android.content.Context
import androidx.activity.ComponentActivity
import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import com.example.engdictionaryapp.ui.QuestionIndicatorImpl
import com.example.engdictionaryapp.ui.util.QuestionIndicator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
class PresentationModule {
    @Provides
    fun provideQuestionIndicator(
        @ApplicationContext context: Context,
        learnWordTrainer: LearnWordTrainer
    ): @JvmSuppressWildcards (ActivityLearnWordBinding) -> QuestionIndicator {
        return { binding ->
            QuestionIndicatorImpl(
                context = context,
                binding = binding,
                learnWordTrainer = learnWordTrainer
            )
        }
    }
}