package de.djetzen.yeartime.adapter.`in`.web

import de.djetzen.yeartime.application.port.`in`.ReadSavedDataUseCase
import de.djetzen.yeartime.domain.models.Day
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
import java.time.Year

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
            .thenReturn(
                Day(
                    LocalDate.of(2021, 1, 14),
                    "dummyUser", RestControllerTestUtils.createHoursListForTest("skiing")
                )
            )

        val result = mockMvc.perform(MockMvcRequestBuilders.get("/2021-01-14/dummyUser"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn().response.contentAsString

        assertThat(result).isEqualTo(
            "{\"date\":\"2021-01-14\",\"user\":\"dummyUser\",\"hoursWithActivities\":[${
                RestControllerTestUtils.createHoursJsonForTest("skiing")
            }]}"
        );
    }

    @Test
    fun ifNoDayPresentReturn204() {
        mockMvc.perform(MockMvcRequestBuilders.get("/2021-01-14/dummyUser"))
            .andExpect(MockMvcResultMatchers.status().isNoContent).andReturn().response.contentAsString
    }

    @Test
    fun callingYearEndpointReturnsAllDaysInYear() {
        Mockito.`when`(readSavedDataUseCase.readYearForUser(Year.of(2021), "dummyUser")).thenReturn(
            listOf(
                Day(LocalDate.of(2021, 1, 14), "dummyUser", RestControllerTestUtils.createHoursListForTest("skiing")),
                Day(LocalDate.of(2021, 1, 15), "dummyUser", RestControllerTestUtils.createHoursListForTest("sleeping")),
                Day(LocalDate.of(2021, 1, 16), "dummyUser", RestControllerTestUtils.createHoursListForTest("gaming"))
            )
        )

        val responseFromYearEndpoint = mockMvc.perform(MockMvcRequestBuilders.get("/year/2021/dummyUser"))
            .andExpect(MockMvcResultMatchers.status().isOk).andReturn().response.contentAsString

        assertThat(responseFromYearEndpoint).isEqualTo(
            "[{\"date\":\"2021-01-14\",\"user\":\"dummyUser\",\"hoursWithActivities\":" +
                    "[${RestControllerTestUtils.createHoursJsonForTest("skiing")}]},{" +
                    "\"date\":\"2021-01-15\",\"user\":\"dummyUser\",\"hoursWithActivities\":" +
                    "[${RestControllerTestUtils.createHoursJsonForTest("sleeping")}]},{" +
                    "\"date\":\"2021-01-16\",\"user\":\"dummyUser\",\"hoursWithActivities\":" +
                    "[${RestControllerTestUtils.createHoursJsonForTest("gaming")}]" + "}]"
        );
    }


}