package com.example.engaz.features.wallet.data.entities.get_balance

data class GetBalanceResponse(
    val `data`: Data,
    val message: String,
    val result: String,
    val status: Int
)