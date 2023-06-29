package com.plcoding.cryptocurrencyappyt.resource.coin.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(private val getCoinsUseCase: GetCoinsUseCase): ViewModel(){
    private val _state = mutableStateOf(CoinListState())
    val state : State<CoinListState> = _state

    init {
        getCoins()
    }

    private fun getCoins(){
        getCoinsUseCase().onEach { resource ->
            when(resource){
                is Resource.Loading -> {
                    _state.value = CoinListState(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = CoinListState(coins = resource.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = CoinListState(
                        error = resource.message ?: "Une erreur s'est produite."
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}