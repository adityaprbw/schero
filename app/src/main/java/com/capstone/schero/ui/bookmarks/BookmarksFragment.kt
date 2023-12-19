package com.capstone.schero.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.schero.databinding.FragmentBookmarksBinding
import com.capstone.schero.ui.MainViewModel
import com.capstone.schero.ui.ViewModelFactory

class BookmarksFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    private var _binding: FragmentBookmarksBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}