package com.kadabengaran.rickalmanac.search

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kadabengaran.rickalmanac.R
import com.kadabengaran.rickalmanac.core.data.Resource
import com.kadabengaran.rickalmanac.core.ui.CharacterAdapter
import com.kadabengaran.rickalmanac.databinding.FragmentSearchBinding
import com.kadabengaran.rickalmanac.detail.DetailCharacterActivity
import com.kadabengaran.rickalmanac.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            setHasOptionsMenu(true)

            val characterAdapter = CharacterAdapter()
            characterAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailCharacterActivity::class.java)
                intent.putExtra(DetailCharacterActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            observeData(characterAdapter)

            binding?.rvUsersTabList?.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = characterAdapter
            }
        }
    }

    private fun observeData(characterAdapter: CharacterAdapter) {
        searchViewModel.listSearchResult.observe(viewLifecycleOwner) { character ->
            if (character != null) {
                when (character) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        characterAdapter.setData(character.data)
                        showLoading(false)
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                        showLoading(false)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.search_bar)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    hideAll()
                    binding?.rvUsersTabList?.visibility = View.VISIBLE
                    searchViewModel.searchUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun hideAll() {
        binding?.apply {
            rvUsersTabList.visibility = View.GONE
            progressBar.visibility = View.GONE
            grNoSearchResult.visibility = View.GONE
            grNoQuery.visibility = View.GONE
            grError.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}