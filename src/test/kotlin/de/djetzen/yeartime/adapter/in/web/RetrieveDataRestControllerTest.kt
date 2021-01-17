package de.djetzen.yeartime.adapter.`in`.web

import de.djetzen.yeartime.application.port.`in`.ReadSavedDataUseCase
import de.djetzen.yeartime.domain.models.Activity
import de.djetzen.yeartime.domain.models.Day
import de.djetzen.yeartime.domain.models.Hour
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

@WebMvcTest(RetrieveDataRestController::class)
@AutoConfigureMockMvc
internal class RetrieveDataRestControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var readSavedDataUseCase: ReadSavedDataUseCase

    @Test
    fun gettingDayForUserReturnsCompletelyMappedDay() {
        Mockito.`when`(readSavedDataUseCase.readDayForUser(LocalDate.of(2021, 1, 14), "dummyUser"))
            .thenReturn(Day(LocalDate.of(2021, 1, 14), "dummyUser", createHoursList("skiing")))
        
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/2021-01-14/dummyUser"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn().response.contentAsString

        assertThat(result).isEqualTo(
            "{\"date\":\"2021-01-14\",\"user\":\"dummyUser\",\"hoursWithActivities\":[${createHoursExpectation("skiing")}]}"
        );
    }

    fun createHoursList(activity: String): List<Hour> {
        return MutableList(24) { index -> Hour(index.toString(), listOf(Activity(activity))) }
    }

    fun createHoursExpectation(activity: String): String {
        var expectation = "";
        for (i in 0..23) {
            expectation += "{\"time\":\"$i\",\"firstActivity\":{\"activity\":\"$activity\"}},"
        }
        return expectation.removeSuffix(",");
    }
}