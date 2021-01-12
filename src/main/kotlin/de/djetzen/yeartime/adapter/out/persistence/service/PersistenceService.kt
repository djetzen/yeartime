package de.djetzen.yeartime.adapter.out.persistence.service

import de.djetzen.yeartime.adapter.out.persistence.repository.ActivityRepository
import de.djetzen.yeartime.adapter.out.persistence.repository.DayRepository
import de.djetzen.yeartime.domain.ActivityEntity
import de.djetzen.yeartime.domain.DayEntity
import de.djetzen.yeartime.domain.HourEntity
import de.djetzen.yeartime.domain.models.Activity
import de.djetzen.yeartime.domain.models.Day
import de.djetzen.yeartime.domain.models.Hour
import java.time.LocalDate

class PersistenceService(val activityRepository: ActivityRepository, val dayRepository: DayRepository) {

    fun findActivityByName(name: String): List<Activity> {
        return activityRepository.findActivityByName(name).map { Activity(it.name) };
    }

    fun saveActivity(activity: Activity) {
        val activityEntity = ActivityEntity(activity.name)
        activityRepository.save(activityEntity);
    }

    fun findDayForUser(day: LocalDate, user: String): List<Day> {
        return dayRepository.findDayByDateOfDayAndUserName(day, user).map { dayEntity ->
            Day(
                dayEntity.dateOfDay,
                dayEntity.userName,
                dayEntity.hourEntities.map { hourEntity ->
                    Hour(
                        hourEntity.time,
                        hourEntity.activityEntities.map { Activity(it.name) })
                })
        };
    }

    fun saveDay(day: Day) {
        val dayEntitiy = DayEntity(
            null,
            day.date,
            day.user,
            day.hours.map { HourEntity(null, it.time, it.activities.map { ActivityEntity(it.name) }) })
        dayRepository.save(dayEntitiy);
    }
}