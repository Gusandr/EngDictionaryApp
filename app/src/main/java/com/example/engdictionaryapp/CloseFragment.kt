package com.example.engdictionaryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.engdictionaryapp.ui.CloseScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CloseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setContent {
            CloseScreen {
                parentFragmentManager.popBackStack()
            }
        }
    }
}