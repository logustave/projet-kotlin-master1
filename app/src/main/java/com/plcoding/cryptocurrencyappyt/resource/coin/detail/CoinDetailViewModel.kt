package com.plcoding.cryptocurrencyappyt.resource.coin.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Constants
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _state = mutableStateOf(CoinDetailState())
    val state : State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAMETER_COIN_ID)?.let { coinId -> getCoin(coinId) }
    }

    private fun getCoin(coinId: String){
        getCoinUseCase(coinId).onEach { resource ->  {
            when(resource){
                is Resource.Loading -> {
                    _state.value = CoinDetailState(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = CoinDetailState(coin = resource.data)
                }

                is Resource.Error -> {
                    _state.value = CoinDetailState(
                        error = resource.message ?: "Une erreur s'est produite."
                    )
                }
            }
        } }.launchIn(viewModelScope)
    }
}