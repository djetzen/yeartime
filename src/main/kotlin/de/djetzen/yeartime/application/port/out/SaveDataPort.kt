package de.djetzen.yeartime.application.port.out

import de.djetzen.yeartime.domain.models.Activity
import de.djetzen.yeartime.domain.models.Day

interface SaveDataPort {
    fun saveActivity(activity: Activity)
    fun saveDay(day: Day)
}