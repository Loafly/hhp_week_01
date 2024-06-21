package io.hhplus.tdd.point.domain

import io.hhplus.tdd.exception.InvalidPointAmountException

data class PointHistory(
    val id: Long,
    val userId: Long,
    val type: TransactionType,
    val amount: Long,
    val timeMillis: Long,
) {
    init {
        if (amount < 0) {
            throw InvalidPointAmountException("포인트는 0 이하일 수 없습니다.")
        }
    }
}

/**
 * 포인트 트랜잭션 종류
 * - CHARGE : 충전
 * - USE : 사용
 */
enum class TransactionType {
    CHARGE, USE
}