package de.djetzen.yeartime.configuration

import de.djetzen.yeartime.adapter.out.persistence.repository.ActivityRepository
import de.djetzen.yeartime.adapter.out.persistence.repository.DayRepository
import de.djetzen.yeartime.adapter.out.persistence.service.PersistenceService
import de.djetzen.yeartime.application.port.`in`.CaptureDayUseCase
import de.djetzen.yeartime.application.port.`in`.ReadSavedDataUseCase
import de.djetzen.yeartime.application.services.CaptureDayService
import de.djetzen.yeartime.application.services.ReadSavedDataService
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

    @Bean
    fun createCaptureDayUseCase(persistenceService: PersistenceService): CaptureDayUseCase {
        return CaptureDayService(persistenceService);
    }

    @Bean
    fun createReadSavedDataUseCase(persistenceService: PersistenceService): ReadSavedDataUseCase {
        return ReadSavedDataService(persistenceService);
    }
}