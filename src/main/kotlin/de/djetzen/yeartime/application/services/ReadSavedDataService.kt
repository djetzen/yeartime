package de.djetzen.yeartime.application.services

import de.djetzen.yeartime.application.port.`in`.ReadSavedDataUseCase
import de.djetzen.yeartime.application.port.out.LoadDataPort
import de.djetzen.yeartime.domain.models.Day
import java.time.LocalDate
import java.time.Year

class ReadSavedDataService(private val loadDataPort: LoadDataPort) : ReadSavedDataUseCase {

    override fun readDayForUser(day: LocalDate, user: String): Day {
        return loadDataPort.findDayForUser(day, user)
    }

    override fun readYearForUser(year: Year, user: String): List<Day> {
        return emptyList();
    }
}