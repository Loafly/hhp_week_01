package io.hhplus.tdd.point.domain

import io.hhplus.tdd.exception.InvalidPointAmountException

// entity의 역할
data class UserPoint(
    val id: Long,
    val point: Long,
    val updateMillis: Long,
) {
    init {
        if (point < 0) {
            throw InvalidPointAmountException("포인트는 0 이하일 수 없습니다.")
        }
    }
}
