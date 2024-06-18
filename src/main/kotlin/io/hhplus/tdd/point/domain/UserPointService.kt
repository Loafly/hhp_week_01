package io.hhplus.tdd.point.domain

import io.hhplus.tdd.point.infra.UserPointRepository
import org.springframework.stereotype.Service

@Service
class UserPointService(private val userPointRepository: UserPointRepository,
                       private val pointHistoryService: PointHistoryService) {

    // 유저 포인트 조회
    fun getUserPointById(id: Long): UserPoint {
        return userPointRepository.findById(id)
    }

    // 유저 포인트 업데이트
    private fun updateUserPoint(id: Long, point: Long): UserPoint {
        return userPointRepository.save(id, point)
    }

    // 유저 포인트 충전
    fun chargeUserPoint(id: Long, amount: Long): UserPoint {
        synchronized(this) {
            val userPoint = getUserPointById(id)
            val totalPoint = userPoint.point + amount
            val updateUserPoint = updateUserPoint(id, totalPoint)
            pointHistoryService.save(id, amount, TransactionType.CHARGE, updateUserPoint.updateMillis)
            return updateUserPoint
        }
    }
}