package io.hhplus.tdd.point.infra

import io.hhplus.tdd.point.domain.UserPoint
import org.springframework.stereotype.Repository

@Repository
interface UserPointRepository {
    fun findById(id: Long):UserPoint
    fun save(id: Long, point: Long): UserPoint
}