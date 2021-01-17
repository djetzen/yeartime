package de.djetzen.yeartime.adapter.`in`.web

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate

@Serializable
data class DayApiBean(
    @Serializable(with = CustomLocalDateSerializer::class)
    val date: LocalDate,
    val user: String,
    val hoursWithActivities: List<HourApiBean> = listOf()
)

@Serializable
data class HourApiBean(
    val time: String,
    val firstActivity: ActivityApiBean
)

@Serializable
data class ActivityApiBean(
    val activity: String
)

@Serializer(forClass = LocalDate::class)
object CustomLocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val string = decoder.decodeString()
        return LocalDate.parse(string);
    }
}