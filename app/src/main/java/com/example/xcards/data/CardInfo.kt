package com.example.xcards.data

data class CardInfo(
    val cards: List<CardContentData> = listOf(),
    val info: CardData = CardData("", 0, 0)
)
