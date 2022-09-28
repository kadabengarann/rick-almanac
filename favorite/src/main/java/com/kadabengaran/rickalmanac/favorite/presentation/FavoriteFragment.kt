package com.kadabengaran.rickalmanac.favorite.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kadabengaran.rickalmanac.core.ui.CharacterAdapter
import com.kadabengaran.rickalmanac.di.FavoriteModuleDependencies
import com.kadabengaran.rickalmanac.favorite.DaggerFavoriteComponent
import com.kadabengaran.rickalmanac.favorite.ViewModelFactory
import com.kadabengaran.rickalmanac.favorite.databinding.FragmentFavoriteBinding
import com.kadabengaran.rickalmanac.presentation.detail.DetailCharacterActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val characterAdapter = CharacterAdapter()
            characterAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailCharacterActivity::class.java)
                intent.putExtra(DetailCharacterActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            favoriteViewModel.favoriteCharacter.observe(viewLifecycleOwner) {
                if (it != null) {
                    characterAdapter.listData = it
                    binding?.grNoCharacter?.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
                }
            }

            with(binding?.rvCharacter) {
                this?.layoutManager = GridLayoutManager(context, 2)
                this?.setHasFixedSize(true)
                this?.adapter = characterAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvCharacter?.adapter = null
        _binding = null
    }
}