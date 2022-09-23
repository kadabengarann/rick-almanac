package com.kadabengaran.rickalmanac.favorite

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kadabengaran.rickalmanac.R
import com.kadabengaran.rickalmanac.core.ui.CharacterAdapter
import com.kadabengaran.rickalmanac.detail.DetailCharacterActivity
import com.kadabengaran.rickalmanac.di.MapsModuleDependencies
import com.kadabengaran.rickalmanac.favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

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
                    MapsModuleDependencies::class.java
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

            favoriteViewModel.favoriteCharacter.observe(viewLifecycleOwner) { dataCharacter ->
                characterAdapter.setData(dataCharacter)
                for (data in dataCharacter) {
                    Log.d("TAGAGAGAGA", "onViewCreated: ${data.name}")
                }
            }

            with(binding?.rvCharacter) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = characterAdapter
            }
        }
    }
}