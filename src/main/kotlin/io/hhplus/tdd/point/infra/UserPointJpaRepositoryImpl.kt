package io.hhplus.tdd.point.infra

import io.hhplus.tdd.database.UserPointTable
import io.hhplus.tdd.point.domain.UserPoint
import org.springframework.stereotype.Repository

@Repository
class UserPointRepositoryImpl(private val userPointTable: UserPointTable) : UserPointRepository {

    override fun findById(id: Long): UserPoint {
        return userPointTable.selectById(id)
    }

    override fun save(id: Long, point: Long): UserPoint {
        return userPointTable.insertOrUpdate(id, point)
    }
}