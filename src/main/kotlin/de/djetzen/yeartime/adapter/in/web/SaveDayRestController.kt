package de.djetzen.yeartime.adapter.`in`.web

import de.djetzen.yeartime.application.port.`in`.CaptureDayUseCase
import de.djetzen.yeartime.domain.models.Day
import de.djetzen.yeartime.domain.models.Hour
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class SaveDayRestController(val captureDayUseCase: CaptureDayUseCase) {

    @PostMapping("/save/{date}/{user}")
    fun saveDay(
        @PathVariable("date") date: String,
        @PathVariable("user") user: String,
        @RequestBody dayApiBean: DayApiBean?
    ): ResponseEntity<String> {
        if (dayApiBean == null) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        val day = Day(LocalDate.parse(date), user, convertToHour(dayApiBean.hoursWithActivities))
        captureDayUseCase.captureDay(day)
        return ResponseEntity("successfully created day with ${day.date} for user ${day.user}", HttpStatus.OK);
    }

    private fun convertToHour(hourList: List<HourApiBean>): List<Hour> {
        return hourList.map { h -> Hour(h.time, h.firstActivity.activity) }.toList()
    }
}