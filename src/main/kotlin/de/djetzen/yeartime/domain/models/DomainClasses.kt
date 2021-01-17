package de.djetzen.yeartime.domain.models

import java.time.LocalDate

data class Activity(
    val name: String
)

data class Day(
    val date: LocalDate,
    val user: String,
    val hours: List<Hour> = listOf()
)

data class Hour(
    val time: String,
    val activity: String
)