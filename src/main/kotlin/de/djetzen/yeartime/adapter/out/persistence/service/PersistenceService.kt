package de.djetzen.yeartime.adapter.out.persistence.service

import de.djetzen.yeartime.adapter.out.persistence.repository.DayRepository
import de.djetzen.yeartime.application.port.out.LoadDataPort
import de.djetzen.yeartime.application.port.out.SaveDataPort
import de.djetzen.yeartime.domain.DayEntity
import de.djetzen.yeartime.domain.HourEntity
import de.djetzen.yeartime.domain.models.Day
import de.djetzen.yeartime.domain.models.Hour
import java.time.LocalDate

class PersistenceService(val dayRepository: DayRepository) :
    SaveDataPort, LoadDataPort {

    override fun findDayForUser(day: LocalDate, user: String): Day? {

        val dayEntity = dayRepository.findDayByDateOfDayAndUserName(day, user) ?: return null
        return Day(
            dayEntity.dateOfDay,
            dayEntity.userName,
            dayEntity.hourEntities.map { hourEntity ->
                Hour(
                    hourEntity.time,
                    hourEntity.activity
                )
            }
        )
    }

    override fun saveDay(day: Day) {
        if (dayRepository.findDayByDateOfDayAndUserName(day.date, day.user) != null) {
            throw KonfliktInStoreException()
        }
        val dayEntity = DayEntity(
                null,
                day.date,
                day.user,
                day.hours.map { HourEntity(null, it.time, it.activity) }.toSet()
        )
        dayRepository.save(dayEntity);
    }
}