package de.djetzen.yeartime.application.services

import de.djetzen.yeartime.application.port.`in`.ReadSavedDataUseCase
import de.djetzen.yeartime.application.port.out.LoadDataPort
import de.djetzen.yeartime.domain.models.Day
import java.time.LocalDate
import java.time.Year
import java.util.stream.Collectors

class ReadSavedDataService(private val loadDataPort: LoadDataPort) : ReadSavedDataUseCase {

    override fun readDayForUser(day: LocalDate, user: String): Day? {
        return loadDataPort.findDayForUser(day, user)
    }

    override fun readYearForUser(year: Year, user: String): List<Day> {
        val startDate = LocalDate.of(year.value, 1, 1);
        val endDate = LocalDate.of(year.value, 12, 31);
        val allDatesInYear = startDate.datesUntil(endDate)
        return allDatesInYear.map { d -> loadDataPort.findDayForUser(d, user) }.collect(Collectors.toList())
            .filterNotNull()
    }
}