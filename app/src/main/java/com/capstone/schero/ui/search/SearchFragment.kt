package com.capstone.schero.ui.search

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.schero.R
import com.capstone.schero.adapter.RecommendationAdapter
import com.capstone.schero.databinding.FragmentSearchBinding
import com.capstone.schero.ui.MainActivity
import com.capstone.schero.ui.MainViewModel
import com.capstone.schero.ui.ViewModelFactory

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private var adapter = RecommendationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        val componentName = ComponentName(requireContext(), MainActivity::class.java)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchScholarship(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        viewModel.search.observe(viewLifecycleOwner) { list ->
            binding.rvScholarship.layoutManager = LinearLayoutManager(requireContext())
            binding.rvScholarship.setHasFixedSize(true)
            binding.rvScholarship.adapter = adapter
            if (list != null) {
                adapter.submitData(list)
                binding.noData.visibility = View.GONE
            } else {
                binding.noData.visibility = View.VISIBLE
                Toast.makeText(requireContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}