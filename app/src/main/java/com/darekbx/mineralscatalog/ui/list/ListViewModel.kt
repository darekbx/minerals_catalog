package com.darekbx.mineralscatalog.ui.list

import androidx.lifecycle.ViewModel
import com.darekbx.mineralscatalog.domain.FetchMineralsUseCase

class ListViewModel(private val fetchMineralsUseCase: FetchMineralsUseCase) : ViewModel() {

    fun minerals() = fetchMineralsUseCase()
}
