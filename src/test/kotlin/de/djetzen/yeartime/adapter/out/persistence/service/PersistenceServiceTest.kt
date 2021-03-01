package de.djetzen.yeartime.adapter.out.persistence.service

import de.djetzen.yeartime.adapter.out.persistence.repository.DayRepository
import de.djetzen.yeartime.domain.models.Day
import de.djetzen.yeartime.domain.models.Hour
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate


private val SAMPLE_ACTIVITY = "skiing"

private val SAMPLE_USER = "Dominik"

@DataJpaTest
internal class PersistenceServiceTest(@Autowired
                                      val dayRepository: DayRepository) {

    lateinit var persistenceService: PersistenceService;

    @BeforeEach
    fun setup() {
        persistenceService = PersistenceService(dayRepository)
    }

    @Test
    fun savedDayEntityIsReadableAndHourAndActivityIsSavedAsWell() {
        val hour = Hour("12", SAMPLE_ACTIVITY)
        val day = Day(LocalDate.now(), SAMPLE_USER, listOf(hour));
        persistenceService.saveDay(day);
        val foundDayByName = persistenceService.findDayForUser(LocalDate.now(), SAMPLE_USER);

        assertThat(foundDayByName!!.date).isEqualTo(LocalDate.now())
        assertThat(foundDayByName.hours).hasSize(1)
        assertThat(foundDayByName.user).isEqualTo(SAMPLE_USER)

        assertThat(foundDayByName.hours).hasSize(1)
        assertThat(foundDayByName.hours[0].activity).isEqualTo(SAMPLE_ACTIVITY)

    }

}