package de.djetzen.yeartime.application.port.`in`

import de.djetzen.yeartime.domain.models.Day
import java.time.LocalDate
import java.time.Year

interface ReadSavedDataUseCase {
    fun readDayForUser(day: LocalDate, user: String): Day?
    fun readYearForUser(year: Year, user: String): List<Day>
}