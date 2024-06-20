package io.hhplus.tdd.point.controller

import io.hhplus.tdd.point.domain.PointHistory
import io.hhplus.tdd.point.domain.TransactionType
import io.hhplus.tdd.point.domain.UserPoint

class PointDto {

    // UserPoint 관련 응답값을 책임지는 클래스
    data class UserPointResponse(
        val id: Long,
        val point: Long,
        val updateMillis: Long,
    ) {
        constructor(userPoint: UserPoint) : this(
            id = userPoint.id,
            point = userPoint.point,
            updateMillis = userPoint.updateMillis
        )
    }

    // UserPointHistory 관련 응답값을 책임지는 클래스
    data class UserPointHistoryResponse(
        val id: Long,
        val userId: Long,
        val type: TransactionType,
        val amount: Long,
        val timeMillis: Long,
    ) {
        constructor(pointHistory: PointHistory) : this(
            id = pointHistory.id,
            userId = pointHistory.userId,
            type = pointHistory.type,
            amount = pointHistory.amount,
            timeMillis = pointHistory.timeMillis
        )
    }
}