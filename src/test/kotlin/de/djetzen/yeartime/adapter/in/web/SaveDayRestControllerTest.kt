package de.djetzen.yeartime.adapter.`in`.web

import de.djetzen.yeartime.application.port.`in`.CaptureDayUseCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@WebMvcTest(SaveDayRestController::class)
@AutoConfigureMockMvc
internal class SaveDayRestControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var captureDayUseCase: CaptureDayUseCase;

    @Test
    fun givenElementWithValidJsonIsConvertedAndSaved() {
        val createdDay = mockMvc.perform(
            MockMvcRequestBuilders.post("/save/2021-01-14/dummyUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"date\":\"2021-01-14\",\"user\":\"dummyUser\",\"hoursWithActivities\":[${
                        RestControllerTestUtils.createHoursJsonForTest(
                            "skiing"
                        )
                    }]}"
                )
        )
        .andExpect(MockMvcResultMatchers.status().isOk)
        .andReturn()

        assertThat(createdDay.response.contentAsString).isEqualTo("successfully created day with 2021-01-14 for user dummyUser")
    }

    @Test
    fun postingElementWithoutBodyReturnsInvalidContent() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/save/2021-01-14/dummyUser"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}