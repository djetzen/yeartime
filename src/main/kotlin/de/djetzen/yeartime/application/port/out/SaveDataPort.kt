package de.djetzen.yeartime.application.port.out

import de.djetzen.yeartime.domain.models.Day

interface SaveDataPort {
    fun saveDay(day: Day)
}