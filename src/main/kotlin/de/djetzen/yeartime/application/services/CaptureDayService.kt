package de.djetzen.yeartime.application.services

import de.djetzen.yeartime.application.port.`in`.CaptureDayUseCase
import de.djetzen.yeartime.application.port.out.SaveDataPort
import de.djetzen.yeartime.domain.models.Day

class CaptureDayService(private val saveDataPort: SaveDataPort) : CaptureDayUseCase {
    override fun captureDay(day: Day) {
        saveDataPort.saveDay(day);
    }
}