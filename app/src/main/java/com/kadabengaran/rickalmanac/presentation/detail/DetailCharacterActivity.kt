package com.kadabengaran.rickalmanac.presentation.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kadabengaran.rickalmanac.R
import com.kadabengaran.rickalmanac.core.data.Resource
import com.kadabengaran.rickalmanac.core.domain.model.Character
import com.kadabengaran.rickalmanac.databinding.ActivityDetailCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailCharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCharacterBinding
    private val detailCharacterViewModel: DetailCharacterViewModel by viewModels()

    private var character: Character? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val detailCharacter = intent.getIntExtra(EXTRA_DATA, 0)
        detailCharacter.let { if (character == null) detailCharacterViewModel.getDetailCharacter(it) }

        observeData()
        setupAction()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupAction() {
        binding.fabFavorite.setOnClickListener {
            character?.let { character ->
                val newFav = character.isFavorite.not()
                detailCharacterViewModel.setFavoriteCharacter(character, newFav)
            }
        }
    }

    private fun observeData() {
        detailCharacterViewModel.detailCharacter.observe(this) { character ->
            if (character != null) {
                when (character) {
                    is Resource.Loading -> {
                        binding.ivCharacterImage.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.ivCharacterImage.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        character.data?.let { showDetailCharacter(it) }
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()                    }
                }
            }
        }
    }

    private fun showDetailCharacter(detailCharacter: Character) {
            character = detailCharacter
            Glide.with(this)
                .load(detailCharacter.image)
                .into(binding.ivCharacterImage)

            binding.apply {
                content.tvDetailStatusValue.text = detailCharacter.status
                content.tvDetailSpeciesValue.text = detailCharacter.species
                content.tvDetailGenderValue.text = detailCharacter.gender
                content.tvDetailOriginValue.text = detailCharacter.origin
                content.tvDetailLocationValue.text = detailCharacter.location
                toolbarLayout.title = detailCharacter.name
            }
            setFavoriteState(detailCharacter.isFavorite)
    }

    private fun setFavoriteState(favorite: Boolean) =
        if (favorite) binding.fabFavorite.setImageResource(R.drawable.ic_favorite) else binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}
