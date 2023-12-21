package com.capstone.schero.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.schero.data.model.ListInputData
import com.capstone.schero.databinding.FragmentHomeBinding
import com.capstone.schero.ui.MainViewModel
import com.capstone.schero.ui.ViewModelFactory
import com.capstone.schero.ui.detail.DetailActivity

class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var benua : String
    private lateinit var jenjang : String
    private lateinit var biaya : String
    private val uniqueRecommendation = HashSet<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        binding.apply {
            spinnerBenua.onItemSelectedListener = this@HomeFragment
            spinnerJenjang.onItemSelectedListener = this@HomeFragment
            spinnerBiaya.onItemSelectedListener = this@HomeFragment

            val benuaAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                ListInputData.listBenua
            )

            val jenjangAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                ListInputData.listJenjang
            )

            val biayaAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                ListInputData.listBiaya
            )

            spinnerBenua.adapter = benuaAdapter
            spinnerJenjang.adapter = jenjangAdapter
            spinnerBiaya.adapter = biayaAdapter

        }

        return root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        binding.apply {
            benua = spinnerBenua.getItemAtPosition(spinnerBenua.selectedItemPosition) as String
            jenjang = spinnerJenjang.getItemAtPosition(spinnerJenjang.selectedItemPosition) as String
            biaya = spinnerBiaya.getItemAtPosition(spinnerBiaya.selectedItemPosition) as String

            recommenderButton.setOnClickListener {
                viewModel.getRecomendation(benua, jenjang, biaya)
            }

            viewModel.setRecomendation.observe(viewLifecycleOwner) { listBeasiswa ->

                uniqueRecommendation.clear()

                for (beasiswa in listBeasiswa) {
                    uniqueRecommendation.add(beasiswa)
                }

                val rekomendasiAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    uniqueRecommendation.toList()
                )

                spinnerRekomendasi.adapter = rekomendasiAdapter
            }

            getInfoButton.setOnClickListener {

                val selectedItem = spinnerRekomendasi.getItemAtPosition(spinnerRekomendasi.selectedItemPosition) as? String
                if (selectedItem != null) {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_NAME, selectedItem)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}