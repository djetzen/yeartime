package de.djetzen.mediakeeper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MediakeeperApplication

fun main(args: Array<String>) {
	runApplication<MediakeeperApplication>(*args)
}
