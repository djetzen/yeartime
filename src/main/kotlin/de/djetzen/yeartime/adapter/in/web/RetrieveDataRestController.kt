package de.djetzen.yeartime.adapter.`in`.web

import de.djetzen.yeartime.application.port.`in`.ReadSavedDataUseCase
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
    @ResponseBody
    fun getDayForUser(
        @PathVariable("date") date: String,
        @PathVariable("user") user: String
    ): ResponseEntity<String> {
        var readDate = readSavedDataUseCase.readDayForUser(LocalDate.parse(date), user)
        return ResponseEntity(DayApiBean(readDate.date, readDate.user).toString(), HttpStatus.OK);
    }

    @GetMapping("/year/{year}/{user}")
    @ResponseBody
    fun getYearForUser(
        @PathVariable("year") year: Int,
        @PathVariable("user") user: String
    ): ResponseEntity<String> {
        var readDate = readSavedDataUseCase.readYearForUser(Year.of(year), user)
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }
}