package com.capstone.schero.ui.search

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.schero.databinding.FragmentSearchBinding
import com.capstone.schero.ui.MainActivity
import com.capstone.schero.ui.MainViewModel
import com.capstone.schero.ui.ViewModelFactory
import com.capstone.schero.ui.detail.DetailActivity

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

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
            binding.apply {
                Glide.with(requireContext())
                    .load(list[0].linkGambar)
                    .into(binding.scholarshipImageView)

                scholarshipNameTextView.text = list[0].namaBeasiswa

                locationTextView.text = list[0].negara

                if (list[0].iPK == "0") {
                    ipkTextView.visibility = View.VISIBLE
                } else {
                    ipkTextView.text = list[0].iPK
                }

                itemCardView.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_NAME, list[0].namaBeasiswa)
                    it.context.startActivity(intent)
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}