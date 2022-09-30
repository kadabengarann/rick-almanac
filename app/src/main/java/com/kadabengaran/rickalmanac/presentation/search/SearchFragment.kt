package com.kadabengaran.rickalmanac.presentation.search

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kadabengaran.rickalmanac.R
import com.kadabengaran.rickalmanac.core.data.Resource
import com.kadabengaran.rickalmanac.core.domain.model.Character
import com.kadabengaran.rickalmanac.core.ui.CharacterAdapter
import com.kadabengaran.rickalmanac.databinding.FragmentSearchBinding
import com.kadabengaran.rickalmanac.presentation.detail.DetailCharacterActivity
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding

    private val characterAdapter = CharacterAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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

            observeData()
            setupView()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.search_bar)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        val tempQuery = searchViewModel.queryValue.value
        if (tempQuery != null && tempQuery.isNotEmpty()) searchView.setQuery(tempQuery, false)
        else binding?.grNoQuery?.visibility = View.VISIBLE

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    hideAll()
                    showLoading()
                    searchViewModel.searchUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) searchViewModel.setQuery(newText)
                return false
            }
        })

        searchView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(p0: View) {
                if (searchViewModel.queryValue.value.isNullOrEmpty()) {
                    hideAll()
                    searchViewModel.clearSearch()
                    binding?.grNoQuery?.visibility = View.VISIBLE
                }
            }

            override fun onViewAttachedToWindow(p0: View) {}
        })
    }

    private fun setupView() {
        characterAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailCharacterActivity::class.java)
            intent.putExtra(DetailCharacterActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        val tempQuery = searchViewModel.queryValue.value
        binding?.rvCharacterSearch?.visibility = View.GONE
        binding?.btnError?.setOnClickListener {
            if (tempQuery != null) searchViewModel.searchUser(tempQuery)
            showLoading()
            binding?.grError?.visibility = View.GONE
        }

        binding?.rvCharacterSearch?.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = characterAdapter
        }
    }

    private fun observeData() {
        searchViewModel.listSearchResult.observe(viewLifecycleOwner) { character ->
            if (character != null) {
                when (character) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        character.data?.let { setSearchData(it) }
                    }
                    is Resource.Error -> {
                        hideAll()
                        binding?.apply {
                            if (character.message == "404")
                                grNoSearchResult.visibility = View.VISIBLE
                            else {
                                grError.visibility = View.VISIBLE
                                tvError.text = character.message ?: getString(R.string.something_wrong)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setSearchData(characterList: List<Character>) {
        hideAll()
        if (characterList.isEmpty()) binding?.grNoSearchResult?.visibility = View.VISIBLE
        else {
            characterAdapter.listData = characterList
            binding?.rvCharacterSearch?.visibility = View.VISIBLE
        }
    }

    private fun hideAll() {
        binding?.apply {
            rvCharacterSearch.visibility = View.GONE
            progressBar.visibility = View.GONE
            grNoSearchResult.visibility = View.GONE
            grNoQuery.visibility = View.GONE
            grError.visibility = View.GONE
        }
    }

    private fun showLoading() { binding?.progressBar?.visibility = View.VISIBLE }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvCharacterSearch?.adapter = null
        _binding = null
    }
}
