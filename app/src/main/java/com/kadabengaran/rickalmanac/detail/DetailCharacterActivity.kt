package com.kadabengaran.rickalmanac.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.kadabengaran.rickalmanac.R
import com.kadabengaran.rickalmanac.core.domain.model.Character
import com.kadabengaran.rickalmanac.databinding.ActivityDetailCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailCharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCharacterBinding
    private val detailCharacterViewModel: DetailCharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val detailCharacter = intent.getParcelableExtra<Character>(EXTRA_DATA)
        showDetailCharacter(detailCharacter)
    }

    private fun showDetailCharacter(detailCharacter: Character?) {
        detailCharacter?.let {
            supportActionBar?.title = detailCharacter.name

            Glide.with(this)
                .load(detailCharacter.image)
                .into(binding.ivCharacterImage)

            var isFavorite = detailCharacter.isFavorite
            setFavoriteState(isFavorite)
            binding.fabFavorite.setOnClickListener {
                isFavorite = !isFavorite
                detailCharacterViewModel.setFavoriteCharacter(detailCharacter, isFavorite)
                setFavoriteState(isFavorite)
            }
        }
    }

    private fun setFavoriteState(favorite: Boolean) {
        if (favorite) {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}
