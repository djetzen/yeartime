package de.djetzen.yeartime.application.port.`in`

import de.djetzen.yeartime.domain.models.Day

interface CaptureDayUseCase {
    fun captureDay(day: Day)
}