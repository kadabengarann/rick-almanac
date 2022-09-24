package com.kadabengaran.rickalmanac.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kadabengaran.rickalmanac.R
import com.kadabengaran.rickalmanac.core.data.Resource
import com.kadabengaran.rickalmanac.core.ui.CharacterAdapter
import com.kadabengaran.rickalmanac.databinding.FragmentHomeBinding
import com.kadabengaran.rickalmanac.presentation.detail.DetailCharacterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val characterAdapter = CharacterAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            observeData()
            setupView()
        }
    }

    private fun setupView() {
        characterAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailCharacterActivity::class.java)
            intent.putExtra(DetailCharacterActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }
        with(binding?.rvCharacters) {
            this?.layoutManager = GridLayoutManager(context, 2)
            this?.setHasFixedSize(true)
            this?.adapter = characterAdapter
        }
    }

    private fun observeData() {
        homeViewModel.character.observe(viewLifecycleOwner) { character ->
            if (character != null) {
                when (character) {
                    is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        characterAdapter.setData(character.data)
                    }
                    is Resource.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        binding?.viewError?.root?.visibility = View.VISIBLE
                        binding?.viewError?.tvError?.text =
                            character.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }
    }

}