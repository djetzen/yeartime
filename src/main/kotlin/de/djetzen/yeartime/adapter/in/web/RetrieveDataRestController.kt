package de.djetzen.yeartime.adapter.`in`.web

import de.djetzen.yeartime.application.port.`in`.ReadSavedDataUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class RetrieveDataRestController(val readSavedDataUseCase: ReadSavedDataUseCase) {

    //TODO: only poc. not allowed to return domain model here
    @GetMapping("/{date}/{user}")
    @ResponseBody
    fun getDayForUser(
        @PathVariable("date") date: String,
        @PathVariable("user") user: String
    ): ResponseEntity<String> {
        var readDate = readSavedDataUseCase.readDayForUser(LocalDate.parse(date), user)
        return ResponseEntity(readDate.toString(), HttpStatus.OK);
    }
}