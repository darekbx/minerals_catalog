package com.darekbx.mineralscatalog.di

import androidx.room.Room
import com.darekbx.mineralscatalog.domain.AddEntryUseCase
import com.darekbx.mineralscatalog.domain.DeleteEntryUseCase
import com.darekbx.mineralscatalog.domain.FetchMineralUseCase
import com.darekbx.mineralscatalog.domain.FetchMineralsUseCase
import com.darekbx.mineralscatalog.mappers.Mappers
import com.darekbx.mineralscatalog.repository.AppDatabase
import com.darekbx.mineralscatalog.ui.details.DetailsViewModel
import com.darekbx.mineralscatalog.ui.list.ListViewModel
import com.darekbx.mineralscatalog.ui.new.NewScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.Locale

val appModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()
    }
    single { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    single { get<AppDatabase>().mineralDao() }
    single { Mappers(get()) }
}

val viewModelModule = module {
    viewModel { NewScreenViewModel(get()) }
    viewModel { ListViewModel(get()) }
    viewModel { DetailsViewModel(get(), get()) }
}

val domainModule = module {
    factory { AddEntryUseCase(get()) }
    factory { DeleteEntryUseCase(get()) }
    factory { FetchMineralsUseCase(get(), get()) }
    factory { FetchMineralUseCase(get(), get()) }
}
