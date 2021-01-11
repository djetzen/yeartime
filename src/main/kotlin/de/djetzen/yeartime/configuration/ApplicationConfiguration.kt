package de.djetzen.yeartime.configuration

import de.djetzen.yeartime.adapter.out.persistence.repository.ActivityRepository
import de.djetzen.yeartime.adapter.out.persistence.repository.DayRepository
import de.djetzen.yeartime.adapter.out.persistence.service.PersistenceService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {
    @Bean
    fun createPersistenceService(
        activityRepository: ActivityRepository,
        dayRepository: DayRepository
    ): PersistenceService {
        return PersistenceService(activityRepository, dayRepository);
    }
}