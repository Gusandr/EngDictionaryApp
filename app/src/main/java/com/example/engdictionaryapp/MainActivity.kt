package com.example.engdictionaryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.init.InitializeQuestionButtons
import com.example.engdictionaryapp.trainer.data.DictionaryJSON
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainerImpl
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var privateBinding: ActivityLearnWordBinding? = null
    private val binding
        get() = privateBinding
            ?: throw IllegalStateException("Binding for ActivityLearnWordBinding must not be null!")

    @Inject
    lateinit var learnWordTrainer: LearnWordTrainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        privateBinding = ActivityLearnWordBinding.inflate(layoutInflater)

        InitializeQuestionButtons(
            this@MainActivity,
            binding,
            learnWordTrainer
        ).initialize()

        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        privateBinding = null
    }
}