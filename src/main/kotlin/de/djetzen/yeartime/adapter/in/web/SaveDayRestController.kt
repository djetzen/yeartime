package de.djetzen.yeartime.adapter.`in`.web

import de.djetzen.yeartime.application.port.`in`.CaptureDayUseCase
import de.djetzen.yeartime.domain.models.Day
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class SaveDayRestController(val captureDayUseCase: CaptureDayUseCase) {

    @PostMapping("/save/{date}/{user}")
    fun saveDay(@PathVariable("date") date: String, @PathVariable("user") user: String): ResponseEntity<String> {
        val day = Day(LocalDate.parse(date), user, listOf())
        captureDayUseCase.captureDay(day)
        return ResponseEntity(HttpStatus.OK);
    }
}