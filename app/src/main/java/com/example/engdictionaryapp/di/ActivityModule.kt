package com.example.engdictionaryapp.di

import android.content.Context
import androidx.activity.ComponentActivity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
abstract class ActivityModule {

    @Binds
    @ActivityContext
    abstract fun bindActivityContext(activity: ComponentActivity): Context
}