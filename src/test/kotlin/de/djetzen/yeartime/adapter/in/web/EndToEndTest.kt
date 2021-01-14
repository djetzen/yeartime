package de.djetzen.yeartime.adapter.`in`.web

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class EndToEndTest @Autowired constructor(
    val mockMvc: MockMvc
) {
    @Test
    fun saveDayWithNoDataCanBeRetrieved() {
        mockMvc.perform(post("/save/2021-01-14/dummyUser")).andExpect(status().isOk).andReturn()
        var result =
            mockMvc.perform(get("/2021-01-14/dummyUser")).andExpect(status().isOk).andReturn().response.contentAsString
        assertThat(result).isEqualTo("Day(date=2021-01-14, user=dummyUser, hours=[])");
    }

    @Test
    fun callingYearEndpointThrowsNotImplementedCode() {
        mockMvc.perform(get("/year/2021/dummyUser")).andExpect(status().isNotImplemented)
    }
}