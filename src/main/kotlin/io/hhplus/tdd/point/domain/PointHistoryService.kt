package io.hhplus.tdd.point.domain

import io.hhplus.tdd.point.infra.PointHistoryRepository
import org.springframework.stereotype.Service

@Service
class PointHistoryService(private val pointHistoryRepository: PointHistoryRepository) {

    fun save(userId: Long, amount: Long, transactionType: TransactionType, updateMillis: Long): PointHistory {
        return pointHistoryRepository.save(userId, amount, transactionType, updateMillis)
    }

    fun getAllByUserId(userId: Long): List<PointHistory> {
        return pointHistoryRepository.findAllByUserId(userId);
    }
}