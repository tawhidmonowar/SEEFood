package org.onedroid.seefood.app.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.onedroid.seefood.app.utils.HttpClientFactory
import org.onedroid.seefood.data.network.RemoteRecipeDataSource
import org.onedroid.seefood.data.network.RemoteRecipeDataSourceImpl
import org.koin.core.module.dsl.viewModelOf
import org.onedroid.seefood.data.repository.MealRepositoryImpl
import org.onedroid.seefood.domain.MealRepository
import org.onedroid.seefood.presentation.detail.DetailViewModel
import org.onedroid.seefood.presentation.home.HomeViewModel

val AndroidKoinModules: Module
    get() = module {
        single { HttpClientFactory.create(get()) }
        single<HttpClientEngine> { OkHttp.create() }
        singleOf(::RemoteRecipeDataSourceImpl).bind<RemoteRecipeDataSource>()
        singleOf(::MealRepositoryImpl).bind<MealRepository>()
        viewModelOf(::HomeViewModel)
        viewModelOf(::DetailViewModel)
    }