package com.example.engdictionaryapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.init.InitializeApp
import com.example.engdictionaryapp.models.Variant

class MainActivity : AppCompatActivity() {
    var privateBinding: ActivityLearnWordBinding? = null
    val binding
        get() = privateBinding
            ?: throw IllegalStateException("Binding for ActivityLearnWordBinding must not be null!")
    val variants = mutableListOf<Variant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InitializeApp(this@MainActivity).initialize()
        setContentView(binding.root)
    }
}