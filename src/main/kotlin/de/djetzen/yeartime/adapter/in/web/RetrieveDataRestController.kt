package de.djetzen.yeartime.adapter.`in`.web

import de.djetzen.yeartime.application.port.`in`.ReadSavedDataUseCase
import de.djetzen.yeartime.domain.models.Hour
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.Year

@RestController
class RetrieveDataRestController(val readSavedDataUseCase: ReadSavedDataUseCase) {

    @GetMapping("/{date}/{user}")
    fun getDayForUser(@PathVariable("date") date: String, @PathVariable("user") user: String): ResponseEntity<String> {
        val readDate = readSavedDataUseCase.readDayForUser(LocalDate.parse(date), user)
        if (readDate != null) {
            return ResponseEntity(
                Json.encodeToString(
                    DayApiBean(
                        readDate.date,
                        readDate.user,
                        convertToHourApiBean(readDate.hours).sortedBy { Integer.parseInt(it.time) })
                ),
                HttpStatus.OK
            )
        };
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping("/year/{year}/{user}")
    @ResponseBody
    fun getYearForUser(
        @PathVariable("year") year: Int,
        @PathVariable("user") user: String
    ): ResponseEntity<String> {
        val readDaysForYear = readSavedDataUseCase.readYearForUser(Year.of(year), user)
        val convertedApiBeans = readDaysForYear.map { it ->
            DayApiBean(
                it.date,
                it.user,
                convertToHourApiBean(it.hours).sortedBy { Integer.parseInt(it.time) })
        }
        return ResponseEntity(
            Json.encodeToString(convertedApiBeans),
            HttpStatus.OK
        );
    }

    private fun convertToHourApiBean(hourList: List<Hour>): List<HourApiBean> {
        return hourList.map { h -> HourApiBean(h.time, ActivityApiBean(h.activity)) }.toList()
    }
}