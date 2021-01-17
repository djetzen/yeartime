package de.djetzen.yeartime

import de.djetzen.yeartime.adapter.`in`.web.RestControllerTestUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class YeartimeApplicationTests @Autowired constructor(
	val mockMvc: MockMvc
) {
	val SIMPLE_DAY_INPUT = "{\"date\":\"2021-01-14\",\"user\":\"dummyUser\",\"hoursWithActivities\":[${
		RestControllerTestUtils.createHoursJsonForTest("skiing")
	}]}"

	@Test
	fun completeEndToEndTest() {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/save/2021-01-14/dummyUser").contentType(MediaType.APPLICATION_JSON)
				.content(SIMPLE_DAY_INPUT)
		)
			.andExpect(MockMvcResultMatchers.status().isOk).andReturn()

		val result =
			mockMvc.perform(MockMvcRequestBuilders.get("/2021-01-14/dummyUser"))
				.andExpect(MockMvcResultMatchers.status().isOk).andReturn().response.contentAsString

		assertThat(result).isEqualTo(
			"{\"date\":\"2021-01-14\",\"user\":\"dummyUser\",\"hoursWithActivities\":[${
				RestControllerTestUtils.createHoursJsonForTest("skiing")
			}]}"
		);
	}

	@Test
	fun contextLoads() {
	}

}
