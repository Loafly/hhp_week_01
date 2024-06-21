package io.hhplus.tdd.point.domain

import io.hhplus.tdd.exception.NotEnoughPointsException
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
            val updatedUserPoint = updateUserPoint(id, totalPoint)
            pointHistoryService.save(id, amount, TransactionType.CHARGE, updatedUserPoint.updateMillis)
            return updatedUserPoint
        }
    }

    // 유저 포인트 사용
    fun useUserPoint(id: Long, amount: Long): UserPoint {
        synchronized(this) {
            val userPoint = getUserPointById(id)
            if (userPoint.point < amount) {
                throw NotEnoughPointsException("포인트가 부족합니다. 현재 포인트: ${userPoint.point}, 사용하려는 포인트: ${amount}")
            }
            val totalPoint = userPoint.point - amount
            val updatedUserPoint = updateUserPoint(userPoint.id, totalPoint)
            pointHistoryService.save(id, amount, TransactionType.USE, updatedUserPoint.updateMillis)
            return updatedUserPoint
        }
    }
}