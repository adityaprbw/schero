package com.capstone.schero.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.schero.data.database.BookmarksHelper
import com.capstone.schero.data.database.MappingHelper
import com.capstone.schero.databinding.FragmentBookmarksBinding
import com.capstone.schero.ui.MainViewModel
import com.capstone.schero.ui.ViewModelFactory
import com.capstone.schero.ui.adapter.BookmarksAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BookmarksFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    private var _binding: FragmentBookmarksBinding? = null

    private val binding get() = _binding!!
    private lateinit var adapter: BookmarksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = BookmarksAdapter()
        binding.rvScholarship.layoutManager = LinearLayoutManager(requireContext())
        binding.rvScholarship.setHasFixedSize(true)
        binding.rvScholarship.adapter = adapter

        loadUserAsync()

        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        return root
    }

    private fun loadUserAsync() {
        lifecycleScope.launch {
            val favHelper = BookmarksHelper.getInstance(requireContext())
            favHelper.open()
            val deferredScholarship = async(Dispatchers.IO) {
                val cursor = favHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val user = deferredScholarship.await()
            if (user.size > 0) {
                adapter.submitData(user)
            } else {
                adapter.submitData(ArrayList())
            }
            favHelper.close()
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserAsync()
    }
}