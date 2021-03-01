package de.djetzen.yeartime

import de.djetzen.yeartime.adapter.`in`.web.RestControllerTestUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class YeartimeApplicationTests @Autowired constructor(
    val mockMvc: MockMvc
) {
    final val SIMPLE_DAY_LITERAL = "2021-01-14"

    val SIMPLE_DAY_INPUT = "{\"date\":\"" + SIMPLE_DAY_LITERAL + "\",\"user\":\"dummyUser\",\"hoursWithActivities\":[${
        RestControllerTestUtils.createHoursJsonForTest("skiing")
    }]}"
    val ANOTHER_DAY = "{\"date\":\"2021-01-15\",\"user\":\"dummyUser\",\"hoursWithActivities\":[${
        RestControllerTestUtils.createHoursJsonForTest("gaming")
    }]}"

    val SIMPLE_DAY_INPUT_OTHER_YEAR = "{\"date\":\"2020-01-14\",\"user\":\"dummyUser\",\"hoursWithActivities\":[${
        RestControllerTestUtils.createHoursJsonForTest("skiing")
    }]}"

    @Test
    @Transactional
    fun savingDayCanBeRetrievedAfterwards() {
        saveDay(SIMPLE_DAY_LITERAL, SIMPLE_DAY_INPUT)
        val retrievedDay = mockMvc.perform(MockMvcRequestBuilders.get("/$SIMPLE_DAY_LITERAL/dummyUser"))
                .andExpect(MockMvcResultMatchers.status().isOk).andReturn().response.contentAsString

        assertThat(retrievedDay).isEqualTo(
                "{\"date\":\"" + SIMPLE_DAY_LITERAL + "\",\"user\":\"dummyUser\",\"hoursWithActivities\":[${
                    RestControllerTestUtils.createHoursJsonForTest("skiing")
                }]}"
        );
    }

    @Test
    @Transactional
    fun savingDaysFromTwoDifferentYearsReturnsOnYearEndpointOnlyDayFromGivenYear() {
        saveDay(SIMPLE_DAY_LITERAL, SIMPLE_DAY_INPUT)
        saveDay("2021-01-15", ANOTHER_DAY)
        saveDay("2020-01-14", SIMPLE_DAY_INPUT_OTHER_YEAR)
        val retrievedDaysFromYear = mockMvc.perform(MockMvcRequestBuilders.get("/year/2021/dummyUser"))
                .andExpect(MockMvcResultMatchers.status().isOk).andReturn().response.contentAsString
        assertThat(retrievedDaysFromYear).isEqualTo(
                "[{\"date\":\"" + SIMPLE_DAY_LITERAL + "\",\"user\":\"dummyUser\",\"hoursWithActivities\":" +
                        "[${RestControllerTestUtils.createHoursJsonForTest("skiing")}]},{" +
                        "\"date\":\"2021-01-15\",\"user\":\"dummyUser\",\"hoursWithActivities\":" +
                        "[${RestControllerTestUtils.createHoursJsonForTest("gaming")}]" + "}]"
        );

    }

    @Test
    @Transactional
    fun savingDuplicateDayIsRejected() {
        saveDay(SIMPLE_DAY_LITERAL, SIMPLE_DAY_INPUT)
        mockMvc.perform(
                MockMvcRequestBuilders.post("/save/$SIMPLE_DAY_LITERAL/dummyUser").contentType(MediaType.APPLICATION_JSON).content(SIMPLE_DAY_INPUT)
        ).andExpect(MockMvcResultMatchers.status().isConflict).andReturn()

    }

    fun saveDay(day: String, content: String) {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/save/$day/dummyUser").contentType(MediaType.APPLICATION_JSON).content(content)
        ).andExpect(MockMvcResultMatchers.status().isOk).andReturn()
    }
}
