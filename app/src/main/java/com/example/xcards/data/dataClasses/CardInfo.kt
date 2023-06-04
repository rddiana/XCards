package com.example.xcards.data.dataClasses

data class CardInfo(
    val cards: List<CardContentData> = listOf(),
    val info: CardData = CardData("", 0, "")
)
