package io.hhplus.tdd.point.infra

import io.hhplus.tdd.database.PointHistoryTable
import io.hhplus.tdd.point.domain.PointHistory
import io.hhplus.tdd.point.domain.TransactionType
import org.springframework.stereotype.Repository

@Repository
class PointHistoryRepositoryImpl(private val pointHistoryTable: PointHistoryTable): PointHistoryRepository {
    override fun save(userId: Long, amount: Long, transactionType: TransactionType, updateMillis: Long): PointHistory {
        return pointHistoryTable.insert(userId, amount, transactionType, updateMillis)
    }

    override fun findAllByUserId(userId: Long): List<PointHistory> {
        return pointHistoryTable.selectAllByUserId(userId)
    }
}