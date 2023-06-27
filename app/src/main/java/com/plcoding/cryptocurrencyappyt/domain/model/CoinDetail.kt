package com.plcoding.cryptocurrencyappyt.domain.model

import com.plcoding.cryptocurrencyappyt.data.remote.dto.TeamMember

data class CoinDetail(
    val coinId: String,
    val name: String,
    val description: String,
    val isActive: Boolean,
    val isNew: Boolean,
    val rank: Int,
    val symbol: String,
    val tags: List<String>,
    val team: List<TeamMember>
)
