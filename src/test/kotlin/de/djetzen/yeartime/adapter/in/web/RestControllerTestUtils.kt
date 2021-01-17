package de.djetzen.yeartime.adapter.`in`.web

import de.djetzen.yeartime.domain.models.Activity
import de.djetzen.yeartime.domain.models.Hour

class RestControllerTestUtils {
    companion object {
        fun createHoursListForTest(activity: String): List<Hour> {
            return MutableList(24) { index -> Hour(index.toString(), listOf(Activity(activity))) }
        }

        fun createHoursApiBeanListForTest(activity: String): List<HourApiBean> {
            return MutableList(24) { index -> HourApiBean(index.toString(), ActivityApiBean(activity)) }
        }

        fun createHoursJsonForTest(activity: String): String {
            var expectation = "";
            for (i in 0..23) {
                expectation += "{\"time\":\"$i\",\"firstActivity\":{\"activity\":\"$activity\"}},"
            }
            return expectation.removeSuffix(",");
        }

    }
}