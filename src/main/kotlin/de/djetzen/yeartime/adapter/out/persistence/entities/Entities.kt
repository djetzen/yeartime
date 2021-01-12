package de.djetzen.yeartime.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "day")
data class DayEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val dateOfDay: LocalDate,
    val userName: String,

    @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
    val hourEntities: List<HourEntity> = listOf()
)

@Entity
@Table(name = "activity")
data class ActivityEntity(
    @Id
    val name: String
)

@Entity
@Table(name = "hour")
data class HourEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val time: String,
    @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
    val activityEntities: List<ActivityEntity>
)
