package com.example.engdictionaryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.init.InitializeQuestionButtons
import com.example.engdictionaryapp.trainer.data.DictionaryJSON
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainerImpl

class MainActivity : AppCompatActivity() {
    private var privateBinding: ActivityLearnWordBinding? = null
    private val binding
        get() = privateBinding
            ?: throw IllegalStateException("Binding for ActivityLearnWordBinding must not be null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        privateBinding = ActivityLearnWordBinding.inflate(layoutInflater)

        InitializeQuestionButtons(
            this@MainActivity,
            binding,
            LearnWordTrainerImpl(DictionaryJSON(resources.openRawResource(R.raw.words)))
        ).initialize()

        setContentView(binding.root)
    }
}

/**
    Hilt Inject - LearnWordTrain Inject без Impl()
    Вынести variant в отдельный класс и назвать его Repository
    Желательно заинджектить в InitialApp

 */