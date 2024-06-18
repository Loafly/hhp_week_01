package io.hhplus.tdd.point.infra

import io.hhplus.tdd.point.domain.PointHistory
import io.hhplus.tdd.point.domain.TransactionType
import org.springframework.stereotype.Repository

@Repository
interface PointHistoryRepository {

    fun save(userId: Long, amount: Long, transactionType: TransactionType, updateMillis: Long): PointHistory
    fun findAllByUserId(userId: Long): List<PointHistory>

}