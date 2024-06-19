package dev.androidbroadcast.news.main

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

public val featuresNewsMainUiLogicKoinModule: Module = module {
    
    factoryOf(::GetAllArticlesUseCase)
    
    viewModelOf(::NewsMainViewModel)
}
