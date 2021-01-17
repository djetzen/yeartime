package de.djetzen.yeartime.adapter.out.persistence.service

import de.djetzen.yeartime.domain.models.Day
import de.djetzen.yeartime.domain.models.Hour
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate


private val SAMPLE_ACTIVITY = "skiing"

private val SAMPLE_USER = "Dominik"

@SpringBootTest
@ActiveProfiles("test")
internal class PersistenceServiceTest @Autowired constructor(
    val persistenceService: PersistenceService
) {

    @Test
    fun savedDayEntityIsReadableAndHourAndActivityIsSavedAsWell() {
        val hour = Hour("12", SAMPLE_ACTIVITY)
        val day = Day(LocalDate.now(), SAMPLE_USER, listOf(hour));
        persistenceService.saveDay(day);
        val foundDayByName = persistenceService.findDayForUser(LocalDate.now(), SAMPLE_USER);

        assertThat(foundDayByName.date).isEqualTo(LocalDate.now())
        assertThat(foundDayByName.hours).hasSize(1)
        assertThat(foundDayByName.user).isEqualTo(SAMPLE_USER)

        assertThat(foundDayByName.hours).hasSize(1)
        assertThat(foundDayByName.hours[0].activity).isEqualTo(SAMPLE_ACTIVITY)

    }

}