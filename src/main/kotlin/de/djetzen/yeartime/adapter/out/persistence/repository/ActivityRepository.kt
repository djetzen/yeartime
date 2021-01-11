package de.djetzen.yeartime.adapter.out.persistence.repository

import de.djetzen.yeartime.domain.ActivityEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository : CrudRepository<ActivityEntity, Long> {
    fun findActivityByName(name: String): List<ActivityEntity>;
}