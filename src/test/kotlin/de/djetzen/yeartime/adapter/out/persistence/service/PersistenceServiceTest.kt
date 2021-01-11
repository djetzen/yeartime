package de.djetzen.yeartime.adapter.out.persistence.service

import de.djetzen.yeartime.domain.models.Activity
import de.djetzen.yeartime.domain.models.Day
import de.djetzen.yeartime.domain.models.Hour
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate


private val SAMPLE_ACTIVITY = "skiing"

private val SAMPLE_USER = "Dominik"

@SpringBootTest
internal class PersistenceServiceTest @Autowired constructor(
    val persistenceService: PersistenceService
) {

    @Test
    fun savedActivityEntityIsReadable() {
        val activity = Activity(SAMPLE_ACTIVITY)
        persistenceService.saveActivity(activity)
        var foundActivitiesByName = persistenceService.findActivityByName(SAMPLE_ACTIVITY)

        assertThat(foundActivitiesByName).hasSize(1);
        assertThat(foundActivitiesByName[0].name).isEqualTo(SAMPLE_ACTIVITY);
    }

    @Test
    fun savedDayEntityIsReadableAndHourAndActivityIsSavedAsWell() {
        val hour = Hour("12", listOf(Activity(SAMPLE_ACTIVITY)))
        val day = Day(LocalDate.now(), SAMPLE_USER, listOf(hour));
        persistenceService.saveDay(day);
        val foundDayByName = persistenceService.findDayForUser(LocalDate.now(), SAMPLE_USER);
        val foundActivityByName = persistenceService.findActivityByName(SAMPLE_ACTIVITY)

        assertThat(foundDayByName).hasSize(1);
        assertThat(foundDayByName[0].date).isEqualTo(LocalDate.now())
        assertThat(foundDayByName[0].hours).hasSize(1)
        assertThat(foundDayByName[0].user).isEqualTo(SAMPLE_USER)

        assertThat(foundActivityByName).hasSize(1)
        assertThat(foundActivityByName[0].name).isEqualTo(SAMPLE_ACTIVITY)
    }

}