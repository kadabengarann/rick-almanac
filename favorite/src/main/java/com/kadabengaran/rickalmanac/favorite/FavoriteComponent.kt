package com.kadabengaran.rickalmanac.favorite

import android.content.Context
import com.kadabengaran.rickalmanac.di.FavoriteModuleDependencies
import com.kadabengaran.rickalmanac.favorite.presentation.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}
