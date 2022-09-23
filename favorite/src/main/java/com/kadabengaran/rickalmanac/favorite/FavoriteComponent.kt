package com.kadabengaran.rickalmanac.favorite

import android.content.Context
import com.kadabengaran.rickalmanac.di.MapsModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [MapsModuleDependencies::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: MapsModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}
