package de.djetzen.yeartime.application.port.out

import de.djetzen.yeartime.domain.models.Day
import java.time.LocalDate

interface LoadDataPort {
    fun findDayForUser(day: LocalDate, user: String): Day?
}