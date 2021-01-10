package de.djetzen.yeartime

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class YeartimeApplication

fun main(args: Array<String>) {
	runApplication<YeartimeApplication>(*args)
}
